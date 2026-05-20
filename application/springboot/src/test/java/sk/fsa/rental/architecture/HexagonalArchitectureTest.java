package sk.fsa.rental.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class HexagonalArchitectureTest {

    private static final com.tngtech.archunit.core.domain.JavaClasses CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("sk.fsa.rental");

    @Test
    void domainMustNotDependOnFrameworksOrAdapters() {
        noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "org.springframework..",
                        "jakarta.persistence..",
                        "org.hibernate..",
                        "sk.fsa.rental.controller..",
                        "sk.fsa.rental.mapper..",
                        "sk.fsa.rental.security..",
                        "sk.fsa.rental.jpa..",
                        "sk.fsa.rental.rest..",
                        "sk.fsa.rental")
                .because("domain business rules must stay independent from frameworks and adapters")
                .check(CLASSES);
    }

    @Test
    void inboundLayerMustNotDependOnOutboundAdapterOrRuntime() {
        noClasses()
                .that().resideInAnyPackage(
                        "sk.fsa.rental.controller..",
                        "sk.fsa.rental.mapper..",
                        "sk.fsa.rental.security..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "sk.fsa.rental.jpa..",
                        "sk.fsa.rental")
                .because("REST inbound code must delegate through domain ports instead of reaching adapters or runtime")
                .check(CLASSES);
    }

    @Test
    void outboundLayerMustNotDependOnInboundApiContractOrRuntime() {
        noClasses()
                .that().resideInAPackage("sk.fsa.rental.jpa..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "sk.fsa.rental.controller..",
                        "sk.fsa.rental.mapper..",
                        "sk.fsa.rental.security..",
                        "sk.fsa.rental.rest..",
                        "sk.fsa.rental.domain.service..",
                        "sk.fsa.rental")
                .because("outbound adapters must implement domain ports and remain persistence/infrastructure details")
                .check(CLASSES);
    }

    @Test
    void controllersMustBeExplicitRestEntryPoints() {
        classes()
                .that().resideInAPackage("sk.fsa.rental.controller..")
                .and().areTopLevelClasses()
                .should().beAnnotatedWith(RestController.class)
                .orShould().beAnnotatedWith(RestControllerAdvice.class)
                .because("controller package should only contain REST entry points and REST error handling")
                .check(CLASSES);
    }

    @Test
    void repositoryPortsAndAdaptersMustFollowHexagonalNamingAndRoles() {
        classes()
                .that().resideInAPackage("sk.fsa.rental.domain.repository..")
                .and().haveSimpleNameEndingWith("Repository")
                .should().beInterfaces()
                .because("domain repositories are ports")
                .check(CLASSES);

        classes()
                .that().resideInAPackage("sk.fsa.rental.jpa..")
                .and().haveSimpleNameEndingWith("RepositoryAdapter")
                .should().beAnnotatedWith(Repository.class)
                .andShould(implementDomainRepositoryPort())
                .because("JPA repository adapters must be Spring repositories implementing domain repository ports")
                .check(CLASSES);
    }

    @Test
    void springDataRepositoriesMustStayInternalToOutboundModule() {
        classes()
                .that().resideInAPackage("sk.fsa.rental.jpa..")
                .and().haveSimpleNameEndingWith("SpringDataRepository")
                .should().beInterfaces()
                .andShould().notBePublic()
                .because("Spring Data repositories are internal implementation details of the outbound adapter")
                .check(CLASSES);
    }

    @Test
    void runtimeRootShouldOnlyContainApplicationAndConfigurationClasses() {
        classes()
                .that().resideInAPackage("sk.fsa.rental")
                .should().beAnnotatedWith(org.springframework.context.annotation.Configuration.class)
                .orShould().beAnnotatedWith(SpringBootApplication.class)
                .because("runtime root package should only contain bootstrap and bean configuration classes")
                .check(CLASSES);
    }

    private static ArchCondition<JavaClass> implementDomainRepositoryPort() {
        return new ArchCondition<>("implement a domain repository port") {
            @Override
            public void check(JavaClass item, ConditionEvents events) {
                Set<String> repositoryInterfaces = item.getAllRawInterfaces().stream()
                        .map(JavaClass::getFullName)
                        .filter(name -> name.startsWith("sk.fsa.rental.domain.repository."))
                        .filter(name -> name.endsWith("Repository"))
                        .collect(java.util.stream.Collectors.toSet());

                boolean satisfied = !repositoryInterfaces.isEmpty();
                String message = item.getName() + (satisfied
                        ? " implements domain port " + repositoryInterfaces
                        : " does not implement any domain repository port");
                events.add(new SimpleConditionEvent(item, satisfied, message));
            }
        };
    }
}
