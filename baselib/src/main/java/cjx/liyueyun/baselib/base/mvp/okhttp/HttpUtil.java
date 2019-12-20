package cjx.liyueyun.baselib.base.mvp.okhttp;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cjx.liyueyun.baselib.base.mvp.utils.LoggerInterceptor;
import okhttp3.OkHttpClient;

/**
 * @author caicai
 * @create 2019/11/26
 * @Describe
 */
public class HttpUtil {
    private static OkHttpClient mOkHttpClient;
    private static HttpUtil mInstance;

    private HttpUtil() {
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

    public static HttpUtil getInstance() {
        if (mInstance == null) {
            synchronized (HttpUtil.class) {
                if (mInstance == null)
                    mInstance = new HttpUtil();
            }
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

}
