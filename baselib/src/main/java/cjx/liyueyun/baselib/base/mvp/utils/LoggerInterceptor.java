package cjx.liyueyun.baselib.base.mvp.utils;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by zhy on 16/3/1.
 */
public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "OkHttpUtils";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            //===>response log
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            if (!TextUtils.isEmpty(clone.message()))
                logUtil.d_2(tag, "message : " + clone.message());
//

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
//                        logUtil.d_2(tag, "responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            String url = clone.request().url().toString();
                            logUtil.d_2(tag, "请求url：" + url + "\n请求结果 : " + resp);
                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            logUtil.d_2(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }
        } catch (Exception e) {
            logUtil.d_2(tag, "exception---" + e.getMessage());
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            Headers headers = request.headers();
            if (headers.size() > 0) {
            }
            logUtil.d_2(tag, "method : " + request.method()
                    + "***headers : " + (headers.size() > 0 ? headers.toString() : "无header") + "***tag:" + (request.tag() == null ? "无tag" : request.tag())
                    + "\nurl:" + request.url().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
