# node.js 安装配置

   1. node.js 安装
      https://www.runoob.com/nodejs/nodejs-install-setup.html

## npm 配置

### 更改全局安装路径

1. 要在你需要存放模块的文件夹里建两个文件夹我是在node目录下建了两个文件夹分别叫node_global和node_cache。
2. 修改npm文件夹下的npmrc文件，打开修改里面的内容，原来的内容删掉，写入

   ```conf
   prefix=D:\programing\Resource\npm\node_global
   cache=D:\programing\Resource\npm\npm-cache
   ```

3. 更改环境变量

   在用户环境或系统环境的path下添加一个路径指向配置中的 `prefix`
   `D:\programing\Resource\npm\node_global`

### 相关命令

1. 获取 npm 安装路径
   `npm config get prefix`
2. 修改全局安装路径
   `npm config set prefix *`

### 常用安装

1. CNPM 安装
   使用淘宝镜像
   `npm install -g cnpm --registry=https://registry.npm.taobao.org`
