package cjx.baselib.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据实体类，获取表名，，创建SQ语句
 */
public class TableUtil {

    private static final String TAG="TableUtil";

    /**
     * 获取表名，默认取得是实体类名,也可以给实体类添加注解 @Table(name = "bookTableName")指定表名
     *
     * @param c 实体类
     * @return
     */
    public static String getTableName(Class<? extends BaseBean<?>> c) {
        String name = null;
        TableName tableNameAnnotation = c.getAnnotation(TableName.class);
        if (tableNameAnnotation != null) {
            name = tableNameAnnotation.name();
        }
        if (TextUtils.isEmpty(name)) {
            name = c.getSimpleName();
        }
        return name;
    }

    /**
     * 拼装sql用的建表语句以及索引语句
     *CREATE TABLE bookTableName (isJava  BLOB NOT NULL, name  TEXT NOT NULL UNIQUE, price  INTEGER NOT NULL, _ID  INTEGER PRIMARY KEY);
     * @param c
     * @return
     */
    public static List<String> getCreateStatements(Class<? extends BaseBean<?>> c) {
        List<String> createStatments = new ArrayList<String>();
        List<String> indexStatments = new ArrayList<String>();

        StringBuilder builder = new StringBuilder();
        String tableName = getTableName(c);
        builder.append("CREATE TABLE ");
//        builder.append("CREATE TABLE IF NOT EXISTS ");//数据库表不存在才创建
        builder.append(tableName);
        builder.append(" (");
        int columnNum = 0;
        Field[] fields = c.getFields();
        for (Field f : fields) {
            f.setAccessible(true);
            TableColumn tableColumnAnnotation = f.getAnnotation(TableColumn.class);
            if (tableColumnAnnotation != null) {
                columnNum++;
                String columnName = f.getName();
                builder.append(columnName);
                builder.append(" ");
                if (tableColumnAnnotation.type() == TableColumn.Types.INTEGER) {
                    builder.append(" INTEGER");
                } else if (tableColumnAnnotation.type() == TableColumn.Types.BLOB) {
                    builder.append(" BLOB");
                } else if (tableColumnAnnotation.type() == TableColumn.Types.TEXT) {
                    builder.append(" TEXT");
                } else {
                    builder.append(" DATETIME");
                }
                if (tableColumnAnnotation.isPrimary()) {
                    builder.append(" PRIMARY KEY");
                } else {
                    if (tableColumnAnnotation.isNotNull()) {
                        builder.append(" NOT NULL");
                    }
                    if (tableColumnAnnotation.isUnique()) {
                        builder.append(" UNIQUE");
                    }
                }
                if (tableColumnAnnotation.isIndex()) {
                    indexStatments.add("CREATE INDEX idx_" + columnName + "_" + tableName + " ON "
                            + tableName + "(" + columnName + ");");
                }
                builder.append(", ");
            }
        }
        builder.setLength(builder.length() - 2); // remove last ','
        builder.append(");");
        if (columnNum > 0) {
            createStatments.add(builder.toString());
            createStatments.addAll(indexStatments);
        }
        return createStatments;
    }


    /**
     * 拼接新增表格字段 sql语句
     * @param c
     * @return
     */
    public static List<String> getAlertStatements(Class<? extends BaseBean<?>> c,int oldVer,int newVer){//一次只能新增一列
        List<String> statementsList=new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        String tableName = getTableName(c);
        builder.append("ALTER TABLE ");
        builder.append(tableName);
        builder.append(" ADD ");
        Field[] fields = c.getFields();
        for (Field f : fields) {
            f.setAccessible(true);
            TableColumn tableColumnAnnotation = f.getAnnotation(TableColumn.class);
            if (tableColumnAnnotation != null) {
                int clumnVer=tableColumnAnnotation.addVersion();
                if (clumnVer>oldVer&&clumnVer<=newVer){
                    //新增列
                    String columnName = f.getName();
                    builder.append(columnName);
                    builder.append(" TEXT;");
                    statementsList.add(builder.toString());
                }
            }
        }
        return statementsList;
    }


    public static boolean isTableExist(SQLiteDatabase db ,Class<? extends BaseBean<?>> c){
        boolean result = false;
        String tabName=getTableName(c);
        if(tabName == null){
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+tabName.trim()+"' ";
            cursor = db.rawQuery(sql, null);
            if (cursor==null)
                return false;
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "isTableExist: ",e );
        }
        return result;
    }

}
