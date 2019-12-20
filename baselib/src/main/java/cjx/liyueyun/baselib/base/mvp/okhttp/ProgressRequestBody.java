package cjx.liyueyun.baselib.base.mvp.okhttp;

import java.io.File;
import java.io.IOException;

import cjx.liyueyun.baselib.base.mvp.okhttp.listener.UpFileProgressListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * @author caicai
 * @create 2019/11/28
 * @Describe
 */
public class ProgressRequestBody extends RequestBody {
    private MediaType mediaType;
    private File file;
    private UpFileProgressListener upFileProgressListener;

    public ProgressRequestBody(MediaType mediaType, File file, UpFileProgressListener progressListener) {
        this.mediaType = mediaType;
        this.file = file;
        this.upFileProgressListener = progressListener;
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public MediaType contentType() {
        return mediaType;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source;
        try {
            String fileName=file.getName();
            source = Okio.source(file);
            //sink.writeAll(source);
            Buffer buf = new Buffer();
            Long remaining = contentLength();
            for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                sink.write(buf, readCount);
                if (upFileProgressListener != null)
                    upFileProgressListener.onProgress(fileName,contentLength(), remaining -= readCount, remaining == 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
