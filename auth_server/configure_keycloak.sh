#!/bin/sh

# Log in to Keycloak admin CLI and perform configurations
/opt/jboss/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080/auth --realm master --user admin --password admin

# Create a new realm
/opt/jboss/keycloak/bin/kcadm.sh create realms -s realm=myrealm -s enabled=true

# Create a user in the realm
/opt/jboss/keycloak/bin/kcadm.sh create users -r myrealm -s username=myuser -s enabled=true -s email=myuser@example.com

# Get the user ID of the created user
USER_ID=$( /opt/jboss/keycloak/bin/kcadm.sh get users -r myrealm --fields id -q username=myuser --output json | jq -r '.[0].id' )

# Add roles to the user
/opt/jboss/keycloak/bin/kcadm.sh add-roles --u $USER_ID --r myrealm --rolename myrole1 --rolename myrole2

echo "Keycloak configured"

# Mark initialization as completed
touch /opt/jboss/initialized.flag