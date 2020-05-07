package com.example.testdemo.activity.thread;

import android.nfc.Tag;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author caicai
 * @create 2019/12/21
 * @Describe 手动锁实现消费者生产者
 */
public class ReentrantLockDemo {

    private ReentrantLock lock = new ReentrantLock();
    private int MAX = 12;
    private int count = 0;
    private Condition productCondition = lock.newCondition();
    private Condition customCondition = lock.newCondition();

    private LinkedList<String> contanier = new LinkedList<>();

    public static void main(String[] args) throws InterruptedException {

        final ReentrantLockDemo lockDemo = new ReentrantLockDemo();


        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int n = 0; n < 20; n++) {
                        lockDemo.get();
                    }
                }
            }, "c" + i).start();
        }
        Thread.sleep(1000);

        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int n = 0; n < 20; n++) {
                        lockDemo.put();
                    }
                }
            }, "p" + i).start();
        }

    }


    private void put() {
        try {
            lock.lock();
            while (contanier.size() == MAX) {
                System.out.print(Thread.currentThread().getName() + "等待" + "\n");
                productCondition.await();
            }

            Thread.sleep(300);
            count++;
            contanier.add("hhhahhah");
            System.out.print(Thread.currentThread().getName() + "生产一个" + "容器剩：" + count + "\n");
            customCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    private void get() {
        try {
            lock.lock();
            while (contanier.size() == 0) {
                System.out.print(Thread.currentThread().getName() + "等待" + "\n");
                customCondition.await();
            }
            count--;
            contanier.removeFirst();
            System.out.print(Thread.currentThread().getName() + "消费一个" + "容器剩：" + count + "\n");
            productCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}
