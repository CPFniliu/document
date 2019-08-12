# rocketMQ 安装配置篇

## linux

## rocketMQ 安装部署运行



# RocketMQ 安装部署及运行

RocketMQ 官方文档中说明的安装先决条件包括：

- 64bit OS, Linux/Unix/Mac is recommended
- 64bit JDK 1.8+
- Maven 3.2.x
- Git

虽然推荐的操作系统是 Linux/Unix/Mac，实际上 Windows 下也是可以安装部署的，本文演示 RocketMQ 在 Windows 10 和 Mac 中的安装部署及运行。

# Windows 10

为简单起见，直接下载 binary release，本文使用 4.2.0 版本

1 解压到本地目录

2 设置系统环境变量 ROCKETMQ_HOME 指向 RocketMQ 的安装目录
此步非常重要，否则无法启动 NameServer 



![img](https://upload-images.jianshu.io/upload_images/5369422-781c9c3017e03a9f.PNG?imageMogr2/auto-orient/strip%7CimageView2/2/w/653/format/webp)


为了方便后续在命令提示符（cmd）窗口快速使用 RocketMQ 的命令，还需要将 %ROCKETMQ_HOME%\bin 添加到系统环境变量 Path 中。

3 启动 NameServer
在命令提示符（cmd）窗口中执行命令 `mqnamesrv.cmd`
启动日志如果有 `The Name Server boot success` 打印则表示 NameServer 启动成功，暂时忽略启动日志中的警告，注意一旦命令提示符（cmd）窗口关闭则 NameServer 停止运行。

```
Java HotSpot(TM) 64-Bit Server VM warning: Using the DefNew young collector with the CMS collector is deprecated and will likely be removed in a future release
Java HotSpot(TM) 64-Bit Server VM warning: UseCMSCompactAtFullCollection is deprecated and will likely be removed in a future release.
The Name Server boot success. serializeType=JSON
```

4 启动 Broker
重新开启一个命令提示符（cmd）窗口，执行命令 `mqbroker.cmd -n localhost:9876`，注意只要没有报错日志应该就是启动成功了，如果启动成功则不会打印任何日志，不要关闭命令提示符（cmd）窗口。需要注意的是必须先启动 NameServer 再启动 Broker，Broker 要在 NameServer 上注册。

5 验证 RocketMQ 是否正常运行

有两种验证方法：

(1) 运行官方提供的快速启动示例

首先，重新开启一个命令提示符（cmd）窗口，执行命令 `set NAMESRV_ADDR=localhost:9876` 设置环境变量，也可以像第 2 步一样将 NAMESRV_ADDR 添加到系统环境变量中；

其次，执行命令 `tools.cmd org.apache.rocketmq.example.quickstart.Producer`，运行官方示例中的消息生产者，可以看到消息全部成功发送，以下截取了部分消息发布日志

```
22:26:06.213 [main] DEBUG i.n.u.i.l.InternalLoggerFactory - Using SLF4J as the default logging framework
22:26:06.224 [main] DEBUG i.n.c.MultithreadEventLoopGroup - -Dio.netty.eventLoopThreads: 16
22:26:06.238 [main] DEBUG i.n.util.internal.PlatformDependent0 - java.nio.Buffer.address: available
22:26:06.238 [main] DEBUG i.n.util.internal.PlatformDependent0 - sun.misc.Unsafe.theUnsafe: available
22:26:06.239 [main] DEBUG i.n.util.internal.PlatformDependent0 - sun.misc.Unsafe.copyMemory: available
22:26:06.240 [main] DEBUG i.n.util.internal.PlatformDependent0 - direct buffer constructor: available
22:26:06.240 [main] DEBUG i.n.util.internal.PlatformDependent0 - java.nio.Bits.unaligned: available, true
22:26:06.240 [main] DEBUG i.n.util.internal.PlatformDependent0 - java.nio.DirectByteBuffer.<init>(long, int): available
22:26:06.241 [main] DEBUG io.netty.util.internal.Cleaner0 - java.nio.ByteBuffer.cleaner(): available
22:26:06.242 [main] DEBUG i.n.util.internal.PlatformDependent - Platform: Windows
22:26:06.242 [main] DEBUG i.n.util.internal.PlatformDependent - Java version: 8
22:26:06.242 [main] DEBUG i.n.util.internal.PlatformDependent - -Dio.netty.noUnsafe: false
22:26:06.242 [main] DEBUG i.n.util.internal.PlatformDependent - sun.misc.Unsafe: available
22:26:06.243 [main] DEBUG i.n.util.internal.PlatformDependent - -Dio.netty.noJavassist: false
22:26:06.327 [main] DEBUG i.n.util.internal.PlatformDependent - Javassist: available
22:26:06.328 [main] DEBUG i.n.util.internal.PlatformDependent - -Dio.netty.tmpdir: C:\Users\Ji\AppData\Local\Temp (java.io.tmpdir)
22:26:06.328 [main] DEBUG i.n.util.internal.PlatformDependent - -Dio.netty.bitMode: 64 (sun.arch.data.model)
22:26:06.329 [main] DEBUG i.n.util.internal.PlatformDependent - -Dio.netty.noPreferDirect: false
22:26:06.329 [main] DEBUG i.n.util.internal.PlatformDependent - io.netty.maxDirectMemory: 1040187392 bytes
22:26:06.342 [main] DEBUG io.netty.channel.nio.NioEventLoop - -Dio.netty.noKeySetOptimization: false
22:26:06.342 [main] DEBUG io.netty.channel.nio.NioEventLoop - -Dio.netty.selectorAutoRebuildThreshold: 512
22:26:06.347 [main] DEBUG i.n.util.internal.PlatformDependent - org.jctools-core.MpscChunkedArrayQueue: available
22:26:06.600 [main] DEBUG i.n.util.internal.ThreadLocalRandom - -Dio.netty.initialSeedUniquifier: 0x2f0e8a555da48856 (took 0 ms)
22:26:06.621 [main] DEBUG io.netty.buffer.ByteBufUtil - -Dio.netty.allocator.type: unpooled
22:26:06.622 [main] DEBUG io.netty.buffer.ByteBufUtil - -Dio.netty.threadLocalDirectBufferSize: 65536
22:26:06.623 [main] DEBUG io.netty.buffer.ByteBufUtil - -Dio.netty.maxThreadLocalCharBufferSize: 16384
22:26:06.639 [NettyClientSelector_1] DEBUG i.n.u.i.JavassistTypeParameterMatcherGenerator - Generated: io.netty.util.internal.__matchers__.org.apache.rocketmq.remoting.protocol.RemotingCommandMatcher
22:26:06.652 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.maxCapacity.default: 32768
22:26:06.652 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.maxSharedCapacityFactor: 2
22:26:06.653 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.linkCapacity: 16
22:26:06.656 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.ratio: 8
22:26:06.667 [NettyClientWorkerThread_1] DEBUG io.netty.buffer.AbstractByteBuf - -Dio.netty.buffer.bytebuf.checkAccessible: true
22:26:06.673 [NettyClientWorkerThread_1] DEBUG io.netty.util.ResourceLeakDetector - -Dio.netty.leakDetection.level: simple
22:26:06.673 [NettyClientWorkerThread_1] DEBUG io.netty.util.ResourceLeakDetector - -Dio.netty.leakDetection.maxRecords: 4
22:26:06.675 [NettyClientWorkerThread_1] DEBUG i.n.util.ResourceLeakDetectorFactory - Loaded default ResourceLeakDetector: io.netty.util.ResourceLeakDetector@395ad0f
SendResult [sendStatus=SEND_OK, msgId=0A000007093C5CAD80860F1D20380000, offsetMsgId=0A00000700002A9F0000000000000000, messageQueue=MessageQueue [topic=TopicTest, brokerName=LAPTOP-C375ASPB, queueId=3], queueOffset=0]
SendResult [sendStatus=SEND_OK, msgId=0A000007093C5CAD80860F1D20880001, offsetMsgId=0A00000700002A9F00000000000000B2, messageQueue=MessageQueue [topic=TopicTest, brokerName=LAPTOP-C375ASPB, queueId=0], queueOffset=0]
SendResult [sendStatus=SEND_OK, msgId=0A000007093C5CAD80860F1D208F0002, offsetMsgId=0A00000700002A9F0000000000000164, messageQueue=MessageQueue [topic=TopicTest, brokerName=LAPTOP-C375ASPB, queueId=1], queueOffset=0]
SendResult [sendStatus=SEND_OK, msgId=0A000007093C5CAD80860F1D20990003, offsetMsgId=0A00000700002A9F0000000000000216, messageQueue=MessageQueue [topic=TopicTest, brokerName=LAPTOP-C375ASPB, queueId=2], queueOffset=0]
SendResult [sendStatus=SEND_OK, msgId=0A000007093C5CAD80860F1D209F0004, offsetMsgId=0A00000700002A9F00000000000002C8, messageQueue=MessageQueue [topic=TopicTest, brokerName=LAPTOP-C375ASPB, queueId=3], queueOffset=1]
SendResult [sendStatus=SEND_OK, msgId=0A000007093C5CAD80860F1D20A50005, offsetMsgId=0A00000700002A9F000000000000037A, messageQueue=MessageQueue [topic=TopicTest, brokerName=LAPTOP-C375ASPB, queueId=0], queueOffset=1]
SendResult [sendStatus=SEND_OK, msgId=0A000007093C5CAD80860F1D20AB0006, offsetMsgId=0A00000700002A9F000000000000042C, messageQueue=MessageQueue [topic=TopicTest, brokerName=LAPTOP-C375ASPB, queueId=1], queueOffset=1]
SendResult [sendStatus=SEND_OK, msgId=0A000007093C5CAD80860F1D20B20007, offsetMsgId=0A00000700002A9F00000000000004DE, messageQueue=MessageQueue [topic=TopicTest, brokerName=LAPTOP-C375ASPB, queueId=2], queueOffset=1]
SendResult [sendStatus=SEND_OK, msgId=0A000007093C5CAD80860F1D20B60008, offsetMsgId=0A00000700002A9F0000000000000590, messageQueue=MessageQueue [topic=TopicTest, brokerName=LAPTOP-C375ASPB, queueId=3], queueOffset=2]
SendResult [sendStatus=SEND_OK, msgId=0A000007093C5CAD80860F1D20BD0009, offsetMsgId=0A00000700002A9F0000000000000642, messageQueue=MessageQueue [topic=TopicTest, brokerName=LAPTOP-C375ASPB, queueId=0], queueOffset=2]
......
```

最后，执行命令 `tools.cmd org.apache.rocketmq.example.quickstart.Consumer`，运行官方示例中的消息消费者，可以看到消息全部成功消费，以下截取了部分消息消费日志

```
22:28:56.473 [main] DEBUG i.n.u.i.l.InternalLoggerFactory - Using SLF4J as the default logging framework
22:28:56.476 [main] DEBUG i.n.c.MultithreadEventLoopGroup - -Dio.netty.eventLoopThreads: 16
22:28:56.490 [main] DEBUG i.n.util.internal.PlatformDependent0 - java.nio.Buffer.address: available
22:28:56.490 [main] DEBUG i.n.util.internal.PlatformDependent0 - sun.misc.Unsafe.theUnsafe: available
22:28:56.491 [main] DEBUG i.n.util.internal.PlatformDependent0 - sun.misc.Unsafe.copyMemory: available
22:28:56.491 [main] DEBUG i.n.util.internal.PlatformDependent0 - direct buffer constructor: available
22:28:56.491 [main] DEBUG i.n.util.internal.PlatformDependent0 - java.nio.Bits.unaligned: available, true
22:28:56.492 [main] DEBUG i.n.util.internal.PlatformDependent0 - java.nio.DirectByteBuffer.<init>(long, int): available
22:28:56.493 [main] DEBUG io.netty.util.internal.Cleaner0 - java.nio.ByteBuffer.cleaner(): available
22:28:56.493 [main] DEBUG i.n.util.internal.PlatformDependent - Platform: Windows
22:28:56.493 [main] DEBUG i.n.util.internal.PlatformDependent - Java version: 8
22:28:56.493 [main] DEBUG i.n.util.internal.PlatformDependent - -Dio.netty.noUnsafe: false
22:28:56.494 [main] DEBUG i.n.util.internal.PlatformDependent - sun.misc.Unsafe: available
22:28:56.494 [main] DEBUG i.n.util.internal.PlatformDependent - -Dio.netty.noJavassist: false
22:28:56.580 [main] DEBUG i.n.util.internal.PlatformDependent - Javassist: available
22:28:56.581 [main] DEBUG i.n.util.internal.PlatformDependent - -Dio.netty.tmpdir: C:\Users\Ji\AppData\Local\Temp (java.io.tmpdir)
22:28:56.582 [main] DEBUG i.n.util.internal.PlatformDependent - -Dio.netty.bitMode: 64 (sun.arch.data.model)
22:28:56.582 [main] DEBUG i.n.util.internal.PlatformDependent - -Dio.netty.noPreferDirect: false
22:28:56.582 [main] DEBUG i.n.util.internal.PlatformDependent - io.netty.maxDirectMemory: 1040187392 bytes
22:28:56.595 [main] DEBUG io.netty.channel.nio.NioEventLoop - -Dio.netty.noKeySetOptimization: false
22:28:56.595 [main] DEBUG io.netty.channel.nio.NioEventLoop - -Dio.netty.selectorAutoRebuildThreshold: 512
22:28:56.600 [main] DEBUG i.n.util.internal.PlatformDependent - org.jctools-core.MpscChunkedArrayQueue: available
22:28:57.080 [main] DEBUG i.n.util.internal.ThreadLocalRandom - -Dio.netty.initialSeedUniquifier: 0x1e52632a543f7abb (took 0 ms)
22:28:57.104 [main] DEBUG io.netty.buffer.ByteBufUtil - -Dio.netty.allocator.type: unpooled
22:28:57.105 [main] DEBUG io.netty.buffer.ByteBufUtil - -Dio.netty.threadLocalDirectBufferSize: 65536
22:28:57.106 [main] DEBUG io.netty.buffer.ByteBufUtil - -Dio.netty.maxThreadLocalCharBufferSize: 16384
22:28:57.122 [NettyClientSelector_1] DEBUG i.n.u.i.JavassistTypeParameterMatcherGenerator - Generated: io.netty.util.internal.__matchers__.org.apache.rocketmq.remoting.protocol.RemotingCommandMatcher
22:28:57.135 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.maxCapacity.default: 32768
22:28:57.136 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.maxSharedCapacityFactor: 2
22:28:57.136 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.linkCapacity: 16
22:28:57.137 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.ratio: 8
22:28:57.146 [NettyClientWorkerThread_1] DEBUG io.netty.buffer.AbstractByteBuf - -Dio.netty.buffer.bytebuf.checkAccessible: true
22:28:57.149 [NettyClientWorkerThread_1] DEBUG io.netty.util.ResourceLeakDetector - -Dio.netty.leakDetection.level: simple
22:28:57.149 [NettyClientWorkerThread_1] DEBUG io.netty.util.ResourceLeakDetector - -Dio.netty.leakDetection.maxRecords: 4
22:28:57.150 [NettyClientWorkerThread_1] DEBUG i.n.util.ResourceLeakDetectorFactory - Loaded default ResourceLeakDetector: io.netty.util.ResourceLeakDetector@4aed4bcc
Consumer Started.
ConsumeMessageThread_7 Receive New Messages: [MessageExt [queueId=2, storeSize=178, queueOffset=1, sysFlag=0, bornTimestamp=1530627967154, bornHost=/10.0.0.7:55776, storeTimestamp=1530627967156, storeHost=/10.0.0.7:10911, msgId=0A00000700002A9F00000000000004DE, commitLogOffset=1246, bodyCRC=988340972, reconsumeTimes=0, preparedTransactionOffset=0, toString()=Message [topic=TopicTest, flag=0, properties={MIN_OFFSET=0, MAX_OFFSET=250, CONSUME_START_TIME=1530628137407, UNIQ_KEY=0A000007093C5CAD80860F1D20B20007, WAIT=true, TAGS=TagA}, body=16]]]
ConsumeMessageThread_15 Receive New Messages: [MessageExt [queueId=3, storeSize=178, queueOffset=2, sysFlag=0, bornTimestamp=1530627967158, bornHost=/10.0.0.7:55776, storeTimestamp=1530627967161, storeHost=/10.0.0.7:10911, msgId=0A00000700002A9F0000000000000590, commitLogOffset=1424, bodyCRC=710410109, reconsumeTimes=0, preparedTransactionOffset=0, toString()=Message [topic=TopicTest, flag=0, properties={MIN_OFFSET=0, MAX_OFFSET=250, CONSUME_START_TIME=1530628137410, UNIQ_KEY=0A000007093C5CAD80860F1D20B60008, WAIT=true, TAGS=TagA}, body=16]]]
ConsumeMessageThread_9 Receive New Messages: [MessageExt [queueId=1, storeSize=179, queueOffset=2, sysFlag=0, bornTimestamp=1530627967171, bornHost=/10.0.0.7:55776, storeTimestamp=1530627967173, storeHost=/10.0.0.7:10911, msgId=0A00000700002A9F00000000000006F4, commitLogOffset=1780, bodyCRC=193412630, reconsumeTimes=0, preparedTransactionOffset=0, toString()=Message [topic=TopicTest, flag=0, properties={MIN_OFFSET=0, MAX_OFFSET=250, CONSUME_START_TIME=1530628137415, UNIQ_KEY=0A000007093C5CAD80860F1D20C3000A, WAIT=true, TAGS=TagA}, body=17]]]
ConsumeMessageThread_16 Receive New Messages: [MessageExt [queueId=1, storeSize=179, queueOffset=3, sysFlag=0, bornTimestamp=1530627967196, bornHost=/10.0.0.7:55776, storeTimestamp=1530627967198, storeHost=/10.0.0.7:10911, msgId=0A00000700002A9F00000000000009C0, commitLogOffset=2496, bodyCRC=216726031, reconsumeTimes=0, preparedTransactionOffset=0, toString()=Message [topic=TopicTest, flag=0, properties={MIN_OFFSET=0, MAX_OFFSET=250, CONSUME_START_TIME=1530628137411, UNIQ_KEY=0A000007093C5CAD80860F1D20DC000E, WAIT=true, TAGS=TagA}, body=17]]]
......
```

(2) 关闭 Broker 和 NameServer

首先，执行命令 `mqshutdown.cmd broker` 关闭 Broker，如果有 Broker 运行则会打印关闭的 Broker 所在线程，如：

```
killing broker
成功: 已终止 PID 为 3960 的进程。
Done!
```

其次，执行命令 `` 关闭 NameServer，如果有 NameServer 运行则会打印关闭的 NameServer 所在线程，如：

```
killing name server
成功: 已终止 PID 为 19504 的进程。
Done!
```





---

### RocketMQ-Console安装及RocketMQ命令行管理工具介绍

## RocketMQ-Console安装及RocketMQ命令行管理工具介绍 原 荐

### 简介

- RocketMQ-Console是RocketMQ项目的扩展插件，是一个图形化管理控制台，提供Broker集群状态查看，Topic管理，Producer、Consumer状态展示，消息查询等常用功能，这个功能在安装好RocketMQ后需要额外单独安装、运行。
- 命令行管理工具（CLI Admin Tool）对RocketMQ集群的管理提供了更多精细化的管理命令，命令行的方式对操作人员的要求稍高一些，当然，掌握了使用方法，就会简单高效很多。命令行管理工具无需额外安装，已经包含在${RocketMQ_HOME}/bin文件夹下面。

### RocketMQ-Console

1. 进入[rocketmq-externals](https://github.com/apache/rocketmq-externals)项目GitHub地址，如下图，可看到RocketMQ项目的诸多扩展项目，其中就包含我们需要下载的rocketmq-console。 ![rocketmq-externals-github](https://static.oschina.net/uploads/img/201805/17143445_gUqj.png)
2. 使用git命令下载项目源码，由于我们仅需要rocketmq-console，故下载此项目对应分支即可。

```
$ git clone -b release-rocketmq-console-1.0.0 https://github.com/apache/rocketmq-externals.git
```

1. 进入项目文件夹并修改配置文件（中文注释是我添加，为方便解释，请删除，不然打包报错）。

```
$ cd rocketmq-externals/rocketmq-console/
$ vi src/main/resources/application.properties 
#管理后台访问上下文路径，默认为空，如果填写，一定要前面加“/”，后面不要加，否则启动报错
server.contextPath=/rocketmq
#访问端口
server.port=8080
#spring.application.index=true
spring.application.name=rocketmq-console
spring.http.encoding.charset=UTF-8
spring.http.encoding.enabled=true
spring.http.encoding.force=true
#logback配置文件路径
logging.config=classpath:logback.xml
#if this value is empty,use env value rocketmq.config.namesrvAddr  NAMESRV_ADDR | now, you can set it in ops page.default localhost:9876
#Name Server地址，修改成你自己的服务地址
rocketmq.config.namesrvAddr=10.0.74.198:9876;10.0.74.199:9876
#if you use rocketmq version < 3.5.8, rocketmq.config.isVIPChannel should be false.default true
rocketmq.config.isVIPChannel=
#rocketmq-console's data path:dashboard/monitor
rocketmq.config.dataPath=/tmp/rocketmq-console/data
#set it false if you don't want use dashboard.default true
rocketmq.config.enableDashBoardCollect=true
```

Name Server地址默认为空，注释说可以在启动项目后在后台配置，经测试，后台配置切换失败，有报错，所以打包前需修改配置文件明确给出Name Server地址，或者启动服务的时候给出rocketmq.config.namesrvAddr参数值。

1. 将项目打成jar包，并运行jar文件。

```
$ mvn clean package -Dmaven.test.skip=true
$ java -jar target/rocketmq-console-ng-1.0.0.jar
#如果配置文件没有填写Name Server
$ java -jar target/rocketmq-console-ng-1.0.0.jar --rocketmq.config.namesrvAddr='10.0.74.198:9876;10.0.74.199:9876'
```

1. 启动成功后，访问地址http://localhost:8080/rocketmq, 即可进入管理后台操作。
   ![OPS](https://static.oschina.net/uploads/img/201805/17160616_PKdM.png)
   ![Cluster](https://static.oschina.net/uploads/img/201805/17160553_czcA.png)

### 命令行管理工具

上面已经讲过命令行管理工具已经包含在RocketMQ项目中，我们进入项目下的bin文件夹，并执行命令`bash mqadmin`，

```
$ bash mqadmin
The most commonly used mqadmin commands are:
   updateTopic          Update or create topic
   deleteTopic          Delete topic from broker and NameServer.
   updateSubGroup       Update or create subscription group
   deleteSubGroup       Delete subscription group from broker.
   updateBrokerConfig   Update broker's config
   updateTopicPerm      Update topic perm
   topicRoute           Examine topic route info
   topicStatus          Examine topic Status info
   topicClusterList     get cluster info for topic
   brokerStatus         Fetch broker runtime status data
   queryMsgById         Query Message by Id
   queryMsgByKey        Query Message by Key
   queryMsgByUniqueKey  Query Message by Unique key
   queryMsgByOffset     Query Message by offset
   queryMsgByUniqueKey  Query Message by Unique key
   printMsg             Print Message Detail
   printMsgByQueue      Print Message Detail
   sendMsgStatus        send msg to broker.
   brokerConsumeStats   Fetch broker consume stats data
   producerConnection   Query producer's socket connection and client version
   consumerConnection   Query consumer's socket connection, client version and subscription
   consumerProgress     Query consumers's progress, speed
   consumerStatus       Query consumer's internal data structure
   cloneGroupOffset     clone offset from other group.
   clusterList          List all of clusters
   topicList            Fetch all topic list from name server
   updateKvConfig       Create or update KV config.
   deleteKvConfig       Delete KV config.
   wipeWritePerm        Wipe write perm of broker in all name server
   resetOffsetByTime    Reset consumer offset by timestamp(without client restart).
   updateOrderConf      Create or update or delete order conf
   cleanExpiredCQ       Clean expired ConsumeQueue on broker.
   cleanUnusedTopic     Clean unused topic on broker.
   startMonitoring      Start Monitoring
   statsAll             Topic and Consumer tps stats
   allocateMQ           Allocate MQ
   checkMsgSendRT       check message send response time
   clusterRT            List All clusters Message Send RT
   getNamesrvConfig     Get configs of name server.
   updateNamesrvConfig  Update configs of name server.
   getBrokerConfig      Get broker config by cluster or special broker!
   queryCq              Query cq command.
```

上面清单中左边为命令名称，右边为命令含义的解释，可以看到，大部分我们常用的功能已包含其中，具体如何使用这些命令，可以通过执行`bash mqadmin help <command>`来了解细节，我们以常用命令`updateTopic`为例，执行`bash mqadmin help updateTopic`,打印如下信息：
![updateTopic](https://static.oschina.net/uploads/img/201805/17170708_y9xz.png)
可以看到，每一个参数项都有解释，理解起来也不困难，现在我们新建一个topic，指定名称为TopicTest，

```
$ bash mqadmin updateTopic -n '10.0.74.198:9876;10.0.74.199:9876' -c hq-mq-cluster -t TestTopic
create topic to 10.0.74.199:10911 success.
create topic to 10.0.74.198:10911 success.
TopicConfig [topicName=TestTopic, readQueueNums=8, writeQueueNums=8, perm=RW-, topicFilterType=SINGLE_TAG, topicSysFlag=0, order=false]
```

创建的topic的默认配置也打印出来了，利用`topicList`命令查看topic清单，

```
$ bash mqadmin topicList -n '10.0.74.198:9876;10.0.74.199:9876'
TestTopic
BenchmarkTest
OFFSET_MOVED_EVENT
SELF_TEST_TOPIC
...
```

可以看见，刚才新建的TopicTest以及一些系统默认的topic。如果想学习了解这些命令的源码实现可以点击查看[这里](https://github.com/apache/rocketmq/tree/master/tools)。

RocketMQ的管理工具就讲到这里，更多技巧及原理还有待深究。。。

### linux 上rocketMQ 简单安装配置

#### 步骤

1. 下载, 上传rocketMQ 二进制版本

2. 配置环境变量

   `vi /etc/profile`

   ```shell
   JAVA_HOME=/soft/jdk1.8.0_111
   MAVEN_HOME=/soft/maven/apache-maven-3.5.3

   CLASSPATH=.:$JAVA_HOME/lib.tools.jar
   PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
   export JAVA_HOME MAVEN_HOME CLASSPATH PATH

   export rocketmq=/soft/RocketMQ/rocketmq-rocketmq-all-4.2.0/distribution/target/apache-rocketmq
   ```

   使变量生效
   `source /etc/profile`

3. 配置rocketMQ

   直接运行可能会提醒内存不足, 此时配置 `apache-rocketmq/bin` 目录下的`runbroker.sh` 和 `runserver.sh` 的参数

    ```shell
    vi runbroker.sh/
    JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn256m"
    JAVA_OPT="${JAVA_OPT} -XX:MaxDirectMemorySize=256m"

    vi runserver.sh
    JAVA_OPT="${JAVA_OPT} -server -Xms128m -Xmx128m -Xmn128m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m"
    ```

4. 配置log文件夹, 若不配置则会输出到当前用户目录文件夹下.

5. 启动和运行

   ```shell
   # 进入到bin目录
   $ cd bin

   # NameServe
   $ nohup sh bin/mqnamesrv &
   # 查看日志看下是否成功
   $ tail -f ~/logs/rocketmqlogs/namesrv.log

   # 启动 Broker
   $ nohup sh bin/mqbroker -n localhost:9876 &
   # 查看日志看下是否成功
   $ tail -f ~/logs/rocketmqlogs/broker.log
   ```

6. 停止

   ```shell
   # 停止 broker
   $ sh bin/mqshutdown broker
   # 停止 nameserver
   $ sh bin/mqshutdown namesrv
   ```

7. 常用命令

   描述 | 命令
   -|-
   查看集群情况 | `./mqadmin clusterList -n 127.0.0.1:9876`
   查看 broker 状态 | `./mqadmin brokerStatus -n 127.0.0.1:9876 -b 172.20.1.138:10911` (注意换成你的 broker 地址)
   查看 topic 列表  | `./mqadmin topicList -n 127.0.0.1:9876`
   查看 topic 状态  | `./mqadmin topicStatus -n 127.0.0.1:9876 -t MyTopic` (换成你想查询的 topic)
   查看 topic 路由  | `./mqadmin topicRoute -n 127.0.0.1:9876 -t MyTopic`
