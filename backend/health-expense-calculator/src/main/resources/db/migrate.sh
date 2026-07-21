#!/bin/sh
set -e

FLYWAY_FLAGS="-url=$DB_URL -table=$SCHEMA_TABLE -user=$FLYWAY_USER -password=$FLYWAY_PASSWORD -locations=$FLYWAY_LOCATIONS"

flyway $FLYWAY_FLAGS repair
flyway $FLYWAY_FLAGS -baselineOnMigrate=true -outOfOrder=true migrate