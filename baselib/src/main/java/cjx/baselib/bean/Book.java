package cjx.baselib.bean;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import org.json.JSONObject;

import cjx.baselib.db.BaseBean;
import cjx.baselib.db.TableName;
import cjx.baselib.db.TableColumn;

@TableName(name = "bookTableName")
public class Book extends BaseBean<Book> {

    @TableColumn(type = TableColumn.Types.TEXT,addVersion = 1,isUnique = true,isNotNull = true)
    public String name;

    @TableColumn(type = TableColumn.Types.INTEGER,addVersion =1,isNotNull = true)
    public int price;

    @TableColumn(type = TableColumn.Types.BLOB, addVersion = 1,isNotNull = true)
    public boolean isJava;

    @TableColumn(type = TableColumn.Types.TEXT,addVersion = 3,isNotNull = true)
    public String other;


    @Override
    public Book parseJSON(JSONObject jsonObj) {

        return this;
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public Book cursorToBean(Cursor cursor) {
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.other = cursor.getString(cursor.getColumnIndex("other"));
        this.price = cursor.getInt(cursor.getColumnIndex("price"));
        this.isJava = cursor.getInt(cursor.getColumnIndex("isJava")) > 0;
        return this;
    }

    @Override
    public ContentValues beanToValues() {
        ContentValues values = new ContentValues();
        if (!TextUtils.isEmpty(name)) {
            values.put("name", name);
        }
        values.put("price", price);
        values.put("isJava", isJava);
        values.put("other", other);
        return values;
    }
}
