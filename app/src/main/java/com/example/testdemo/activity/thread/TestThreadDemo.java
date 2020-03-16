package com.example.testdemo.activity.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class TestThreadDemo {

    private static List<String> strings;
    private static Object lock;

    public static void main(String[] a) {
        strings = new ArrayList<>();
        lock = new Object();
        init();


    }


    private static void init() {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程A start");
                synchronized (lock) {
                    for (int i = 0; i < 10; i++) {
                        try {
                            strings.add("" + i);
                            System.out.println("线程A  Add:" + i);
                            if (strings.size() == 5) {
                                lock.wait();
                            } else {
                                lock.notify();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程B start");
                synchronized (lock) {
                    while (true) {
                        try {
                            if (strings.size() == 5) {
                                System.out.println("唤醒线程B");
                                lock.notify();
                                break;
                            } else {
                                System.out.println("线程B等待");
                                lock.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        try {
            threadB.start();
            Thread.sleep(1500);
            threadA.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
