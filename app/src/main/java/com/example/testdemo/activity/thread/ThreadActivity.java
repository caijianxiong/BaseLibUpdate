package com.example.testdemo.activity.thread;

import android.os.Handler;
import android.os.Message;

import com.example.testdemo.R;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import cjx.liyueyun.baselib.base.mvp.BaseActivity;
import cjx.liyueyun.baselib.base.mvp.log.logUtil;

public class ThreadActivity extends BaseActivity {


    private int i;
    private Object lock;
    private volatile boolean currentThread01;
    private volatile int n;
    private CountDownLatch downLatch;
    private ReentrantLock lock1;

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    public int getLayoutId() {
        return R.layout.activity_therad;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        //多线程轮流打印1-1000

        //多线程按顺序从1==1000打印

        //通过公平锁实现，两个线程交替打印1-100
//        FairLock r1 = new FairLock();
//        Thread t1 = new Thread(r1, "Thread_t1");
//        Thread t2 = new Thread(r1, "Thread_t2");
//        Thread t3 = new Thread(r1, "Thread_t3");
//        t1.start();
//        t2.start();
//        t3.start();
        //


//        Thread.yield();//线程礼让实现,,,,可能没有效果，，因为礼让的线程可能线程调度再度选中
//        Thread thread01 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 1000; i++) {
//                   logUtil.d(TAG, "i=" + i);
//                    Thread.yield();
//                }
//
//            }
//        });
//        Thread thread02 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 1000; i++) {
//                   logUtil.d(TAG, "j=" + i);
//                    Thread.yield();
//                }
//
//            }
//        });
//        thread01.start();
//        thread02.start();


        //通过notify wait实现
//        lock = new Object();
//        currentThread01 = true;
//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    synchronized (lock) {
//                        if (currentThread01) {
//                            if (i >= 100) break;
//                            i++;
//                            try {
//                                Thread.sleep(600);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                           logUtil.d(TAG, Thread.currentThread().getName() + "i=" + i);
//                            currentThread01 = false;
//                            lock.notify();
//                        } else {
//                            try {
//                                lock.wait();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                               logUtil.d(TAG, "error=" + e.getMessage());
//                            }
//                        }
//                    }
//                }
//            }
//        });
//        t1.setName("线程1---");
//        t1.start();
//
//
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    synchronized (lock) {
//                        if (!currentThread01) {
//                            if (i >= 100) break;
//                            i++;
//                            try {
//                                Thread.sleep(600);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                           logUtil.d(TAG, Thread.currentThread().getName() + "i=" + i);
//                            currentThread01 = true;
//                            lock.notify();
//                        } else {
//                            try {
//                                lock.wait();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                               logUtil.d(TAG, "error=" + e.getMessage());
//                            }
//                        }
//                    }
//                }
//            }
//        });
//        t2.setName("线程2---");
//        t2.start();


        //countDownLatch（门栓）线程三，，数到100执行线程四
//        downLatch = new CountDownLatch(1);
//        n = 0;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    downLatch.await();//关门等待
//                   logUtil.d(TAG, "thread04启动");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "thread04").start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//               logUtil.d(TAG, "thread03启动");
//                while (true) {
//                    n++;
//                    if (n > 100) {
//                        downLatch.countDown();//开门
//                    }
//                    if (n > 150) break;
//                   logUtil.d(TAG, Thread.currentThread().getName() + "---n=" + n);
//                }
//            }
//        }, "thread03").start();

        //reentrantLock  重入锁  可替代synchronized
//        lock1 = new ReentrantLock();
//        Thread thread05 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
////            lock.lock();
//                    lock1.lockInterruptibly();//设置锁可以被打断
//                    //doSomething
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    lock1.unlock();//所没有锁住回报异常
//                }
//            }
//        });
//        thread05.start();
//        thread05.interrupt();//打断ReentrantLock锁住的线程

//
//        lock1 = new ReentrantLock(true);//公平锁实现两个线程轮流打印  true公平锁  false不公平
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 100; i++) {
//                    try {
//                        lock1.lock();
//                        Thread.sleep(200);//jvm根据等待时间判断下个线程谁来，，加个sleep时间，，，不然效果不明显
//                       logUtil.d(TAG, Thread.currentThread().getName() + "获得锁");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                       logUtil.d(TAG,"error----------");
//                    } finally {
//                        lock1.unlock();
//                    }
//                }
//
//            }
//        };
//        new Thread(runnable, "thread01").start();
//        new Thread(runnable, "thread02").start();


        //
        //阻塞式容器  实现生产者消费者
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();


        /**
         * 生产者，消费者
         */


    }


    private synchronized void print01(String string) throws InterruptedException {
        if (!currentThread01) {
            lock.wait();
        }
       logUtil.d(TAG, string);
        lock.notify();
        currentThread01 = true;
    }

    private synchronized void print02(String string) throws InterruptedException {
       logUtil.d(TAG, string);
        if (currentThread01) {
            lock.wait();
        }
       logUtil.d(TAG, string);
        lock.notify();
        currentThread01 = false;
    }

    class FairLock implements Runnable {
        private ReentrantLock fairLock = new ReentrantLock(true);
        private int i = 1;

        @Override
        public void run() {
            while (i < 100) {
                synchronized (Thread.currentThread()) {
                    try {
                        fairLock.lock();
                        Thread.currentThread().wait(100);
                       logUtil.d("TTTAAAAGGG", Thread.currentThread().getName() + " " + i++);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        fairLock.unlock();
                    }
                }
            }
        }
    }


}
