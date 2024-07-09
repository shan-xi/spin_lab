#!/bin/bash

# Source directory
ADMIN_SOURCE_DIR="$HOME/workspace_btse/repo/bp-admin"

# Destination directory
DEST_DIR="$HOME/workspace_btse/tool/apache-tomcat-8.5.100/webapps"

# Run Gradle build
echo "Running Gradle build..."
cd "$ADMIN_SOURCE_DIR"
gradle pay10-pg-ui:clean pay10-pg-ui:build -x test
if [ $? -ne 0 ]; then
  echo "Gradle build failed. Exiting."
  exit 1
fi
echo "Gradle build completed successfully."

# List of files to copy
declare -a files=(
  "$ADMIN_SOURCE_DIR/pay10-pg-ui/build/libs/pgui.war"
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

