package cjx.baselib.net.mInterface;

public interface IRequest {
     <T> void enqueue(MyCallback<T> callback);
     IRequest doOnMain();
}
