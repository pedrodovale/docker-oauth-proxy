#!/bin/bash

INITIALIZED_FLAG="/opt/jboss/initialized.flag"

# Start Keycloak
/opt/jboss/keycloak/bin/standalone.sh -b 0.0.0.0 &

# Wait for Keycloak to start (you can customize the delay if needed)
sleep 20

# Check if Keycloak has started successfully
if [ ! -e "$configured_file" ]; then if pgrep -f "/opt/jboss/keycloak" > /dev/null; then
  echo "Keycloak has started. Proceeding with initialization..."

  # Run initialization only after Keycloak has started (if needed)

  if [ ! -f "$INITIALIZED_FLAG" ]; then
    /opt/jboss/configure_keycloak.sh
    echo "Initialization completed."
    # Mark initialization as completed
    touch "$INITIALIZED_FLAG"
  fi

else
  echo "Keycloak failed to start. Initialization aborted."
fi

# Keep the container running
tail -f /dev/null
