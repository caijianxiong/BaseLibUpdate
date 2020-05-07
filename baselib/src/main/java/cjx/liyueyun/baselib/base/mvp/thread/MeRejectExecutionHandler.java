package cjx.liyueyun.baselib.base.mvp.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import cjx.liyueyun.baselib.base.mvp.log.logUtil;

public class MeRejectExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
       logUtil.d("WeExecutor",r.toString()+"任务被线程池拒绝");
    }
}
