package com.example.testdemo;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.testdemo.activity.CollectionActivity;
import com.example.testdemo.activity.custom_view.CustomViewActivity;
import com.example.testdemo.activity.handler.HandlerActivity;
import com.example.testdemo.utils.SignUtil;
import java.util.Map;

import cjx.liyueyun.baselib.base.mvp.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private Map<String, String> hashMap;
    private Button btnCollection, btnThread, btCustomView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btnCollection = (Button) findViewById(R.id.btnCollection);
        btnThread = (Button) findViewById(R.id.btnThread);
        btCustomView = (Button) findViewById(R.id.btCustomView);

        btnCollection.setOnClickListener(this);
        btnThread.setOnClickListener(this);
        btCustomView.setOnClickListener(this);

        String sha1=SignUtil.getAppSignatureSHA1(this,this.getPackageName());
        String md5=SignUtil.getAppSignatureMD5(this,this.getPackageName());
        String sha256=SignUtil.getAppSignatureSHA256(this,this.getPackageName());

        Log.i(TAG, "sha1: "+sha1+"\n"+"md5:"+md5+"\n"+"sha256:"+sha256);

    }

    @Override
    public void initData() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCollection:
                startActivity(CollectionActivity.class);
                break;

            case R.id.btnThread:
//                startActivity(ThreadActivity.class);
                startActivity(HandlerActivity.class);
                break;

            case R.id.btCustomView:
                startActivity(CustomViewActivity.class);
//                startActivity(StepViewActivity.class);
                break;
        }
    }
}
