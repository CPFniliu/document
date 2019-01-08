# Spring 数据访问篇

## Spring 事务的传播属性

所谓 spring 事务的传播属性，就是定义在存在多个事务同时存在的时候，spring 应该如何处理这些事务的行为。这些属性在 TransactionDefinition 中定义，具体常量的解释见下表：

常量名称 | 常量解释
-|-
PROPAGATION_REQUIRED | 支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择，也是 Spring 默认的事务的传播。
PROPAGATION_REQUIRES_NEW | 新建事务，如果当前存在事务，把当前事务挂起。新建的事务将和被挂起的事务没有任何关系，是两个独立的事务，外层事务失败回滚之后，不能回滚内层事务执行的结果，内层事务失败抛出异常，外层事务捕获，也可以不处理回滚操作
PROPAGATION_SUPPORTS | 支持当前事务，如果当前没有事务，就以非事务方式执行。
PROPAGATION_MANDATORY | 支持当前事务，如果当前没有事务，就抛出异常。
PROPAGATION_NOT_SUPPORTED | 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
PROPAGATION_NEVER | 以非事务方式执行，如果当前存在事务，则抛出异常。
PROPAGATION_NESTED | 如果一个活动的事务存在，则运行在一个嵌套的事务中。如果没有活动事务，则按 REQUIRED 属性执行。它使用了一个单独的事务，这个事务拥有多个可以回滚的保存点。内部事务的回滚不会对外部事务造成影响。 它只对DataSourceTransactionManager 事务管理器起效。

## Spring 中的隔离级别

常量 | 解释
-|-
ISOLATION_DEFAULT | 这是个 PlatfromTransactionManager 默认的隔离级别，使用数据库默认的事务隔离级别。另外四个与 JDBC 的隔离级别相对应。
ISOLATION_READ_UNCOMMITTED | 这是事务最低的隔离级别，它充许另外一个事务可以看到这个事务未提交的数据。这种隔离级别会产生脏读，不可重复读和幻像读。
ISOLATION_READ_COMMITTED | 保证一个事务修改的数据提交后才能被另外一个事务读取。另外一个事务不能读取该事务未提交的数据。
ISOLATION_REPEATABLE_READ | 这种事务隔离级别可以防止脏读，不可重复读。但是可能出现幻像读。
ISOLATION_SERIALIZABLE | 这是花费最高代价但是最可靠的事务隔离级别。事务被处理为顺序执行。

## spring 事务传播的机制 实例分析

假设外层事务 Service A 的 Method A() 调用 内层 Service B 的 Method B()

```java
void MethodA() {
   try {
      ServiceB.MethodB();
   } catch (SomeException) {
      // 执行其他业务, 如 ServiceC.MethodC();
   }
}
```

1. PROPAGATION_REQUIRED(spring默认)
   如果ServiceB.MethodB()的事务级别定义为PROPAGATION_REQUIRED，那么执行ServiceA.MethodA()的时候spring已经起了事务，这时调用ServiceB.MethodB()，ServiceB.MethodB()看到自己已经运行在ServiceA.MethodA()的事务内部，就不再起新的事务。假如ServiceB.MethodB()运行的时候发现自己没有在事务中，他就会为自己分配一个事务。这样，在ServiceA.MethodA()或者在ServiceB.MethodB()内的任何地方出现异常，事务都会被回滚。
2. PROPAGATION_REQUIRES_NEW
   比如我们设计ServiceA.MethodA()的事务级别为PROPAGATION_REQUIRED，ServiceB.MethodB()的事务级别为PROPAGATION_REQUIRES_NEW。
   那么当执行到ServiceB.MethodB()的时候，ServiceA.MethodA()所在的事务就会挂起，ServiceB.MethodB()会起一个新的事务，等待ServiceB.MethodB()的事务完成以后，它才继续执行。
   他与PROPAGATION_REQUIRED的事务区别在于事务的回滚程度了。因为ServiceB.MethodB()是新起一个事务，那么就是存在两个不同的事务。如果ServiceB.MethodB()已经提交，那么ServiceA.MethodA()失败回滚，ServiceB.MethodB()是不会回滚的。如果ServiceB.MethodB()失败回滚，如果他抛出的异常被ServiceA.MethodA()捕获，ServiceA.MethodA()事务仍然可能提交(主要看B抛出的异常是不是A会回滚的异常)。
3. PROPAGATION_SUPPORTS
   假设ServiceB.MethodB()的事务级别为PROPAGATION_SUPPORTS，那么当执行到ServiceB.MethodB()时，如果发现ServiceA.MethodA()已经开启了一个事务，则加入当前的事务，如果发现ServiceA.MethodA()没有开启事务，则自己也不开启事务。这种时候，内部方法的事务性完全依赖于最外层的事务。
4. PROPAGATION_NESTED
   现在的情况就变得比较复杂了,ServiceB.MethodB()的事务属性被配置为PROPAGATION_NESTED,此时两者之间又将如何协作呢?
   ServiceB#MethodB如果rollback,那么内部事务(即ServiceB#MethodB)将回滚到它执行前的SavePoint而外部事务(即ServiceA#MethodA)可以有以下两种处理方式:
   - 捕获异常，执行异常分支逻辑
      这种方式也是嵌套事务最有价值的地方,它起到了分支执行的效果,如果ServiceB.MethodB失败,那么执行ServiceC.MethodC(),而ServiceB.MethodB已经回滚到它执行之前的SavePoint,所以不会产生脏数据(相当于此方法从未执行过),这种特性可以用在某些特殊的业务中,而PROPAGATION_REQUIRED和PROPAGATION_REQUIRES_NEW都没有办法做到这一点。
   - 外部事务回滚/提交代码不做任何修改,那么如果内部事务(ServiceB#MethodB)rollback,那么首先ServiceB.MethodB回滚到它执行之前的SavePoint(在任何情况下都会如此),外部事务(即ServiceA#MethodA)将根据具体的配置决定自己是commit还是rollback
5. 另外三种事务传播属性基本用不到，在此不做分析。

> PROPAGATION_REQUIRES_NEW 启动一个新的, 不依赖于环境的 "内部" 事务. 这个事务将被完全 commited 或 rolled back 而不依赖于外部事务, 它拥有自己的隔离范围, 自己的锁, 等等. 当内部事务开始执行时, 外部事务将被挂起, 内务事务结束时, 外部事务将继续执行. 
> 另一方面, PROPAGATION_NESTED 开始一个 "嵌套的" 事务,  它是已经存在事务的一个真正的子事务. 潜套事务开始执行时,  它将取得一个 savepoint. 如果这个嵌套事务失败, 我们将回滚到此 savepoint. 潜套事务是外部事务的一部分, 只有外部事务结束后它才会被提交. 
> 由此可见, PROPAGATION_REQUIRES_NEW 和 PROPAGATION_NESTED 的最大区别在于, PROPAGATION_REQUIRES_NEW 完全是一个新的事务, 而 PROPAGATION_NESTED 则是外部事务的子事务, 如果外部事务 commit, 潜套事务也会被 commit, 这个规则同样适用于 roll back.

## springtx 其他属性

### @Transactional注解中常用参数说明

type | name
-|-
readOnly | 该属性用于设置当前事务是否为只读事务，设置为true表示只读，false则表示可读写，默认值为false。例如：@Transactional(readOnly=true)
rollbackFor | 该属性用于设置需要进行回滚的异常类数组，当方法中抛出指定异常数组中的异常时，则进行事务回滚。<br/> 例如：指定单一异常类：@Transactional(rollbackFor=RuntimeException.class) <br/> 指定多个异常类：@Transactional(rollbackFor={RuntimeException.class, Exception.class})
rollbackForClassName | 该属性用于设置需要进行回滚的异常类名称数组， 当方法中抛出指定异常名称数组中的异常时， 则进行事务回滚。例如：<br/>指定单一异常类名称：@Transactional(rollbackForClassName=”RuntimeException”) <br/>指定多个异常类名称：@Transactional(rollbackForClassName={“RuntimeException”,”Exception”})
noRollbackFor | 该属性用于设置不需要进行回滚的异常类数组， 当方法中抛出指定异常数组中的异常时，不进行事务回滚。 例如：<br/>指定单一异常类：@Transactional(noRollbackFor=RuntimeException.class) <br/>指定多个异常类：@Transactional(noRollbackFor={RuntimeException.class, Exception.class})
noRollbackForClassName | 该属性用于设置不需要进行回滚的异常类名称数组，当方法中抛出指定异常名称数组中的异常时，不进行事务回滚。例如：<br/>指定单一异常类名称：@Transactional(noRollbackForClassName=”RuntimeException”)  <br/>指定多个异常类名称：@Transactional(noRollbackForClassName={“RuntimeException”,”Exception”})
propagation | 该属性用于设置事务的传播行为，例如：@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
isolation | 该属性用于设置底层数据库的事务隔离级别，事务隔离级别用于处理多事务并发的情况，通常使用数据库的默认隔离级别即可，基本不需要进行设置
timeout | 该属性用于设置事务的超时秒数，默认值为-1表示永不超时

注意

1. @Transactional 只能被应用到public方法上, 对于其它非public的方法,如果标记了@Transactional也不会报错,但方法没有事务功能.
2. 用 spring 事务管理器,由spring来负责数据库的打开, 提交, 回滚. 默认遇到运行期例外(throw new RuntimeException(“注释”);)会回滚，即遇到不受检查（unchecked）的例外时回滚；而遇到需要捕获的例外(throw new Exception(“注释”);)不会回滚,即遇到受检查的例外（就是非运行时抛出的异常，编译器会检查到的异常叫受检查例外或说受检查异常）时，需我们指定方式来让事务回滚 要想所有异常都回滚,要加上 @Transactional( rollbackFor={Exception.class,其它异常})
3. Spring团队的建议是你在具体的类（或类的方法）上使用 @Transactional 注解，而不要使用在类所要实现的任何接口上。你当然可以在接口上使用 @Transactional 注解，但是这将只能当你设置了基于接口的代理时它才生效。因为注解是 不能继承 的，这就意味着如果你正在使用基于类的代理时，那么事务的设置将不能被基于类的代理所识别，而且对象也将不会被事务代理所包装（将被确认为严重的）。因 此，请接受Spring团队的建议并且在具体的类上使用 @Transactional 注解。

## 使用步骤：

步骤一、在spring配置文件中引入\<tx:\>命名空间

```xml
<beans xmlns=”http://www.springframework.org/schema/beans”
 xmlns:xsi=”http://www.w3.org/2001/XMLSchema-instance”
 xmlns:tx=”http://www.springframework.org/schema/tx”
 xsi:schemaLocation=”http://www.springframework.org/schema/beans
 http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
 http://www.springframework.org/schema/tx
 http://www.springframework.org/schema/tx/spring-tx-3.0.xsd”>

<!-- 具有@Transactional 注解的bean自动配置为声明式事务支持 -->
<!-- 如果proxy-target-class 属性值被设置为true，那么基于类的代理将起作用（这时需要cglib库） -->
<tx:annotation-driven transaction-manager=”transactionManager” proxy-target-class=”true”/>

 </beans>
```

```java

   @Transactional
   public class TestServiceBean implements TestService {}
   // 当类中某些方法不需要事物时:

   @Transactional
   public class TestServiceBean implements TestService {
      private TestDao dao;

      public void setDao(TestDao dao) {
         this.dao = dao;
      }

      @Transactional(propagation = Propagation.NOT_SUPPORTED)
      public List<Object> getAll() {
         return null;
      }
   }
```

## Spring事务API架构图

![Spring事务API架构图](/assets/frame/spring/Spring事务API架构图.png)

