package cjx.liyueyun.baselib.base.mvp.okhttp.callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cjx.liyueyun.baselib.base.mvp.okhttp.MyErrorException;
import okhttp3.Response;

/**
 * @author caicai
 * @create 2019/11/27
 * @Describe
 */
public abstract class FileCallBack extends MyCallBack<File> {

    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public FileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }


    public void saveFile(Response response) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            if (response.body() != null) {
                is = response.body().byteStream();
                final long total;
                total = response.body().contentLength();
                long sum = 0;
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, destFileName);
//            if (!file.exists()) {
//                dir.mkdirs();
//            }
                fos = new FileOutputStream(file);
                while ((len = is.read(buf)) != -1) {
                    sum += len;
                    fos.write(buf, 0, len);
                    final long finalSum = sum;
                    onProgress(finalSum * 1.0f / total, total);
                }
                fos.flush();
                onSuccess(file);
                onAfter(file);
            } else {
                onError(new MyErrorException(new RuntimeException("response.body==null")));
            }
        } catch (Exception e) {
            onError(new MyErrorException(e));
        } finally {
            try {
                response.body().close();
                if (is != null) is.close();
            } catch (IOException e) {
                onError(new MyErrorException(e));
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                onError(new MyErrorException(e));
            }

        }
    }
}
