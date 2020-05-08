package cjx.baselib.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;

public abstract class BaseBean<T> implements Serializable {

    //isPrimary一张表只能有一个参数为true，唯一标示，自增主键，不可多个参数设置
    @TableColumn(type = TableColumn.Types.INTEGER, isPrimary = true)
    public static final String _ID = "_id";

    /**
     * 将json对象转化为Bean实例
     *
     * @param jsonObj
     * @return
     */
    public abstract T parseJSON(JSONObject jsonObj);

    /**
     * 将Bean实例转化为json对象
     *
     * @return
     */
    public abstract JSONObject toJSON();

    /**
     * 将数据库的cursor转化为Bean实例（如果对象涉及在数据库存取，需实现此方法）
     *
     * @param cursor
     * @return
     */
    public abstract T cursorToBean(Cursor cursor);

    /**
     * 将Bean实例转化为一个ContentValues实例，供存入数据库使用（如果对象涉及在数据库存取，需实现此方法）
     *
     * @return
     */
    public abstract ContentValues beanToValues();

    @SuppressWarnings("unchecked")
    public T parseJSON(Gson gson, String json) {
        return (T) gson.fromJson(json, this.getClass());
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        try {
            Class<?> c = getClass();
            Field[] fields = c.getFields();
            for (Field f : fields) {
                f.setAccessible(true);
                final TableColumn tableColumnAnnotation = f.getAnnotation(TableColumn.class);
                if (tableColumnAnnotation != null) {
                    if (tableColumnAnnotation.type() == TableColumn.Types.INTEGER) {
                        values.put(f.getName(), f.getInt(this));
                    } else if (tableColumnAnnotation.type() == TableColumn.Types.BLOB) {
                        values.put(f.getName(), (byte[]) f.get(this));
                    } else if (tableColumnAnnotation.type() == TableColumn.Types.TEXT) {
                        values.put(f.getName(), f.get(this).toString());
                    } else {//其他类型转换成string
                        values.put(f.getName(), f.get(this).toString());
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return values;
    }


}
