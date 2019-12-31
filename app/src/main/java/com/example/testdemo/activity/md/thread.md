java多线程总结

new Thread是jvm调用底层代码创建线程的
线程执行任务，，通过runnable  callable的实现类实现的
线程内runnable内抛出异常不会被捕获   callable异常会被捕获（发生异常所会被释放---多线程数据会出现异常）

锁，锁住的是对象，，不是对象的引用，，，，方法锁对象是this   静态方法锁对象是当前类的class对象
wait notify  notifyAll是在synchronized内使用的

wait会释放锁，，，notify，notifyAll不会释放锁，notifyAll唤醒的其他线程去竞争锁
synchronized代码块或者方法内代码执行完会自定释放锁

reentrantLock 手动锁  参数为true即为公平锁，可以手动控制锁，，，synchronized  jvm控制锁的释放

支持多线程的容器
BlockingQueue

Thread  join方法：  t.join调用该方法，会阻塞当前线程，，直到t线程执行完，当前线程才会继续执行


1，同步锁
2，同步容器，阻塞队列容器
3，线程池操作




