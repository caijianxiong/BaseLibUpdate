package cjx.baselib.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cjx.baselib.log.logUtil;

public class ThreadPool {

    private static ExecutorService executor;
    private static final String TAG = "ThreadPool";

    /**
     * 此类型无法实例化
     */
    private ThreadPool() {
    }


    /**
     * 默认最大并发数<br>
     */
    private static final int DEFAULT_MAX_CONCURRENT = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 线程池名称格式
     */
    private static final String THREAD_POOL_NAME = "WeExecutor-Thread:";

    /**
     * 线程工厂名称
     */
    private static final ThreadFactory FACTORY = new BaseThreadFactory(THREAD_POOL_NAME);

    /**
     * 线程池拒绝策略
     */
    private static final MeRejectExecutionHandler REJECT_EXECUTION_HANDLER = new MeRejectExecutionHandler();

    /**
     * 默认队列大小
     */
    private static final int DEFAULT_SIZE = 500;

    /**
     * 执行队列
     */
    private static BlockingQueue<Runnable> executeQueue = new ArrayBlockingQueue<>(DEFAULT_SIZE);

    /**
     * 默认线程存活时间
     */
    private static final long DEFAULT_KEEP_ALIVE = 60L;


    static {
        // 创建 Executor
        // 此处默认最大值改为处理器数量的 4 倍
        try {
            executor = new ThreadPoolExecutor(DEFAULT_MAX_CONCURRENT, DEFAULT_MAX_CONCURRENT * 4, DEFAULT_KEEP_ALIVE,
                    TimeUnit.SECONDS, executeQueue, FACTORY,REJECT_EXECUTION_HANDLER);

            // 关闭事件的挂钩
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                   logUtil.d(TAG, "WeExecutor shutting down.");
                    executor.shutdown();
                    try {
                        // 等待1秒执行关闭
                        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                           logUtil.d(TAG, "WeExecutor shutdown immediately due to wait timeout.");
                            executor.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        logUtil.e(TAG, e);
                        executor.shutdownNow();
                    }
                   logUtil.d(TAG, "WeExecutor shutdown complete.");
                }
            }));
        } catch (Exception e) {
            logUtil.d(TAG, e);
            throw new ExceptionInInitializerError(e);
        }
    }


    /**
     * 执行任务，不管是否成功<br>
     *
     * @param task
     * @return
     */
    public static boolean executeTask(Runnable task) {

        try {
            executor.execute(task);
        } catch (Exception e) {
            logUtil.d("Task executing error:", e);
            return false;
        }

        return true;
    }

    /**
     * 提交任务，并可以在稍后获取其执行情况<br>
     * 当提交失败时，会抛出 {@link }
     *
     * @param task
     * @return
     */
    public static <T> Future<T> submitTask(Callable<T> task) {

        try {
            return executor.submit(task);
        } catch (RejectedExecutionException e) {
            logUtil.d("Task executing error:", e);
            throw new UnsupportedOperationException("Unable to submit the task, rejected.", e);
        }
    }

}
