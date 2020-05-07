package cjx.liyueyun.baselib.base.mvp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caicai
 * @create 2019/9/26
 * @Describe
 */
public abstract class BaseActivity extends AppCompatActivity {


    public String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(this.getLayoutId());
        LibApplication.addActivity(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LibApplication.removeActivity(this);
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 普通activity加载数据
     */
    public abstract void initData();


    public void startActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }




}
