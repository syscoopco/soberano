#!/data/data/com.termux/files/usr/bin/sh

/data/data/com.termux/files/usr/opt/apacheds/bin/apacheds.sh default start

/data/data/com.termux/files/usr/opt/tomcat11/bin/startup.sh

pg_ctl -D /data/data/com.termux/files/home/postgresql_data start

#END OF syscoop-startup.sh
