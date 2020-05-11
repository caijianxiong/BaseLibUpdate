package com.example.testdemo.dbmanager;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.List;

import cjx.baselib.bean.Book;
import cjx.baselib.db.BaseBean;
import cjx.baselib.db.manager.AbsDBManager;

public class BookDbManager extends AbsDBManager<Book> {



    @NonNull
    @Override
    protected Uri getTableUri() {
        return null;
    }

    @Override
    protected void saveData(Book data) {

    }

    @Override
    protected void removeData(Book data) {

    }

    @Override
    protected void cleanTable() {

    }

    @Override
    protected void deleteTable() {

    }
}
