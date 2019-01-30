#

## Tomcat 部署项目的三种方法

1. 第一种方法比较普通，但是我们需要将编译好的项目重新 copy 到 webapps 目录下，多出了两步操作

2. 第二种方法直接在 server.xml 文件中配置，但是从 tomcat5.0版本开始后，server.xml 文件作为 tomcat 启动的主要配置文件，一旦 tomcat 启动后，便不会再读取这个文件，因此无法再 tomcat 服务启动后发布 web 项目

3. 第三种方法是最好的，每个项目分开配置，tomcat 将以`\conf\Catalina\localhost` 目录下的 xml 文件的文件名作为 web 应用的上下文路径，而不再理会 `<Context>` 中配置的 path 路径，因此在配置的时候，可以不写 path。

通常我们使用第三种方法