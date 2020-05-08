package cjx.baselib.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cjx.baselib.net.mInterface.IRequest;
import cjx.baselib.net.mInterface.MPost;
import cjx.baselib.net.mInterface.MyCallback;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * post 提交键值对
 * 提交json字符串
 * 上传文件
 * 上传Multipart文件
 * <p>
 * param添加的参数只在，提交键值对和表单数据才有用
 * header都生效
 */
public class PostRequest implements IRequest, MPost {

    private OkHttpClient httpClient;
    private Request.Builder builder;
    private HashMap<String, String> headers;
    private HashMap<String, String> parmas;
    private RequestBody requestBody;

    public PostRequest(OkHttpClient httpClient) {
        this.httpClient = httpClient;
        builder = new Request.Builder();
        parmas = new HashMap<>();
    }

    @Override
    public <T> void enqueue(final MyCallback<T> callback) {
        addHeaders();
        if (requestBody == null) {
            //默认提交键值对
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder);
            requestBody = builder.build();
        }
        Request request = builder.post(requestBody).build();
        Call call = httpClient.newCall(request);
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
    public void doOnMain() {

    }


    @Override
    public PostRequest postJsonStr(String json) {
        requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return this;
    }

    @Override
    public PostRequest postFile(File file) {
        requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        return this;
    }

    /**
     * 常见fileMediaType
     * json : application/json
     * xml : application/xml
     * png : image/png
     * jpg : image/jpeg
     * gif : imge/gif
     *
     * @param name
     * @param fileMediaType
     */
    @Override
    public PostRequest postMultipartFile(String name, String fileMediaType, File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart(name, file.getName(), RequestBody.create(MediaType.parse(fileMediaType), file));
        addParams(builder);
        requestBody = builder.build();
        return this;
    }


    @Override
    public PostRequest url(String url) {
        builder.url(url);
        return this;
    }

    @Override
    public PostRequest param(String key, String value) {
        if (parmas == null)
            parmas = new HashMap<>();
        parmas.put(key, value);
        return this;
    }

    @Override
    public PostRequest params(Map<String, String> map) {
        if (parmas == null)
            parmas = new HashMap<>();
        parmas.putAll(map);
        return this;
    }

    @Override
    public PostRequest header(String key, String value) {
        if (headers == null)
            headers = new HashMap<>();
        headers.put(key, value);
        return this;
    }

    @Override
    public PostRequest headers(Map<String, String> map) {
        if (headers == null)
            headers = new HashMap<>();
        headers.putAll(map);
        return this;
    }

    private void addHeaders() {
        if (headers == null) return;
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
    }

    private void addParams(FormBody.Builder builder) {
        if (parmas == null) return;
        for (Map.Entry<String, String> entry : parmas.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
    }

    private void addParams(MultipartBody.Builder builder) {
        if (parmas == null) return;
        for (Map.Entry<String, String> entry : parmas.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
    }


}
