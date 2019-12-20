package cjx.liyueyun.baselib.base.mvp.okhttp.request;

import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Set;

import javax.activation.MimetypesFileTypeMap;

import cjx.liyueyun.baselib.base.mvp.LibApplication;
import cjx.liyueyun.baselib.base.mvp.okhttp.ProgressRequestBody;
import cjx.liyueyun.baselib.base.mvp.okhttp.base.BaseHttpRequest;
import cjx.liyueyun.baselib.base.mvp.okhttp.base.IRequest;
import cjx.liyueyun.baselib.base.mvp.okhttp.base.IRequestCall;
import cjx.liyueyun.baselib.base.mvp.okhttp.listener.UpFileProgressListener;
import cjx.liyueyun.baselib.base.mvp.okhttp.request_call.MyRequestCall;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author caicai
 * @create 2019/11/28
 * @Describe
 */
public class HttpPost<T> extends BaseHttpRequest<T> {

    @Override
    public IRequestCall<T> build() {
        return new MyRequestCall<>(this);
    }

    /**
     * 通过param上传表单参数（k-v）
     */
    public IRequest<T> post() {
        if (params != null && !params.isEmpty()) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            Set<String> keys = params.keySet();
            for (String key : keys) {
                bodyBuilder.add(key, params.get(key));
            }
            getBuilder().post(bodyBuilder.build());
        }
        return this;
    }

    /**
     * 上传文件---param有效--可同时上传key-value参数
     *
     * @param keyName   上传的文件，，命名
     * @param valueFile
     * @return
     */
    public IRequest<T> post(String keyName, @NonNull File valueFile, @Nullable UpFileProgressListener progressListener) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Set<String> keys = params.keySet();
        for (String key : keys) {
            builder.addFormDataPart(key, params.get(key));
        }
        //待确认getFileMediaType(valueFile)类型是否正确
//        builder.addFormDataPart(keyName, valueFile.getName(), RequestBody.create(MediaType.parse(getFileMediaType(valueFile)), valueFile));
        builder.addFormDataPart(keyName, valueFile.getName(),
                new ProgressRequestBody(MediaType.parse(getFileMediaType(valueFile)), valueFile, progressListener));
        getBuilder().post(builder.build());
        return this;
    }

    /**
     * 多文件上传--多文件进度根据fileName--分别回调
     */
    public IRequest<T> post(@Nullable UpFileProgressListener progressListener, File... valueFile) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Set<String> keys = params.keySet();
        for (String key : keys) {
            builder.addFormDataPart(key, params.get(key));
        }
        //待确认getFileMediaType(valueFile)类型是否正确
//        builder.addFormDataPart(keyName, valueFile.getName(), RequestBody.create(MediaType.parse(getFileMediaType(valueFile)), valueFile));
        for (int i = 0; i < valueFile.length; i++) {
            File file = valueFile[i];
            builder.addFormDataPart(file.getName(), file.getName(),
                    new ProgressRequestBody(MediaType.parse(getFileMediaType(file)), file, progressListener));
        }
        getBuilder().post(builder.build());
        return this;
    }

    /**
     * 上传json类型文本数据
     *
     * @param beenJson
     * @return
     */
    public IRequest<T> post(@NonNull Object beenJson) {
        String json = LibApplication.getmGson().toJson(beenJson);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        getBuilder().post(body);
        return this;
    }

    /**
     * 获取文件的MIME类型
     *
     * @param file
     * @return
     */
    private String getFileMediaType(File file) {
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        return mimeTypesMap.getContentType(file);
    }

}
