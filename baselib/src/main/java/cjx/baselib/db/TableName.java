package cjx.baselib.db;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 注解数据库表名，运行时，根据实体类获取此类对应的数据库表名
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TableName {
    String name();
}
