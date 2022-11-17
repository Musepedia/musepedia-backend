export $(xargs < .env)
docker exec -it mysql sh -c "mysql -uroot -p${MYSQL_PASSWORD} mgs -e \
\" \
CREATE USER '${BACKUP_USERNAME}'@'%' IDENTIFIED WITH mysql_native_password BY '${BACKUP_PASSWORD}'; \
GRANT RELOAD, LOCK TABLES, REPLICATION CLIENT, BACKUP_ADMIN, PROCESS ON, SELECT *.* TO '${BACKUP_USERNAME}'@'%'; \
FLUSH PRIVILEGES; \
\" \
"
