package cjx.baselib.log;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import cjx.BuildConfig;

public class logUtil {

    private static String TAG = "logUtil";
    private static logUtil instance;
    public static final int VERBOSE = 1;//能输出所有级别详细信息
    public static final int DEBUG = 2;//能输出Debug、Info、Warning、Error级别的Log信息
    public static final int INFO = 3;//能输出Info、Warning、Error级别的Log信息
    public static final int WARN = 4;//能输出Warning、Error级别的Log信息
    public static final int ERROR = 5;
    public static final int NOTHING = 6;//不打印日记

    //application可配置参数
    private static int LOG_LEVEL = 1;
    private static boolean OPEN_LOG = true; // 日志文件总开关
    private static boolean WRITE_TO_FILE = true;// 日志写入文件开关
    private static String LOG_SAVE_PATH = "";// 日志文件在sdcard中的路径
    private static int LOG_FILE_MAX_SAVE_DAYS = 5;// sd卡中日志文件的最多保存天数


    private static String LOG_FILE_NAME = "Log.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);// 日志的输出格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);// 日志文件格式
    private static HashMap<String, String> tagMsgMap;
    private static Handler logWriteHandler;
    private static Gson gson;

    private logUtil() {
        HandlerThread logThread = new HandlerThread("logWriteThread", Thread.NORM_PRIORITY);
        logThread.start();
        Looper looper = logThread.getLooper();
        logWriteHandler = new Handler(looper, new HandlerCallBack());
        tagMsgMap = new HashMap<>();
        gson=new Gson();
    }


    public static logUtil getInstance() {
        if (instance == null) {
            synchronized (logUtil.class) {
                if (instance == null)
                    instance = new logUtil();
            }
        }
        return instance;
    }


    public Builder init() {
        return new Builder();
    }

    private void setParams(boolean isOPenLog, boolean isWriteToFile, String logSavePath, int maxSaveDays, int logLevel) {
        OPEN_LOG = isOPenLog;
        WRITE_TO_FILE = isWriteToFile;
        LOG_SAVE_PATH = logSavePath;
        LOG_FILE_MAX_SAVE_DAYS = maxSaveDays;
        LOG_LEVEL = logLevel;
    }

    public static class Builder {

        private Builder() {
        }

        private int logLevel;
        private boolean isOPenLog;
        private boolean isWriteToFile;
        private String logSavePath;
        private int maxSaveDays;

        public Builder level(int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Builder isOpenLog(boolean isOPenLog) {
            this.isOPenLog = isOPenLog;
            return this;
        }

        public Builder isWriteToFile(boolean isWriteToFile) {
            this.isWriteToFile = isWriteToFile;
            return this;
        }

        public Builder setLogSavePath(String logSavePath) {
            this.logSavePath = logSavePath;
            return this;
        }

        public Builder setMaxSaveDays(int maxSaveDays) {
            this.maxSaveDays = maxSaveDays;
            return this;
        }


        public void build() {
            if (instance == null)
                throw new RuntimeException("logUtil instance is null");
            instance.setParams(isOPenLog, isWriteToFile, logSavePath, maxSaveDays, logLevel);
        }

    }


    public static void w(String tag, Object msg) { // 警告信息
        w(tag, gson.toJson(msg));
    }

    public static void e(String tag, Object msg) { // 错误信息
        e(tag, gson.toJson(msg));
    }

    public static void d(String tag, Object msg) {// 调试信息
        d(tag, gson.toJson(msg));
    }

    public static void i(String tag, Object msg) {//
        i(tag, gson.toJson(msg));
    }

    public static void v(String tag, Object msg) {
        v(tag, gson.toJson(msg));
    }

    public static void w(String tag, String text) {
        log(tag, text, WARN);
    }

    public static void e(String tag, String text) {
        log(tag, text, ERROR);
    }

    public static void d(String tag, String text) {
        log(tag, text, DEBUG);
    }

    public static void i(String tag, String text) {
        log(tag, text, INFO);
    }

    public static void v(String tag, String text) {
        log(tag, text, VERBOSE);
    }

    private static void log(String tag, String msg, int level) {
        if (OPEN_LOG) {
            if (level < LOG_LEVEL) return;
            switch (level) {
                case ERROR:
                    Log.e(tag, msg);
                    break;
                case WARN:
                    Log.w(tag, msg);
                    break;
                case INFO:
                    Log.i(tag, msg);
                    break;
                case DEBUG:
                    Log.d(tag, msg);
                    break;
                case VERBOSE:
                    Log.v(tag, msg);
                    break;
                case NOTHING:
                default:
                    break;
            }
        }

        if (WRITE_TO_FILE && logWriteHandler != null ) {//debug 写debug以上的日志   release只写入error日志
            if (BuildConfig.DEBUG&&level>=DEBUG){
                sendMsg(tag, msg, level);
            }else if (!BuildConfig.DEBUG&&level>=WARN){
                //release 写入 warn error
                sendMsg(tag, msg, level);
            }
        }
    }

    private static void sendMsg(String tag, String msg, int level) {
        tagMsgMap.clear();
        tagMsgMap.put("tag", tag);
        tagMsgMap.put("msg", msg);
        Message m = Message.obtain();
        m.what = level;
        m.obj = tagMsgMap;
        logWriteHandler.sendMessage(m);
    }


    static class HandlerCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            int level = msg.what;
            HashMap<String, String> str = (HashMap<String, String>) msg.obj;
            String tag = str.get("tag");
            String text = str.get("msg");
            writeLogToFile(level, tag, text);
            return false;
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @param logLevel
     * @param tag
     * @param text
     */
    private static void writeLogToFile(int logLevel, String tag, String text) {// 新建或打开日志文件

        if ("".equals(LOG_SAVE_PATH))
            throw new RuntimeException(" please set a log save rootPath");

        Date nowTime = new Date();
        String needWriteFile = logfile.format(nowTime);
        String needWriteMessage = myLogSdf.format(nowTime) + " logLevel:" + logLevel + "***" + tag + "----" + text;

        File dirsFile = new File(LOG_SAVE_PATH);
        if (!dirsFile.exists()) {
            dirsFile.mkdirs();
        }

        File file = new File(dirsFile.toString(), needWriteFile + LOG_FILE_NAME);// LOG_SAVE_PATH
        if (!file.exists()) {
            try {
                //在指定的文件夹中创建文件
                file.createNewFile();
            } catch (Exception e) {
            }
        }
        try {
            FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            Log.d(TAG,e.getMessage());
        }
    }

    /**
     * 删除制定的日志文件
     */
    private static void delFile() {// 删除日志文件
        String needDelFile = logfile.format(getDateBefore());
        File dirPath = Environment.getExternalStorageDirectory();
        File file = new File(dirPath, needDelFile + LOG_FILE_NAME);// LOG_SAVE_PATH
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
     */
    private static Date getDateBefore() {
        Date nowTime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowTime);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - LOG_FILE_MAX_SAVE_DAYS);
        return now.getTime();
    }

}
