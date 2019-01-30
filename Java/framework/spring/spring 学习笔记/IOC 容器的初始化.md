# IOC 容器的初始化(参考沽泡学院Tom老师文档做的笔记)

IOC 容器的初始化包括 BeanDefinition 的 Resource 定位、载入和注册这三个基本的过程。我们以 ApplicationContext 为例讲解，ApplicationContext 系列容器也许是我们最熟悉的，因为 Web 项目中使用的 XmlWebApplicationContext 就属于这个继承体系，还有 ClasspathXmlApplicationContext

## ApplicationContext 的相应结构图

![ApplicationContext](/assets/frame/spring/ApplicationContext.png)

## FileSystemXmlApplicationContext 的 IOC 容器流程

## FileSystemXmlApplicationContext 加载配置文件

```java
String classPath = "E:\\pdata\\resources\\beans.xml";
FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext(classPath);
```

实际调用的是

```java
   public FileSystemXmlApplicationContext(String configLocation) throws BeansException {
      this(new String[] {configLocation}, true, null);
   }

   public FileSystemXmlApplicationContext(
         String[] configLocations, boolean refresh, @Nullable ApplicationContext parent)
         throws BeansException {
      // 逐级向上调用super(parent); 这样为不同的 Spring 应用提供了一个共享的 Bean 定义环境。保持父上下文可以维持一个上下文体系。
      super(parent);
      // 设置资源路径
      setConfigLocations(configLocations);
      if (refresh) {
         // 调用 AbstractApplicationContext 的 refresh() 方法
         refresh();
      }
   }
```

### 设置资源加载器和资源定位

1. 通过分析 FileSystemXmlApplicationContext 的源代码可以知道，在创建 FileSystemXmlApplicationContext 容器时，构造方法做以下两项重要工作：首先，调用父类容器的构造方法 (super(parent) 方法)为容器设置好 Bean 资源加载器。然后，再调用父类 AbstractRefreshableConfigApplicationContext 的 setConfigLocations(configLocations)方法设置 Bean 定义资源文件的定位路径。
2. 通过追踪 FileSystemXmlApplicationContext 的继承体系，发现之后在 AbstractApplicationContext 构造方法中调用 PathMatchingResourcePatternResolver 的构造方法创建 Spring 资源加载器,
3. 在设置容器的资源加载器之后 ， 接下来 FileSystemXmlApplicationContext 执行setConfigLocations 方法通过调用其父类 AbstractRefreshableConfigApplicationContext 的方法进行对 Bean 定义资源文件的定位

### AbstractApplicationContext 的 refresh() 方法

SpringIOC 容器对 Bean 定义资源的载入是从 refresh()函数开始的，refresh()是一个模板方法，refresh()方法的作用是：在创建 IOC 容器前，如果已经有容器存在，则需要把已有的容器销毁和关闭，以保证在 refresh 之后使用的是新建立起来的 IOC 容器。refresh 的作用类似于对 IOC 容器的重启，在新建立好的容器中对容器进行初始化，对 Bean 定义资源进行载入

```java
public void refresh() throws BeansException, IllegalStateException {
   synchronized (this.startupShutdownMonitor) {
      //调用容器准备刷新的方法，获取容器的当时时间，同时给容器设置同步标识
      prepareRefresh();
      //告诉子类启动 refreshBeanFactory()方法，Bean 定义资源文件的载入从子类的 refreshBeanFactory()方法启动
      ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
      //为 BeanFactory 配置容器特性，例如类加载器、事件处理器等
      prepareBeanFactory(beanFactory);

      try {
         //为容器的某些子类指定特殊的 BeanPost 事件处理器
         postProcessBeanFactory(beanFactory);
         //调用所有注册的 BeanFactoryPostProcessor 的 Bean
         invokeBeanFactoryPostProcessors(beanFactory);
         //为 BeanFactory 注册 BeanPost 事件处理器.
         //BeanPostProcessor 是 Bean 后置处理器，用于监听容器触发的事件
         registerBeanPostProcessors(beanFactory);
         //初始化信息源，和国际化相关.
         initMessageSource();
         //初始化容器事件传播器.
         initApplicationEventMulticaster();
         //调用子类的某些特殊 Bean 初始化方法
         onRefresh();
         //为事件传播器注册事件监听器.
         registerListeners();
         //初始化所有剩余的单例 Bean
         finishBeanFactoryInitialization(beanFactory);
         //初始化容器的生命周期事件处理器，并发布容器的生命周期事件
         finishRefresh();
      }
      catch (BeansException ex) {
         if (logger.isWarnEnabled()) {
         logger.warn("Exception encountered during context initialization - cancelling refresh attempt: " + ex);
         }
         //销毁已创建的 Bean
         destroyBeans();
         //取消 refresh 操作，重置容器的同步标识.
         cancelRefresh(ex);
         throw ex;
      }
      finally {
         resetCommonCaches();
      }
   }
}
```

refresh()方法主要为 IOC 容器 Bean 的生命周期管理提供条件，Spring IOC 容器载入 Bean 定义资源文件从其子类容器的 refreshBeanFactory() 方法启动， 所 以整个 refresh() 中

```java
ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
```

这句以后代码的都是注册容器的信息源和生命周期事件，载入过程就是从这句代码启动。

#### AbstractApplicationContext 的 obtainFreshBeanFactory()

1. AbstractApplicationContext 的 refreshBeanFactory()方法，启动容器载入 Bean 定义资源文件的过程，代码如下：

   ```java
   // class : AbstractApplicationContext
   protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
      //这里使用了委派设计模式，父类定义了抽象的 refreshBeanFactory()方法，具体实现调用子类容器 AbstractRefreshableApplicationContext 的 refreshBeanFactory()方法
      refreshBeanFactory();
      ConfigurableListableBeanFactory beanFactory = getBeanFactory();
      if (logger.isDebugEnabled()) {
         logger.debug("Bean factory for " + getDisplayName() + ": " + beanFactory);
      }
      return beanFactory;
   }
   ```

2. AbstractApplicationContext 类中只抽象定义了 refreshBeanFactory()方法，容器真正调用的是其子类 AbstractRefreshableApplicationContext 实现的 refreshBeanFactory()方法, 在这个方法中，先判断 BeanFactory 是否存在，如果存在则先销毁 beans 并关闭 beanFactory，接着创建DefaultListableBeanFactory，并调用 loadBeanDefinitions(beanFactory)装载 bean 定义。

   ```java
   // class : AbstractRefreshableApplicationContext
   @Override
   protected final void refreshBeanFactory() throws BeansException {
      //如果已经有容器，销毁容器中的 bean，关闭容器
      if (hasBeanFactory()) {
         destroyBeans();
         closeBeanFactory();
      }
      try {
         //创建 IOC 容器
         DefaultListableBeanFactory beanFactory = createBeanFactory();
         beanFactory.setSerializationId(getId());
         //对 IOC 容器进行定制化，如设置启动参数，开启注解的自动装配等
         customizeBeanFactory(beanFactory);
         //调用载入 Bean 定义的方法，主要这里又使用了一个委派模式，在当前类中只定义了抽象的 loadBeanDefinitions 方法，具体的实现调用子类容器
         loadBeanDefinitions(beanFactory);
         synchronized (this.beanFactoryMonitor) {
            this.beanFactory = beanFactory;
         }
      }
      catch (IOException ex) {
         throw new ApplicationContextException("I/O error parsing bean definition source for " + getDisplayName(), ex);
      }
   }
   ```

AbstractRefreshableApplicationContext中只定义了抽象的loadBeanDefinitions方法，容器真正调用的是其子类AbstractXmlApplicationContext对该方法的实现

AbstractXmlApplicationContext的 loadBeanDefinitions方法创建了 XmlBeanDefinitionReader, 即创建 Bean 读取器，并通过回调设置到容器中去，容器使用该读取器读取 Bean 定义资源, 设置 SAX xml 解析器. 再经过Xml校验机制, 之后根据不同种类的 XmlApplicationContext 执行不同的loadBeanDefinitions操作, 最终资源加载器获取要读入的资源.

XmlBeanDefinitionReader将 Bean 定义资源转换为 Document 对象：交由 DefaultBeanDefinitionDocumentReader 对Bean 进行解析

DefaultBeanDefinitionDocumentReader对Bean定义转换的Document对象解析的流程中，在其parseDefaultElement方法中完成对Document对象的解析后得到封装BeanDefinition的BeanDefinitionHold对象，然后调用BeanDefinitionReaderUtils的registerBeanDefinition方法向IOC容器注册解析的Bean，而调用 BeanDefinitionReaderUtils 向 IOC 容器注册解析的 BeanDefinition 时，真正完成注册功能的是 DefaultListableBeanFactory。
**DefaultListableBeanFactory中使用一个HashMap的集合对象存放IOC容器中注册解析的BeanDefinition**

```java
// class : DefaultListableBeanFactory
   /** Map of bean definition objects, keyed by bean name */
   private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

   //向 IOC 容器注册解析的 BeanDefiniton
   @Override
   public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
      ... ...
   }
```

至此，Bean定义资源文件中配置的Bean被解析过后，已经注册到IOC容器中，被容器管理起来，真正完成了IOC容器初始化所做的全部工作。现在IOC容器中已经建立了整个Bean的配置信息，这些BeanDefinition信息已经可以使用，并且可以被检索，IOC容器的作用就是对这些注册的Bean定义信息进行处理和维护。这些的注册的Bean定义信息是IOC容器控制反转的基础，正是有了这些注册的数据，容器才可以进行依赖注入。

总结：
现在通过上面的代码，总结一下IOC容器初始化的基本步骤：
(1).初始化的入口在容器实现中的refresh()调用来完成。
(2).对bean定义载入IOC容器使用的方法是loadBeanDefinition,其中的大致过程如下：通过ResourceLoader来完成资源文件位置的定位，DefaultResourceLoader
是默认的实现，同时上下文本身就给出了ResourceLoader的实现，可以从类路径，文件系统,URL等方式来定为资源位置。如果是XmlBeanFactory作为IOC容器，那么需要为它指定bean定义的资源，也就是说bean定义文件时通过抽象成Resource来被IOC容器处理的，容器通过BeanDefinitionReader来完成定义信息的解析和Bean信息的注册,往往使用的是XmlBeanDefinitionReader来解析bean的xml定义文件-实际的处理过程是委托给BeanDefinitionParserDelegate来完成的，从而得到bean的定义信息，这些信息在Spring中使用BeanDefinition对象来表示-这个名字可以让我们想到loadBeanDefinition,RegisterBeanDefinition这些相关方法-他们都是为处理BeanDefinitin服务的，容器解析得到BeanDefinition以后，需要把它在IOC容器中注册，这由IOC实现BeanDefinitionRegistry接口来实现。注册过程就是在IOC容器内部维护的一个HashMap来保存得到的BeanDefinition的过程。这个HashMap是IOC容器持有Bean信息的场所，以后对Bean的操作都是围绕这个HashMap来实现的。

然后我们就可以通过BeanFactory和ApplicationContext来享受到SpringIOC的服务了,在使用IOC容器的时候，我们注意到除了少量粘合代码，绝大多数以正确IOC风格编写的应用程序代码完全不用关心如何到达工厂，因为容器将把这些对象与容器管理的其他对象钩在一起。基本的策略是把工厂放到已知的地方，最好是放在对预期使用的上下文有意义的地方，以及代码将实际需要访问工厂的地方。Spring本身提供了对声明式载入web应用程序用法的应用程序上下文,并将其存储在ServletContext中的框架实现。

## IOC容器个人理解

IOC 容器的初始化包括 BeanDefinition 的 Resource 定位、载入和注册这三个基本的过程

### IOC 容器的初始化

1. 定位
   1. 设置资源加载器
      调用父类 AbstractApplicationContext.PathMatchingResourcePatternResolver 方法为容器设置好 Bean 资源加载器,再调用父类 AbstractRefreshableConfigApplicationContext.setConfigLocations(configLocations)方法设置 Bean 定义资源文件的定位路径。
   2. AbstractApplicationContext.refresh() 初始化容器
2. 在创建 IOC 容器前，如果已经有容器存在，则需要把已有的容器销毁和关闭，以保证在 refresh 之后使用的是新建立起来的 IOC 容器。refresh 的作用类似于对 IOC 容器的重启，在新建立好的容器中对容器进行初始化，对 Bean 定义资源进行载入

```java
refresh(){
   // 方法，启动容器载入 Bean 定义资源文件的过程
   AbstractApplicationContext.refreshBeanFactory(){
      AbstractRefreshableApplicationContext.refreshBeanFactory(){
         创建DefaultListableBeanFactory
         AbstractXmlApplicationContext.loadBeanDefinitions(beanFactory)装载 bean 定义。{
            创建了Bean 读取器 XmlBeanDefinitionReader，并通过回调设置到容器中去，容器使用该读取器读取 Bean 定义资源, 设置 SAX xml 解析器, 再经过Xml校验机制.
            之后根据不同种类的 XmlApplicationContext 执行不同的loadBeanDefinitions操作, 最终资源加载器获取要读入的资源.{
               1. XmlBeanDefinitionReader将 Bean 定义资源转换为 Document 对象：交由 DefaultBeanDefinitionDocumentReader 对Bean 进行解析,
               2. DefaultBeanDefinitionDocumentReader将Document解析后得到封装BeanDefinition的BeanDefinitionHold对象，
               3. 调用BeanDefinitionReaderUtils的registerBeanDefinition方法向IOC容器注册解析的Bean，{
                  // 注册Bean
                  DefaultListableBeanFactory中使用一个HashMap的集合对象存放IOC容器中注册解析的BeanDefinition
               }
            }
         }
      }
   }
}
```

### IOC 容器的注入

以来完成之后就是注入的过程了。在IOC容器中的BeanDefinition是怎么注入的呢。
1.注入由AbstractAutowireCapableBeanFactory中的方法populateBean()方法完成（包括了对autoware属性的处理）
2.在该方法中通过BeanDefinitionResolver来对BeanDefinition进行解析，并注入到property中
3.至此完成IOC容器的初始化以及依赖注入。
注
1.具体的BeanWrapper的setPropertyValues.
2.在IOC容器初始化完成之后，可以通过lazy-init属性来设置是否立即进行依赖注入。lazy-init属性定义在BeanDefinition中。
3.IOC容器的后置监听器BeanPostProcessor.
至此是IOC容器的初始化以及依赖注入的过程简单描述。具体细节描述待日后更新。
晚安世界。O(∩_∩)O！
