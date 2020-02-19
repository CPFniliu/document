
ECMAScript标准的缺陷
   - 没有模块系统
   - 标准库较少
   - 没有标准接口
   - 缺乏管理系统

CommonJs规范
   - 主要是为了弥补JavaScript没有标准的缺陷
   - 为Js指定了一个美好的愿景,希望Js能够在任何地方运行
   - 对模块的定义
    - 模块引用
    - 模块定义
    - 模块标识


模块化
   - require("jspath"); // node中require引入模块
   - 每一个js文件就是一个模块
   - 在Node中, 每一个js文件的js代码默认在函数中
     可以通过expors来向外部暴漏变量或方法
     exports.x = 3;
   - 模块分为核心模块和
在node中有一个全局对象是global, 它的作用和网页中的window类似,
在node执行时, js代码实际上是封装在一个函数中
function (exports, require, module, __filename, __dirname)

argument | 含义
- | -
exports | 该对象用来将变量和函数暴漏在外部, 是module的一个属性.exports = module.exports
require | 函数, 用来引入外部模块
module | 代表当前木块本身, exports其一个属性
__filename| 当前模块完整路径
__dirname |当前模块所在文件夹路径



## 包简介
包实际上就是一个压缩文件, 符合规范的包解压后应该有类似如下目录

文件结构 | 简介 | 是否必须
-|-
package.json | 描述文件 | 必须
bin | 可执行文件 | 非必需
lib | js代码 | 非必需
doc | 文档 | 非必需
test | 单元测试 | 非必需

## npm

npm命令 | 作用
-|-
npm -v |查看npm的版本
npm version | 查看所有模块的版本
npm search 包名 | 搜索包
npm remove/r 包名 | 移除包
npm install/i 包名 | 安装包
npm install/i 包名 --save | 安装包并添加到依赖中
npm install 包名 -g | 全局安装安装包(全局安装的包一般都是一些工具)
npm install | 下载当前项目的依赖包
npm install -g npm | 升级npm到最新版本


## Buffer(缓冲区)
- Buffer的结构和数组很像, 操作的方法也和数组类似.
- 与数组的区别
   数组无法存储二进制文件
   Buffer 存储的都是二进制数据
   Buffer 大小一旦确定, 即不能修改
   BUffer每一位存储范围为 00-FF
   eg : var buf0 = Buffer.alloc(10);
   buf0[10] = 67; // 失效
   buf0[2] = 510 // 1FE, buf0中无法存储超过255的数据,过长则截取,结果buf0[2]存储的为FE


常用命令
   Buffer.from(str) | 将str字符串转为Buffer数据类型
   Buffer.alloc(10) | 分配给10个长度的Buffer对象
   Buffer.allocUnSafe(10) | 仅仅分配10个长度的Buffer对象


## 文件系统
   - 文件系统简单来说就是通过NOde来操作系统中的文件

   var fs = require("fs");
   fs模块中所有操作都有同步和异步两种方式
   同步方法会阻塞运行,当执行完毕之前
   异步方法不会阻塞运行, 结果通过回调函数获取

手动打开文件操作
   1. 打开文件
   2. 像文件中写入内容
   3. 保存并关闭文件

- 同步文件写入
- 异步文件写入
- 简单文件写入
- 流式文件写入
- 简单文件读取
- 流式文件读取
