package cjx.baselib.sp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharedPreference 官方文档说明不支持多线程，，进程也不安全
 */
public class SpUtil {

    private static final SpUtil instance = new SpUtil();
    private static SharedPreferences defaultSp;
    private static SpBuilder spBuilder;

    private SpUtil() {
    }

    public static SpUtil getInstance() {
        return instance;
    }

    public synchronized SpBuilder builder() {
        if (spBuilder == null)
            spBuilder = new SpBuilder();
        return spBuilder;
    }


}
