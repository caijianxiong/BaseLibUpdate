package cjx.baselib.net.mInterface;

import java.util.Map;

import cjx.baselib.net.GetRequest;

public interface MGet {
    GetRequest url(String url);
    GetRequest header(String key,String value);
    GetRequest headers(Map<String,String> map);
}
