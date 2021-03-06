package cjx.baselib;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author caicai
 * @create 2019/9/26
 * @Describe 
 */
public abstract class BaseEasyMvpActivity<T extends BaseEasyPresenter> extends BaseActivity implements BaseView {

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        super.onCreate(savedInstanceState);
    }

    public abstract T initPresenter();


    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }
}
