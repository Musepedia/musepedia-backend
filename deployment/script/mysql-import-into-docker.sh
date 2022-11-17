# 在项目根目录下/mgs运行脚本来读取.env文件
# bash ./deployment/script/mysql-import-into-docker.sh
export $(xargs < .env)
docker cp ./dump.sql mysql:/root/dump.sql
docker exec -t mysql sh -c "mysql -uroot -p${MYSQL_PASSWORD} mgs < /root/dump.sql"
