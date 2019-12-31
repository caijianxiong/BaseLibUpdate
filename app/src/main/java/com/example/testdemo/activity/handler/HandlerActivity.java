package com.example.testdemo.activity.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testdemo.R;

import cjx.liyueyun.baselib.base.mvp.BaseActivity;
import cjx.liyueyun.baselib.base.mvp.utils.logUtil;

public class HandlerActivity extends BaseActivity {

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });
    private ServiceHandler serviceHandler;
    private Handler handlerThread;
    private Button btn01;


    @Override
    public int getLayoutId() {
        return R.layout.activity_handler;
    }

    @Override
    public void initView() {
        btn01 = (Button) findViewById(R.id.btn01);


        //方法一，创建工作线程的handler
        HandlerThread thread = new HandlerThread("backgroundThread", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Looper mServiceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(mServiceLooper);

        //

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handlerThread = new Handler(Looper.myLooper(),new MyThreadCallBack());
                Looper.loop();
            }
        }).start();




        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                serviceHandler.sendEmptyMessageDelayed(111111, 1200);
                handlerThread.sendEmptyMessageDelayed(111111, 1200);
            }
        });

    }

    @Override
    public void initData() {

    }

    class MyThreadCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            logUtil.d_2(TAG, "ThreadName:" + Thread.currentThread().getName() + "----msgWhat:" + msg.what);
            return false;
        }
    }


    class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            logUtil.d_2(TAG, "ThreadName:" + Thread.currentThread().getName() + "----msgWhat:" + msg.what);

        }
    }

}
