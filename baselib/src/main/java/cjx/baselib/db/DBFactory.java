package cjx.baselib.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBFactory {
    private static final String TAG = DBFactory.class.getSimpleName();

    private DBConfig mConfig;
    private SQLiteDatabase mSQLiteDB;

    private MyDBOpenHelper mDBOpenHelper;

    private final Context mContext;

    private static DBFactory instance ;

    private DBFactory(Context context) {
        mContext = context;
    }

    public static void init(Context context,DBConfig dbConfig){
        if(instance==null){
            instance = new DBFactory(context.getApplicationContext());
            instance.setDBConfig(dbConfig);
        }
    }

    public static DBFactory getInstance(){
        return instance;
    }


    public void setDBConfig(DBConfig dbConfig){
        mConfig = dbConfig;
    }

    public DBConfig getDBConfig(){
        return mConfig;
    }

    /**
     * 获取数据库对象
     * @return
     */
    public SQLiteDatabase open() {
        if(mSQLiteDB==null){
            mDBOpenHelper = new MyDBOpenHelper(mContext, mConfig.dbName, null, mConfig.dbVersion);
            mSQLiteDB = mDBOpenHelper.getWritableDatabase();
        }
        return mSQLiteDB;
    }

    public void close() {
        if(mDBOpenHelper!=null){
            mDBOpenHelper.close();
        }
    }

    public void beginTransaction() {
        if(mSQLiteDB!=null){
            mSQLiteDB.beginTransaction();
        }
    }

    public void endTransaction() {
        if (mSQLiteDB!=null&&mSQLiteDB.inTransaction()) {
            mSQLiteDB.endTransaction();
        }
    }

    public void setTransactionSuccessful() {
        if (mSQLiteDB!=null){
            mSQLiteDB.setTransactionSuccessful();
        }
    }

    private final class MyDBOpenHelper extends SQLiteOpenHelper {

        public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //创建表格
        @Override
        public void onCreate(SQLiteDatabase db) {
            //全部表，建一边
            for (Class<? extends BaseBean<?>> table : mConfig.tableList) {
                try {
                    for (String statement : TableUtil.getCreateStatements(table)) {
                        Log.d(TAG, "创建表格："+statement);
                        db.execSQL(statement);
                    }
                } catch (Throwable e) {
                    Log.e(TAG, "onCreate  Can't create table " + table.getSimpleName()+"  error:"+e.getMessage());
                }
            }
        }

        //更新表格
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "onUpgrade: " + oldVersion + " >> " + newVersion);
            //SQ表格不存在才去创建，
//            for (Class<? extends BaseBean<?>> table : mConfig.tableList) {
//                try {
//                    db.execSQL("DROP TABLE IF EXISTS " + TableUtil.getTableName(table));
//                } catch (Throwable e) {
//                    Log.e(TAG, "onUpgrade Can't create table " + table.getSimpleName());
//                }
//            }
            onCreate(db);
        }

    }

    public void cleanTable(String tableName, int maxSize, int batchSize) {
        Cursor cursor = mSQLiteDB.rawQuery("select count(_id) from " + tableName, null);
        if (cursor.getCount() != 0 && cursor.moveToFirst() && !cursor.isAfterLast()) {
            if (cursor.getInt(0) >= maxSize) {
                int deleteSize = maxSize - batchSize;
                mSQLiteDB.execSQL("delete from " + tableName + " where _id in (" + "select _id from " + tableName
                        + " order by _id " + "  limit " + deleteSize + " )");
            }
        }
        cursor.close();
    }

}
