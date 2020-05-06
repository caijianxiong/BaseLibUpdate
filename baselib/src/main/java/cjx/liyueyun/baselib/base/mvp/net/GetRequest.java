package cjx.liyueyun.baselib.base.mvp.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cjx.liyueyun.baselib.base.mvp.net.mInterface.IRequest;
import cjx.liyueyun.baselib.base.mvp.net.mInterface.MGet;
import cjx.liyueyun.baselib.base.mvp.net.mInterface.MyCallback;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRequest implements IRequest, MGet {

    private HashMap<String, String> headers;
    private OkHttpClient httpClient;
    private Request.Builder builder;
    private boolean callOnMain=false;
    public GetRequest( OkHttpClient httpClient) {
        this.httpClient=httpClient;
        builder=new Request.Builder();
        builder.method("GET",null);
    }

    @Override
    public <T> void enqueue(final MyCallback<T> callback) {
        addHeaders();
        Call call=httpClient.newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //解析成泛型T
                String responseData;
                if (response.body() == null) {
                    callback.onFailure(new RuntimeException(" response is null"));
                    return;
                }
                responseData = response.body().string();
                if (TextUtils.isEmpty(responseData)) {
                    callback.onFailure(new RuntimeException(" response is null"));
                } else {
                    ResponseTransformer<T> transformer=new ResponseTransformer<T>();
                    callback.onSuccess(transformer.transform(responseData));
                }

            }
        });
    }

    @Override
    public GetRequest url(String url) {
        builder.url(url);
        return this;
    }

    @Override
    public GetRequest header(String key, String value) {
        if (headers == null)
            headers = new HashMap<>();
        headers.put(key, value);
        return this;
    }

    @Override
    public GetRequest headers(Map<String, String> map) {
        if (headers == null)
            headers = new HashMap<>();
        headers.putAll(map);
        return this;
    }

    @Override
    public void doOnMain() {

    }


    private void addHeaders() {
        if (headers == null) return;
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
    }
}
