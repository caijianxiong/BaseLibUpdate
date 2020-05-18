package cjx.baselib.net.mInterface;

import java.util.HashMap;
import java.util.Map;

import cjx.baselib.net.GetRequest;

public abstract class AbsCommon {
    protected HashMap<String, String> headers;
    private boolean isDoOnMain=false;

    protected void setHeader(String key, String value) {
        if (headers == null)
            headers = new HashMap<>();
        headers.put(key, value);
    }

    protected void setHeaders(Map<String, String> map) {
        if (headers == null)
            headers = new HashMap<>();
        headers.putAll(map);
    }

    protected HashMap<String, String> getHeaders() {
        return headers;
    }


    protected void setDoOnMain() {
        isDoOnMain=true;
    }
}
