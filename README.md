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

- 使用IDEA第一次打开项目时会自动下载maven所有依赖
- 点击**mgsbackend-admin**/src/main/java/cn.abstractmgs.admin/App.java main()函数旁边的<span style="color:green">▶</span>启动项目
