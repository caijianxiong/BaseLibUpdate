package com.example.testdemo;

import com.example.testdemo.activity.CrashHandler;

import cjx.baselib.LibApplication;
import cjx.baselib.log.logUtil;
import cjx.baselib.utils.FileUtil;

public class MyApplication extends LibApplication {


    private String packgeName;

    @Override
    public void onCreate() {
        super.onCreate();
        String logPath = FileUtil.getSdCachePath(this);
        logUtil.getInstance()
                .init()
                .level(logUtil.DEBUG)
                .isOpenLog(true)
                .isWriteToFile(true)
                .setLogSavePath(logPath)
                .setMaxSaveDays(5)
                .build();

        packgeName = this.getPackageName();
        CrashHandler.getInstance().init();

    }




}
