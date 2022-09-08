# MGSBackend-core

### QA-RS-Based-Museum-Guide-System
- 本仓库包含MGS导览部分后端核心代码
- **Version Beta 0.2.6**

### Notices
- Spring Boot端口: 80
- gRPC端口: 5555
- MySQL端口: 3306
- Redis端口：6379
- <span style="color: sandybrown"> v0.2+ maven打包方式已更改，第三方依赖在target/lib下<br>第一次上传请上传lib目录以及mgsbackend-core.jar，之后如果依赖没有变更只需上传mgsbackend-core.jar</span>

### Environment

- jdk1.8+
- redis
- mysql8.0


### Usage
- 编译grpc类 maven: mgsbackend(root)->lifecycle->package
  
如果使用本地数据库：

- 本地运行请确保application.yaml中的mysql用户名密码与本地相符，并且建立数据库mgs
- 初次运行请确保application.yaml中spring.datasource.initialization-mode = always用于初始化数据，之后运行可以设为never


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
- 2022/01/15 0.2.4 支持随机获取展品/展区信息（用户偏好）供问卷使用，支持更新用户已有的偏好设置，支持展品别名提问，修复用户登录状态错误
- 2022/03/13 0.2.5 支持Redis缓存，重构QAController，重构tbl_recommend_question表，提供推荐算法后端API，支持可视化回答（需要Python端 v1.0.12+支持）
- 2022/06/25 0.2.6 支持多博物馆切换，现在QA抽取的文本仅限于当前选定的博物馆；支持记录用户的提问，可以对每个问题进行反馈