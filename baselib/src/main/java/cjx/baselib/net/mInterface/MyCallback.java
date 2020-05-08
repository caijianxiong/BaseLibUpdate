package cjx.baselib.net.mInterface;

public interface MyCallback<T> {

    void onFailure(Exception e);
    void onSuccess(T t);

}
