export $(xargs < .env)
docker run --rm -it --user root --privileged \
--network mgs_mgs \
-v mgs_mysql-data:/var/lib/mysql \
-v /data/backup:/xtrabackup_backupfiles \
perconalab/percona-xtrabackup xtrabackup --prepare
