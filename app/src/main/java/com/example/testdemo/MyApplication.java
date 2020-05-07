package com.example.testdemo;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;

import com.example.testdemo.activity.CrashHandler;

import java.util.LinkedList;

import cjx.liyueyun.baselib.base.mvp.LibApplication;
import cjx.liyueyun.baselib.base.mvp.log.logUtil;
import cjx.liyueyun.baselib.base.mvp.utils.FileUtil;

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
        CrashHandler.getInstance().init(this);

    }




}
