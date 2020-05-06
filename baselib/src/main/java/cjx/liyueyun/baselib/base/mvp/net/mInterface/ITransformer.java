package cjx.liyueyun.baselib.base.mvp.net.mInterface;

import okhttp3.Response;

public interface ITransformer<T> {

    T transform(String response);

}
