# MGS

### QA-RS-Based-Museum-Guide-System
- 本仓库包含项目的后端

### Notices
- Spring Boot端口: 80
- gRPC端口: 5555
- MySQL端口: 3306

### Usage
- 编译grpc类 maven: mgsbackend-core->protobuf->compile, compile-custom
- GET请求`localhost:80/api/qa?question=xxx`，在数据库中匹配合适的文本，实现Java与Python的通信（传输question+text），并接受来自Python调用模型后的结果
- (测试用)GET请求`localhost:80/api/select/text?label=xxx`，实现根据给定的label查询对应的文章（Demo期间，label与text一一对应）