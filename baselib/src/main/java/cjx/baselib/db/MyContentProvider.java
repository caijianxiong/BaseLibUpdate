package cjx.baselib.db;

import cjx.baselib.bean.Book;
import cjx.baselib.bean.Phone;

public class MyContentProvider extends AbsContentProvider {
    @Override
    public void init() {

        // 数据库相关参数设置
        DBConfig config = new DBConfig.Builder()
                .addTatble(Book.class)//添加一个表，多个表重复调用
                .addTatble(Phone.class)
                .setName("sss.db")//数据库名称
                .setVersion(5)//版本号，新增，修改表version要升级+1
                .setAuthority("com.sss.contentProvider")//设置外部访问的唯一表示，maniFest申明contentProvider要填写一致
                .build();

        DBFactory.init(getContext(), config);
    }
}
