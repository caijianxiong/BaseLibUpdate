package com.example.testdemo.activity.thread;

import java.util.concurrent.Semaphore;

import cjx.liyueyun.baselib.base.mvp.utils.logUtil;

public class SemaphoreTestThread extends Thread {

    private Semaphore semaphore;

    public SemaphoreTestThread(Semaphore semaphore,String name) {
        this.semaphore = semaphore;
        this.setName(name);
    }

    static int i=0;

    @Override
    public void run() {
        try {
            while (true){
                semaphore.acquire();
                if(i<=1000){
                    sleep(100);
                    logUtil.d_2("SemaphoreTestThread",Thread.currentThread().getName() + " " + i++);
                }
                else
                    break;
                semaphore.release();
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
