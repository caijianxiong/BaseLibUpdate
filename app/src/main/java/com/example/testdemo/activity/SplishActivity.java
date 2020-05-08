package com.example.testdemo.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;

import com.example.testdemo.MainActivity;
import com.example.testdemo.R;

import java.util.List;

import cjx.baselib.BaseActivity;
import cjx.baselib.log.logUtil;
import cjx.baselib.permission.PermissionHelper;

public class SplishActivity extends BaseActivity implements PermissionHelper.PermissionListener {


    @Override
    public int getLayoutId() {
        return R.layout.activity_splish;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        //申请权限
        String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!PermissionHelper.hasPermissions(this, permissions)) {
            PermissionHelper.requestPermissions(this,
                    permissions,
                    this);
        }else {
            goMain();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onGranted() {
        logUtil.d(TAG, "权限通过");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goMain();

            }
        },2000);


    }

    private void goMain() {
        startActivity(new Intent(SplishActivity.this, MainActivity.class));
    }

    @Override
    public void onGranted(List<String> grantedPermission) {
        logUtil.d(TAG, "部分权限通过");

    }

    @Override
    public void onDenied(List<String> deniedPermission) {
        logUtil.d(TAG, "权限未通过");
    }
}
