package com.example.testdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testdemo.activity.CollectionActivity;
import com.example.testdemo.activity.custom_view.CustomViewActivity;
import com.example.testdemo.activity.custom_view.StepViewActivity;
import com.example.testdemo.activity.thread.ThreadActivity;

import java.util.HashMap;
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
                startActivity(ThreadActivity.class);
                break;

            case R.id.btCustomView:
//                startActivity(CustomViewActivity.class);
                startActivity(StepViewActivity.class);
                break;
        }
    }
}
