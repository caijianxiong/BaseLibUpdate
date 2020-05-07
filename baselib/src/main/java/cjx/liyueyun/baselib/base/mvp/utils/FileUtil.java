package cjx.liyueyun.baselib.base.mvp.utils;

import android.content.Context;

/**
 * - 三种路径:
 * 1. /data/data/package_name/ 该目录是只对应用可见的，而且如果手机没有root，用adb也是看不了这个目录，该目录用来存储和应用周期相关的文件，会随着应用的卸载一起删除掉
 * - /data/data/com.android.framework/shared_prefs/ 用来存储SharedPreference
 * - /data/data/com.android.framework/databases/ 用来存储数据库DB，相关函数还有getDatabasePath()
 * - /data/data/com.android.framework/app_webview 和 /data/data/package_name/xxxwebviewcachexxx 用来存储应用内置webview所产生的cache和cookies等，该目录由于android版本不同名字和位置也可能不同
 * - /data/data/com.android.framework/lib 用来存储该应用的.so静态库文件
 * - /data/data/com.android.framework/cache 该目录可以使用函数getCacheDir()获取
 * - data/data/com.android.framework/files 该目录可以使用函数getFilesDir()获取，openFileInput()和openFileOutput()函数也是在该目录下操作文件，fileList()函数是用来列出该files目录下的所有文件，deleteFile(String name)用来删除该files目录下的文件
 * - /data/data/com.android.framework/ 这个目录下面当然也能够创建子集的目录，使用的方法就是getDir(String name, int mode)，参数中的name就是需要在该目录下创建的子目录名字,你如果能够打开应用的该目录，一般会在该目录下看到很多子目录
 * - getPackageCodePath() = /data/app/com.android.framework-1.apk
 * - getPackageResourcePath() = /data/app/com.android.framework-1.apk
 */
public class FileUtil {

    /**
     * @param appContext
     * @return /data/data/com.example.testdemo/cache
     */
    public static String getAppCachePath(Context appContext){
        return appContext.getCacheDir().getAbsolutePath();
    }

    /**
     * @param appContext
     * @return /data/data/com.example.testdemo/files
     */
    public static String getAppFilePath(Context appContext){
        return appContext.getFilesDir().getAbsolutePath();
    }

    /**
     *
     * @param appContext
     * @return  /storage/emulated/0/Android/data/com.example.testdemo/cache
     */
    public static String getSdCachePath(Context appContext){
        if (appContext.getExternalCacheDir()==null)
            throw new RuntimeException("sdCacheFile is null");

       return appContext.getExternalCacheDir().getAbsolutePath();
    }

    /**
     *
     * @param appContext
     * @return  /storage/emulated/0/Android/obb/com.example.testdemo
     */
    public static String getSdObbPath(Context appContext){
        return appContext.getObbDir().getAbsolutePath();
    }


}
