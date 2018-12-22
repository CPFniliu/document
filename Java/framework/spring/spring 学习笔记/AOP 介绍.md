# Spring AOP 学习笔记

[TOC]

## 基础知识

### 示例配置

   ```xml
   <!-- 代理配置 -->
   <aop:config proxy-target-class="true" >
      <!-- 切入点 配置 -->
      <aop:pointcut expression="execution(* cn.cpf.aop.xml.ProxyDemo.testProxy1(..))" id="pt1"/>
      <!-- 切面 配置 -->
      <aop:aspect ref = "xmlProxy1" order="1">
         <!-- 通知（Advice）配置 -->
         <aop:before method="before" pointcut-ref="pt1" />
         <aop:around method="around" pointcut-ref="pt1" />
         <aop:after method="after" pointcut-ref="pt1" />
         <aop:after-throwing method="afterThrow" throwing="e" pointcut-ref="pt1" />
         <aop:after-returning method="afterReturn" returning="rst"  pointcut-ref="pt1" />
      </aop:aspect>
      <aop:aspect ref = "xmlProxy2" order="2">
         <aop:around method="around" pointcut-ref="pt1" />
         <aop:before method="before" pointcut-ref="pt1" />
         <aop:after method="after" pointcut-ref="pt1" />
         <aop:after-throwing method="afterThrow" throwing="e" pointcut-ref="pt1" />
         <aop:after-returning method="afterReturn" returning="rst"  pointcut-ref="pt1" />
      </aop:aspect>
   </aop:config>
   ```

   ```java
   //配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
   @Pointcut("execution(* com.gupaoedu.aop.service..*(..))")
   public void aspect(){ }

   /*
   * 配置前置通知,使用在方法 aspect()上注册的切入点同时接受 JoinPoint 切入点对象,可以没有该参数
   */
   @Before("aspect()")
   public void before(JoinPoint joinPoint){
      log.info("before " + joinPoint);
   }

   //配置后置通知,使用在方法 aspect()上注册的切入点
   @After("aspect()")
   public void after(JoinPoint joinPoint){
      log.info("after " + joinPoint);
   }

   //配置环绕通知,使用在方法 aspect()上注册的切入点
   @Around("aspect()")
   public void around(JoinPoint joinPoint){
      try {
         ((ProceedingJoinPoint) joinPoint).proceed();
      } catch (Throwable e) {
         long end = System.currentTimeMillis();
      }
   }

   //配置后置返回通知,使用在方法 aspect()上注册的切入点
   @AfterReturning("aspect()")
   public void afterReturn(JoinPoint joinPoint){
      log.info("afterReturn " + joinPoint);
   }

   //配置抛出异常后通知,使用在方法 aspect()上注册的切入点
   @AfterThrowing(pointcut="aspect()", throwing="ex")
   public void afterThrow(JoinPoint joinPoint, Exception ex){
      log.info("afterThrow " + joinPoint + "\t" + ex.getMessage());
   }
   ```

### 相关概念

   切面（Aspect）: 对应一个代理类。
   通知（Advice）：对应一个代理类中的一个代理方法。
   切入点（Pointcut）：匹配连接点的断言，在AOP中通知和一个切入点表达式关联。切面中的所有通知所关注的连接点，都由切入点表达式来决定。
   连接点（Joinpoint）：程序执行过程中的某一行为，例如，MemberService.get的调用或者MemberService.delete抛出异常等行为。
   目标对象（TargetObject）：代理对象

### 通知（Advice）类型：

   前置通知（Before advice）：在某连接点之前执行的通知，但这个通知不能阻止连接点之前的执行流程（除非它抛出一个异常）。
   后置通知（After returning advice）：在某连接点正常完成后执行的通知：例如，一个方法没有抛出任何异常，正常返回。
   异常通知（After throwing advice）：在方法抛出异常退出时执行的通知。
   最终通知（After (finally) advice）：当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）。
   环绕通知（Around Advice）：包围一个连接点的通知，如方法调用。这是最强大的一种通知类型。环绕通知可以在方法调用前后完成自定义的行为。它也会选择是否继续执行连接点或直接返回它自己的返回值或抛出异常来结束执行。

   通知方法中可以没有参数, 也可以加一个 JoinPoint 类型的参数用于获取调用代理对象方法的参数信息.

### 代理过程

   spring 代理 分为 JDK 代理, 和 CGlib 动态代理,
   可以通过 proxy-target-class 进行配置, 为true则是基于类的代理将起作用（需要cglib库），为false或者省略这个属性，则会使用标准的JDK 基于接口的代理。
   如果没有配置 proxy-target-class 属性, spring默认走JDK 代理.
   但无论如何配置如果代理对象没有实现接口，spring会自动使用CGLIB代理。

### AOP执行顺序

#### 通知的执行顺序

- 基于 xml 配置 :
   xml的配置通知的顺序决定了执行的顺序, 配在前面的通知先执行.
- 基于 annotation :
   使用注解的情况下, Advices 执行顺序与类种方法排序无关
   正常情况下执行顺序：
      `around start -> before ->around end -> after -> afterreturning`
   异常情况下执行顺序
      `around start -> before -> around throwable -> after -> afterthrowing`

#### 多切面的执行顺序

order 属性 : 一个代理对象, 被多个切面类切入的话, 可以通过设置order属性来配置它们的执行顺序.
order 属性是一个整数, 每个切面默认的 Order 是整数的最大值.

1. order 值不等时
  (同心圆原理, 要执行的方法为圆心，最外层的order最小) 在执行被代理的目标方法之前, order较小的先执行, 在执行被代理的目标方法后order较大的先执行.
  
2. order 值相等时
   - 基于 xml
      xml配置中较前的那个先执行
   - 基于 annotation
      似乎是通过全类名(不太确定, 这个, 但是可以确定不是通过bean的id名)

### Pointcut-execution表达式

   > 官方文档 https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop

   在AspectJ中，切入点表达式可以通过 “&&”、“||”、“!”等操作符结合起来。

   格式

   ```java
      execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
      execution([权限修饰符] [返回值类型] [简单类名/全类名] [方法名]([参数列表]))
   ```

   示例

   ```java
      execution(public int cn.cpf.aop.xml.ProxyObject.execute(String, int))
      execution(* cn.cpf..*.*(..))
      execution(* *.*(..))
      // 任意类中第一个参数为int类型的add方法或sub方法
      execution (* *.add(int,..)) || execution(* *.sub(int,..))
   ```

   标识符 | 含义
   -|-
   execution() | 表达式的主体
   第一个“*”符号 | 表示返回值的类型任意
   com.loongshawn.method.ces | AOP所切的服务的包名，即，需要进行横切的业务类
   包名后面的“..” | 表示当前包及子包
   第二个“*” | 表示类名，*即所有类
   .*(..) | 表示任何方法名，括号表示参数，两个点表示任何参数类型

   The following examples show some common pointcut expressions:

   execution expression | means
   -|-
   execution(public * *(..)) | any public method
   execution(* set*(..)) | any method with a name that begins with set
   execution(* com.xyz.service.AccountService.*(..)) | any method defined by the AccountService interface
   execution(* com.xyz.service.*.*(..)) | any method defined in the service package
   execution(* com.xyz.service..*.*(..)) | any method defined in the service package or one of its sub-packages

### aop 配置属性

#### 属性 proxy-target-class

   proxy-target-class属性值决定AOP代理是基于接口的还是基于类创建。为true则是基于类的代理将起作用（需要cglib库），为false或者省略这个属性，则会使用标准的JDK 基于接口的代理。
   但运行时如果代理对象没有实现接口，spring会自动使用CGLIB代理。

   > proxy-target-class在spring事务、aop、缓存这几块都有设置，其作用都是一样的。
   > [spring的proxy-target-class详解](https://blog.csdn.net/shaoweijava/article/details/76474652)

#### 属性 expose-proxy

   属性值为 true 和 false, true 表示就通过一个ThreadLocal保存代理

   是否暴露当前代理对象为ThreadLocal模式。

## AOP 使用

使用SpringAOP可以基于两种方式，一种是比较方便和强大的注解方式，另一种则是中规中矩的xml配置方式。

### 基于xml 配置

- pom 文件

   ```xml
   <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aop</artifactId>
   </dependency>
   <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
   </dependency>
   <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
   </dependency>
   <dependency>
      <groupId>cglib</groupId>
      <artifactId>cglib</artifactId>
   </dependency>
   ```

- xml 文件

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:context="http://www.springframework.org/schema/context"
      xmlns:aop="http://www.springframework.org/schema/aop"
      xsi:schemaLocation="
         http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context
         http://www.springframework.org/schema/context/spring-context-3.0.xsd
         http://www.springframework.org/schema/aop
         http://www.springframework.org/schema/aop/spring-aop.xsd">

      <!-- 扫描路径 -->
      <context:component-scan base-package="cn.cpf.aop"/>
      <!-- 代理类 -->
      <bean id="xmlProxy1" class="cn.cpf.aop.xml.XmlProxy1"></bean>
      <bean id="xmlProxy2" class="cn.cpf.aop.xml.XmlProxy2"></bean>
      <!-- 代理对象 -->
      <bean id="proxyDemo" class="cn.cpf.aop.xml.ProxyDemo"></bean>

      <!-- 代理配置 -->
      <aop:config proxy-target-class="true">
         <!-- 切入点 配置 -->
         <aop:pointcut expression="execution(* cn.cpf.aop.xml.ProxyDemo.testProxy1(..))" id="pt1"/>
         <!-- 切面 配置 -->
         <aop:aspect ref = "xmlProxy1" order="1">
            <!-- 通知（Advice）配置 -->
            <aop:before method="before" pointcut-ref="pt1" />
            <aop:around method="around" pointcut-ref="pt1" />
            <aop:after method="after" pointcut-ref="pt1" />
            <aop:after-throwing method="afterThrow" throwing="e" pointcut-ref="pt1" />
            <aop:after-returning method="afterReturn" returning="rst"  pointcut-ref="pt1" />
         </aop:aspect>
         <aop:aspect ref = "xmlProxy2" order="2">
            <aop:around method="around" pointcut-ref="pt1" />
            <aop:before method="before" pointcut-ref="pt1" />
            <aop:after method="after" pointcut-ref="pt1" />
            <aop:after-throwing method="afterThrow" throwing="e" pointcut-ref="pt1" />
            <aop:after-returning method="afterReturn" returning="rst"  pointcut-ref="pt1" />
         </aop:aspect>
      </aop:config>
   </beans>
   ```

- 代理类接口（JDK代理使用）

   ```java
   package cn.cpf.aop.xml;

   public interface IProxyDemoService {
      void testProxy1();
      void testProxy2();
   }
   ```

- 代理类(一个抽象类, 两个代理类继承这个抽象类便于打印输出)

   ```java
   package cn.cpf.aop.xml;

   import org.aspectj.lang.ProceedingJoinPoint;

   public abstract class XmlProxy {

      abstract protected void println(String string);

      public void before() {
         println(" before no args ");
      }

      public void after() {
         println(" after ");
      }

      public void afterReturn(Object rst) {
         println(" afterReturn ");
      }

      public void afterThrow(Throwable e) {
         println(" afterThrow " + e.toString());
      }

      public void around(ProceedingJoinPoint pjp) {
         println(" around start ");
         try {
            pjp.proceed();
         } catch (Throwable throwable) {
            println(" around throwable ");
            throwable.printStackTrace();
         }
         println(" around end ");
      }
   }


   package cn.cpf.aop.xml;
   public class XmlProxy1 extends XmlProxy {
      @Override
      protected void println(String string) {
         LoggerUtils.logInfo("XmlProxy 1 == " + string);
      }
   }


   package cn.cpf.aop.xml;
   public class XmlProxy2 extends XmlProxy{
      @Override
      protected void println(String string) {
         LoggerUtils.logInfo("XmlProxy 2 == " + string);
      }
   }
   ```

- 代理对象

   ```java
   package cn.cpf.aop.xml;

   import cn.cpf.aop.common.LoggerUtils;

   public class ProxyDemo implements IProxyDemoService {

      @Override
      public void testProxy1() {
         LoggerUtils.logInfo("执行 testProxy1 方法");
      }

      @Override
      public void testProxy2() {
         LoggerUtils.logInfo("执行 testProxy2 方法");
      }
   }
   ```

- 测试

   ```java
   public static void main(String[] args) {
      ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aop-xml.xml");
      IProxyDemoService proxyDemoService = applicationContext.getBean("proxyDemo", IProxyDemoService.class);
      proxyDemoService.testProxy1();
   }
   ```

   > 通过getBean 获取Bean时, 若该Bean配置了AOP代理, 则返回的是代理对象
   > 注意返回的对象, 如果是走的是JDK代理, 则返回的是JDK代理对象, 需要用接口进行接受返回的值, 否则会因为无法转换而抛出异常.
   > 若是使用的是CGlib代理的话, 可以用接口(如果有的话) 和实体类进行接受.

- 输出

   ```log
   00:52:22.857 [main] INFO cn.cpf.aop.xml.LoggerUtils - XmlProxy 1 ==  before no args
   00:52:22.857 [main] INFO cn.cpf.aop.xml.LoggerUtils - XmlProxy 1 ==  around start
   00:52:22.857 [main] INFO cn.cpf.aop.xml.LoggerUtils - XmlProxy 2 ==  before no args
   00:52:22.857 [main] INFO cn.cpf.aop.xml.LoggerUtils - XmlProxy 2 ==  around start
   00:52:22.869 [main] INFO cn.cpf.aop.xml.LoggerUtils - 执行 testProxy1 方法
   00:52:22.869 [main] INFO cn.cpf.aop.xml.LoggerUtils - XmlProxy 2 ==  around end
   00:52:22.869 [main] INFO cn.cpf.aop.xml.LoggerUtils - XmlProxy 2 ==  after
   00:52:22.869 [main] INFO cn.cpf.aop.xml.LoggerUtils - XmlProxy 2 ==  afterReturn
   00:52:22.869 [main] INFO cn.cpf.aop.xml.LoggerUtils - XmlProxy 1 ==  around end
   00:52:22.869 [main] INFO cn.cpf.aop.xml.LoggerUtils - XmlProxy 1 ==  after
   00:52:22.869 [main] INFO cn.cpf.aop.xml.LoggerUtils - XmlProxy 1 ==  afterReturn
   ```

### besed on annotation

- 注解配置(xml)

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:context="http://www.springframework.org/schema/context"
      xmlns:aop="http://www.springframework.org/schema/aop"
      xsi:schemaLocation="
         http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context
         http://www.springframework.org/schema/context/spring-context-3.0.xsd
         http://www.springframework.org/schema/aop 
         http://www.springframework.org/schema/aop/spring-aop.xsd">

      <!-- 配置注解扫面路径 -->
      <context:component-scan base-package="cn.cpf" />
      <!-- 开启aspectj代理 -->
      <aop:aspectj-autoproxy />
   </beans>
   ```

- 代理类接口（JDK代理使用）

   ```java
   package cn.cpf.aop.annotation;

   public interface IAopAnnotation {

      void testProxy1();

      void testProxy2();
   }
   ```

- Aspect 切面类添加注解(java)

   ```java
   // 代理抽象类, 为了减少代码量便于查看
   package cn.cpf.aop.annotation;

   import org.aspectj.lang.ProceedingJoinPoint;
   import org.aspectj.lang.annotation.*;

   @Aspect
   public abstract class AnnotationProxy {

      abstract protected void println(String string);

      @Pointcut("execution(* cn.cpf.aop.annotation.AnnotationProxyObjectDemo.testProxy1(..))")
      public void pointCut() {}

      // 输入带有 @Pointcut 注解 的方法名
      @Before("pointCut()")
      public void before() {
         println(" before no args ");
      }

      // 可以直接输入表达式
      @After("execution(* cn.cpf.aop.annotation.AnnotationProxyObjectDemo.testProxy1(..))")
      public void after() {
         println(" after ");
      }

      @AfterReturning(returning = "rst", value = "pointCut()")
      public void afterReturn(Object rst) {
         println(" afterReturn ");
      }

      @AfterThrowing(throwing = "e", value = "pointCut()")
      public void afterThrow(Throwable e) {
         println(" afterThrow " + e.toString());
      }

      @Around("pointCut()")
      public void around(ProceedingJoinPoint pjp) {
         println(" around start ");
         try {
               pjp.proceed();
         } catch (Throwable throwable) {
               println(" around throwable ");
               throwable.printStackTrace();
         }
         println(" around end ");
      }
   }

   // 代理类1, order 排序为 2
   package cn.cpf.aop.annotation;

   import cn.cpf.aop.common.LoggerUtils;
   import org.springframework.core.annotation.Order;
   import org.springframework.stereotype.Component;

   @Component
   @Order(2)
   public class AnnotationProxy1 extends AnnotationProxy{

      @Override
      protected void println(String string) {
         LoggerUtils.logInfo("AnnotationProxy 1 == " + string);
      }
   }


   // 代理类2,  order 排序为 1
   package cn.cpf.aop.annotation;

   import cn.cpf.aop.common.LoggerUtils;
   import org.springframework.core.annotation.Order;
   import org.springframework.stereotype.Component;

   @Component
   @Order(1)
   public class AnnotationProxy2 extends AnnotationProxy{

      @Override
      protected void println(String string) {
         LoggerUtils.logInfo("AnnotationProxy 2 == " + string);
      }
   }

   ```

- 代理对象

   ```java
   package cn.cpf.aop.annotation;

   import cn.cpf.aop.common.LoggerUtils;
   import org.springframework.stereotype.Component;

   @Component("annotationProxyObjectDemo")
   public class AnnotationProxyObjectDemo implements IAopAnnotation {

      @Override
      public void testProxy1() {
         LoggerUtils.logInfo("执行 testProxy1 方法");
      }

      @Override
      public void testProxy2() {
         LoggerUtils.logInfo("执行 testProxy2 方法");
      }

   }
   ```

- 测试

   ```java
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aop-annotation.xml");
        IAopAnnotation aopAnnotation = applicationContext.getBean("annotationProxyObjectDemo", IAopAnnotation.class);
        aopAnnotation.testProxy1();
    }
   ```

- 输出

   ```log
   14:00:09.987 [main] INFO cn.cpf.aop.common.LoggerUtils - AnnotationProxy 2 ==  around start
   14:00:09.987 [main] INFO cn.cpf.aop.common.LoggerUtils - AnnotationProxy 2 ==  before no args
   14:00:09.987 [main] INFO cn.cpf.aop.common.LoggerUtils - AnnotationProxy 1 ==  around start
   14:00:09.987 [main] INFO cn.cpf.aop.common.LoggerUtils - AnnotationProxy 1 ==  before no args
   14:00:09.987 [main] INFO cn.cpf.aop.common.LoggerUtils - 执行 testProxy1 方法
   14:00:09.987 [main] INFO cn.cpf.aop.common.LoggerUtils - AnnotationProxy 1 ==  around end
   14:00:09.987 [main] INFO cn.cpf.aop.common.LoggerUtils - AnnotationProxy 1 ==  after
   14:00:09.987 [main] INFO cn.cpf.aop.common.LoggerUtils - AnnotationProxy 1 ==  afterReturn
   14:00:09.987 [main] INFO cn.cpf.aop.common.LoggerUtils - AnnotationProxy 2 ==  around end
   14:00:09.987 [main] INFO cn.cpf.aop.common.LoggerUtils - AnnotationProxy 2 ==  after
   14:00:09.987 [main] INFO cn.cpf.aop.common.LoggerUtils - AnnotationProxy 2 ==  afterReturn
   ```
