package cjx.liyueyun.baselib.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author caicai
 * @create 2019/11/25
 * @Describe
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        super.onCreate(savedInstanceState);
    }

    public abstract P initPresenter();

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }


}
