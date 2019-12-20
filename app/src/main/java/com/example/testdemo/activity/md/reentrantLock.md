reentrantLock  重入锁/手动锁  可替代synchronized

 ReentrantLock lock =new ReentrantLock();
        try{
            //上锁
            lock.lock();
            //doSomething
        }catch (Exception e){
            e.printStackTrace();
        }finally {
             //必须的手动释放
            lock.unlock();
        }

        不同的方法，用同一把锁互斥----类似于synchronized只有一个持有锁的线程才能进入
        1，ReentrantLock可以尝试锁定 boolean lock =tryLock(5,TimeUtil.SECONDS);
           尝试5锁定，，成功锁定，，不执行下面代码，，没说成功，还是会执行下面代码，，不会像synchronized其他线程没有释放锁，，就死等别的线程释放锁才能执行
        2，打断锁
         Thread thread05 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lock1.lockInterruptibly();//设置锁可以被打断
                            //doSomething
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            lock1.unlock();//锁没有锁住回报异常
                        }
                    }
                });
                thread05.start();
                thread05.interrupt();//打断ReentrantLock锁住的线程
         3，可指定公平锁
