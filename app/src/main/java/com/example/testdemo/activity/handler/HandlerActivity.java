package com.example.testdemo.activity.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.testdemo.R;

import cjx.liyueyun.baselib.base.mvp.BaseActivity;

public class HandlerActivity extends BaseActivity {

    private String TAG=this.getClass().getSimpleName();
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });
    private ServiceHandler serviceHandler;


    @Override
    public int getLayoutId() {
        return R.layout.activity_handler;
    }

    @Override
    public void initView() {
        HandlerThread thread = new HandlerThread("family.tv", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Looper mServiceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public void initData() {

    }


    class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

        }
    }

}
