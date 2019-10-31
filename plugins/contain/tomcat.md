#

## Tomcat 部署项目的三种方法

1. 第一种方法比较普通，但是我们需要将编译好的项目重新 copy 到 webapps 目录下，启动项目

2. 第二种方法直接在 server.xml 文件中配置

   从 tomcat5.0版本开始后，server.xml 文件作为 tomcat 启动的主要配置文件，一旦 tomcat 启动后，便不会再读取这个文件，因此无法再 tomcat 服务启动后发布 web 项目.
   打开tomcat下`conf/server.xml`，在`<Host></Host>`标签之间输入项目配置信息

   ```xml
      <!-- 
         path : 浏览器访问时的路径名
         docBase : web项目的WebRoot所在的路径，注意是WebRoot的路径，不是项目的路径。其实也就是编译后的项目
         reloadble : 设定项目有改动时，tomcat是否重新加载该项目 
      -->
      <Context path="/WebProject" docBase="D:/WebProject" reloadable="true" />
   ```

3. 第三种方法是最好的，每个项目分开配置，tomcat 将以`\conf\Catalina\localhost` 目录下的 xml 文件的文件名作为 web 应用的上下文路径，而不再理会 `<Context>` 中配置的 path 路径，因此在配置的时候，可以不写 path。

   1. 进入到 `apache-tomcat\conf\Catalina\localhost` 目录，新建一个 `项目名.xml` 文件.
   2. 在 新建的 xml 文件中，增加下面配置语句（和上面的是一样的,但是不需要 path 配置，加上也没什么用）.

      ```xml
      <Context docBase="D:/WebProject" reloadable="true" />
      ```

通常我们使用第三种方法
