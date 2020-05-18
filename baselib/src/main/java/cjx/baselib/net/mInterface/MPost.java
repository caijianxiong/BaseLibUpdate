package cjx.baselib.net.mInterface;

import java.io.File;
import java.util.Map;

import cjx.baselib.net.GetRequest;
import cjx.baselib.net.PostRequest;


public interface MPost {

    PostRequest url(String url);

    PostRequest header(String key, String value);
    PostRequest headers(Map<String,String> map);

    PostRequest param(String key, String value);

    PostRequest params(Map<String, String> map);

    PostRequest postFile(File file);

    PostRequest postMultipartFile(String name, String fileMediaType, File file);

    PostRequest postJsonStr(String json);
}
