# AOP 相关

## 基本概念

切面（Aspect）：官方的抽象定义为“一个关注点的模块化，这个关注点可能会横切多个对象”。“切面”在ApplicationContext中\<aop:aspect\>来配置。
连接点（Joinpoint）：程序执行过程中的某一行为，例如，MemberService.get的调用或者MemberService.delete抛出异常等行为。
通知（Advice）：“切面”对于某个“连接点”所产生的动作。其中，一个“切面”可以包含多个“Advice”。
切入点（Pointcut）：匹配连接点的断言，在AOP中通知和一个切入点表达式关联。切面中的所有通知所关注的连接点，都由切入点表达式来决定。
目标对象（TargetObject）：被一个或者多个切面所通知的对象。例如，AServcieImpl和BServiceImpl，当然在实际运行时，SpringAOP采用代理实现，实际AOP操作的是TargetObject的代理对象。
AOP代理（AOPProxy）：在SpringAOP中有两种代理方式，JDK动态代理和CGLIB代理。默认情况下，TargetObject实现了接口时，则采用JDK动态代理，例如，AServiceImpl；反之，采用CGLIB代理，例如，BServiceImpl。强制使用CGLIB代理需要将\<aop:config\>的proxy-target-class属性设为true。

## 通知（Advice）类型：

前置通知（Beforeadvice）：在某连接点（JoinPoint）之前执行的通知，但这个通知不能阻止连接点前的执行。ApplicationContext中在\<aop:aspect\>里面使用\<aop:before\>元素进行声明。例如，TestAspect中的doBefore方法。
后置通知（Afteradvice）：当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）。ApplicationContext中在\<aop:aspect\>里面使用\<aop:after\>元素进行声明。例如，ServiceAspect中的returnAfter方法，所以Teser中调用UserService.delete抛出异常时，returnAfter方法仍然执行。
返回后通知（Afterreturnadvice）：在某连接点正常完成后执行的通知，不包括抛出异常的情况。ApplicationContext中在\<aop:aspect\>里面使用\<after-returning\>元素进行声明。
环绕通知（Aroundadvice）：包围一个连接点的通知，类似Web中Servlet规范中的Filter的doFilter方法。可以在方法的调用前后完成自定义的行为，也可以选择不执行。ApplicationContext中在\<aop:aspect\>里面使用\<aop:around\>元素进行声明。例如，ServiceAspect中的around方法。
抛出异常后通知（Afterthrowingadvice）：在方法抛出异常退出时执行的通知。ApplicationContext中在\<aop:aspect\>里面使用\<aop:after-throwing\>元素进行声明。例如，ServiceAspect中的returnThrow方法。

注：可以将多个通知应用到一个目标对象上，即可以将多个切面织入到同一目标对象。

## spring 配置

### 五种 Advice

   Before
   After
   Around
   AfterReturning
   AfterThrowing

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

```java
```
```java
```
```java
```
```java
```
```java
```



### Pointcut-execution表达式

   > 官方文档 https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop

   ```java
      execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
      execution(* cn.cpf..*.*(..))
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
         <!-- 代理 切面 表达式 -->
         <aop:pointcut expression="execution(* cn.cpf.aop.xml.UserServiceImpl.insert(..))" id="aop1" />
         <!-- 设置代理类 -->
         <aop:aspect ref = "xmlProxy">
               <!-- 设置代理方法 -->
               <aop:before method="startTransaction" pointcut-ref="aop1" />
         </aop:aspect>
      </aop:config>

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
         System.err.println("XmlProxy 1 == " + string);
      }
   }


   package cn.cpf.aop.xml;
   public class XmlProxy2 extends XmlProxy{
      @Override
      protected void println(String string) {
         System.out.println("XmlProxy 2 == " + string);
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

- 分析(基于xml配置, spring5.0版本)
   1. 对于一个切面来说, xml种的配置通知的顺序决定了执行的顺序, 配在前面的通知先执行.
   2. 对于多个切面来说, order属性可以决定执行顺序.
      - 在执行被代理的目标方法之前, order较小的先执行, 在执行被代理的目标方法后order较大的先执行.
      - 若没有设置order属性, 或者order属性值相等, 那么在xml配置中较前的那个先执行.

### annotation

1. 注解配置(xml)
   - 在xml文件中声明激活自动扫描组件功能，
   - 同时激活自动代理功能（来测试AOP的注解功能）

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
      <!-- 开启注解 -->
      <context:annotation-config />
      <!-- 开启aspectj代理 -->
      <aop:aspectj-autoproxy />
   </beans>
   ```

2. Aspect 切面类添加注解(java)

   ```java
   //声明这是一个组件
   @Component
   //声明这是一个切面 Bean
   @Aspect
   public class AnnotaionAspect {

      private final static Logger log = Logger.getLogger(String.valueOf(AnnotaionAspect.class));

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
         long start = System.currentTimeMillis();
         try {
            ((ProceedingJoinPoint) joinPoint).proceed();
            long end = System.currentTimeMillis();
            log.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
         } catch (Throwable e) {
            long end = System.currentTimeMillis();
            log.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
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
   }
   ```

3. 测试
   ...

4. 分析执行顺序
   - 正常执行
      所有通知order一样，执行顺序：around start -> before ->around start -> afterreturning -> after
      before.order < around.order，执行顺序： before -> around start
      afterreturning.order > around.order，执行顺序：afterreturning -> around end
      after.order > around.order，执行顺序：after -> around end
      after.order >afterreturning.order，执行顺序： after -> afterreturning
   2、异常情况
      所有通知order一样，执行顺序：around start -> before ->  afterthrowing -> after
      before.order < around.order，执行顺序： before -> around start
      after.order > afterthrowing .order，执行顺序：after -> afterthrowing