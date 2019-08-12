

 *以下关于子类和父类的到底是几个对象，也就是影响到父类和子类获取的是否是同一个锁（以下解释来自博客 http://blog.csdn.net/aitangyong/article/details/22695399）
 *使用A a = new A()这种方式创建对象的时候，JVM会在后台给我们分配内存空间，然后调用构造函数执行初始化操作，
 *最后返回内存空间的引用。
 *即构造函数只是进行初始化，并不负责分配内存空间（创建对象）。
 *所以呢其实创建子类对象的时候，JVM会为子类对象分配内存空间，
 *并调用父类的构造函数。
 *我们可以这样理解：创建了一个子类对象的时候，在子类对象内存中，
 *有两份数据，一份继承自父类，一份来自子类，但是他们属于同一个对象（子类对象），
 *只不过是java语法提供了this和super关键字来让我们能够按照需要访问这2份数据而已。
 *这样就产生了子类和父类的概念，但实际上只有子类对象，没有父类对象。


Sync中的nonfairTryAcquire()方法实现
 3 * 这个跟公平类中的实现主要区别在于不会判断当前线程是否是等待时间最长的线程

 如果该锁被获取n次，则前（n-1）次tryRelease(int releases)方法必须返回false，而只有同步状态完全释放了，才返回true，该方法将同步状态是否为0作为最终释放的条件，当同步状态为0时，将占有线程设置为null，并返回true，表示释放成功。

 但是非公平锁是默认实现：非公平性锁可能使线程“饥饿”，但是极少的线程切换，可以保证其更大的吞吐量。而公平性锁，保证了锁的获取按照FIFO原则，代价是进行大量的线程切换。





 ## 闭包是什么

 js : 将






mysql

Compressed TAR Archive 二进制包安装方式, 即免安装解压运行版
TAR 安装板




一个 destinction 有多种操作, 通过炒作类型

一个操作里面有多个源, 判断源是否存在, 存在则调用响应函数

iframe 子页面获取当前页面src

`window.location.pathname`

父页面获取相关子页面的src

`$('iframe#contentFrame').attr('src')`

```js

cedaClientInfo = new ClientInfo();
var uul = eval('({"wsUrl":"ws://192.168.2.2:9063","sioUrl":"http://192.168.2.2:9066","forcePolling":false})');
cedaConnection = new CedaWebSocketConnection(url, cedaClientInfo);


```





tip : i同业报 validate 错误可能是应为数据库配置不对， 看下日志