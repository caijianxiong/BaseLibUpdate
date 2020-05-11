package cjx.baselib.db.manager;

import cjx.baselib.bean.Book;
import cjx.baselib.db.BaseBean;
import cjx.baselib.log.logUtil;

public class DBManagerFactory {
    private static final String TAG="DBTanagerFactory";

    public static <M extends AbsDBManager> M createDBManager(Class<M> mClass) {

        M dbManager=null;
        try {
            dbManager= (M) Class.forName(mClass.getName()).newInstance();
        }catch (Exception e){
            logUtil.e(TAG,e.getMessage());
        }
        return dbManager;
    }
}
