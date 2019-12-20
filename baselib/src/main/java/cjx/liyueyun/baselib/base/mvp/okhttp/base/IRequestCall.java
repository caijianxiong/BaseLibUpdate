package cjx.liyueyun.baselib.base.mvp.okhttp.base;

import cjx.liyueyun.baselib.base.mvp.okhttp.callback.ICallback;
import cjx.liyueyun.baselib.base.mvp.okhttp.callback.MyCallBack;

/**
 * @author caicai
 * @create 2019/11/26
 * @Describe
 */
public interface IRequestCall<T> {
    Object enqueue(MyCallBack<T> callback);
}
