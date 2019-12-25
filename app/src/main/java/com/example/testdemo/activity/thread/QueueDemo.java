package com.example.testdemo.activity.thread;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author caicai
 * @create 2019/12/23  支持高并发的容器
 * @Describe
 */
public class QueueDemo {
    /**
     *
     */



    //阻塞式容器  实现生产者消费者
    private BlockingQueue<String> blockingQueue=new LinkedBlockingQueue<>();




    static Queue<String> queue = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 100; i++) {
            queue.add("票号：" + i);
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 12; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String s = queue.poll();//取出容器第一个的值
                        if (s == null)
                            break;
                        System.out.print("销售处：" + s+"\n");
                    }
                }
            }, "thread--" + i).start();
        }
    }
}
