package cjx.baselib.thread;


import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import cjx.baselib.log.logUtil;

/**
 * 统一线程池
 */
public class WeExecutor {

    private static String TAG = "WeExecutor";
    private static WeExecutor instance;
    private static final Object OBJECT = new Object();
    private static Handler mainHandler;


    private WeExecutor() {
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static WeExecutor getInstance() {
        if (instance == null) {
            synchronized (OBJECT) {
                if (instance == null) {
                    instance = new WeExecutor();
                }
            }
        }
        return instance;
    }


    public boolean execute(Runnable runnable) {
        return ThreadPool.executeTask(new WorkRunnable(runnable));
    }

    public <T> Future<T> submitTask(Callable<T> task) {
        return ThreadPool.submitTask(task);
    }


    public void executeMain(Runnable runnable) {
        mainHandler.post(runnable);
    }


    /**
     * 此类记录可能会被 Executor 吃掉的异常<br>
     */
    private class WorkRunnable implements Runnable {
        private final Runnable runnable;

        public WorkRunnable(final Runnable target) {
            this.runnable = target;
        }

        @Override
        public void run() {
            // 捕获异常，避免在 Executor 里面被吞掉了
            if (runnable != null) {
                try {
                    runnable.run();
                } catch (Exception e) {
                   logUtil.d("WeExecutor run error:", e.getMessage());
                }
            }
        }
    }


}
