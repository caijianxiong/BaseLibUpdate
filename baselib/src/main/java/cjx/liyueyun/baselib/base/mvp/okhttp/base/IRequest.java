package cjx.liyueyun.baselib.base.mvp.okhttp.base;

import java.util.Map;

import okhttp3.Request;

/**
 * @author caicai
 * @create 2019/11/26
 * @Describe
 */
public interface IRequest<T> {

    abstract IRequest<T> header(String key, String value);

    abstract IRequest<T> header(Map<String, String> map);

    abstract IRequest<T> addParam(String key, String value);

    abstract IRequest<T> addParams(Map<String, String> map);

    abstract IRequest<T> url(String url);

    abstract IRequestCall<T> build();

    Request buildRequest();

    abstract IRequest<T> tag(Object tag);

}
