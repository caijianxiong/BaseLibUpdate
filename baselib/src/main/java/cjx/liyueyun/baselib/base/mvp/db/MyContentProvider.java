package cjx.liyueyun.baselib.base.mvp.db;

import cjx.liyueyun.baselib.base.mvp.bean.Book;

public class MyContentProvider extends AbsContentProvider {
    @Override
    public void init() {

        // 数据库相关参数设置
        DBConfig config = new DBConfig.Builder()
                .addTatble(Book.class)
                .setName("sss.db").setVersion(2)
                .setAuthority("com.sss.contentProvider").build();

        DBFactory.init(getContext(), config);
    }
}
