
### 一. 认识Spring

1. Spring概述

   Spring 框架的核心特性是可以用于开发任何 Java 应用程序，
   spring 是个Java企业级应用的 **轻量级** 的开源开发框架。Spring主要用来开发Java应用，但是有些扩展是针对构建J2EE平台的web应用。
   Spring 框架目标是 **简化Java企业级应用开发**，解决企业应用开发的复杂性的优秀的开源框架。并通过POJO为基础的编程模型促进良好的编程习惯。
   Spring是一个轻量级的控制反转（IoC）和面向切面（AOP）的容器框架.

2. spring 优点 :
   轻量, 方便解耦，简化开发；
   属于一个万能的框架，跟很多框架都是百搭；
   控制反转：Spring通过控制反转实现了松散耦合，对象们给出它们的依赖，而不是创建或查找依赖的对象们。
   面向切面的编程(AOP)：Spring支持面向切面的编程，并且把应用业务逻辑和系统服务分开。
   容器：Spring 包含并管理应用中对象的生命周期和配置。
   MVC框架：Spring的WEB框架是个精心设计的框架，是Web框架的一个很好的替代品。
   事务管理：Spring 提供一个持续的事务管理接口，可以扩展到上至本地事务下至全局事务（JTA）。
   异常处理：Spring 提供方便的API把具体技术相关的异常（比如由JDBC，Hibernate or JDO抛出的）转化为一致的unchecked 异常。

3. 特征:
   依赖注入
   面向切面编程

4. Spring模块

   ![Spring Framework 结构图](../../../pic/)
   1. 核心容器
      核心容器由spring-core，spring-beans，spring-context，spring-context-support和spring-expression（SpEL，Spring表达式语言，Spring Expression Language）等模块组成，它们的细节如下：
      模块名称 | 模块简述
      -|-
      spring-core  | 提供了框架的基本组成部分，包括 IoC 和依赖注入功能。
      spring-beans | 提供 BeanFactory，工厂模式的微妙实现，它移除了编码式单例的需要，并且可以把配置和依赖从实际编码逻辑中解耦。
      context      | 在core和 beans 模块的基础上建立起来的，它以一种类似于JNDI注册的方式访问对象。Context模块继承自Bean模块，并且添加了国际化（比如，使用资源束）、事件传播、资源加载和透明地创建上下文（比如，通过Servelet容器）等功能。Context模块也支持Java EE的功能，比如EJB、JMX和远程调用等。ApplicationContext接口是Context模块的焦点。
      spring-context-support | 提供了对第三方库集成到Spring上下文的支持，比如缓存（EhCache, Guava, JCache）、邮件（JavaMail）、调度（CommonJ, Quartz）、模板引擎（FreeMarker, JasperReports, Velocity）等。
      spring-expression | 模块提供了强大的表达式语言，用于在运行时查询和操作对象图。它是JSP2.1规范中定义的统一表达式语言的扩展，支持set和get属性值、属性赋值、方法调用、访问数组集合及索引的内容、逻辑算术运算、命名变量、通过名字从Spring IoC容器检索对象，还支持列表的投影、选择以及聚合等。。

   2. 其他模块
      JDBC module, ORM module, OXM module, Java Messaging Service(JMS) module, Transaction module, Web module, Web-Servlet module, Web-Struts module, Web-Portlet module, aop, aspects.

   3. 七大模块

      1. Spring Core： Core封装包是框架的最基础部分，提供IOC和依赖注入特性。这里的基础概念是BeanFactory，它提供对Factory模式的经典实现来消除对程序性单例模式的需要，并真正地允许你从程序逻辑中分离出依赖关系和配置。

      2. Spring Context: 构建于Core封装包基础上的 Context封装包，提供了一种框架式的对象访问方法，有些象JNDI注册器。Context封装包的特性得自于Beans封装包，并添加了对国际化（I18N）的支持（例如资源绑定），事件传播，资源装载的方式和Context的透明创建，比如说通过Servlet容器。

      3. Spring DAO:  DAO (Data Access Object)提供了JDBC的抽象层，它可消除冗长的JDBC编码和解析数据库厂商特有的错误代码。 并且，JDBC封装包还提供了一种比编程性更好的声明性事务管理方法，不仅仅是实现了特定接口，而且对所有的POJOs（plain old Java objects）都适用。

      4. Spring ORM: ORM 封装包提供了常用的“对象/关系”映射APIs的集成层。 其中包括JPA、JDO、Hibernate 和 iBatis 。利用ORM封装包，可以混合使用所有Spring提供的特性进行“对象/关系”映射，如前边提到的简单声明性事务管理。

      5. Spring AOP: Spring的 AOP 封装包提供了符合AOP Alliance规范的面向方面的编程实现，让你可以定义，例如方法拦截器（method-interceptors）和切点（pointcuts），从逻辑上讲，从而减弱代码的功能耦合，清晰的被分离开。而且，利用source-level的元数据功能，还可以将各种行为信息合并到你的代码中。

      6. Spring Web: Spring中的 Web 包提供了基础的针对Web开发的集成特性，例如多方文件上传，利用Servlet listeners进行IOC容器初始化和针对Web的ApplicationContext。当与WebWork或Struts一起使用Spring时，这个包使Spring可与其他框架结合。

      7. Spring Web MVC: Spring中的MVC封装包提供了Web应用的Model-View-Controller（MVC）实现。Spring的MVC框架并不是仅仅提供一种传统的实现，它提供了一种清晰的分离模型，在领域模型代码和Web Form之间。并且，还可以借助Spring框架的其他特性。

-. 面试题
   [关于Spring的69个面试问答——终极列表](http://www.importnew.com/11657.html)
   [Spring总结以及在面试中的一些问题](https://www.cnblogs.com/wang-meng/p/5701982.html)

### 二. spring
