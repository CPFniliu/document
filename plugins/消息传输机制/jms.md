# JMS

[TOC]

## 简介

### JMS 优缺点

优点 : 提供**消息灵活**性, **松散耦合**, **异步性**, **削峰**
缺点 : 一致性问题

## 两种消息传递域

JMS1.0.2 规范中定义了两种消息传递域：**点对点 （PTP）**消息传递域和**发布/订阅消息**传递域。

### 点对点消息传递域的特点如下：

model : 消息队列（Queue）, 发送者(Sender), 接收者(Receiver)

1. 每个消息只能有一个消费者。
2. 消费者在成功接收消息之后需向队列应答成功
3. 队列保留着消息，直到他们被消费或超时。
4. 消息的生产者和消费者之间没有时间上的相关性。无论消费者在生产者发送消息的时候是否处于运行状态，它都可以提取消息。

### 发布/订阅消息传递域的特点如下：

model : 主题（Topic）, 发布者（Publisher）, 订阅者（Subscriber）

1. 每个消息可以有多个消费者。
2. 生产者和消费者之间有时间上的相关性。订阅一个主题的消费者只能消费自它订阅之后发布的消息。
3. 针对某个主题（Topic）的订阅者，它必须创建一个订阅者之后，才能消费发布者的消息，而且为了消费消息，订阅者必须保持运行的状态。
4. JMS 规范允许客户创建持久订阅，这在一定程度上放松了时间上的相关性要求。持久订阅允许消费者消费它在未处于激活状态时发送的消息。

## 消息的消费

　　在JMS中，消息的产生和消息是异步的。对于消费来说，JMS的消息者可以通过两种方式来消费消息。
　　1. 同步
　　　　订阅者或消费者调用receive方法来接收消息，receive方法在能够接收到消息之前（或超时之前）将一直阻塞
　　2. 异步
　　　　订阅者或消费者可以注册为一个消息监听器。当消息到达之后，系统自动调用监听器的onMessage方法。

## JMS 应用程序接口

### javax.jms.ConnectionFactory 接口(连接工厂)

   ```java
   connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616");
   connection = connectionFactory.createConnection();
   ```

   连接工厂是客户用来创建连接的对象，针对两种不同的jms消息模型，分别有QueueConnectionFactory和TopicConnectionFactory两种。可以通过JNDI来查找ConnectionFactory对象。
   一些实现的中间件实现了这些工厂, 例如 ActiveMQ 提供的ActiveMQConnectionFactory。

### javax.jms.Connection(连接)

   Connection表示在客户端和JMS系统之间建立的链接（对TCP/IP socket的包装）。
　 Connection可以产生一个或多个Session。跟ConnectionFactory一样，Connection也有两种类型：QueueConnection和TopicConnection。

   Session createSession(boolean transacted, int acknowledgeMode)
   session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

   transacted : true or false 表示是否支持事务, 若为 true, 则 acknowledgeMode 值可以忽略(被jms服务器设置为SESSION_TRANSACTED).
   acknowledgeMode[mark jia] : Session 中的静态变量[javax.jms.Session(会话)](#javaxjmssession会话)

### javax.jms.Session(会话)

   ```java
      // 自动确认
      int AUTO_ACKNOWLEDGE = 1;
      // 客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。
      // 一个消息消费者消费了 10 个消息，然后确认了第 5 个消息，那么 0~5 的消息都会被确认
      int CLIENT_ACKNOWLEDGE = 2;
      // 允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
      int DUPS_OK_ACKNOWLEDGE = 3;
      // 事务
      int SESSION_TRANSACTED = 0;
   ```

   JMS Session 是生产和消费消息的一个单线程上下文。会话用于创建消息生产者（producer）、消息消费者（consumer）和消息 （message）等。会话提供了一个事务性的上下文，在这个上下文中，一组发送和接收被组合到了一个原子操作中。
   也分QueueSession和TopicSession。

### javax.jms.Destination(目标)

   Destination是一个包装了消息目标标识符的被管对象，消息目标是指消息发布和接收的地点，或者是队列，或者是主题
   Destination实际上就是两种类型的对象：Queue、Topic可以通过JNDI来查找Destination。

### javax.jms.MessageProducer 接口(消息的生产者)

   消息生产者由Session创建，并用于将消息发送到Destination。消费者可以同步地（阻塞模式），或异步（非阻塞）接收队列和主题类型的消息。
   同样，消息生产者分两种类型：QueueSender和TopicPublisher。可以调用消息生产者的方法（send或publish方法）发送消息。

### javax.jms.MessageConsumer 接口(消息消费者)

   消息消费者由Session创建，用于接收被发送到Destination的消息。两种类型：QueueReceiver和TopicSubscriber。
   可分别通过session的createReceiver(Queue)或createSubscriber(Topic)来创建。
   当然，也可以session的creatDurableSubscriber方法来创建持久化的订阅者。

### javax.jms.Message 接口（消息）

   是在消费者和生产者之间传送的对象，也就是说从一个应用程序创送到另一个应用程序。一个消息有三个主要部分：
   1. 消息头（必须）：包含用于识别和为消息寻找路由的操作设置。
   2. 一组消息属性（可选）：包含额外的属性，支持其他提供者和用户的兼容。可以创建定制的字段和过滤器（消息选择器）。
   3. 一个消息体（可选）：允许用户创建五种类型的消息（文本消息，映射消息，字节消息，流消息和对象消息）。消息接口非常灵活，并提供了许多方式来定制消息的内容。
   消息接口非常灵活，并提供了许多方式来定制消息的内容。

#### Message 子接口

   TextMessage java.lang.String 对象，如 xml 文件内容
   MapMessage 名/值对的集合，名是 String 对象，值类型可以是 Java 任何基本类型
   BytesMessage 字节流
   StreamMessage Java 中的输入输出流
   ObjectMessage Java 中的可序列化对象
   Message 没有消息体，只有消息头和属性。

### javax.jms.MessageListener

　　消息监听器。如果注册了消息监听器，一旦消息到达，将自动调用监听器的onMessage方法。
　　EJB中的MDB（Message-Driven Bean）就是一种MessageListener。

## 特性

### 持久性

   `producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);`

   JMS 支持以下两种消息提交模式：
   PERSISTENT。指示 JMS provider 持久保存消息，以保证消息不会因为 JMS provider 的失败而丢失。
   NON_PERSISTENT。不要求 JMS provider 持久保存消息。

## JMS 消息的可靠性机制

消息的消费通常包含 3 个阶段：客户接收消息、客户处理消息、消息被确认

首先，来简单了解 JMS 的事务性会话和非事务性会话的概念

JMS Session 接口提供了 commit 和 rollback 方法。事务提交意味着生产的所有消息被发送，消费的所有消息被确认；事务回滚意味着生产的所有消息被销毁，消费的所有消息被恢复并重新提交，除非它们已经过期。 事务性的会话总是牵涉到事务处理中，commit 或 rollback 方法一旦被调用，一个事务就结束了，而另一个事务被开始。关闭事务性会话将回滚其中的事务

在事务型会话中在事务状态下进行发送操作，消息并未真正投递到中间件，而只有进行 session.commit 操作之后，消息才会发送到中间件，再转发到适当的消费者进行处理。如果是调用 rollback 操作，则表明，当前事务期间内所发送的消息都取
消掉。

CLIENT_ACKNOWLEDGE 特性
在这种模式中，确认是在会话层上进行，确认一个被消费的消息将自动确认所有已被会话消费的消息。列如，如果一个消息消费者消费了 10 个消息，然后确认了第 5 个消
息，那么 0~5 的消息都会被确认 ->
演示如下：发送端发送 10 个消息，接收端接收 10 个消息，
但是在 i==5 的时候，调用 message.acknowledge()进行
确认，会发现 0~4 的消息都会被确认
Session.DUPS_ACKNOWLEDGE
消息延迟确认。指定消息提供者在消息接收者没有确认发
送时重新发送消息，这种模式不在乎接受者收到重复的消
息。
消息的持久化存储
消息的持久化存储也是保证可靠性最重要的机制之一，也
就是消息发送到 Broker 上以后，如果 broker 出现故障宕
机了，那么存储在 broker 上的消息不应该丢失。可以通过
下面的代码来设置消息发送端的持久化和非持久化特性
MessageProducer
producer=session.createProducer(destination)
;
做技术人的指路明灯，做职场生涯的精神导师
producer.setDeliveryMode(DeliveryMode.PERSIS
TENT);
➢ 对于非持久的消息，JMS provider 不会将它存到文件/数
据库等稳定的存储介质中。也就是说非持久消息驻留在
内存中，如果 jms provider 宕机，那么内存中的非持久
消息会丢失
➢ 对于持久消息，消息提供者会使用存储-转发机制，先将
消息存储到稳定介质中，等消息发送成功后再删除。如
果 jms provider 挂掉了，那么这些未送达的消息不会丢
失；jms provider 恢复正常后，会重新读取这些消息，
并传送给对应的消费者。

### 优先级

   producer.setPriority(4);

   可以使用消息优先级来指示 JMS provider 首先提交紧急的消息。优先级分 10个级别，从 0（最低）到 9（最高）。如果不指定优先级，默认级别是 4。需要注意的是，JMS provider 并不一定保证按照优先级的顺序提交消息。

### 消息过期

   可以设置消息在一定时间后过期，默认是永不过期。

### 临时目的地

   可以通过会话上的 createTemporaryQueue 方法和 createTemporaryTopic 方法来创建临时目的地。它们的存在时间只限于创建它们的连接所保持的时间。只有创建该临时目的地的连接上的消息消费者才能够从临时目的地中提取消息。

### 持久订阅

首先消息生产者必须使用 PERSISTENT 提交消息。客户可以通过会话上的 createDurableSubscriber 方法来创建一个持久订阅，该方法的第一个参数必须是一个 topic。第二个参数是订阅的名称。
JMS provider 会存储发布到持久订阅对应的 topic 上的消息。如果最初创建持久订阅的客户或者任何其它客户使用相同的连接工厂和连接的客户 ID、相同的主题和相同的订阅名再次调用会话上的 createDurableSubscriber 方法，那么该持久订阅就会被激活。JMS provider 会向客户发送客户处于非激活状态时所发布的消息。

持久订阅在某个时刻只能有一个激活的订阅者。持久订阅在创建之后会一直
保留，直到应用程序调用会话上的 unsubscribe 方法。

### 本地事务

在一个 JMS 客户端，可以使用本地事务来组合消息的发送和接收。JMS Session 接口提供了 commit 和 rollback 方法。事务提交意味着生产的所有消息被发送，消费的所有消息被确认；事务回滚意味着生产的所有消 息被销毁，消费的所有消息被恢复并重新提交，除非它们已经过期。
事务性的会话总是牵涉到事务处理中，commit 或 rollback 方法一旦被调用，一个事务就结束了，而另一个事务被开始。关闭事务性会话将回滚其中的事务。需要注意的是，如果使用请求/回复机制，即发送一个消息，同时希望在同一个事务中等待接收该消息的回复，那么程序将被挂起，因为知道事务提 交，发送操作才会真正执行。
需要注意的还有一个，**消息的生产和消费不能包含在同一个事务中**。

JMS开发的基本步骤
1：创建一个JMS connection factory
2：通过connection factory来创建JMS connection
3：启动JMS connection
4：通过connection创建JMS session
5：创建JMS destination
6：创建JMS producer,或者创建JMS message，并设置destination
7：创建JMS consumer，或者是注册一个JMS message listener
8：发送或者接受JMS message(s)
9：关闭所有的JMS资源(connection, session, producer, consumer等)

