package com.example.testdemo.activity.thread;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author caicai
 * @create 2019/12/21
 * @Describe 多线程生产者与消费者
 */
public class ProducerCustomDemo {

    private int allCount = 0;
    private int MAX = 12;//容器最大12
    private int count = 0;//容器内产品数量
    private LinkedList<String> container = new LinkedList<String>();

    public static void main(String[] args) {

        final ProducerCustomDemo demo = new ProducerCustomDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.allCount += 10;
                for (int i = 0; i < 10; i++) {
                    demo.put("product" + i);
                }
            }
        }, "p01---").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.allCount += 16;
                for (int i = 0; i < 16; i++) {
                    demo.put("product" + i);
                }
            }
        }, "p02---").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        if (demo.allCount == 0 && demo.container.size() == 0)
                            break;
                        demo.get();
                    }
                }
            }
        }, "c01---").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        if (demo.allCount == 0 && demo.container.size() == 0)
                            break;
                        demo.get();
                    }
                }
            }
        }, "c02---").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        if (demo.allCount == 0 && demo.container.size() == 0)
                            break;
                        demo.get();
                    }
                }
            }
        }, "c03---").start();

    }

    private synchronized void get() {
        while (container.size() == 0) {
            try {
                System.out.print(Thread.currentThread().getName() + "等待\n");
                if (allCount == 0) {
                    notifyAll();
                    System.out.print(Thread.currentThread().getName() + "结束\n");
                    return;
                }
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        container.remove(0);
        count--;
        System.out.print(Thread.currentThread().getName() + "消费一个产品" + "容器产品数：" + count + "\n");
        notifyAll();//走完了释放锁
    }

    private synchronized void put(String product) {
        while (container.size() == MAX) {
            try {
                wait();//释放锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        allCount--;
        container.add(product);
        count++;
        System.out.print(Thread.currentThread().getName() + "生产一个产品" + "容器产品数：" + count + "--还剩产品：" + allCount + "\n");
        notifyAll();//走完了释放锁
    }

}
