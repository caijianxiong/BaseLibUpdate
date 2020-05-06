package cjx.liyueyun.baselib.base.mvp.net.mInterface;

import cjx.liyueyun.baselib.base.mvp.net.mInterface.MyCallback;

public interface IRequest {
     <T> void enqueue(MyCallback<T> callback);
     void doOnMain();
}
