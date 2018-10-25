### Spring Bean 生命周期
1. BeanFactoyPostProcessor 实例化
   1. 实例化 Bean 工厂后处理器 : BeanFactoyPostProcessor。
   2. beanFactoyPostProcessor.postProcessBeanFactory();
   3. 实例化Bean后处理器 : BeanPostProcessor。
   4. 实例化感知的bean后处理器适配器 ：InstantiationAwareBeanPostProcessorAdapter。

2. Bean实例化，然后通过某些BeanFactoyPostProcessor来进行依赖注入
   1. instantiationAwareBeanPostProcessor.postProcessBeforeInstantiation();
   2. 执行 bean 的构造器。
   3. instantiationAwareBeanPostProcessor.postProcessPropertyValues();
   4. 为 Bean 注入属性。

3. BeanPostProcessor的调用
   1. BeanNameAware.setBeanName();
      BeanFactoryAware.setBeanFactory();
      ApplicationContextAware.setApplicationContext();
      ResourceLoaderAware.setResourceLoader();
      ServletContextAware.setServletContextAware();

   2. BeanPostProcessor.postProcessBeforeInitialization(Object o, String s);
   3. InitializingBean.afterPropertiesSet();
   4. 调用 <bean> 的 init-method 属性指定的初始化方法。
   5. BeanPostProcessor.postProcessAfterInitialization(Object o, String s);
   6. instantiationAwareBeanPostProcessor.postProcessAfterInitialization(Object o, String s);

   ---正常执行---

4. Bean销毁阶段.
   1. 调用 DisposableBean 接口的 destory 方法
   2. 调用 <bean> 的 destory-method 属性指定的初始化方法。


二、各种接口方法分类


### 各种接口方法分类

Bean的完整生命周期经历了各种方法调用，这些方法可以划分为以下几类：

1、Bean的建立：容器寻找Bean的定义信息并将其实例化。

2、Setter注入执行Bean的属性依赖注入。

3、BeanNameAware的setBeanName()：如果Bean类有实现org.springframework.beans.BeanNameAware接口，工厂调用Bean的setBeanName()方法传递Bean的ID。

4、BeanFactoryAware的setBeanFactory()：如果Bean类有实现org.springframework.beans.factory.BeanFactoryAware接口，工厂调用setBeanFactory()方法传入工厂自身。

5、BeanPostProcessors的ProcessBeforeInitialization()：如果有org.springframework.beans.factory.config.BeanPostProcessors和Bean关联，那么其postProcessBeforeInitialization()方法将被将被调用。

6、initializingBean的afterPropertiesSet()：如果Bean类已实现org.springframework.beans.factory.InitializingBean接口，则执行他的afterProPertiesSet()方法。

7、Bean定义文件中定义init-method：可以在Bean定义文件中使用"init-method"属性设定方法名称例如：如果有以上设置的话，则执行到这个阶段，就会执行initBean()方法。

8、BeanPostProcessors的ProcessaAfterInitialization()：如果有任何的BeanPostProcessors实例与Bean实例关联，则执行BeanPostProcessors实例的ProcessaAfterInitialization()方法。

此时，Bean已经可以被应用系统使用，并且将保留在BeanFactory中知道它不在被使用。有两种方法可以将其从BeanFactory中删除掉。

9、DisposableBean的destroy()：在容器关闭时，如果Bean类有实现org.springframework.beans.factory.DisposableBean接口，则执行他的destroy()方法。

10、Bean定义文件中定义destroy-method方法。




Bean的完整生命周期经历了各种方法调用，这些方法可以划分为以下几类：

   1、Bean自身的方法 : 这个包括了Bean本身调用的方法和通过配置文件中<bean>的init-method和destroy-method指定的方法
   2、Bean级生命周期接口方法 : 这个包括了BeanNameAware、BeanFactoryAware、InitializingBean 和 DiposableBean 这些接口的方法
   3、容器级生命周期接口方法 : 这个包括了 InstantiationAwareBeanPostProcessor 和 BeanPostProcessor 这两个接口实现，一般称它们的实现类为“后处理器”。
   4、工厂后处理器接口方法 : 这个包括了BeanFactoryPostProcessor等等非常有用的工厂后处理器接口的方法。工厂后处理器也是容器级的。在应用上下文装配配置文件之后立即调用。


#### BeanFactoryPostProcessor 接口
   可以在 spring 的 bean 创建之前，修改 bean 的定义属性。也就是说，Spring 允许 BeanFactoryPostProcessor 在容器实例化任何其它bean之前读取配置元数据，并可以根据需要进行修改.
   > 可以把bean的scope从singleton改为prototype，也可以把property的值给修改掉。
     可以同时配置多个BeanFactoryPostProcessor，并通过设置'order'属性来控制各个BeanFactoryPostProcessor的执行次序。



#### BeanFactoryPostProcessor和BeanPostProcessor
   >[Spring的BeanFactoryPostProcessor和BeanPostProcessor - caihaijiang的专栏 - CSDN博客](https://blog.csdn.net/caihaijiang/article/details/35552859?utm_source=blogxgwz3)


#### InitializingBean 和 DisposableBean
   > [Spring中Bean的生命中期与InitializingBean和DisposableBean接口](https://blog.csdn.net/czplplp_900725/article/details/24932669)


1. Spring提供了一些标志接口，用来改变BeanFactory中的bean的行为。它们包括InitializingBean和DisposableBean。
实现这些接口将会导致BeanFactory调用前一个接口的afterPropertiesSet()方法，调用后一个接口destroy()方法，从而使得bean可以在初始化和析构后做一些特定的动作。

在内部，Spring使用BeanPostProcessors 来处理它能找到的标志接口以及调用适当的方法。如果你需要自定义的特性或者其他的Spring没有提供的生命周期行为，
你可以实现自己的 BeanPostProcessor。关于这方面更多的内容可以看这里：第 3.7 节“使用BeanPostprocessors定制bean”。

所有的生命周期的标志接口都在下面叙述。在附录的一节中，你可以找到相应的图，展示了Spring如何管理bean；那些生命周期的特性如何改变你的bean的本质特征以及它们如何被管理。

1. InitializingBean / init-method

实现org.springframework.beans.factory.InitializingBean 接口允许一个bean在它的所有必须的属性被BeanFactory设置后，来执行初始化的工作。InitializingBean接口仅仅制定了一个方法：

* Invoked by a BeanFactory after it has set all bean properties supplied    * (and satisfied BeanFactoryAware and ApplicationContextAware).    * <p>This method allows the bean instance to perform initialization only    * possible when all bean properties have been set and to throw an    * exception in the event of misconfiguration.    * @throws Exception in the event of misconfiguration (such    * as failure to set an essential property) or if initialization fails.    */    void afterPropertiesSet() throws Exception;

注意：通常InitializingBean接口的使用是能够避免的（而且不鼓励，因为没有必要把代码同Spring耦合起来）。Bean的定义支持指定一个普通的初始化方法。在使用XmlBeanFactory的情况下，可以通过指定init-method属性来完成。举例来说，下面的定义：

<bean id="exampleInitBean" class="examples.ExampleBean" init-method="init"/>public class ExampleBean {    public void init() {        // do some initialization work    }}

同下面的完全一样：

<bean id="exampleInitBean" class="examples.AnotherExampleBean"/>public class AnotherExampleBean implements InitializingBean {    public void afterPropertiesSet() {        // do some initialization work    }}

但却不把代码耦合于Spring。

2. DisposableBean / destroy-method

实现org.springframework.beans.factory.DisposableBean接口允许一个bean，可以在包含它的BeanFactory销毁的时候得到一个回调。DisposableBean也只指定了一个方法：

/**    * Invoked by a BeanFactory on destruction of a singleton.    * @throws Exception in case of shutdown errors.    * Exceptions will get logged but not rethrown to allow    * other beans to release their resources too.    */    void destroy() throws Exception;

注意：通常DisposableBean接口的使用能够避免的（而且是不鼓励的，因为它不必要地将代码耦合于Spring）。 Bean的定义支持指定一个普通的析构方法。在使用XmlBeanFactory使用的情况下，它是通过 destroy-method属性完成。举例来说，下面的定义：

<bean id="exampleInitBean" class="examples.ExampleBean" destroy-method="destroy"/>public class ExampleBean {    public void cleanup() {        // do some destruction work (like closing connection)    }}

同下面的完全一样：

<bean id="exampleInitBean" class="examples.AnotherExampleBean"/>public class AnotherExampleBean implements DisposableBean {    public void destroy() {        // do some destruction work    }}

但却不把代码耦合于Spring。

重要的提示：当以portotype模式部署一个bean的时候，bean的生命周期将会有少许的变化。通过定义，Spring无法管理一个non-singleton/prototype bean的整个生命周期，因为当它创建之后，它被交给客户端而且容器根本不再留意它了。当说起non-singleton/prototype bean的时候，你可以把Spring的角色想象成“new”操作符的替代品。从那之后的任何生命周期方面的事情都由客户端来处理。BeanFactory中bean的生命周期将会在第 3.4.1 节“生命周期接口”一节中有更详细的叙述 .



----
