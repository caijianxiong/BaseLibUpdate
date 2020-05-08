package cjx.baselib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;

import java.util.LinkedList;


/**
 * @author caicai
 * @create 2019/11/26
 * @Describe
 */
public class LibApplication extends Application {

    public static LibApplication application;
    public static Context appContext;
    public static Gson mGson;
    private static LinkedList<Activity> activities;


    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        appContext=this;
        activities = new LinkedList<>();
        mGson=new Gson();
        ///data/data/PACKAGE_NAME/files/tombstones  xCrash捕获的崩溃日志文件路径
//        xcrash.XCrash.init(this);
    }

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAllActivity() {
        for (Activity activity : activities) {
            activity.finish();
        }
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
