package cjx.baselib.db;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cjx.baselib.bean.Book;

public class DBUtils {

    public static Uri URI_BOOK = AbsContentProvider.buildUri(Book.class);


    /**
     * 根据书名查询一本书
     *
     * @param context
     * @param bookName
     * @return
     */
    public static Book queryBookByName(Context context, String bookName) {
        ContentResolver mResolver = context.getContentResolver();
        Cursor cursor = mResolver.query(URI_BOOK, null, "name=?", new String[]{
                bookName
        }, null);
        Book bean = new Book();
        while (cursor.moveToNext()) {
            bean.cursorToBean(cursor);
        }
        cursor.close();
        return bean;
    }


    public static List<Book> queryAllBook(Context context) {
        ContentResolver mResolver = context.getContentResolver();
        Cursor cursor = mResolver.query(URI_BOOK, null, null, null, null);
        List<Book> books=new ArrayList<>();
        while (cursor.moveToNext()) {
            Book bean = new Book();
            bean.cursorToBean(cursor);
            books.add(bean);
        }
        cursor.close();
        return books;
    }

    /**
     * 保存一本书,数据库不存在insert  ，存在update
     *
     * @param context
     */
    public static void saveBook(Context context, Book book) {
        ContentResolver mResolver = context.getContentResolver();
        Cursor cursor = mResolver.query(URI_BOOK,
                null,
                "name=?",
                new String[]{book.name},
                null,null);
        if (cursor==null)return;
        if (cursor.moveToNext()){
            //数据库存在数据，更新
            mResolver.update(URI_BOOK, book.beanToValues(), "name=?",
                    new String[]{book.name});
        }else {
            //新增
            mResolver.insert(URI_BOOK, book.beanToValues());
        }
        cursor.close();
    }


    /**
     * 删除一本书
     *
     * @param context
     * @param bookName
     */
    public static void deleteBook(Context context, String bookName) {
        ContentResolver mResolver = context.getContentResolver();
        mResolver.delete(URI_BOOK, "name=?", new String[]{
                bookName
        });
    }


}
