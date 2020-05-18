package cjx.baselib.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.util.Map;

import cjx.baselib.net.mInterface.AbsCommon;
import cjx.baselib.net.mInterface.IRequest;
import cjx.baselib.net.mInterface.MGet;
import cjx.baselib.net.mInterface.MyCallback;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRequest extends AbsCommon implements IRequest, MGet {

    private OkHttpClient httpClient;
    private Request.Builder builder;
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
    public GetRequest doOnMain() {
       setDoOnMain();
        return this;
    }

    @Override
    public GetRequest url(String url) {
        builder.url(url);
        return this;
    }

    @Override
    public GetRequest header(String key, String value) {
        setHeader(key, value);
        return this;
    }

    @Override
    public GetRequest headers(Map<String, String> map) {
        setHeaders(map);
        return this;
    }


    private void addHeaders() {
        if (headers == null) return;
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
    }
}
