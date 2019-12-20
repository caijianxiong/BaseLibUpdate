package cjx.liyueyun.baselib.base.mvp.okhttp.base;

import android.net.Uri;

import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import cjx.liyueyun.baselib.base.mvp.okhttp.HttpUtil;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author caicai
 * @create 2019/11/26
 * @Describe
 */
public abstract class BaseHttpRequest<T> implements IRequest<T> {

    protected OkHttpClient httpClient;
    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected Request.Builder builder;

    public Request.Builder getBuilder() {
        if (builder == null)
            builder = new Request.Builder();
        return builder;
    }

    public BaseHttpRequest() {
        this.builder = new Request.Builder();
    }

    @Override
    public IRequest<T> header(String key, String value) {
        if (headers == null)
            headers = new LinkedHashMap<>();
        headers.put(key, value);
        return this;
    }


    @Override
    public IRequest<T> header(Map<String, String> map) {
        if (headers == null)
            headers = new LinkedHashMap<>();
        headers.putAll(map);
        return this;
    }

    @Override
    public IRequest<T> addParams(Map<String, String> map) {
        if (params == null)
            params = new LinkedHashMap<>();
        params.putAll(map);
        return this;
    }

    @Override
    public IRequest<T> addParam(String key, String value) {
        if (params == null)
            params = new LinkedHashMap<>();
        params.put(key, value);
        return this;
    }

    @Override
    public IRequest<T> tag(Object tag) {
        this.tag = tag;
        builder.tag(tag);
        return this;
    }

    @Override
    public IRequest<T> url(String url) {
        this.url = url;
        builder.url(url);
        return this;
    }

    @Override
    public Request buildRequest() {
        return builder.build();
    }

    protected void appendHeaders() {
        if (headers == null || headers.isEmpty()) return;
        Headers.Builder headerBuilder = new Headers.Builder();
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

}
