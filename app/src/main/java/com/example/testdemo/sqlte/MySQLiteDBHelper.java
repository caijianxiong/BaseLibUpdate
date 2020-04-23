package com.example.testdemo.sqlte;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "test.db";
    public static final String TABLE_NAME = "Orders";
    private final DBCreateAndUpDateTableHelper tableHelper;

    public MySQLiteDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        tableHelper = new DBCreateAndUpDateTableHelper();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        tableHelper.createTable(db);

        String sql = "create table if not exists " + TABLE_NAME + " (Id integer primary key, CustomName text, OrderPrice integer, Country text)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
