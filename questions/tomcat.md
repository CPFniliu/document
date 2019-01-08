十六个Tomcat常见面试题 你都知道答案吗?
0条评论 2017-09-01 17:57    it168网站 原创 　作者: 谢涛　编辑: 谢涛
　　【IT168 编译】由于Tomcat技术先进、性能稳定，而且免费，因此深受Java 爱好者的喜爱并得到了部分软件开发商的认可，是目前比较流行的Web 应用服务器。以下是一些比较常见的Tomcat面试题：

十六个Tomcat常见面试题 你知道答案吗

　　1)解释什么是Jasper?

　　Jasper是Tomcat的JSP引擎

　　它解析JSP文件，将它们编译成JAVA代码作为servlet

　　在运行时，Jasper允许自动检测JSP文件的更改并重新编译它们

　　2)请说明select * from tab的输出结果是什么?

　　显示数据库中的默认表

　　3)请解释如何配置Tomcat来使用IIS和NTLM ?

　　必须遵循isapi_redirector.dll的标准指令

　　配置IIS使用“集成windows验证”

　　确保在服务器.xml中您已经禁用了tomcat身份验证

　　<Connector port = “8009” enableLooksup = “false” redirect port = “8443” protocol = “AJP/1.3” tomcatAuthentication = “false” />

　　4)请解释一下什么时候可以使用“.”，什么时候可以使用“[]”?

　　如果正在运行bean属性，请使用“.”操作符，如果正在执行映射值或数组索引，则首选使用“[]”运算符。虽然两个运算符可以互换。

　　5)请解释Tomcat的默认端口是什么?

　　Tomcat的默认端口是8080。在本地机器上初始化Tomcat之后，您可以验证Tomcat是否正在运行URL:http://localhost:8080

　　6)请解释Tomcat中使用的连接器是什么?

　　在Tomcat中，使用了两种类型的连接器：

　　HTTP连接器:它有许多可以更改的属性，以确定它的工作方式和访问功能，如重定向和代理转发

　　AJP连接器:它以与HTTP连接器相同的方式工作，但是他们使用的是HTTP的AJP协议。AJP连接器通常通过插件技术mod_jk在Tomcat中实现

　　7)请阐述Catalina的配置文件有哪些?

　　Catalina包含的配置文件有：

　　·policy

　　·properties

　　·properties

　　·xml

　　·xml

　　·Tomcat-users.xml

　　·xml

　　8)请解释将Tomcat作为一个Windows 服务运行会带来哪些好处?

　　运行Tomcat作为windows服务带来了以下的好处：

　　自动启动:对于需要在维护后远程重新启动系统的环境来说，这是至关重要的

　　启动无活动用户登录的服务器:Tomcat通常在刀片服务器上运行，这些服务器甚至可能没有一个活动监视器，Windows服务可以在没有活动用户的情况下启动

　　安全性:在Windows服务下的Tomcat可以让您在一个特殊的系统帐户下运行它，这个账户可以从其他用户帐户中得到保护

　　9)解释何时在Tomcat使用SSL ?

　　当你将Tomcat作为独立的web服务器运行时，需使用Tomcat来处理连接

　　10)解释如何使用WAR文件部署web应用程序?

　　在Tomcat的web应用程序目录下，jsp、servlet和它们的支持文件被放置在适当的子目录中。你可以将web应用程序目录下的所有文件压缩到一个压缩文件中，以.war文件扩展名结束。你可以通过在webapps目录中放置WAR文件来执行web应用程序。当一个web服务器开始执行时，它会将WAR文件的内容提取到适当的webapps子目录中。

　　11)解释什么是Tomcat Valve?

　　Tomcat Valve——Tomcat 4引入的新技术，它允许您将Java类的实例链接到一个特定的Catalina容器。

　　12)说明Tomcat配置了多少个Valve?

　　Tomcat配置了四种类型的Valve：

　　·访问日志

　　·远程地址过滤

　　·远程主机过滤器

　　·客户请求记录器

　　13)解释servlet如何完成生命周期?

　　在Tomcat上运行的典型servlet生命周期如下：

　　·Tomcat通过它的其中一个连接器接收来自客户端的请求

　　·进程请求Tomcat将此请求映射为适当的

　　·一旦请求被定向到适当的servlet，Tomcat就会验证servlet类是否已经加载。如果不是Tomcat将servlet包装成Java字节码，这是由JVM执行的，并形成servlet的实例

　　·Tomcat通过调用它的init来启动servlet，它包含能够筛选Tomcat配置文件并相应地采取行动的代码，并声明它可能需要的任何资源

　　·一旦servlet启动，Tomcat就可以调用servlet的服务方法来进行请求

　　·在servlet的生命周期中，Tomcat和servlet可以通过使用侦听器类来进行协调或通信，从而跟踪各种状态变化的servlet

　　·删除servlet，Tomcat调用servlet销毁方法

　　14)请说明NAT协议的目的是什么?

　　NAT协议的目的是将私有IP地址从公共IP地址隐藏起来，并给组织提供一定的安全性。

　　15)请解释一下MAC代表什么?

　　MAC意味着中访问控制

　　16)请解释什么是Tomcat Coyote ?

　　Tom coyote是基于HTTP / 1.1规范的HTTP连接器，通过监听TCP / IP端口并将请求发送回请求客户端，向Tomcat引擎接收和传输web请求。