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

## Spring事务API架构图

![Spring事务API架构图](/assets/frame/spring/Spring事务API架构图.png)

