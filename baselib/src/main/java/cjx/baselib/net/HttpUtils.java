package cjx.baselib.net;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cjx.baselib.utils.LoggerInterceptor;
import okhttp3.OkHttpClient;

public class HttpUtils {

    private static HttpUtils instance = null;
    public static OkHttpClient mOkHttpClient;

    private HttpUtils() {
        //添加cookie
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG", true))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
//                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
    }

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null)
                    instance = new HttpUtils();
            }
        }
        return instance;
    }


    public GetRequest get() {
        if (mOkHttpClient==null)
            throw new RuntimeException("HttpUtils not init");
        return new GetRequest(mOkHttpClient);
    }


    public PostRequest post() {
        if (mOkHttpClient==null)
            throw new RuntimeException("HttpUtils not init");
        return new PostRequest(mOkHttpClient);
    }

}
