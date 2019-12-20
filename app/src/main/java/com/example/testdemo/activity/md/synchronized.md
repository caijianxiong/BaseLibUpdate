synchronized  锁住的是对象


1，synchronized锁住范围内的代码块，，一次只能一个线程访问，只有访问结束后自动释放锁，jvm分配给其他线程，其他线程才能访问（其他时间其他线程等待）
2，锁静态方法，，锁对象就是当前class类  锁方法锁对象就是this   还可以锁某个对象，，但对象不可变（不能重新赋值）
3，1）一个同步方法（synchronized方法）内可以调用另一个同步方法，锁是可以重入的
   2）子类的同步方法可以调用父类的同步方法
   3）程序执行过程中出现异常，锁是会被释放的（多线程，要处理异常，不然数据会发生错乱）

4，模拟一个死锁,,两个线程1 需要A的锁，内部又需要B的锁才能执行完  ，线程2刚好相反，，但执行时，1持有A的锁  2持有B的锁  相互都不能执行完
        Object lockA=new Object();
        Object lockB=new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lockA){
                    print01("A");
                    synchronized (lockB){
                        print02("B");
                    }
                }
            }
        }).start();

          new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (lockB){
                            print01("A");
                            synchronized (lockA){
                                print02("B");
                            }
                        }
                    }
                }).start();

 5，volatile 和  synchronized的区别
    volatile：使一个变量在多个线程可见（一个线程变，，另一个线程取得是变化的值）
    区别：volatile只保证可见性，synchronized既保证可见性又保证原子性

    只保证可见性存在的问题：如果多个线程对统一共享变量进行修改，就只对自己线程修改完的数据，通知其他线程（其他线程就只用这个数据，++操作，就会忽略其他线程+ 的结果），
                            而不管其他线程修改的结果。线程多，共享数据复杂不建议用

    java，，提供了一系列的原子性操作类AtomicXXXX,保证原子性，，但是多个原子性方法之间，，多线程时也不能确保原子性  ，用synchronized，才能确保

    synchronized锁的范围越小，，程序的效率越高