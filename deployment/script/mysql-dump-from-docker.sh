# 在项目根目录下/mgs运行脚本来读取.env文件
# bash ./deployment/script/mysql-dump-from-docker.sh
export $(xargs < .env)
docker exec -t mysql sh -c "mysqldump -uroot -p${MYSQL_PASSWORD} mgs > /root/dump.sql"
docker cp mysql:/root/dump.sql ./dump.sql
