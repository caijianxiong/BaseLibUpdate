package cjx.liyueyun.baselib.base.mvp.thread;

import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 给线程池创建的线程重命名
 */
public class BaseThreadFactory implements ThreadFactory {

    //原子性自增1
    final AtomicInteger threadNumber = new AtomicInteger(1);
    final String namePrefix;

    public BaseThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(@NonNull Runnable r) {
        Thread t = new Thread( r,namePrefix + threadNumber.getAndIncrement());
        if (t.isDaemon())
            t.setDaemon(true);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
