package cjx.baselib.db;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cjx.baselib.bean.Book;

public class DBUtils {

    public static Uri URI_SMARTDOWNLOAD = AbsContentProvider.buildUri(Book.class);


    /**
     * 根据书名查询一本书
     *
     * @param context
     * @param bookName
     * @return
     */
    public static Book queryBookByName(Context context, String bookName) {
        ContentResolver mResolver = context.getContentResolver();
        Cursor cursor = mResolver.query(URI_SMARTDOWNLOAD, null, "bookName=?", new String[]{
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
        Cursor cursor = mResolver.query(URI_SMARTDOWNLOAD, null, null, null, null);
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
     * 保存一本书
     *
     * @param context
     */
    public static void insertBook(Context context, Book book) {
        ContentResolver mResolver = context.getContentResolver();
        mResolver.insert(URI_SMARTDOWNLOAD, book.beanToValues());
    }

    /**
     * 实时更新一本书
     *
     * @param context
     */
    public static void updateBook(Context context, Book book) {
        ContentResolver mResolver = context.getContentResolver();

            mResolver.update(URI_SMARTDOWNLOAD, book.beanToValues(), "name=? and price=?",
                    new String[]{
                           book.name, book.price + ""
                    });

    }

    /**
     * 删除一本书
     *
     * @param context
     * @param bookName
     */
    public static void deleteSmartDown(Context context, String bookName) {
        ContentResolver mResolver = context.getContentResolver();
        mResolver.delete(URI_SMARTDOWNLOAD, "name=?", new String[]{
                bookName
        });
    }


}
