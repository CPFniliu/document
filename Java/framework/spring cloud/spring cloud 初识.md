许多设计良好的web应用，可以被按职责分为四层。这些层次是表现层、持久层、业务层、和域模型层。每一个层次都有其独特的职责，不能把各自的功能与其它层次相混合。每一个应用层都应该和其它层隔离开来，但允许使用接口在层间进行通信。我们开始来看看每个层，并讨论一下它们各自都应该提供什么和不应该提供什么。 





一般情况下,Hibernate DAO只操作一个POJO对象，因此一个DAO对应一个POJO对象。 Service层是为了处理包含多个POJO对象（即对多个表的数据操作）时，进行事务管理（声明式事务管理）。Service层（其接口的实现类）被注入多个DAO对象，以完成其数据操作。

域模型层（Domain model）
软件开发要干什么： 
反映真实世界要自动化的业务流程 
解决现实问题 
领域Domain 
Domain特指软件关注的领域 
在不能充分了解业务领域的情况下是不可能做出一个好的软件 
Domain层是整个系统的核心层，该层维护一个使用面向对象技术实现的领域模型，几乎全部的业务逻辑会在该层实现。 
Domain层包含: 
1.Entity（实体）： 
有一类对象拥有唯一标识符能够跨越系统的生命周期甚至能超越软件系统的一系列的延续性和标识符这样的对象称为实体。 
2.ValueObject(值对象) 
对某个对象是什么不感兴趣，只关心它拥有的属性用来描述领域的特殊方面、且没有标识符的一个对象，叫做值对象,能被简单的创建和丢弃，生命周期中不会被持久化值对象可以被共享，值对象应该不可变 
3.Domain Event（领域事件） 
4.Repository（仓储）等 