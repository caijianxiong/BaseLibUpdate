package cjx.liyueyun.baselib.base.mvp.log;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class logUtil {

    private static logUtil instance;
    public static final int VERBOSE = 1;//能输出所有级别详细信息
    public static final int DEBUG = 2;//能输出Debug、Info、Warning、Error级别的Log信息
    public static final int INFO = 3;//能输出Info、Warning、Error级别的Log信息
    public static final int WARN = 4;//能输出Warning、Error级别的Log信息
    public static final int ERROR = 5;
    public static final int NOTHING = 6;//不打印日记

    private static int LOG_LEVEL = 1;


    private static boolean OPEN_LOG = true; // 日志文件总开关
    private static boolean WRITE_TO_FILE = true;// 日志写入文件开关
    private static String LOG_SAVE_PATH = "";// 日志文件在sdcard中的路径
    private static int LOG_FILE_MAX_SAVE_DAYS = 0;// sd卡中日志文件的最多保存天数
    private static String LOG_FILE_NAME = "Log.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);// 日志的输出格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);// 日志文件格式

    private logUtil() {

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

    private void setParams(boolean isOPenLog, boolean isWriteToFile, String logSavePath, int maxSaveDays,int logLevel) {
        OPEN_LOG = isOPenLog;
        WRITE_TO_FILE = isWriteToFile;
        LOG_SAVE_PATH = logSavePath;
        LOG_FILE_MAX_SAVE_DAYS = maxSaveDays;
        LOG_LEVEL=logLevel;
    }

    public class Builder {

        public Builder() {
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
            instance.setParams(isOPenLog, isWriteToFile, logSavePath, maxSaveDays,logLevel);
        }

    }


    public static void w(String tag, Object msg) { // 警告信息
        w(tag, msg.toString());
    }

    public static void e(String tag, Object msg) { // 错误信息
        e(tag, msg.toString());
    }

    public static void d(String tag, Object msg) {// 调试信息
        d(tag, msg.toString());
    }

    public static void i(String tag, Object msg) {//
        i(tag, msg.toString());
    }

    public static void v(String tag, Object msg) {
        v(tag, msg.toString());
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
        if (WRITE_TO_FILE)//日志写入文件开关
            writeLogtoFile(String.valueOf(level), tag, msg);
    }


    /**
     * 打开日志文件并写入日志
     *
     * @param mylogtype
     * @param tag
     * @param text
     */
    private static void writeLogtoFile(String mylogtype, String tag, String text) {// 新建或打开日志文件
        Date nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        String needWriteMessage = myLogSdf.format(nowtime) + "    " + mylogtype + "    " + tag + "    " + text;
        File dirPath = Environment.getExternalStorageDirectory();

        File dirsFile = new File(LOG_SAVE_PATH);
        if (!dirsFile.exists()) {
            dirsFile.mkdirs();
        }
        //Log.i("创建文件","创建文件");
        File file = new File(dirsFile.toString(), needWriteFiel + LOG_FILE_NAME);// LOG_SAVE_PATH
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
            e.printStackTrace();
        }
    }

    /**
     * 删除制定的日志文件
     */
    public static void delFile() {// 删除日志文件
        String needDelFiel = logfile.format(getDateBefore());
        File dirPath = Environment.getExternalStorageDirectory();
        File file = new File(dirPath, needDelFiel + LOG_FILE_NAME);// LOG_SAVE_PATH
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
     */
    private static Date getDateBefore() {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - LOG_FILE_MAX_SAVE_DAYS);
        return now.getTime();
    }

}
