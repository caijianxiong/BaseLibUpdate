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
    public Uri getTableUri() {
        return null;
    }

    @Override
    public void saveData(Book data) {

    }

    @Override
    public void removeData(Book data) {

    }

    @Override
    public void cleanTable() {

    }

    @Override
    public void deleteTable() {

    }
}
