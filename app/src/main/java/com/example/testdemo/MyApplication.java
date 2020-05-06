package com.example.testdemo;

import android.app.Application;

import cjx.liyueyun.baselib.base.mvp.log.logUtil;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String logPath=this.getFilesDir().getAbsolutePath();
        logUtil.getInstance()
                .init()
                .level(logUtil.DEBUG)
                .isOpenLog(true)
                .isWriteToFile(true)
                .setLogSavePath(logPath)
                .setMaxSaveDays(5)
                .build();

    }
}
