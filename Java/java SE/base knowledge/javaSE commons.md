# commons

## 方法

### finalize() 方法

finalize 主动调用Object的finalize方法不能够主动销毁对象, 销毁对象的方法是将对象制空. gc回收机制在销毁对象前会调用finalize函数处理一些相应的逻辑.
finalized 方法不建议使用, 原因如下

1. Java语言规范并不保证finalize方法会被及时地执行、而且根本不会保证它们会被执行
2. finalize方法可能会带来性能问题。因为JVM通常在单独的低优先级线程中完成finalize的执行
3. 对象再生问题：finalize方法中，可将待回收对象赋值给GC Roots可达的对象引用，从而达到对象再生的目的
4. finalize方法至多由GC执行一次(用户当然可以手动调用对象的finalize方法，但并不影响GC对finalize的行为)

### System.gc

System.gc 同步阻塞方法, 执行时就会停止整个应用的所有响应, 直到回收机制回收完成

### 强引用 & 弱引用 & 软引用 & 虚引用

强引用 : 普通引用. 内存不足抛 Out of Memory 异常
软引用(SoftReferenve) : 内存充足的情况下不会被回收，内存不充足的情况下才会被回收。能够很好地避OOM异常。
弱引用(WeakReference) : , 如果不被外界引用的话, 当垃圾回收机制运行时, 弱引用引用的对象就会被回收掉.
虚引用(PhantomReference) : 极不常用, 且必须和引用队列 （ReferenceQueue）联合使用, 建立的时候同时传递一个引用队列进去, 若垃圾回收机制启动时, 则将对象传到队列中去.虚引用主要用来跟踪对象被垃圾回收器回收的活动。当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之 关联的引用队列中。
引用队列(ReferenceQueue) : 当一个obj被gc掉之后，其相应的包装类，即ref对象会被放入queue中。我们可以从queue中获取到相应的对象信息，同时进行额外的处理。比如反向操作，数据清理等。

   ```java
      public void testWeakReference() {
         // 若弱引用的变量不被外界引用的时候,就可以被gc回收掉
         WeakReference<Object> softReference = new WeakReference<>(new Object());
         System.out.println(softReference.get());
         System.gc();
         System.out.println(softReference.get());

         // 垃圾回收时不会回收常量池中的内容
         softReference = new WeakReference<>("ghjfg");
         System.out.println(softReference.get());
         System.gc();
         System.out.println(softReference.get());
      }
   ```

