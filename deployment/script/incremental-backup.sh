export $(xargs < .env)
INCRE_DIR=inc3
docker run --rm -it --user root --privileged=true \
--network mgs_mgs \
-v mgs_mysql-data:/var/lib/mysql \
-v /data/backup:/xtrabackup_backupfiles \
perconalab/percona-xtrabackup xtrabackup --backup \
--target-dir=/xtrabackup_backupfiles/${INCRE_DIR} \
--incremental-basedir=/xtrabackup_backupfiles \
--host=mysql \
--user=${BACKUP_USERNAME} \
--password=${BACKUP_PASSWORD}
