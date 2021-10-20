# MGS

### QA-RS-Based-Museum-Guide-System
- 本仓库包含项目的后端

### Notices
- Spring Boot端口: 80
- gRPC端口: 5555
- MySQL端口: 3306

### Usage
- 编译grpc类 maven: mgsbackend-core->protobuf->compile, compile-custom
- GET请求`localhost:80/api/qa?question=xxx&text=xxx`，实现Java与Python的通信，并接受来自Python调用模型后的结果