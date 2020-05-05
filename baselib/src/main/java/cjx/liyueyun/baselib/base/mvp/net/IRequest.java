package cjx.liyueyun.baselib.base.mvp.net;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public interface IRequest {
     <T> void enqueue(MyCallback<T> callback);
     void doOnMain();
}
