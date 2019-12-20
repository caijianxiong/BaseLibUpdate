package cjx.liyueyun.baselib.base.mvp.okhttp.callback;

import cjx.liyueyun.baselib.base.mvp.okhttp.MyErrorException;
import okhttp3.Callback;

/**
 * @author caicai
 * @create 2019/11/25
 * @Describe
 */
public interface ICallback<T> {

    abstract void onBefore();

    abstract void onSuccess(T response);

    abstract void onError(MyErrorException e);

    abstract void onAfter(T response);

    abstract void onProgress(float progress, float total);

}
