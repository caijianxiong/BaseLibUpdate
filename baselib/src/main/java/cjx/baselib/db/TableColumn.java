package cjx.baselib.db;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
//注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
@Retention(RetentionPolicy.RUNTIME)
public  @interface TableColumn {

    public enum Types {
        INTEGER, TEXT, BLOB, DATETIME
    }

    Types type() default Types.TEXT;

    boolean isPrimary() default false;

    boolean isIndex() default false;

    boolean isNotNull() default false;

    boolean isUnique() default false;
}
