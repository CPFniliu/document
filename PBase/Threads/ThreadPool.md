# ThreadPool

> https://www.jianshu.com/p/ae67972d1156

## 基本

### 线程池概述

在Java中，线程池的概念是Executor这个接口，具体实现为 ThreadPoolExecutor 类.
Executor框架是一种将线程的创建和执行分离的机制。它基于Executor和ExecutorService接口，及这两个接口的实现类ThreadPoolExecutor展开，Executor有一个内部线程池，并提供了将任务传递到池中线程以获得执行的方法，可传递的任务有如下两种：通过Runnable接口实现的任务和通过Callable接口实现的任务。在这两种情况下，只需要传递任务到执行器，

### 线程池作用

1. 将线程的创建和执行分离. 增加执行效率和资源利用率.
2. 对线程进行简单管理
3. 简化高并发编程

### 相关结构

![ThreadPoolExecutor](/assets/concurrent/ThreadPoolExecutor-struct.png)

## 线程池的创建方式

### ThreadPoolExecutor

```java
   /**
     * jdk 1.8.191
     *
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters.
     *
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 该线程池中线程总数最大值 : 核心线程数 + 非核心线程数
     * @param keepAliveTime 存活时间 一个非核心线程，如果不干活(闲置状态)的时长超过这个参数所设定的时长，就会被销毁掉，如果设置allowCoreThreadTimeOut = true，则会作用于核心线程。
     * @param unit 存活时间单位
     * @param workQueue the queue to use for holding tasks before they are
     *        executed.  This queue will hold only the {@code Runnable}
     *        tasks submitted by the {@code execute} method.
     * @param threadFactory 创建新线程工厂
     * @param handler the handler to use when execution is blocked
     *        because the thread bounds and queue capacities are reached
     * @throws IllegalArgumentException if one of the following holds:<br>
     *         {@code corePoolSize < 0}<br>
     *         {@code keepAliveTime < 0}<br>
     *         {@code maximumPoolSize <= 0}<br>
     *         {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue}
     *         or {@code threadFactory} or {@code handler} is null
     */
    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory, RejectedExecutionHandler handler)

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, Executors.defaultThreadFactory(), defaultHandler);
    }

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, defaultHandler);
    }

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                              RejectedExecutionHandler handler) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, Executors.defaultThreadFactory(), handler);
    }

    private static final RejectedExecutionHandler defaultHandler = new AbortPolicy();

```

### 下面来解释下各个参数：

参数 | means
-|-
int corePoolSize | 核心线程数
int maximumPoolSize | 线程总数最大值 : 核心线程数 + 非核心线程数
long keepAliveTime | 该线程池中非核心线程闲置超时时长, 一个非核心线程，如果不干活(闲置状态)的时长超过这个参数所设定的时长，就会被销毁掉，如果设allowCoreThreadTimeOut = true，则会作用于核心线程。
TimeUnit unit | keepAliveTime的单位
BlockingQueue workQueue | 该线程池中的任务维护队列
ThreadFactory threadFactory | 创建线程的工厂
RejectedExecutionHandler handler | 饱和策略

> 如果指定ThreadPoolExecutor的allowCoreThreadTimeOut这个属性为true，那么核心线程如果不干活(闲置状态)的话，超过一定时间(时长下面参数决定)，就会被销毁掉。

### BlockingQueue 参数

可选BlockingQueue参数 | means
-|-
SynchronousQueue | 这个队列接收到任务的时候，会直接提交给线程处理，而不保留它，没有空闲线程就新建一个线程来处理这个任务！使用这个类型队列的时候，maximumPoolSize一般指定成Integer.MAX_VALUE，即无限大
LinkedBlockingQueue | 这个队列接收到任务的时候，如果当前线程数小于核心线程数，则新建线程(核心线程)处理任务；如果当前线程数等于核心线程数，则进入队列等待。由于这个队列没有最大值限制，即所有超过核心线程数的任务都将被添加到队列中，这也就导致了maximumPoolSize的设定失效，因为总线程数永远不会超过corePoolSize
ArrayBlockingQueue | 可以限定队列的长度，接收到任务的时候，如果没有达到corePoolSize的值，则新建线程(核心线程)执行任务，如果达到了，则入队等候，如果队列已满，则新建线程(非核心线程)执行任务，又如果总线程数到了maximumPoolSize，并且队列也满了，则发生错误
DelayQueue | 队列内元素必须实现Delayed接口，这就意味着你传进去的任务必须先实现Delayed接口。这个队列接收到任务时，首先先入队，只有达到了指定的延时时间，才会执行任务 eg : 延迟3秒后执行任务，从开始执行任务这个时候开始计时，每7秒执行一次不管执行任务需要多长的时间。

### 线程池执行原理

这里给总结一下，当一个任务被添加进线程池时，执行策略：

1.线程数量未达到corePoolSize，则新建一个线程(核心线程)执行任务
2.线程数量达到了corePools，则将任务移入队列等待
3.队列已满，新建线程(非核心线程)执行任务
4.队列已满，总线程数又达到了maximumPoolSize，就会由(RejectedExecutionHandler)抛出异常

### Executors四种线程池

1. Executors.newCachedThreadPool()

   ```java
      public static ExecutorService newCachedThreadPool() {
         return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
      }
   ```

   这种线程池内部没有核心线程，线程的数量是有没限制的。
   在创建任务时，若有空闲的线程时则复用空闲的线程，若没有则新建线程。
   没有工作的线程（闲置状态）在超过了60S还不做事，就会销毁。

2. Executors.newFixedThreadPool(int nThreads);

   ```java
   public static ExecutorService newFixedThreadPool(int nThreads) {
      return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
   }
   ```

   该线程池的最大线程数等于核心线程数，所以在默认情况下，该线程池的线程不会因为闲置状态超时而被销毁。
   如果当前线程数小于核心线程数，并且也有闲置线程的时候提交了任务，这时也不会去复用之前的闲置线程，会创建新的线程去执行任务。如果当前执行任务数大于了核心线程数，大于的部分就会进入队列等待。等着有闲置的线程来执行这个任务。

3. Executors.newSingleThreadPool()

   ```java
   public static ExecutorService newSingleThreadExecutor() {
      return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()));
   }
   ```

   有且仅有一个工作线程执行任务
   所有任务按照指定顺序执行，即遵循队列的入队出队规则

4. Executors.newScheduledThreadPool(int corePoolSize)

   ```java
      new ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS, new DelayedWorkQueue());
   ```

   DEFAULT_KEEPALIVE_MILLIS就是默认10L，这里就是10秒。这个线程池有点像是吧CachedThreadPool和FixedThreadPool 结合了一下。

   不仅设置了核心线程数，最大线程数也是Integer.MAX_VALUE。
   这个线程池是上述4个中为唯一个有延迟执行和周期执行任务的线程池。

### RejectedExecutionHandler

在使用线程池并且使用有界队列的时候，如果队列满了，任务添加到线程池的时候就会有问题，针对这些问题java线程池提供了以下几种策略：

可选 RejectedExecutionHandler | means
-|-
AbortPolicy | 线程池的默认策略。使用该策略时，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。
DiscardPolicy | 如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常
DiscardOldestPolicy | 丢弃最老的。也就是说如果队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列。
CallerRunsPolicy | 如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行。
自定义 | RejectedExecutionHandler接口，并且实现rejectedExecution方法就可以了

源码

```java
    public static class CallerRunsPolicy implements RejectedExecutionHandler {
        public CallerRunsPolicy() { }
        // 如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                r.run();
            }
        }
    }

    public static class AbortPolicy implements RejectedExecutionHandler {
        public AbortPolicy() { }
        // 线程池的默认策略。使用该策略时，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            throw new RejectedExecutionException("Task " + r.toString() + " rejected from " + e.toString());
        }
    }

    /**
     * A handler for rejected tasks that silently discards the
     * rejected task.
     */
    public static class DiscardPolicy implements RejectedExecutionHandler {
        public DiscardPolicy() { }
        // 如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        }
    }

    /**
     * A handler for rejected tasks that discards the oldest unhandled
     * request and then retries {@code execute}, unless the executor
     * is shut down, in which case the task is discarded.
     */
    public static class DiscardOldestPolicy implements RejectedExecutionHandler {
        public DiscardOldestPolicy() { }
        // 丢弃最老的。也就是说如果队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列。
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                e.getQueue().poll();
                e.execute(r);
            }
        }
    }

```