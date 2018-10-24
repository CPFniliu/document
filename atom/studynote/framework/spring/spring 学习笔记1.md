
### 一. 认识Spring

1. Spring概述

   Spring 框架的核心特性是可以用于开发任何 Java 应用程序，
   spring 是个Java企业级应用的 **轻量级** 的开源开发框架。Spring主要用来开发Java应用，但是有些扩展是针对构建J2EE平台的web应用。
   Spring 框架目标是 **简化Java企业级应用开发**，解决企业应用开发的复杂性的优秀的开源框架。并通过POJO为基础的编程模型促进良好的编程习惯。
   Spring是一个轻量级的控制反转（IoC）和面向切面（AOP）的容器框架。其核心是Bean工厂(Bean Factory)，用以构造我们所需要的M(Model)。

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
   ![Spring Framework 结构图](/pic/java/Spring/Spring7大模块划分.jpg)
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

   3. 七大模块划分

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
   1. spring三大核心学习

      1. 控制反转(IOC : Inversion of Control)
         > 一个对象A依赖另一个对象B就要自己去new 这是高度耦合的 IOC容器的使用。 比如在B中使用A很多，哪一天A大量更改，那么B中就要修改好多代码。通俗的理解是：平常我们new一个实例，这个实例的控制权是我们程序员，而控制反转是指new实例工作不由我们程序员来做而是交给spring容器来做。
         针对一个接口，我们可能会写多个实现类，如果在代码中、程序中对实现类的对象进行创建，当想更换实现类时(使用其他的实现类)，就需要对代码进行更改。
         一个使用实例
         通过spring的IOC功能，在xml配置文件中，给接口的实现类起一个名字“XXX”，代码中创建对象时，使用以下方式创建：

      2. 依赖注入(DI : Dependency Injection)
         >首先应该明白两个问题：1，谁依赖谁；2，谁注入，注入什么？
         利用xml的配置信息，在客户端代码中不用具体new任何的java对象了，java对象的创建工作，和对象中元素的赋值工作可以交给xml（spring）处理。
         回答文中开头两个问题：1.客户端代码中，具体对象的创建依赖于xml文件（spring，即IOC容器）；2.是IOC容器注入，在运行期，根据xml的配置信息，将具体的对象注入到相应的bean中。
         JavaBean：为了写出方便他人使用的类，于是规定，必须有一个零参的构造函数，同时还要用get/set方法，以便隐藏内部细节，方便使用和之后的代码更新。
         针对一个JavaBean，为了使用它，首先需要new一个对象，之后需要对其中的set方法进行调用进而赋值。代码之间的联系变得很大，封装的特性渐渐变小。这样在修改代码时，就麻烦了。要成堆的更改，尤其是在不同团队分工开发的过程中，代码变更影响巨大。
         通过控制反转（IOC）、依赖注入，new的同一种对象，在xml文件中都给他起一个小名，这样更改时只需要在xml文件中，将小名对应的类的具体路径更改了。不需要一个个.java文件替换。
         在使用set方法传值时，如果针对具体的属性值，进行填写，更改起来也会麻烦一些，通过Spring来进行赋值，更改起来更加方便。

      3. 面向切面AOP
         可以通过预编译方式和运行期动态代理实现在不修改源代码的情况下给程序动态统一添加功能的一种技术。
         将日志记录，性能统计，安全控制，事务处理，异常处理等代码从业务逻辑代码中划分出来

   2. 依赖注入(DI : Dependency Injection)和控制反转(IOC : Inversion of Control)
         依赖注入和控制反转是对同一件事情的不同描述，从某个方面讲，就是它们描述的角度不同。
         依赖注入是从应用程序的角度在描述，可以把依赖注入描述完整点：应用程序依赖容器创建并注入它所需要的外部资源；
         而控制反转是从容器的角度在描述，描述完整点：容器控制应用程序，由容器反向的向应用程序注入应用程序所需要的外部资源。
         >[***控制反转（IOC）和依赖注入（DI）的区别***](https://blog.csdn.net/doris_crazy/article/details/18353197?utm_source=blogxgwz1)

   - 依赖注入（DI）的三种方式，分别为：
      1. 接口注入.
      2. Setter方法注入.
      3. 构造方法注入.

   - Spring 五种生命周期
     singleton, prototype, request, session, global session。
