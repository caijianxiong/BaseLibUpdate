package cjx.baselib.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

public class CustomGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
//        builder
//        .setMemoryCache(MemoryCache memoryCache)
//        .setBitmapPool(BitmapPool bitmapPool)
//        .setDiskCache(DiskCache.Factory diskCacheFactory)
//        .setDiskCacheService(ExecutorService service)
//        .setResizeService(ExecutorService service)
//        .setDecodeFormat(DecodeFormat decodeFormat)
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);//设置图片清晰度
        int cacheSize = 10 << 20;
        builder.setDiskCache(
                new InternalCacheDiskCacheFactory(context, cacheSize)//自定义为缓存空间大小，缓存位置在内部存储器
                //new ExternalCacheDiskCacheFactory(context, cacheSize)//自定义为缓存空间大小，缓存位置在外部存储器
        );
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
