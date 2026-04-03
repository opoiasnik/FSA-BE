FROM quay.io/keycloak/keycloak:26.1.0

ADD realms/realm-rental.json /opt/keycloak/data/import/realm-rental.json

ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

EXPOSE 8080

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--import-realm"]
