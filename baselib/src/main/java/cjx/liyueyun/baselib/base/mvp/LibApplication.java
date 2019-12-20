package cjx.liyueyun.baselib.base.mvp;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;

/**
 * @author caicai
 * @create 2019/11/26
 * @Describe
 */
public class LibApplication extends Application {

    public static LibApplication application;
    public static Context appContext;
    public static Gson mGson;


    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        appContext=this;
        mGson=new Gson();
    }

    public static Gson getmGson() {
        return mGson;
    }

    public static LibApplication getApplication() {
        return application;
    }

    public static Context getAppContext() {
        return appContext;
    }
}
