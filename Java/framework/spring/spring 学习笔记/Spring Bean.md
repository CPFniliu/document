# spring Bean

[TOC]

## 相关网页

   > [Spring中Bean的生命中期与InitializingBean和DisposableBean接口](https://blog.csdn.net/czplplp_900725/article/details/24932669)
   > [Spring中Bean的五大作用域及其生命周期](https://blog.csdn.net/qq_40587575/article/details/80007257)

## spring Bean 作用域

类别 | 说明 | 使用相关
-|-|-
singleton | 单例模式, 在整个Spring IoC容器中，使用singleton定义的Bean将只有一个实例
prototype | 原型模式, 每次通过容器的getBean方法获取 prototype 定义的Bean时，都将产生一个新的Bean实例
request | 对于每次HTTP请求，使用request定义的Bean都将产生一个新实例，即每次HTTP请求将会产生不同的Bean实例 | 只有在Web应用中使用Spring时，该作用域才有效(仅适用于WebApplicationContext环境)
session | 对于每次HTTP Session，使用 session 定义的Bean都将产生一个新实例。| ^
global session | 每个全局的HTTP Session，使用session定义的Bean都将产生一个新实例。典型情况下，仅在使用portlet context的时候有效。| ^
自定义的Scope | --- | ---

## Spring Bean 生命周期

在Spring中，我们可以从两个层面定义Bean的生命周期：第一个层面是Bean的作用范围(Singleton、Prototype以及Web中的Request、Session和global session)，第二个层面是实例化Bean时所经历的一系列阶段。

### Spring Bean 生命周期流程

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
   5. BeanNameAware.setBeanName();
      BeanFactoryAware.setBeanFactory();
      ApplicationContextAware.setApplicationContext();
      ResourceLoaderAware.setResourceLoader();
      ServletContextAware.setServletContextAware();

3. BeanPostProcessor的调用
   1. BeanPostProcessor.postProcessBeforeInitialization(Object o, String s);
   2. InitializingBean.afterPropertiesSet();
   3. 调用\<bean\> 的 init-method 属性指定的初始化方法。
   4. BeanPostProcessor.postProcessAfterInitialization(Object o, String s);
   5. instantiationAwareBeanPostProcessor.postProcessAfterInitialization(Object o, String s);

   如果在\<bean\>中指定Bean的作用范围为scope=“prototype”，将Bean返回给调用者，调用者负责Bean后续生命周期的管理，Spring不再管理这个Bean的生命周期。如果作用范围设置为scope=“singleton”，则将Bean放入到Spring IoC容器的缓存池中，并将Bean的应用返回给调用者，Spring继续对这些Bean进行后续的生命管理。

   ---正常执行---

4. Bean销毁阶段.
   1. 调用 DisposableBean 接口的 destory 方法
   2. 调用 \<bean\> 的 destory-method 属性指定的初始化方法。

>当以portotype模式部署一个bean的时候，bean的生命周期将会有少许的变化。通过定义，Spring无法管理一个non-singleton/prototype bean的整个生命周期，因为当它创建之后，它被交给客户端而且容器根本不再留意它了。当说起non-singleton/prototype bean的时候，你可以把Spring的角色想象成“new”操作符的替代品。从那之后的任何生命周期方面的事情都由客户端来处理。

## spring Bean 相关方法

### 各种接口方法分类

Bean的完整生命周期经历了各种方法调用，这些方法可以划分为以下几类：

1. Bean自身的方法：
   这个包括了Bean本身调用的方法和通过配置文件中\<bean\>的init-method和destroy-method指定的方法
2. Bean级生命周期接口方法：
   这个包括了BeanNameAware、BeanFactoryAware、InitializingBean和DiposableBean这些接口的方法
3. 容器级生命周期接口方法：
   这个包括了 InstantiationAwareBeanPostProcessor 和 BeanPostProcessor 这两个接口实现，一般称它们的实现类为“后处理器”。
4. 工厂后处理器接口方法：
   这个包括了AspectJWeavingEnabler, ConfigurationClassPostProcessor, CustomAutowireConfigurer等等非常有用的工厂后处理器接口的方法。工厂后处理器也是容器级的。在应用上下文装配配置文件之后立即调用。

### 容器级生命周期接口方法

#### BeanFactoryPostProcessor

可以在 spring 的 bean 创建之前，修改 bean 的定义属性。也就是说，Spring 允许 BeanFactoryPostProcessor 在容器实例化任何其它bean之前读取配置元数据，并可以根据需要进行修改.
> 可以把bean的scope从singleton改为prototype，也可以把property的值给修改掉。
> 可以同时配置多个BeanFactoryPostProcessor，并通过设置'order'属性来控制各个BeanFactoryPostProcessor的执行次序。

#### BeanPostProcessor

Spring将在初始化bean前后对BeanPostProcessor实现类进行回调.
BeanPostProcessor接口将对所有的bean都起作用，即所有的bean初始化前后都会回调BeanPostProcessor实现类
用户可以使用该方法对某些Bean进行特殊的处理，甚至改变Bean的行为，BeanPostProcessor在Spring框架中占有重要的地位，为容器提供对Bean进行后续加工处理的切入点，Spring容器所提供的各种“神奇功能”(如果AOP、动态代理等)都通过BeanPostProcessor实施；

#### [InstantiationAwareBeanPostProcessor](https://blog.csdn.net/u010634066/article/details/80321854)

#### BeanFactoryPostProcessor 和 BeanPostProcessor

   > [Spring的BeanFactoryPostProcessor和BeanPostProcessor](https://blog.csdn.net/caihaijiang/article/details/35552859?utm_source=blogxgwz3)

### Bean级生命周期接口方法

#### BeanNameAware & BeanFactoryAware & ApplicationContextAware & ResourceLoaderAware & ServletContextAware

   1. BeanNameAware的setBeanName()：如果Bean类有实现org.springframework.beans.BeanNameAware接口，工厂调用Bean的setBeanName()方法传递Bean的ID。

   2. BeanFactoryAware的setBeanFactory()：如果Bean类有实现org.springframework.beans.factory.BeanFactoryAware接口，工厂调用setBeanFactory()方法传入工厂自身。
   ......

#### InitializingBean & DisposableBean 

   Spring提供了一些标志接口，用来改变BeanFactory中的bean的行为。它们包括InitializingBean和DisposableBean。

   实现这些接口将会导致BeanFactory调用前一个接口的afterPropertiesSet()方法，调用后一个接口destroy()方法，从而使得bean可以在初始化和析构后做一些特定的动作。

   在内部，Spring使用BeanPostProcessors 来处理它能找到的标志接口以及调用适当的方法。如果你需要自定义的特性或者其他的Spring没有提供的生命周期行为，你可以实现自己的 BeanPostProcessor。

   相对于BeanPostProcessor，InitializingBean 和 DisposableBean 接口是针对单个bean的，即只有在对应的 bean 实现了 InitializingBean 或 DisposableBean 接口才会对其进行回调。

### Bean自身方法

通过 init-method & destroy-method 指向的方法

> **重要的提示**：
注意：通常 InitializingBean & DisposableBean 接口的使用是能够避免的（而且不鼓励，因为没有必要把代码同Spring耦合起来）。Bean的定义支持指定一个普通的初始化方法或析构方法。在使用 XmlBeanFactory 的情况下，可以通过指定 init-method or destroy-method 属性来完成, 例如，如下面代码所示，1和2，3和4实现效果是相同的，但却不把代码耦合于Spring。

   1. InitializingBean
      实现org.springframework.beans.factory.InitializingBean 接口允许一个bean在它的所有必须的属性被BeanFactory设置后，来执行初始化的工作。InitializingBean接口仅仅制定了一个方法：
      ```HTML
      <bean id="exampleInitBean" class="examples.AnotherExampleBean"/>
      ```
      ```JAVA
      public class AnotherExampleBean implements DisposableBean {
         @Override
         public void afterPropertiesSet() throws Exception {
             // do some initialization work
         }
      }
      ```
   2. init-method
      ```HTML
      <bean id="exampleInitBean" class="examples.ExampleBean" init-method="init"/>
      ```
      ```JAVA
      public class ExampleBean {
          public void init() {
              // do some initialization work
          }
      }
      ```
   3. DisposableBean
      实现org.springframework.beans.factory.DisposableBean接口允许一个bean，可以在包含它的BeanFactory销毁的时候得到一个回调。DisposableBean也只指定了一个方法：
      ```HTML
      <bean id="exampleInitBean" class="examples.AnotherExampleBean"/>
      ```
      ```JAVA
      public class AnotherExampleBean implements DisposableBean {
         @Override
         public void destroy() throws Exception {
             // do some destruction work (like closing connection)
         }
      }
      ```
   4. destroy-method
      ```HTML
      <bean id="exampleInitBean" class="examples.ExampleBean" destroy-method="destroy"/>
      ```
      ```JAVA
      public class ExampleBean {
          public void destroy() {
              // do some destruction work (like closing connection)
          }
      }
      ```
---
