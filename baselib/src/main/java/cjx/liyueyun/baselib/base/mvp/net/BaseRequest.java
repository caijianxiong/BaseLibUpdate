package cjx.liyueyun.baselib.base.mvp.net;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public abstract class BaseRequest  {

    abstract void url(String url);
    abstract void parma(String key,String value);
    abstract void parmas(Map <String,String> map);
    abstract void header(String key,String value);
    abstract void headers(Map <String,String> map);

}
