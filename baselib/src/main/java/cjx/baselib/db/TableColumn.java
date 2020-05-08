package cjx.baselib.db;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 数据库表元素注解，根据注解可以获取实体类的 属性名，类型，以及定义属性名在sql语句中的特性
 */
//注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
@Retention(RetentionPolicy.RUNTIME)
public  @interface TableColumn {

    public enum Types {
        INTEGER, TEXT, BLOB, DATETIME
    }

    Types type() default Types.TEXT;

    boolean isPrimary() default false;//表中只能有一个元素添加自动定义约束，自增主键

    boolean isIndex() default false;//是否创建索引，创建索引搜索更快，但是更新更慢

    boolean isNotNull() default false;//是否不能为空

    boolean isUnique() default false; //表中元素唯一性约束(表中元素值唯一，，插入相同的值会插入失败)，表中可以多个元素添加唯一性约束
}
