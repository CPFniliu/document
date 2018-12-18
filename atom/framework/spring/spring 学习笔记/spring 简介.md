# 一. 认识Spring

## Spring 简介

### spring 概述

- Spring 框架的核心特性是可以用于开发任何 Java 应用程序，
- spring 是个Java企业级应用的 **轻量级** 的开源开发框架。Spring主要用来开发Java应用，但是有些扩展是针对构建J2EE平台的web应用。
- Spring 框架目标是 **简化Java企业级应用开发**，解决企业应用开发的复杂性的优秀的开源框架。并通过POJO为基础的编程模型促进良好的编程习惯。
- Spring是一个轻量级的控制反转（IoC）和面向切面（AOP）的容器框架。其核心是Bean工厂(Bean Factory)，用以构造我们所需要的M(Model)。

> Spring 设计的初衷
> Spring 是为解决企业级应用开发的复杂性而设计，她可以做很多事。但归根到底支撑 Spring 的仅仅是少许的基本理念，而所有地这些的基本理念都能可以追溯到一个最根本的使命：简化开发。这是一个郑重的承诺，其实许多框架都声称在某些方面做了简化。
> 而 Spring 则立志于全方面的简化 Java 开发。对此，她主要采取了 4 个关键策略：
>
> 1. 基于 POJO 的轻量级和最小侵入性编程；
> 2. 通过依赖注入和面向接口松耦合；
> 3. 基于切面和惯性进行声明式编程；
> 4. 通过切面和模板减少样板式代码；
>
> 而他主要是通过：面向 Bean、依赖注入以及面向切面这三种方式来达成的。

### spring 优点

- 轻量, 方便解耦，简化开发；
- 属于一个万能的框架，跟很多框架都是百搭；
- 控制反转：Spring通过控制反转实现了松散耦合，对象们给出它们的依赖，而不是创建或查找依赖的对象们。
- 面向切面的编程(AOP)：Spring支持面向切面的编程，并且把应用业务逻辑和系统服务分开。
- 容器：Spring 包含并管理应用中对象的生命周期和配置。
- MVC框架：Spring的WEB框架是个精心设计的框架，是Web框架的一个很好的替代品。
- 事务管理：Spring 提供一个持续的事务管理接口，可以扩展到上至本地事务下至全局事务（JTA）。
- 异常处理：Spring 提供方便的API把具体技术相关的异常（比如由JDBC，Hibernate or JDO抛出的）转化为一致的unchecked 异常。

### spring 特征

- 依赖注入
- 面向切面编程

---

## spring 模块和结构

### Spring 按结构划分

#### 1. 核心容器：

由 spring-beans、spring-core、spring-context 和 spring-expression（Spring Expression Language, SpEL） 4 个模块组成。spring-beans 和 spring-core 模块是 Spring 框架的核心模块，包含了控制反转（Inversion of Control, IOC）和依赖注入（Dependency Injection, DI）。

**BeanFactory** 接口是 Spring 框架中的核心接口，它是工厂模式的具体实现。BeanFactory 使用控制反转对应用程序的配置和依赖性规范与实际的应用程序代码进行了分离。但 BeanFactory 容器实例化后并不会自动实例化 Bean，只有当 Bean 被使用时 BeanFactory 容器才会对该 Bean 进行实例化与依赖关系的装配。

spring-context 模块构架于核心模块之上，他扩展了 BeanFactory，为她添加了 Bean 生命周期控制、框架事件体系以及资源加载透明化等功能。此外该模块还提供了许多企业级支持，如邮件访问、远程访问、任务调度等，**ApplicationContext** 是该模块的核心接口，她是 BeanFactory 的超类，与BeanFactory 不同，ApplicationContext 容器实例化后会自动对 **所有的单实例 Bean** 进行实例化与依赖关系的装配，使之处于待用状态。

spring-expression 模块是统一表达式语言（EL）的扩展模块，可以查询、管理运行中的对象，同时也方便的可以调用对象方法、操作数组、集合等。它的语法类似于传统 EL，但提供了额外的功能，最出色的要数函数调用和简单字符串的模板函数。这种语言的特性是基于 Spring 产品的需求而设计，他可以非常方便地同 Spring IOC 进行交互。

#### 2. AOP 和设备支持：

由 spring-aop、spring-aspects 和 spring-instrument 3 个模块组成。spring-aop 是 Spring 的另一个核心模块，是 AOP 主要的实现模块。作为继 OOP 后，对程序员影响最大的编程思想之一，AOP 极大地开拓了人们对于编程的思路。在 Spring 中，他是以 JVM 的动态代理技术为基础，然后设计出了一系列的 AOP 横切实现，比如前置通知、返回通知、异常通知等，同时，Pointcut 接口来匹配切入点，可以使用现有的切入点来设计横切面，也可以扩展相关方法根据需求进行切入。
spring-aspects 模块集成自 AspectJ 框架，主要是为 Spring AOP 提供多种 AOP 实现方法。spring-instrument 模块是基于 JAVA SE 中的"java.lang.instrument"进行设计的，应该算是AOP 的一个支援模块，主要作用是在 JVM 启用时，生成一个代理类，程序员通过代理类在运行时修改类的字节，从而改变一个类的功能，实现 AOP 的功能。在分类里，我把他分在了 AOP 模块下，在 Spring 官方文档里对这个地方也有点含糊不清，这里是纯个人观点。

#### 3. 数据访问及集成：

由 spring-jdbc、spring-tx、spring-orm、spring-jms 和 spring-oxm 5 个模块组成。
spring-jdbc 模块是 Spring 提供的 JDBC 抽象框架的主要实现模块，用于简化 Spring JDBC。主要是提供 JDBC 模板方式、关系数据库对象化方式、SimpleJdbc 方式、事务管理来简化 JDBC 编程，主要实现类是 JdbcTemplate、SimpleJdbcTemplate 以及 NamedParameterJdbcTemplate。

spring-tx 模块是 Spring JDBC 事务控制实现模块。使用 Spring 框架，它对事务做了很好的封装，通过它的 AOP 配置，可以灵活的配置在任何一层；但是在很多的需求和应用，直接使用 JDBC 事务控制还是有其优势的。其实，**事务是以业务逻辑为基础的；一个完整的业务应该对应业务层里的一个方法；**如果业务操作失败，则整个事务回滚；所以，事务控制是绝对应该放在业务层的；但是，持久层的设计则应该遵循一个很重要的原则：**保证操作的原子性，即持久层里的每个方法都应该是不可以分割的**。所以，在使用 Spring JDBC 事务控制时，应该注意其特殊性。
spring-orm 模块是 ORM 框架支持模块，主要集成 Hibernate, Java Persistence API (JPA) 和 Java Data Objects (JDO) 用于资源管理、数据访问对象(DAO)的实现和事务策略。
spring-jms 模块（Java Messaging Service）能够发送和接受信息，自 Spring Framework 4.1以后，他还提供了对 spring-messaging 模块的支撑。
spring-oxm 模块主要提供一个抽象层以支撑 OXM（OXM 是 Object-to-XML-Mapping 的缩写，它是一个 O/M-mapper，将 java 对象映射成 XML 数据，或者将 XML 数据映射成 java 对象），例如：JAXB, Castor, XMLBeans, JiBX 和 XStream 等。

#### 4. Web：

由 spring-web、spring-webmvc、spring-websocket 和 spring-webflux 4 个模块组
成。
spring-web 模块为 Spring 提供了最基础 Web 支持，主要建立于核心容器之上，通过 Servlet 或
者 Listeners 来初始化 IOC 容器，也包含一些与 Web 相关的支持。
spring-webmvc 模 块 众 所 周 知 是 一 个 的 Web-Servlet 模 块 ， 实 现 了 Spring MVC
（model-view-Controller）的 Web 应用。
spring-websocket 模块主要是与 Web 前端的全双工通讯的协议。（资料缺乏，这是个人理解）
spring-webflux 是一个新的非堵塞函数式 Reactive Web 框架，可以用来建立异步的，非阻塞，
事件驱动的服务，并且扩展性非常好。

#### 5. 报文发送：

即 spring-messaging 模块。
spring-messaging 是从 Spring4 开始新加入的一个模块，主要职责是为 Spring 框架集成一些基
础的报文传送应用。

#### 6.Test：

即 spring-test 模块。
spring-test 模块主要为测试提供支持的，毕竟在不需要发布（程序）到你的应用服务器或者连接
到其他企业设施的情况下能够执行一些集成测试或者其他测试对于任何企业都是非常重要的。

### Spring模块

   ![Spring Framework 结构图](/assets/frame/spring/Spring7大模块划分.jpg)
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
      1. Spring  Core ： Core封装包是框架的最基础部分，提供IOC和依赖注入特性。这里的基础概念是BeanFactory，它提供对Factory模式的经典实现来消除对程序性单例模式的需要，并真正地允许你从程序逻辑中分离出依赖关系和配置。

      2. Spring  Context : 构建于Core封装包基础上的 Context 封装包，提供了一种框架式的对象访问方法，有些象JNDI注册器。Context封装包的特性得自于Beans封装包，并添加了对国际化（I18N）的支持（例如资源绑定），事件传播，资源装载的方式和Context的透明创建，比如说通过Servlet容器。

      3. Spring DAO:  DAO (Data Access Object)提供了JDBC的抽象层，它可消除冗长的JDBC编码和解析数据库厂商特有的错误代码。 并且，JDBC封装包还提供了一种比编程性更好的声明性事务管理方法，不仅仅是实现了特定接口，而且对所有的POJOs（ plain  old Java objects）都适用。

      4. Spring ORM: ORM 封装包提供了常用的“对象/关系”映射APIs的集成层。 其中包括JPA、JDO、Hibernate 和 iBatis 。利用ORM封装包，可以混合使用所有Spring提供的特性进行“对象/关系”映射，如前边提到的简单声明性事务管理。

      5. Spring AOP: Spring的 AOP 封装包提供了符合AOP Alliance规范的面向方面的编程实现，让你可以定义，例如方法拦截器（method-interceptors）和切点（pointcuts），从逻辑上讲，从而减弱代码的功能耦合，清晰的被分离开。而且，利用source-level的元数据功能，还可以将各种行为信息合并到你的代码中。

      6. Spring Web: Spring中的 Web 包提供了基础的针对Web开发的集成特性，例如多方文件上传，利用Servlet listeners进行IOC容器初始化和针对Web的ApplicationContext。当与WebWork或Struts一起使用Spring时，这个包使Spring可与其他框架结合。

      7. Spring Web MVC: Spring中的MVC封装包提供了Web应用的Model-View-Controller（MVC）实现。Spring的MVC框架并不是仅仅提供一种传统的实现，它提供了一种清晰的分离模型，在领域模型代码和Web Form之间。并且，还可以借助Spring框架的其他特性。

# 二. spring
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
         接口注入模式因为具备侵入性，它要求组件必须与特定的接口相关联，因此并不被看好，实际使用有限。
      2. 构造方法注入.
         1、“**在构造期即创建一个完整、合法的对象**”，对于这条Java设计原则，Type2无疑是最好的响应者。
         2、避免了繁琐的setter方法的编写，所有依赖关系均在构造函数中设定，依赖关系集中呈现，更加易读。
         3、由于没有setter方法，依赖关系在构造时由容器一次性设定，因此组件在被创建之后即处相对“不变”的稳定状态，无需担心上层代码在调用过程中执行setter方法对组件依赖关系产生破坏，特别是对于Singleton模式的组件而言，这可能对整个系统产生重大的影响。
         4、同样，由于关联关系仅在构造函数中表达，只有组件创建者需要关心组件内部的依赖关系。对调用者而言，组件中的依赖关系处于黑盒之中。对上层屏蔽不必要的信息，也为系统的层次清晰性提供了保证。
         5、通过构造子注入，意味着我们可以在构造函数中决定依赖关系的注入顺序，对于一个大量依赖外部服务的组件而言，依赖关系的获得顺序可能非常重要，比如某个依赖关系注入的先决条件是组件的DataSource及相关资源已经被设定。
      3. Setter方法注入.
         1、对于习惯了传统JavaBean开发的程序员而言，通过setter方法设定依赖关系显得更加直观，更加自然。
         2、如果依赖关系（或继承关系）较为复杂，那么Type2模式的构造函数也会相当庞大（我们需要在构造函数中设定所有依赖关系），此时Type3模式往往更为简洁。
         3、对于某些第三方类库而言，可能要求我们的组件必须提供一个默认的构造函数（如Struts中的Action），此时Type2类型的依赖注入机制就体现出其局限性，难以完成我们期望的功能。
      >构造方法注入和Setter方法注入模式各有千秋，而Spring、PicoContainer都对构造方法注入和Setter方法注入类型的依赖注入机制提供了良好支持。这也就为我们提供了更多的选择余地。理论上，以构造方法注入类型为主，辅之以Setter方法注入类型机制作为补充，可以达到最好的依赖注入效果，不过对于基于Spring Framework开发的应用而言，Setter方法注入使用更加广泛。
