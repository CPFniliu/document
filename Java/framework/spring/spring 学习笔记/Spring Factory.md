# springFactory

## 容器

- 容器是spring的核心，使IoC管理所有和组件
- 在Spring中，组件无需自己负责与其他组件的关联。取而代之的是，容器负责把协作组件的引用给予各个组件。创建系统组件之间协作关系的这个动作是DI的关键，通常被称之为装配；
- 容器可以创建组件，装配和配置组件，以及管理他们的整个生命周期（从new到finalize）；
- **Spring提供了多种容器实现**，并分为两类 ：
      - Bean工厂（BeanFactory接口），提供了基础的依赖注入支持。
      - ApplicationContext 应用上下文

Spring 的IoC容器就是一个实现了BeanFactory接口的可实例化类。事实上，Spring提供了两种不同的容器：一种是最基本的BeanFactory，另一种是扩展的ApplicationContext。BeanFactory 仅提供了最基本的依赖注入支持，而 ApplicationContext 则扩展了BeanFactory ,提供了更多的额外功能。二者对Bean的初始化也有很大区别。BeanFactory当需要调用时读取配置信息，生成某个类的实例。如果读入的Bean配置正确，则其他的配置中有错误也不会影响程序的运行。而ApplicationContext 在初始化时就把 xml 的配置信息读入内存，对 XML 文件进行检验，如果配置文件没有错误，就创建所有的Bean ,直接为应用程序服务。相对于基本的BeanFactory，ApplicationContext 唯一的不足是占用内存空间。当应用程序配置Bean较多时，程序启动较慢。

ApplicationContext会利用Java反射机制自动识别出配置文件中定义的BeanPostProcessor、InstantiationAwareBeanPostProcessor和BeanFactoryPostProcessor，并自动将它们注册到应用上下文中；而BeanFactory需要在代码中通过手工调用addBeanPostProcessor()方法进行注册。

Bean装配实际上就是让容器知道程序中都有哪些Bean，可以通过以下两种方式实现：

- 配置文件（最为常用，体现了依赖注入DI的思想）
- 编程方式（写的过程中向BeanFactory去注册）

## BeanFactory

### BeanFactory 简介

BeanFactory 是工厂模式的一个实现，提供了 依赖注入/控制反转 功能，负责读取bean配置，管理bean的加载，实例化，维护bean之间的依赖关系，负责bean的声明周期。用来把应用的配置和依赖从正真的应用代码中分离。
BeanFactory不像其他工厂模式的实现，他们只是分发一种类型的对象，而Bean工厂是一个通用的工厂，可以创建和分发各种类型的Bean。
BeanFactory 使用延迟加载所有的Bean，为了从BeanhFactory得到一个Bean,只要调用getBean()方法，就能获得Bean，
> Spring 3.1之前最常用的 BeanFactory 实现是 XmlBeanFactory 类，它根据XML文件中的定义加载beans。该容器从XML 文件读取配置元数据并用它去创建一个完全配置的系统或应用。Spring 3.1以后已经废弃了XmlBeanFactory这个类了。推荐使用 **DefaultListableBeanFactory** 和 **XmlBeanDefinitionReader** 替换，两个类配合使用。

eg : 使用 ~~XmlBeanFactory~~ （Spring 3.1以后已经 **废弃** 了这个类）

```JAVA
   public void testBean2() {
      BeanFactory factory = new XmlBeanFactory(new ClassPathResource("spring-bean.xml"));
      Person person = factory.getBean("person", Person.class);
      System.out.println(person);
   }
```

eg : 使用 **DefaultListableBeanFactory** & **XmlBeanDefinitionReader**

```Java
public void testIOC() throws Exception {
        // 现在，把对象的创建交给spring的IOC容器
        Resource resource = new ClassPathResource("cn/itcast/a_hello/applicationContext.xml");
        // 创建容器对象(Bean的工厂), IOC容器 = 工厂类 + applicationContext.xml
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        // 得到容器创建的对象
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);//新增XMl阅读器
        reader.loadBeanDefinitions(resource);
        User user = (User) factory.getBean("user1");
        System.out.println(user.getId());
}
```

### BeanFactory继承结构

BeanFactory继承结构图
![DefaultListableBeanFactory](/assets/frame/spring/Spring5-DefaultListableBeanFactory实现继承结构图.png)

其中 BeanFactory 作为最顶层的一个接口类，它定义了 IOC 容器的基本功能规范，BeanFactory 有三个子类：ListableBeanFactory、HierarchicalBeanFactory 和 AutowireCapableBeanFactory。但是从上图中我们可以发现最终的默认实现类是 DefaultListableBeanFactory，他实现了所有的接口。那为何要定义这么多层次的接口呢？查阅这些接口的源码和说明发现，每个接口都有他使用的场合，它主要是为了区分在 Spring 内部在操作过程中对象的传递和转化过程中，对对象的数据访问所做的限制。

- ListableBeanFactory 接口表示这些 Bean 是可列表的.
- HierarchicalBeanFactory 表示的是这些 Bean 是有继承关系的，也就是每个 Bean 有可能有父 Bean。
- AutowireCapableBeanFactory 接口定义 Bean 的自动装配规则。这四个接口共同定义了 Bean 的集合、Bean 之间的关系、以及 Bean行为.

![BeanFactory类关系继承图](/assets/frame/spring/Spring-BeanFactory类关系继承图.jpg)

## ApplicationContext

### ApplicationContext 简介

应用上下文（ApplicationContext接口），建立在 BeanFactory 基础之上（*ApplicationContext 接口扩展于 BeanFactory接口*），除了提供上述BeanFactory所能提供的功能之外，还额外提供了系统架构服务。
   a、提供文本信息解析，支持I18N
   b、提供载入文件资源的通用方法
   c、向注册为监听器的Bean发送事件
   d、ApplicationContext接口扩展BeanFactory接口
   e、ApplicationContext提供附加功能

1. ApplicationContext的三个经常用到的实现类：
   a. ClassPathXmlApplication ：把上下文文件当成类路径资源
   b. FileSystemXmlApplication ：从文件系统中的XML文件载入上下文定义信息
   c. XmlWebApplicationContext ：从Web系统中的XML文件载入上下文定义信息

   第一种和第二种的区别在于，ClassPathXmlApplication 可以在整个类路径（包括JAR文件）中寻找定义Bean的XML文件；而 FileSystemXmlApplication 只能在指定路径中寻找。

   ```java
   // FileSystemXmlApplication
   ApplicationContext context = new FileSystemXmlApplicationContext("c:/pirate.xml");
   // ClassPathXmlApplication
   ApplicationContext context = new ClassPathXmlApplicationContext("pirate.xml");
   ```

#### Resource

   要创建 XmlBeanFactory，需要传递一个org.springframework.core.io.Resource实例给构造函数。此 Resource 对象提供XML文件给工厂。

>有以下几种方式配置XML源：

   org.springframework.core.io.ByteArrayResource
   org.springframework.core.io.ClassPathResource
   org.springframework.core.io.DescriptiveResource
   org.springframework.core.io.FileSystemResource
   org.springframework.core.io.InputStreamResource
   org.springframework.web.portlet.contentx.PortletContextResource
   org.springframework.web.context.support.ServletContextResource
   org.springframework.core.io.UrlResource

### Bean设置：设值注入：

1. 简单配置

```XML
<bean id="xxx" class="Bean的全称类名">
   <!-- value中的值可以是基本数据类型或者String类型，spring将会自动判断设置的类型并且将其转换成合适的值 -->
   <property name="xx" value="xxxxx"></property>
</bean>
```

1. 引用配置

```XML
<bean id="xxx" class="Bean的全称类名">
   <!-- ref中的值是引用数据类型，spring容器会完成获取工作 -->
   <property name="xx" ref="xxxxx"></property>
</bean>
```

1. List和数组

```XML
<bean id="xxx" class="Bean的全称类名">
   <property name="list">
      <!-- list元素内可以是任何元素，但不能违背Bean所需要的对象类型 -->
      <list>
         <value>xxxxx</value>
         <ref bean="xxxxx"/>
      </list>
   </property>
</bean>
```

1. Set配置：和\<list\>一样，将\<list\>改成\<set\>。
2. Map配置：Map中的每条数据是由一个键和一个值组成，用\<entry\>元素来定义。

```XML
<bean id="xxx" class="Bean的全称类名">
   <!-- 注意：配置entry时，属性key的值只能是String，因为Map通常用String作为主键 -->
   <property name="list">
      <entry key="key1">
         <value></value>
      </entry>
   </property>
</bean>
<bean id="xxx" class="Bean的全称类名">
   <property name="list">
      <entry key="key1">
         <ref bean=""/>
      </entry>
   </property>
</bean>
```

1. Properties 配置：使用<props>和<map>相似，最大区别是<prop>的值都是String

                注意：
             使用设值注入必须要有set方法，通过name属性找set方法

                优势：a、通过set()方法设定更直观，更自然
                      b、当依赖关系较复杂时，使用set()方法更清晰
             构造子注入：必须要有无参和带参的构造方法，加上index（值从0开始）属性避免注入混淆
               `<constractor-arg>`

             注意：设值注入可以和构造子注入混合使用。先调用构造方法产生对象，再调用set()方法赋值。但只使用设值注入时，会先调用无参的构造方法

             优势：a、依赖关系一次性设定，对外不准修改

                   b、无需关心方式

                   c、注入有先后顺序，防止注入失败





作用：

a. 国际化支持
b. 资源访问：Resource rs = ctx. getResource(“classpath:config.properties”), “file:c:/config.properties”
c. 事件传递：通过实现ApplicationContextAware接口
3. 常用的获取ApplicationContext的方法：
FileSystemXmlApplicationContext：从文件系统或者url指定的xml配置文件创建，参数为配置文件名或文件名数组
ClassPathXmlApplicationContext：从classpath的xml配置文件创建，可以从jar包中读取配置文件
WebApplicationContextUtils：从web应用的根目录读取配置文件，需要先在web.xml中配置，可以配置监听器或者servlet来实现

   ```XML
   <listener>
      <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
   </listener>
   <servlet>
      <servlet-name>context</servlet-name>
      <servlet-class>org.springframework.web.context.ContextLoaderServlet</servlet-class>
      <load-on-startup>1</load-on-startup>
   </servlet>
   这两种方式都默认配置文件为web-inf/applicationContext.xml，也可使用context-param指定配置文件
   <context-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>/WEB-INF/myApplicationContext.xml</param-value>
   </context-param>
   ```

### BeanFactory 和 ApplicationContext 的区别

BeanFactory 和 ApplicationContext 的一个大的区别是：BeanFactory 在初始化容器时，并未实例化Bean，直到第一次访问某个Bean时才实例目标 Bean；而 ApplicationContext 则在初始化应用上下文时就实例化所有单实例的 Bean。
ApplicationContext会利用Java反射机制自动识别出配置文件中定义的 BeanPostProcessor、InstantiationAwareBeanPostProcessor 和 BeanFactoryPostProcessor，并将它们自动注册到应用上下文中；而BeanFactory需要我们手工调用 addBeanPostProcessor() 方法进行注册.下面我们来看一下 ApplicationContext 中 Bean 的生命周期的图示：

![AbstractApplicationContext.refresh()](/pic\java\Spring\AbstractApplicationContext.refresh方法.jpg "AbstractApplicationContext.refresh")
