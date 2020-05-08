package cjx.baselib.bean;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONObject;

import cjx.baselib.db.BaseBean;
import cjx.baselib.db.TableColumn;
import cjx.baselib.db.TableName;

@TableName(name = "phoneTable")
public class Phone extends BaseBean<Phone> {

    @TableColumn(type = TableColumn.Types.TEXT,isNotNull = true)
    public String name;
    @TableColumn(type = TableColumn.Types.INTEGER)
    public int price;

    @Override
    public Phone parseJSON(JSONObject jsonObj) {
        return null;
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public Phone cursorToBean(Cursor cursor) {
        this.name=cursor.getString(cursor.getColumnIndex("name"));
        this.price=cursor.getInt(cursor.getColumnIndex("price"));
        return null;
    }

    @Override
    public ContentValues beanToValues() {
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("price",price);
        return values;
    }
}
