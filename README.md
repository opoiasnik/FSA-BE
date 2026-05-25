# Property Rental Application

This application will allow users to search for apartments, houses or rooms available for rent or sale. Property owners will be able to publish property listings with descriptions, photos, structured address information, price details and property features.

Users will be able to browse available properties, filter them by city, price, property type and additional features, view detailed information about each listing, save listings to favourites, contact owners through conversations and request property viewings.

Property owners will be able to manage their listings by creating, editing, activating, deactivating or deleting property offers. They will also be able to upload photos, process viewing requests, manage communication with interested users and view owner dashboard statistics.

The goal of the application is to simplify the process of finding and offering accommodation through a centralized online platform.

# Zber poziadaviek

- **RQ01** System umozni pouzivatelovi zaregistrovat sa do systemu pomocou pouzivatelskeho mena, e-mailu a hesla.
- **RQ02** System umozni pouzivatelovi prihlasit sa do systemu.
- **RQ03** System bude rozlisovat roly pouzivatelov (`USER`, `OWNER`).
- **RQ04** System umozni pouzivatelovi vyhladavat nehnutelnosti dostupne na prenajom alebo predaj.
- **RQ05** System umozni pouzivatelovi filtrovat nehnutelnosti podla mesta, typu ponuky, ceny, typu nehnutelnosti a vlastnosti.
- **RQ06** System umozni pouzivatelovi zoradovat vysledky podla ceny, rozlohy alebo datumu pridania.
- **RQ07** System umozni pouzivatelovi zobrazit detail vybranej nehnutelnosti.
- **RQ08** System zobrazi detail nehnutelnosti vratane popisu, fotografii, adresy, ceny, vlastnosti a kontaktu na vlastnika.
- **RQ09** System umozni zobrazit polohu nehnutelnosti na mape, ak ma nehnutelnost suradnice.
- **RQ10** System bude evidovat adresu nehnutelnosti ako strukturovany udaj (ulica, mesto, PSC, krajina, okres, region, suradnice).
- **RQ11** System bude overovat adresu nehnutelnosti a doplnat suradnice pomocou geokodovacej sluzby.
- **RQ12** System bude evidovat cenu nehnutelnosti ako hodnotu a menu.
- **RQ13** System bude evidovat vlastnosti nehnutelnosti, napriklad typ, rozlohu, pocet izieb, poschodie, rok vystavby, energeticku triedu, zariadenie, parkovanie, balkon, vytah a povolenie domacich zvierat.
- **RQ14** System umozni pouzivatelovi ulozit inzerat medzi oblubene.
- **RQ15** System umozni pouzivatelovi odstranit inzerat zo zoznamu oblubenych.
- **RQ16** System umozni pouzivatelovi kontaktovat vlastnika nehnutelnosti cez konverzaciu.
- **RQ17** System umozni pouzivatelom odosielat spravy v konverzacii.
- **RQ18** System umozni pouzivatelovi poziadat o obhliadku nehnutelnosti.
- **RQ19** System umozni vlastnikovi schvalit alebo zamietnut ziadost o obhliadku.
- **RQ20** System umozni pouzivatelovi zrusit vlastnu ziadost o obhliadku.
- **RQ21** System bude evidovat stav ziadosti o obhliadku (`PENDING`, `APPROVED`, `REJECTED`, `CANCELLED`).
- **RQ22** System umozni vlastnikovi vytvarat nove inzeraty nehnutelnosti.
- **RQ23** System umozni vlastnikovi upravovat existujuce inzeraty.
- **RQ24** System umozni vlastnikovi aktivovat alebo deaktivovat vlastny inzerat.
- **RQ25** System umozni vlastnikovi odstranit inzerat, ktory uz nie je aktualny.
- **RQ26** System bude evidovat stav inzeratu (`ACTIVE`, `INACTIVE`, `DELETED`).
- **RQ27** System zobrazi beznemu pouzivatelovi iba aktivne inzeraty.
- **RQ28** System umozni vlastnikovi pridat k inzeratu viacero fotografii.
- **RQ29** System umozni pouzivatelovi upravit osobne udaje v profile.
- **RQ30** System umozni pouzivatelovi nahrat profilovu fotografiu.
- **RQ31** System umozni pouzivatelovi poziadat o overenie e-mailovej adresy.
- **RQ32** System umozni pouzivatelovi potvrdit e-mailovu adresu pomocou overovacieho kodu.
- **RQ33** System umozni pouzivatelovi nastavit e-mailove notifikacie pre spravy a obhliadky.
- **RQ34** System bude posielat e-mailove notifikacie pri novych spravach a zmenach obhliadok podla preferencii pouzivatela.
- **RQ35** System zobrazi vlastnikovi dashboard so zoznamom jeho inzeratov, ziadostami o obhliadku a statistikami.
- **RQ36** System zobrazi vlastnikovi statistiky aktivnych inzeratov, ulozeni medzi oblubene, cakajucich obhliadok a poctu zobrazeni.
- **RQ37** System umozni vlastnikovi exportovat zoznam jeho inzeratov do CSV suboru.

# Slovnik pojmov

| Pojem | Anglicky nazov | Definicia |
|-------|----------------|-----------|
| **Pouzivatel** | User | Osoba, ktora pouziva system na vyhladavanie nehnutelnosti, ukladanie oblubenych inzeratov, komunikaciu s vlastnikmi alebo ziadosti o obhliadku. |
| **Vlastnik** | Owner | Pouzivatel s opravnenim vytvarat, upravovat, aktivovat, deaktivovat a odstranovat vlastne inzeraty. |
| **Nehnutelnost** | Property | Objekt urceny na prenajom alebo predaj, napriklad byt, dom alebo izba. |
| **Inzerat** | Listing | Ponuka nehnutelnosti zverejnena v systeme s detailnymi informaciami. |
| **Prenajom** | Rental | Forma ponuky, pri ktorej je nehnutelnost dostupna na docasne uzivanie za poplatok. |
| **Predaj** | Sale | Forma ponuky, pri ktorej je nehnutelnost urcena na odkupenie. |
| **Adresa** | Address | Strukturovane umiestnenie nehnutelnosti obsahujuci ulicu, mesto, PSC, krajinu a volitelne suradnice. |
| **Geokodovanie** | Geocoding | Proces overenia adresy a ziskania geografickych suradnic. |
| **Cena** | Price | Financna hodnota nehnutelnosti evidovana ako suma a mena. |
| **Typ nehnutelnosti** | Property Type | Kategoria nehnutelnosti, napriklad byt, dom alebo izba. |
| **Vlastnosti nehnutelnosti** | Property Features | Subor charakteristik nehnutelnosti, napriklad rozloha, pocet izieb, poschodie, zariadenie a parkovanie. |
| **Fotografia** | Photo | Obrazok priradeny k inzeratu alebo profilu pouzivatela. |
| **Oblubene** | Favorites | Zoznam inzeratov, ktore si pouzivatel ulozil na neskorsie zobrazenie. |
| **Konverzacia** | Conversation | Komunikacne vlakno medzi pouzivatelom a vlastnikom tykajuce sa konkretneho inzeratu. |
| **Sprava** | Message | Jednotliva textova sprava odoslana v ramci konverzacie. |
| **Ziadost o obhliadku** | Viewing Request | Poziadavka pouzivatela na obhliadku konkretnej nehnutelnosti v navrhnutom termine. |
| **Stav ziadosti o obhliadku** | Viewing Status | Informacia o tom, ci je ziadost cakajuca, schvalena, zamietnuta alebo zrusena. |
| **Profil** | Profile | Osobne udaje pouzivatela, avatar, kontaktne udaje a nastavenia notifikacii. |
| **Overenie e-mailu** | Email Verification | Proces potvrdenia e-mailovej adresy pomocou overovacieho kodu. |
| **Notifikacia** | Notification | Informovanie pouzivatela o dolezitej udalosti, napriklad novej sprave alebo zmene obhliadky. |
| **Dashboard vlastnika** | Owner Dashboard | Prehlad vlastnikovych inzeratov, obhliadok a statistik. |
| **OpenAPI** | OpenAPI | Formalny popis REST API, z ktoreho sa generuju rozhrania a DTO triedy. |
| **Hexagonalna architektura** | Hexagonal Architecture | Architektonicky styl, ktory oddeluje domenu od vstupnych a vystupnych adapterov. |

# Pripady pouzitia

- **UC-01** Registracia pouzivatela
- **UC-02** Prihlasenie pouzivatela
- **UC-03** Vyhladavanie a filtrovanie nehnutelnosti
- **UC-04** Zobrazenie detailu nehnutelnosti
- **UC-05** Ulozenie inzeratu medzi oblubene
- **UC-06** Kontaktovanie vlastnika nehnutelnosti
- **UC-07** Odoslanie spravy v konverzacii
- **UC-08** Vytvorenie noveho inzeratu
- **UC-09** Uprava existujuceho inzeratu
- **UC-10** Odstranenie inzeratu
- **UC-11** Aktivacia alebo deaktivacia inzeratu
- **UC-12** Pridanie fotografii k inzeratu
- **UC-13** Vytvorenie ziadosti o obhliadku
- **UC-14** Spracovanie ziadosti o obhliadku
- **UC-15** Zrusenie ziadosti o obhliadku
- **UC-16** Uprava profilu pouzivatela
- **UC-17** Nahratie profilovej fotografie
- **UC-18** Overenie e-mailovej adresy
- **UC-19** Nastavenie e-mailovych notifikacii
- **UC-20** Zobrazenie dashboardu vlastnika
- **UC-21** Export inzeratov do CSV suboru

## UC-03 Vyhladavanie a filtrovanie nehnutelnosti

**Ucel**  
Vyhladat dostupne nehnutelnosti urcene na prenajom alebo predaj.

**Pouzivatel**  
Pouzivatel

**Vstupne podmienky**  
Pouzivatel je prihlaseny. System obsahuje aspon jeden aktivny inzerat.

**Vystup**  
Pouzivatel vidi zoznam nehnutelnosti, ktore zodpovedaju zvolenym filtrom.

**Postup**

1. Pouzivatel otvori sekciu nehnutelnosti.
2. System zobrazi dostupne aktivne inzeraty.
3. Pouzivatel zada mesto, cenu, typ ponuky alebo dalsie filtre.
4. Pouzivatel potvrdi vyhladavanie.
5. System spracuje filtre, zoradenie a strankovanie.
6. System zobrazi vysledky vyhladavania.

**Alternativny scenar**

3a. Pouzivatel nezada ziadny filter.  
System zobrazi vsetky aktivne inzeraty.

6a. System nenajde ziadne zodpovedajuce nehnutelnosti.  
System zobrazi informaciu, ze neboli najdene ziadne vysledky.

## UC-06 Kontaktovanie vlastnika nehnutelnosti

**Ucel**  
Vytvorit alebo otvorit konverzaciu s vlastnikom vybranej nehnutelnosti.

**Pouzivatel**  
Pouzivatel

**Vstupne podmienky**  
Pouzivatel je prihlaseny. Inzerat existuje a nepatri prihlasenemu pouzivatelovi.

**Vystup**  
Pouzivatel je presmerovany do konverzacie s vlastnikom.

**Postup**

1. Pouzivatel otvori detail vybranej nehnutelnosti.
2. System zobrazi detail inzeratu a moznost kontaktovat vlastnika.
3. Pouzivatel zvoli moznost kontaktovat vlastnika.
4. System vytvori alebo najde existujucu konverzaciu.
5. System presmeruje pouzivatela do sekcie sprav.

**Alternativny scenar**

3a. Pouzivatel nie je prihlaseny.  
System vyzve pouzivatela na prihlasenie.

4a. Pouzivatel sa pokusi kontaktovat vlastnika vlastneho inzeratu.  
System akciu nepovoli.

## UC-08 Vytvorenie noveho inzeratu

**Ucel**  
Vytvorit novy inzerat nehnutelnosti urceny na prenajom alebo predaj.

**Pouzivatel**  
Vlastnik

**Vstupne podmienky**  
Vlastnik je prihlaseny do systemu.

**Vystup**  
V systeme pribudne novy inzerat nehnutelnosti.

**Postup**

1. Vlastnik otvori formular na vytvorenie inzeratu.
2. Vlastnik zada nazov a popis inzeratu.
3. Vlastnik zada adresu nehnutelnosti.
4. System overi adresu a doplni suradnice pomocou geokodovania.
5. Vlastnik zada cenu a menu.
6. Vlastnik zada vlastnosti nehnutelnosti.
7. Vlastnik prida fotografie.
8. Vlastnik potvrdi vytvorenie inzeratu.
9. System overi vyplnenie povinnych udajov.
10. System ulozi novy inzerat do databazy.
11. System zobrazi potvrdenie o uspesnom vytvoreni.

**Alternativny scenar**

4a. Adresu sa nepodari overit.  
System zobrazi chybove hlasenie pri adrese.

7a. Vlastnik neprida ziadnu fotografiu.  
System zobrazi chybove hlasenie a inzerat nepublikuje.

9a. Niektory povinny udaj nie je vyplneny.  
System zobrazi chybove hlasenie a neulozi inzerat.

## UC-09 Uprava existujuceho inzeratu

**Ucel**  
Upravit udaje existujuceho inzeratu.

**Pouzivatel**  
Vlastnik

**Vstupne podmienky**  
Vlastnik je prihlaseny do systemu. Inzerat existuje a patri prihlasenemu vlastnikovi.

**Vystup**  
Udaje inzeratu su v systeme aktualizovane.

**Postup**

1. Vlastnik otvori dashboard.
2. Vlastnik vyberie konkretny inzerat.
3. Vlastnik zvoli moznost upravit inzerat.
4. System zobrazi formular s aktualnymi udajmi.
5. Vlastnik upravi pozadovane udaje.
6. Vlastnik potvrdi zmeny.
7. System overi spravnost udajov.
8. System ulozi zmeny do databazy.
9. System zobrazi potvrdenie o uspesnej uprave.

**Alternativny scenar**

7a. Niektory povinny udaj nie je vyplneny spravne.  
System zobrazi chybove hlasenie a zmeny neulozi.

## UC-13 Vytvorenie ziadosti o obhliadku

**Ucel**  
Vytvorit ziadost o obhliadku vybranej nehnutelnosti.

**Pouzivatel**  
Pouzivatel

**Vstupne podmienky**  
Pouzivatel je prihlaseny. Vybrany inzerat existuje, je aktivny a nepatri prihlasenemu pouzivatelovi.

**Vystup**  
V systeme pribudne nova ziadost o obhliadku so stavom `PENDING`.

**Postup**

1. Pouzivatel otvori detail vybranej nehnutelnosti.
2. System zobrazi moznost poziadat o obhliadku.
3. Pouzivatel zvoli moznost poziadat o obhliadku.
4. System zobrazi formular ziadosti o obhliadku.
5. Pouzivatel zada navrhovany termin.
6. Pouzivatel moze pridat poznamku.
7. Pouzivatel odosle ziadost.
8. System overi vyplnenie povinnych udajov.
9. System ulozi ziadost so stavom `PENDING`.
10. System spristupni ziadost vlastnikovi nehnutelnosti.

**Alternativny scenar**

5a. Pouzivatel nezada termin obhliadky.  
System ziadost neodosle.

8a. Pouzivatel sa pokusi poziadat o obhliadku vlastneho inzeratu.  
System akciu nepovoli.

## UC-14 Spracovanie ziadosti o obhliadku

**Ucel**  
Schvalit alebo zamietnut ziadost o obhliadku.

**Pouzivatel**  
Vlastnik

**Vstupne podmienky**  
Vlastnik je prihlaseny. Ziadost sa tyka jeho inzeratu a je v stave `PENDING`.

**Vystup**  
Ziadost ma stav `APPROVED` alebo `REJECTED`.

**Postup**

1. Vlastnik otvori zoznam ziadosti o obhliadku.
2. System zobrazi ziadosti tykajuce sa jeho inzeratov.
3. Vlastnik vyberie ziadost.
4. Vlastnik zvoli schvalenie alebo zamietnutie.
5. System overi, ze prihlaseny pouzivatel je vlastnikom inzeratu.
6. System zmeni stav ziadosti.
7. System ulozi aktualizovanu ziadost.

## UC-16 Uprava profilu pouzivatela

**Ucel**  
Aktualizovat osobne a kontaktne udaje pouzivatela.

**Pouzivatel**  
Pouzivatel, Vlastnik

**Vstupne podmienky**  
Pouzivatel je prihlaseny.

**Vystup**  
Profil pouzivatela je aktualizovany.

**Postup**

1. Pouzivatel otvori profil.
2. Pouzivatel upravi meno, priezvisko, e-mail, telefon alebo bio.
3. Pouzivatel ulozi zmeny.
4. System validuje udaje.
5. System ulozi profil.
6. System zobrazi aktualizovane udaje.

## UC-18 Overenie e-mailovej adresy

**Ucel**  
Overit e-mailovu adresu pouzivatela.

**Pouzivatel**  
Pouzivatel, Vlastnik

**Vstupne podmienky**  
Pouzivatel je prihlaseny a e-mailova adresa este nie je overena.

**Vystup**  
E-mailova adresa je oznacena ako overena.

**Postup**

1. Pouzivatel otvori profil.
2. Pouzivatel poziada o overenie e-mailu.
3. System odosle overovaci kod na e-mail.
4. Pouzivatel zada kod do formulara.
5. System overi kod.
6. System oznaci e-mailovu adresu ako overenu.

## UC-20 Zobrazenie dashboardu vlastnika

**Ucel**  
Zobrazit vlastnikovi prehlad jeho inzeratov, ziadosti o obhliadku a statistik.

**Pouzivatel**  
Vlastnik

**Vstupne podmienky**  
Vlastnik je prihlaseny.

**Vystup**  
Vlastnik vidi dashboard so svojimi datami.

**Postup**

1. Vlastnik otvori dashboard.
2. System nacita vlastnikove inzeraty.
3. System nacita ziadosti o obhliadku.
4. System nacita statistiky.
5. System zobrazi prehlad vlastnikovi.

# Technicka realizacia backendu

Backend je rozdeleny na moduly:

| Modul | Zodpovednost |
|-------|--------------|
| `application/domain` | Domenove objekty, sluzby, fasady, repository porty, factories, predicates a domenove testy. |
| `application/api-spec` | OpenAPI kontrakt a generovane API/DTO triedy. |
| `application/inbound-controller-rest` | REST controllery, MapStruct mappers, security a exception handling. |
| `application/outbound-repository-jpa` | JPA adaptery, Spring Data repository, ORM mapping a SMTP adapter. |
| `application/outbound-geocoding-rest` | REST adapter pre Nominatim geocoding. |
| `application/springboot` | Spring Boot aplikacia, konfiguracia beanov, Liquibase changelogy a ArchUnit testy. |

# OpenAPI

OpenAPI kontrakt sa nachadza v subore:

```text
application/api-spec/src/main/resources/openapi/rental-api.yaml
```

# UML diagramy

PlantUML zdrojove kody diagramov su v priecinku:

```text
docs/diagrams
```

| Subor | Obsah |
|-------|-------|
| `backend-hexagonal-architecture.puml` | Moduly a zavislosti hexagonalnej architektury backendu. |
| `backend-domain-model.puml` | Domenovy model a vztahy medzi hlavnymi objektmi. |
| `backend-use-cases.puml` | Use-case diagram funkcii systemu. |
| `backend-viewing-request-sequence.puml` | Sekvencny diagram vytvorenia ziadosti o obhliadku. |

# Spustenie

## Lokalne zavislosti

```sh
docker compose up -d
```

## Backend aplikacia

```sh
mvn spring-boot:run -pl application/springboot -am
```

## Testy

```sh
mvn test
```

# Testovanie

System je overeny pomocou:

- domenovych testov (`ListingTest`, `UserTest`, `ViewingRequestTest`),
- testov domenovych sluzieb (`ListingServiceTest`, `ViewingRequestServiceTest`),
- testu factory (`ListingFactoryTest`),
- testov predicate pravidiel (`ListingPredicateTest`),
- testu Nominatim geocoding adaptera,
- ArchUnit testu hexagonalnej architektury.
