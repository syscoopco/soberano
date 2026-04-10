#!/bin/sh
psql --host 127.0.0.1 --port 5432 --dbname soberano --username <trusted role in pg_hba.conf> < /home/user/soberano_scripts/close_closed_order_process_runs.sql
