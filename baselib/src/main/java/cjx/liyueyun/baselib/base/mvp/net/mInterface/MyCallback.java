package cjx.liyueyun.baselib.base.mvp.net.mInterface;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public interface MyCallback<T> {

    void onFailure(Exception e);
    void onSuccess(T t);

}
