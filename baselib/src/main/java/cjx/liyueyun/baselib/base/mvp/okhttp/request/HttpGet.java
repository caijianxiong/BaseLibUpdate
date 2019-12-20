package cjx.liyueyun.baselib.base.mvp.okhttp.request;

import android.net.Uri;

import java.util.Set;

import cjx.liyueyun.baselib.base.mvp.okhttp.base.BaseHttpRequest;
import cjx.liyueyun.baselib.base.mvp.okhttp.base.IRequest;
import cjx.liyueyun.baselib.base.mvp.okhttp.base.IRequestCall;
import cjx.liyueyun.baselib.base.mvp.okhttp.request_call.MyRequestCall;
import okhttp3.Request;

/**
 * @author caicai
 * @create 2019/11/26
 * @Describe
 */
public class HttpGet<T> extends BaseHttpRequest<T> {

    @Override
    public IRequestCall<T> build() {
        appendHeaders();
        appendParams();
        return new MyRequestCall<>(this);
    }

    private String appendParams() {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }

    /**
     * get方法要在build之前调用
     * @return
     */
    public IRequest<T> get() {
        getBuilder().get();
        return this;
    }
}
