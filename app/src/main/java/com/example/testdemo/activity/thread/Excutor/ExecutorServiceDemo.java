package com.example.testdemo.activity.thread.Excutor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author caicai
 * @create 2019/12/24
 * @Describe
 */
public class ExecutorServiceDemo {


    public static void main(String[] a) throws ExecutionException, InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                        System.out.print("name:" + Thread.currentThread().getName() + "\n");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


//        service.shutdown();

        System.out.print("terminated:" + service.isTerminated() + "\n");
        System.out.print("shutdown:" + service.isShutdown() + "\n");
        System.out.print("status:" + service + "\n");

        //****************************************
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(5000);
                return 10010;
            }
        });

        new Thread(task).start();
        System.out.print("FutureTask--result:" + task.get());

        //*******************
        ExecutorService service02 = Executors.newFixedThreadPool(5);
        Future<Integer> future=service02.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 111;
            }
        });

        System.out.print(future.get());//阻塞  任务执行完返回
        System.out.print(future.isDone());

        /**
         * 六种线程池
         * ExecutorService几种获取实例方法
         */

        Executors.newCachedThreadPool();//来任务才new线程
//        newSingleThreadExecutor单线程

        //ScheduledExecutorService 定时器线程池，，线程复用




    }

}
