package cjx.baselib.db.manager;

import android.content.ContentResolver;
import android.net.Uri;

import cjx.baselib.db.BaseBean;

public interface IDBManager<T extends BaseBean<?>> {
    Uri buildUri(Class<T> tClass);
    ContentResolver createResolver();
}
