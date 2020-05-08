package cjx.baselib.bean;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import org.json.JSONObject;

import cjx.baselib.db.BaseBean;
import cjx.baselib.db.Table;
import cjx.baselib.db.TableColumn;
@Table(name = "bookTableName")
public class Book extends BaseBean<Book> {

    @TableColumn(type = TableColumn.Types.TEXT, isNotNull = true)
    public String name;

    @TableColumn(type = TableColumn.Types.INTEGER, isNotNull = true)
    public int price;

    @TableColumn(type = TableColumn.Types.BLOB, isNotNull = true)
    public boolean isJava;


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

        values.put("price",price);
        values.put("isJava",isJava);
        return values;
    }
}
