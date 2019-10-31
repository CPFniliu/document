# yarn 安装配置

## 安装

在官网上下载可执行文件

## 配置

1. 改变 yarn 全局安装位置
   - `yarn config  set global-folder "D:\programing\Resource\Yarn\Data\global"`
   - 然后你会在你的用户目录找到 `.yarnrc` 的文件，打开它，找到 `global-folder` ，改为 `--global-folder`

2. 改变 yarn 缓存位置
   - `yarn config set cache-folder "D:\Software\yarn\cache"`

3. 在我们使用 全局安装 包的时候，会在 “D:\Software\yarn\global” 下 生成 node_modules\.bin 目录
   我们需要将 D:\Software\yarn\global\node_modules\.bin 整个目录 添加到系统环境变量中去，否则通过yarn 添加的全局包 在cmd 中是找不到的。

> 检查当前yarn 的 bin的 位置
> `yarn global bin`
> 检查当前 yarn 的 全局安装位置
> `yarn global dir`
