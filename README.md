# MGS

### QA-RS-Based-Museum-Guide-System
- 本仓库包含项目的后端

### Notices
- Spring Boot端口: 80
- gRPC端口: 5555
- MySQL端口: 3306

### Usage
- 编译grpc类 maven: mgsbackend-core->protobuf->compile, compile-custom
- GET请求`localhost:80/api/qa?question=xxx`，在数据库中匹配合适的文本，实现Java与Python的通信（传输question+text），并接受来自Python调用模型后的结果，同时能够返回若干推荐问题
对于问题`银杏的寿命有多长`
```json
{
  "status": 1,
  "answer": "3000年以上",
  "recommendQuestions": [
    "银杏的种子叫什么",
    "银杏的种子有什么疗效"
  ]
}
```

对于无法回答的问题
```json
{
  "status": 0,
  "answer": "暂时无法回答这个问题",
  "recommendQuestions": [
    "榕树的原产地在哪",
    "榕树有多高"
  ]
}
```

### Update Logs
- 2021/10/23 支持QA（调用Python模型）和RS（伪推荐）