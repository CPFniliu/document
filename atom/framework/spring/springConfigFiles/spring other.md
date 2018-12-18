
## BeanFactory 和 FactoryBean

其 中 BeanFactory 指 的 是 IOC 容 器 的 编 程 抽 象 ， 比 如
ApplicationContext，XmlBeanFactory 等，这些都是 IOC 容器的具体表现，需要使用什么样的容器
由客户决定,但 Spring 为我们提供了丰富的选择。FactoryBean 只是一个可以在 IOC 而容器中被管理
的一个 Bean,是对各种处理过程和资源使用的抽象,FactoryBean 在需要时产生另一个对象，而不返回
FactoryBean 本身,我们可以把它看成是一个抽象工厂，对它的调用返回的是工厂生产的产品。所有的
FactoryBean 都实现特殊的 org.springframework.beans.factory.FactoryBean 接口，当使用容
器中 FactoryBean 的时候，该容器不会返回 FactoryBean 本身,而是返回其生成的对象。Spring 包括
了大部分的通用资源和服务访问抽象的 FactoryBean 的实现，其中包括:对 JNDI 查询的处理，对代理
对象的处理，对事务性代理的处理，对 RMI 代理的处理等，这些我们都可以看成是具体的工厂,看成是
咕泡出品，必属精品 www.gupaoedu.com
50
Spring 为我们建立好的工厂。也就是说 Spring 通过使用抽象工厂模式为我们准备了一系列工厂来生产
一些特定的对象,免除我们手工重复的工作，我们要使用时只需要在 IOC 容器里配置好就能很方便的使
用了。


#### JavaBean 和 Spring Bean 的 区别
   - java bean的描述
      1. JavaBean是一个普通的对象类，该类必须有一个无参构造。
      2. JavaBean的所有属性必须进行get/set封装，如果是bool属性，则需要用is进行封装。
   - java bean的作用域
      page : 作用域为当前页面
      request : 浏览器当次请求服务器所涉及的服务器资源，包含forward（请求转发）和include（包含）
      session : 浏览器和服务器的本次会话期间，所有涉及到的资源。
      application : 服务器的启动和关闭的整段时间。
   - Spring Bean
      在Spring中，**那些组成应用程序的主体及由 Spring IoC 容器所管理的对象，被称之为bean。** 简单地讲，bean就是由IoC容器初始化、装配及管理的对象，除此之外，bean就与应用程序中的其他对象没有什么区别了。而bean的定义以及bean相互间的依赖关系将通过配置元数据来描述。
   - 传统javabean更多地作为值传递参数，而spring中的bean用处几乎无处不在，任何组件都可以被称为bean。
   写法不同：传统javabean作为值对象，要求每个属性都提供getter和setter方法；但spring中的bean只需为接受设值注入的属性提供setter方法。
   生命周期不同：传统javabean作为值对象传递，不接受任何容器管理其生命周期；spring中的bean有spring管理其生命周期行为。
   所有可以被spring容器实例化并管理的java类都可以称为bean。
   javabean来管理对象，方便数据传递和对象管理。
   spring中的bean，是通过配置文件、javaconfig等的设置，有spring自动实例化，用完后自动销毁的对象。让我们只需要在用的时候使用对象就可以，不用考虑如果创建类对象（这就是spring的注入）。一般是用在服务器端代码的执行上。 望采纳！！！
