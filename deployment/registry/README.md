### 部署docker registry server

首先创建如下包含registry用户名密码的.env文件
```properties
REGISTRY_USERNAME=<username>
REGISTRY_PASSWORD=<password>
```

然后执行如下命令
```shell
# 生成帐号密码
bash setup-htpasswd.sh
docker-compose up -d
```

