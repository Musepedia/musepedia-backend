[![Checkstyle](https://github.com/AbstractMGS/musepedia-backend/actions/workflows/checkstyle.yaml/badge.svg?branch=dev)](https://github.com/AbstractMGS/musepedia-backend/actions/workflows/checkstyle.yaml)


# MGS

本仓库包括MGS后端各部分，具体可查看各模块的README

## MavenModule说明

### 模块说明
- 本仓库包含项目的后端部分，采用了MavenModule的形式，包括:
  - mgsbackend-common: 通用模块，包括后端共用的基础类，工具类等 
  - mgsbackend-model: 导览部分与后台共用模型
  - mgsbackend-core: 导览部分后端模块
  - mgsbackend-admin: 后台管理模块
  - ~~mgsbackend-label: 标注服务(未启用)~~
  
### 打包注意

- 需要首先运行maven -> mgsbackend(root) -> Lifecycle -> install 将common,model等模块安装到本地maven仓库，否则其他模块单独打包时会**找不到依赖**
- common, model模块发生变动时，单独打包前最好先clean后重新install这些模块

## 开发说明

- 需要IDEA插件：lombok，CheckStyle
- 使用IDEA第一次打开项目时会自动下载maven所有依赖
- 点击**mgsbackend-admin/edu**/src/main/java/com.mimiter.mgs.admin/App.java main()函数旁边的<span style="color:green">▶</span>启动项目

### 风格检查Checkstyle

- Checkstyle参考了google的风格，并进行了部分调整，仍有很多不完善的地方，之后也会逐步修改。
- Checkstyle的IDEA配置在Tools->Checkstyle，配置方法可参考[博客](https://blog.csdn.net/weixin_46565024/article/details/125798094)。
  - checkstyle.xml地址为 https://static.musepedia.cn/common/checkstyle.xml
  - checkstyle-suppressions.xml地址为 https://static.musepedia.cn/common/checkstyle-suppressions.xml
- 提交之前先自行通过checkstyle检查，ci/cd也配有checkstyle的检查。
- 因为仍是完善中的Checkstyle，也没有强制Checkstyle pass才能提交。不合理的地方可以通过@SuppressWarning("checkstyle:xxx")来忽略，并向我反馈，进行后续修改。