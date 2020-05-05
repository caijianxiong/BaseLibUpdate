package cjx.liyueyun.baselib.base.mvp.net;

import java.util.Map;

public interface MGet {
    GetRequest url(String url);
    GetRequest header(String key,String value);
    GetRequest headers(Map<String,String> map);
}
