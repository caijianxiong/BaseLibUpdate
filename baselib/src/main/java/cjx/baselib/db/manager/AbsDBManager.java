package cjx.baselib.db.manager;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cjx.baselib.LibApplication;
import cjx.baselib.bean.Book;
import cjx.baselib.db.BaseBean;
import cjx.baselib.log.logUtil;


public abstract class AbsDBManager<T extends BaseBean<T>> {
    private static String TAG = "AbsDBManager";
    private Uri uri;
    protected ContentResolver mResolver;


    public AbsDBManager() {
        mResolver = LibApplication.getAppContext().getContentResolver();
        uri = getTableUri();
    }

    @NonNull
    protected abstract Uri getTableUri();

    protected List<T> queryAll(Class<T> tClass) {

        Cursor cursor = mResolver.query(uri, null, null, null, null);

        if (cursor == null)
            return null;

        List<T> books = new ArrayList<>();
        while (cursor.moveToNext()) {
            T bean = null;
            bean = classToT(tClass);
            bean.cursorToBean(cursor);
            books.add(bean);
        }
        cursor.close();
        return books;
    }

    protected abstract void saveData(T data);

    protected abstract void removeData(T data);

    //清除表数据
    protected abstract void cleanTable();

    protected abstract void deleteTable();//删除表格


    private T classToT(T data) {
        T bean = null;
        try {
            bean = (T) data.getClass().newInstance();
        } catch (Exception e) {
            logUtil.e(TAG, e.getMessage());
        }
        return bean;
    }

    private T classToT(Class<T> tClass) {
        T bean = null;
        try {
            bean = tClass.newInstance();
        } catch (Exception e) {
            logUtil.e(TAG, e.getMessage());
        }
        return bean;
    }

}
