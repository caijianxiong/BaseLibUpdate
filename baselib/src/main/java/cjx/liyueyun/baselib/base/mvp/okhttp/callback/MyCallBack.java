package cjx.liyueyun.baselib.base.mvp.okhttp.callback;


/**
 * @author caicai
 * @create 2019/11/26
 * @Describe
 */
public abstract class MyCallBack<T> implements ICallback<T> {

    @Override
    public void onBefore() {

    }

    @Override
    public void onAfter(T response) {

    }

    @Override
    public void onProgress(float progress,float total) {

    }
}
