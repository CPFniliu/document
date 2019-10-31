### spring boot 学习笔记

1. 简介
   1.1 简化Spring开发的一个框架.
   1.2 spring boot整合了几乎所有的框架.
   1.3 设计目的是用来简化新Spring应用的初始搭建以及开发过程。
   1.4 奉行的宗旨: 废除掉所有配置文件, 让开发变得简单纯粹, 核心 :  尽量实现零配置,
   1.5 Spring Boot 并不是对 Spring 功能上的增强，而是提供了一种快速使用 Spring 的方式。

2. 优点
　　创建独立的 Spring 应用程序
　　嵌入的 Tomcat，无需部署 WAR 文件
　　简化 Maven 配置
　　自动配置 Spring.
　　提供生产就绪型功能，如指标，健康检查和外部配置
　　开箱即用，没有代码生成，也无需 XML 配置。



1.1 建立一个quick state maven项目
1.2 配置一个父pom
1.2.1 建立一个maven项目(quickStart)
1.2.2 去掉相关配置信息,pom.xml文件的打包类型改为pom类型


注解

annotation | 含义
-|-
@Controller | 代表当前类为控制器
@EnableAutoConfiguration| 代表自动注解配置
@RequestMapping("/") | 代表映射路径
@ResponseBody | 代表返回数据以字符串或者是json的格式返回, 没有该注解则默认跳转返回字符串代表的url
@SpringBootApplication | @EnableAutoConfiguration + @ComponentScan("当前package路径") + 其他配置
@RestController | 代表其中所有方法都以字符串或者是json的格式返回

### Spring-boot的2大优点

1.基于Spring框架的“约定优先于配置（COC）”理念以及最佳实践之路。
2.针对日常企业应用研发各种场景的Spring-boot-starter自动配置依赖模块，且“开箱即用”（约定spring-boot-starter- 作为命名前缀，都位于org.springframenwork.boot包或者命名空间下）。

#### 配置路径访问


#### Spring Boot 之Spring Boot Starter依赖包及作用

1. spring-boot-starter-parent
   这是Spring Boot的父级依赖，这样当前的项目就是Spring Boot项目了。
   spring-boot-starter-parent 是一个特殊的starter，它用来提供相关的Maven默认依赖。使用它之后，常用的包依赖可以省去version标签。
2. spring-boot-starter

3. spring-boot-starter-web

快速web应用开发, 有这个依赖后在当下项目运行mvn spring-boot:run就可以直接启用一个嵌套了tomcat的web应用。
> 如果没有提供任何服务的Cotroller,访问任何路径都会返回一个springBoot默认的错误页面（Whitelabel error page）。

三种启动方式

   1. 运行带有main方法类
   2. 通过命令行 java -jar 的方式
   3. 通过spring-boot-plugin的方式

嵌入式Web容器层面的约定和定制

spring-boot-starter-web默认使用嵌套式的Tomcat作为Web容器对外提供HTTP服务，默认端口8080对外监听和提供服务。

我们同样可以使用spring-boot-starter-jetty或者spring-boot-starter-undertow作为Web容器。

想改变默认的配置端口，可以在 application.properties 中指定：

```properties
server.port = 9000(the port number you want)

server.address
server.ssl.*
server.tomcat.*
```

如果上诉仍然没有办法满足要求，springBoot支持对嵌入式的Web容器实例进行定制，可以通过向IoC容器中注册一个EmbeddedServletContainerCustomizer类型的组件来对嵌入式的Web容器进行定制

   ```java
   public class UnveilSpringEmbeddedTomcatCustomizer implements EmbeddedServletContainer{
      public void customize(ConfigurableEmbeddedServletContainer container){
         container.setPort(9999);
         container.setContextPath("C\\hello");
                        ...
      }
   }
   ```

4. spring-boot-configuration-processor

spring默认使用yml中的配置，但有时候要用传统的xml或properties配置，就需要使用`spring-boot-configuration-processor`了
再在你的配置类开头加上@PropertySource("classpath:your.properties")，其余用法与加载yml的配置一样

5. spring-boot-starter-logging

常见的日志系统大致有：java.util默认提供的日志支持，log4j,log4j2,commons logging,spring-boot-starter-logging也是其中的一种。
springBoot将使用logback作为应用日志的框架，程序启动时，由`org.springframework.boot.logging-Logging-Application-Lisetener`根据情况初始化并使用。

> 如果要想改变springBoot提供的应用日志设定，可以通过以下原则：
> 遵循logback的约定，在classpath中使用自己定制的logback.xml配置文件。
> 在文件系统的任意一个位置提供自己的logback.xml配置文件，然后通过logging.config配置项指向这个配置文件然后引用它，例如在application.properties中指定如下的配置：
> `logging.config=/{some.path.you.defined}/any-logfile-name-I-like.log}`



依赖包 | 作用
-|-
spring-boot-starter | 这是Spring Boot的核心启动器，包含了自动配置、日志和YAML。

spring-boot-starter-amqp  | 通过spring-rabbit来支持AMQP协议（Advanced Message Queuing Protocol. 。
spring-boot-starter-aop | 支持面向方面的编程即AOP，包括spring-aop和AspectJ。
spring-boot-starter-artemis | 通过Apache Artemis支持JMS的API（Java Message Service API. 。

spring-boot-starter-batch 
支持Spring Batch，包括HSQLDB数据库。

spring-boot-starter-cache | 支持Spring的Cache抽象。
spring-boot-starter-cloud-connectors | 支持Spring Cloud Connectors，简化了在像Cloud Foundry或Heroku这样的云平台上连接服务。
spring-boot-starter-data-elasticsearch | 支持ElasticSearch搜索和分析引擎，包括spring-data-elasticsearch。
spring-boot-starter-data-gemfire | 支持GemFire分布式数据存储，包括spring-data-gemfire。
spring-boot-starter-data-jpa | 支持JPA（Java Persistence API. ，包括spring-data-jpa、spring-orm、Hibernate。
spring-boot-starter-data-mongodb | 支持MongoDB数据，包括spring-data-mongodb。
spring-boot-starter-data-rest | 通过spring-data-rest-webmvc，支持通过REST暴露Spring Data数据仓库。
spring-boot-starter-data-solr | 支持Apache Solr搜索平台，包括spring-data-solr。
`spring-boot-starter-freemarker` | 支持FreeMarker模板引擎。
spring-boot-starter-groovy-templates | 支持Groovy模板引擎。
spring-boot-starter-hateoas | 通过spring-hateoas支持基于HATEOAS的RESTful Web服务。
spring-boot-starter-hornetq | 通过HornetQ支持JMS。
spring-boot-starter-integration | 支持通用的spring-integration模块。
`spring-boot-starter-jdbc` | 支持JDBC数据库。
spring-boot-starter-jersey | 支持Jersey RESTful Web服务框架。
spring-boot-starter-jta-atomikos | 通过Atomikos支持JTA分布式事务处理。
spring-boot-starter-jta-bitronix | 通过Bitronix支持JTA分布式事务处理。
spring-boot-starter-mail | 支持javax.mail模块。
spring-boot-starter-mobile | 支持spring-mobile。
spring-boot-starter-mustache | 支持Mustache模板引擎。
spring-boot-starter-redis | 支持Redis键值存储数据库，包括spring-redis。
spring-boot-starter-security | 支持spring-security。
spring-boot-starter-social-facebook | 支持spring-social-facebook
spring-boot-starter-social-linkedin | 支持pring-social-linkedin

spring-boot-starter-social-twitter | 支持pring-social-twitter

spring-boot-starter-test | 支持常规的测试依赖，包括JUnit、Hamcrest、Mockito以及spring-test模块。

spring-boot-starter-thymeleaf 
支持Thymeleaf模板引擎，包括与Spring的集成。

spring-boot-starter-velocity 
支持Velocity模板引擎。

spring-boot-starter-web 
S支持全栈式Web开发，包括Tomcat和spring-webmvc。

spring-boot-starter-websocket 
支持WebSocket开发。

spring-boot-starter-ws 
支持Spring Web Services。 
Spring Boot应用启动器面向生产环境的还有2种，具体如下：

spring-boot-starter-actuator 
增加了面向产品上线相关的功能，比如测量和监控。

spring-boot-starter-remote-shell 
增加了远程ssh shell的支持。 
最后，Spring Boot应用启动器还有一些替换技术的启动器，具体如下：

spring-boot-starter-jetty 
引入了Jetty HTTP引擎（用于替换Tomcat. 。

spring-boot-starter-log4j 
支持Log4J日志框架。

spring-boot-starter-logging 
引入了Spring Boot默认的日志框架Logback。

spring-boot-starter-tomcat 
引入了Spring Boot默认的HTTP引擎Tomcat。

spring-boot-starter-undertow 
引入了Undertow HTTP引擎（用于替换Tomcat. 。
 ———————————————— 
版权声明：本文为CSDN博主「danny_shu」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/shuxing520/article/details/78213862