1. AbstractApplicationContext
2. AbstractRefreshableApplicationContext
3. AbstractRefreshableConfigApplicationContext
4. AbstractXmlApplicationContext
5. FileSystemXmlApplicationContext

AbstractApplicationContext.refresh(){
// Prepare this context for refreshing.
prepareRefresh();

// Tell the subclass to refresh the internal bean factory.
ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();{
   // 在这个方法中，先判断 BeanFactory 是否存在，如果存在则先销毁 beans 并关闭 beanFactory，接着创建 DefaultListableBeanFactory，并调用 loadBeanDefinitions(beanFactory)装载 bean 定义。
   refreshBeanFactory(); --> AbstractRefreshableApplicationContext.refreshBeanFactory(){
      // 如果已经有容器，销毁容器中的 bean，关闭容器
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
         loadBeanDefinitions(beanFactory); --> AbstractXmlApplicationContext {
            //实现父类抽象的载入 Bean 定义方法
            @Override
            protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException{
               //创建 XmlBeanDefinitionReader，即创建 Bean 读取器，并通过回调设置到容器中去，容器使用该读取器读取 Bean 定义资源
               XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
               //为 Bean 读取器设置 Spring 资源加载器，AbstractXmlApplicationContext 的
               //祖先父类 AbstractApplicationContext 继承 DefaultResourceLoader，因此，容器本身也是一个资源加载器
               beanDefinitionReader.setEnvironment(this.getEnvironment());
               beanDefinitionReader.setResourceLoader(this);
               //为 Bean 读取器设置 SAX xml 解析器
               beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));
               //当 Bean 读取器读取 Bean 定义的 Xml 资源文件时，启用 Xml 的校验机制
               initBeanDefinitionReader(beanDefinitionReader);
               //Bean 读取器真正实现加载的方法
               loadBeanDefinitions(beanDefinitionReader);
            }

            protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader) {
               reader.setValidating(this.validating);
            }

            //Xml Bean 读取器加载 Bean 定义资源
            protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
               //获取 Bean 定义资源的定位
               Resource[] configResources = getConfigResources();
               if (configResources != null) {
                  //Xml Bean 读取器调用其父类 AbstractBeanDefinitionReader 读取定位的 Bean 定义资源
                  reader.loadBeanDefinitions(configResources);
               }
               // 如果子类中获取的 Bean 定义资源定位为空，则获取 FileSystemXmlApplicationContext
               // 构造方法中 setConfigLocations 方法设置的资源
               String[] configLocations = getConfigLocations();
               if (configLocations != null) {
                  //Xml Bean 读取器调用其父类 AbstractBeanDefinitionReader 读取定位
                  //的 Bean 定义资源
                  reader.loadBeanDefinitions(configLocations);
               }
            }

               //这里又使用了一个委托模式，调用子类的获取 Bean 定义资源定位的方法
               //该方法在 ClassPathXmlApplicationContext 中进行实现，对于我们
               //举例分析源码的 FileSystemXmlApplicationContext 没有使用该方法
            @Nullable
            protected Resource[] getConfigResources() {
               return null;
            }

         }
         synchronized (this.beanFactoryMonitor) {
            this.beanFactory = beanFactory;
         }
      }
   }
   ConfigurableListableBeanFactory beanFactory = getBeanFactory();
   if (logger.isDebugEnabled()) {
   logger.debug("Bean factory for " + getDisplayName() + ": " + beanFactory);
   }
   return beanFactory;
}

// Prepare the bean factory for use in this context.
prepareBeanFactory(beanFactory);

try {
// Allows post-processing of the bean factory in context subclasses.
postProcessBeanFactory(beanFactory);

// Invoke factory processors registered as beans in the context.
invokeBeanFactoryPostProcessors(beanFactory);

// Register bean processors that intercept bean creation.
registerBeanPostProcessors(beanFactory);

// Initialize message source for this context.
initMessageSource();

// Initialize event multicaster for this context.
initApplicationEventMulticaster();

// Initialize other special beans in specific context subclasses.
onRefresh();

// Check for listener beans and register them.
registerListeners();

// Instantiate all remaining (non-lazy-init) singletons.
finishBeanFactoryInitialization(beanFactory);

// Last step: publish corresponding event.
finishRefresh();
}
