#!/bin/bash

# Source directory
SOURCE_DIR="/Users/spin.liao/workspace-artemis-main/admin/artemis-main"

# Destination directory
DEST_DIR="/Users/spin.liao/workspace/bppay/apache-tomcat-8.5.100/webapps"

# List of files to copy
declare -a files=(
  "$SOURCE_DIR/pay10-crm/build/libs/crm.war"
  "$SOURCE_DIR/pay10-crm-ws/build/libs/crmws.war"
  "$SOURCE_DIR/pay10-crypto/build/libs/crypto.war"
  "$SOURCE_DIR/pay10-pg-ui/build/libs/pgui.war"
  "$SOURCE_DIR/pay10-pg-ws/build/libs/pgws.war"
  "$SOURCE_DIR/pay10-notification-email/build/libs/email.war"
  "$SOURCE_DIR/pay10-crm/build/libs/crm-merchant.war"
)

# Loop through files and copy each one
for file in "${files[@]}"; do
  if [ -f "$file" ]; then
    cp "$file" "$DEST_DIR"
    echo "Copied $file to $DEST_DIR"
  else
    echo "File not found: $file"
  fi
done

echo "All files copied."

