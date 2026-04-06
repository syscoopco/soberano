#! /bin/bash
# postgresql database backup script (cron-job)
# author: nadie

pg_dump --no-password --username=tcptool_backup --dbname=soberano --clean --no-tablespaces --no-unlogged-table-data --column-inserts --inserts --quote-all-identifiers --schema "soberano" --format tar  --schema "metamodel" --file=/opt/tomcat/webapps/tcptool/records/backups/soberanodb.backup