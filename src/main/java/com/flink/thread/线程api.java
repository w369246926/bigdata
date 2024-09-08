//package com.flink.thread;
//
//public class 线程api {
//    public ThreadPoolExecutor(int corePoolSize,
//
//                              int maximumPoolSize,
//
//                              long keepAliveTime,
//
//                              TimeUnit unit,
//
//                              BlockingQueue<Runnable> workQueue,
//
//                              ThreadFactory threadFactory,
//
//                              RejectedExecutionHandler handler)
//
//    corePoolSize : 核心线程
//
//    maximumPoolSize : 最大线程(最大线程-核心线程=可以超时的线程)
//
//    keepAliveTime : 超时线程时间
//
//    TimeUnit unit : 超时线程时间单位
//
//    workQueue : 常用的 LinkedBlockingQueue和SynchronousQueue
//
//    threadFactory : 线程工厂，就是创建线程的工厂，可以使用 Executors.defaultThreadFactory() 或者 自定义
//
//    handler : 拒绝策略
//
//    AbortPolicy : 默认策略，直接抛 RejectedExecutionException 异常，可以比如说在提交线程中进行异常处理，重试提交
//
//    DiscardPolicy : 直接扔掉，一般不会这么玩
//
//    DiscardOldestPolicy : 其实就是对头移除，队尾增加任务，一般也不用
//
//    CallerRunsPolicy : 只要线程还没有关闭，那么这个策略就会直接在提交任务的用户线程运行当前任务，说白了，就是线程池提交不了任务了，我要占用用户线程，比如说main线程来执行任务，可能会影响主线程任务的执行
//
//
//
//    execute(new Runnable()) 在任务提交的时候
//
//1、小于核心线程创建核心线程
//
//2、大于核心线程，向队列中压入
//
//3、队列满，创建非核心线程
//
//
//
//            addWorker
//
//1、使用 ReentrantLock 加锁，保证了创建线程的 HashSet<Worker> workers 线程安全，
//
//            2、Worker是集成了AQS，同时实现了 Runnable，说明Worker是一个可以加锁的Runnable线程
//
//3、将Worker线程启动，Worker本身是Runnable，但是里面包含一个线程Thread，将Runnable放入到Thread，然后启动Worker
//
//    Worker使用AQS的两个作用：
//
//    在shutdown的时候，可以让正在运行的任务运行完毕，因为正在运行任务是获取了这个worker的锁，所以shutdown的时候，去获取锁获取不到，然后就不interrupt
//
//    正在运行的任务可以运行完毕之后推出，因为之前已经设置了SHUTDOWN标记
//
//    一旦Worker启动之后，就开始无限循环的getTask，如果获取一个任务，则加锁，执行该任务
//
//    getTask很关键 :
//
//    如果执行了shutdown
//
//            getTask
//
//1、设置线程池当前的状态为shutdown，将空闲的Worker interrupt了，Worker run方法中有两处可以响应 interrupt
//
//    第一处就是 task.run()，本身提交的任务是可以响应interrupt的，比如说sleep，第二处是getTask的 workQueue.poll 和 workQueue.take 也可以响应中断请求
//
//    针对shutdown情况下，不会对 task.run进行 interrupt，最多只会对 getTask的 workQueue.poll 和 workQueue.take 空闲线程进行interrupt
//
//2、如果当前线程状态为shutdown状态，且workQueue.isEmpty()空了，那就直接return null， 或者下面走非核心线程超时
//
//
//
//            如果执行了shutdownnow
//
//    不管是正在执行的任务还是核心线程阻塞或者非核心线程等待超时的线程，都会进行interrupt，一起进行退出
//
//
//
//    SynchronousQueue，是针对CachedThreadPool的
//
//    CachedThreadPool创建出来的都是非核心线程，核心原理 :
//
//    当offer时候，如果poll没有线程在获取，那就会失败，直接创建非核心线程
//
//    如果在offer的时候，正好有线程在poll，那就会复用之前创建的非核心线程
//
//    put和take相对来说是阻塞的，零容忍
//
//
//
//    submit(new Callable())， callable 以构造参数传入到 FutureTask 中
//
//    FutureTask有 Runnable和Future两种特性，其实说白了就是创建了一个返回值的引用对象
//
//    submit其实比execute来说就多创建一个 FutureTask 对象，FutureTask对象封装了 new Callable()，然后其他方式都是一样的
//
//    submit和execute不同的是，execute在Worker run方法直接调用task.run，submit其实也是调用的 FutureTask run中的 callable.run方法
//
//    callable.run是有结果的，如果正常执行完毕，通过CAS将当前FutureTask NEW 设置为 COMPLETING，设置outcome = v(计算结果)，调用 finishCompletion
//
//    将通过get park住的线程进行unpark(是一个栈，栈中的线程都进行unpark)
//
//    get的时候，如果完成了直接将值返回，如果未完成，类似AQS 压栈 线程LockSupport.park
//}
