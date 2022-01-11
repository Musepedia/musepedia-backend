# MGS

### QA-RS-Based-Museum-Guide-System
- 本仓库包含项目的后端
- **Version Beta 0.2.3**

### Notices
- Spring Boot端口: 80
- gRPC端口: 5555
- MySQL端口: 3306
- Redis端口：6379
- <span style="color: sandybrown"> v0.2+ maven打包方式已更改，第三方依赖在target/lib下<br>第一次上传请上传lib目录以及mgsbackend-core.jar，之后如果依赖没有变更只需上传mgsbackend-core.jar</span>

### Usage(mgsbackend-core)
- 编译grpc类 maven: mgsbackend(root)->lifecycle->compile
- 本地运行请确保application.yaml中的mysql用户名密码与本地相符，并且建立数据库mgs，
  初次运行请确保application.yaml中spring.datasource.initialization-mode = always用于初始化数据，之后运行可以设为never
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
- GET请求`localhost:80/api/exhibit?id=xxx`请求展品信息，包括展品标签、展品介绍和相关资料链接
```json
{
  "status": 200,
  "message": "ok",
  "data": {
    "label": "银杏",
    "description": "银杏，落叶乔木，寿命可达3000年以上 ...",
    "url": ""
  }
}
```
- GET请求`localhost:80/api/exhibit/preference`随机从每个展区获取至多N个展品（N固定，现在N=4）
```json
{
  "status": 200,
  "message": "ok",
  "data": [
    {
      "label": "打碗花",
      "exhibitionHall": {
        "createTime": null,
        "updateTime": null,
        "id": null,
        "name": "植物",
        "description": "这是植物展区的介绍 Botany",
        "museumId": null
      },
      "figureUrl": null,
      "hot": false
    }
  ]
}
```

### Bug Reports
- 目前为了实现label的分词，每次在分词前都会动态获取数据库中所有的label并添加至自定义词典中用以分词，这个方法可能会影响性能，后续更新中可以改为永久加入词典
- 随机获取展品时使用了`ORDER BY RAND()`，可能会随着数据规模扩大而影响性能

### Update Logs
- 2021/10/23 支持QA（调用Python模型）和RS（伪推荐）
- 2021/11/03 修复部分推荐的问题无法被回答的情况，现在仅少部分问题无法回答
- 2021/11/30 数据库整理，级联查询效率问题待讨论
- 2021/12/07 0.2.1 支持微信登录
- 2021/12/08 0.2.2 支持label-text一对多抽取，Python端（v1.0.7+）兼容原先的一对一抽取方法，因此getText()方法已停止使用
- 2021/12/15 0.2.3 支持展示展品信息
- 2022/01/11 0.2.4 支持随机获取展品/展区信息（用户偏好）供问卷使用（todo: 偏好信息更新）