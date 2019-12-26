package com.example.testdemo.activity.custom_view;


import android.view.View;
import android.widget.Button;

import com.example.testdemo.R;

import cjx.liyueyun.baselib.base.mvp.BaseActivity;

public class CustomViewActivity extends BaseActivity implements View.OnClickListener {

    private StepView stepView;
    private Button btAdd, btLess;

    private int step = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_view;
    }

    @Override
    public void initView() {
        stepView = (StepView) findViewById(R.id.stepView);
        btLess = (Button) findViewById(R.id.btLess);
        btAdd = (Button) findViewById(R.id.btAdd);
        btLess.setOnClickListener(this);
        btAdd.setOnClickListener(this);
    }

    @Override
    public void initData() {
        stepView.setCurrentStep(1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btLess:
                if (step == 1) return;
                stepView.setCurrentStep(--step);
                break;
            case R.id.btAdd:
                if (step == 10) return;
                stepView.setCurrentStep(++step);
                break;
        }
    }
}
