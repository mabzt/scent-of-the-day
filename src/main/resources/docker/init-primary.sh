#!/bin/bash
set -e
psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d "$POSTGRES_DB" <<-EOSQL
    CREATE ROLE repl_user WITH REPLICATION LOGIN PASSWORD 'replication_password';
EOSQL
echo "host replication repl_user all scram-sha-256" >> "$PGDATA/pg_hba.conf"