package cjx.liyueyun.baselib.base.mvp.net;

import java.io.File;
import java.util.Map;


public interface MPost {
    PostRequest url(String url);

    PostRequest param(String key, String value);

    PostRequest params(Map<String, String> map);

    PostRequest header(String key, String value);

    PostRequest headers(Map<String, String> map);

    PostRequest postFile(File file);

    PostRequest postMultipartFile(String name, String fileMediaType, File file);

    PostRequest postJsonStr(String json);
}
