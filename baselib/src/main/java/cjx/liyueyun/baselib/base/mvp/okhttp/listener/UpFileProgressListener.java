package cjx.liyueyun.baselib.base.mvp.okhttp.listener;

/**
 * @author caicai
 * @create 2019/11/28
 * @Describe
 */
public interface UpFileProgressListener {
    void onProgress(String fileName,long totalBytes, long remainingBytes, boolean done);
}
