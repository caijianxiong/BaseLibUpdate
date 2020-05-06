package com.example.testdemo;

import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.testdemo.activity.CollectionActivity;
import com.example.testdemo.activity.custom_view.CustomViewActivity;
import com.example.testdemo.activity.handler.HandlerActivity;
import com.example.testdemo.utils.SignUtil;

import java.util.List;
import java.util.Map;
import java.util.prefs.PreferencesFactory;

import cjx.liyueyun.baselib.base.mvp.BaseActivity;
import cjx.liyueyun.baselib.base.mvp.net.HttpUtils;
import cjx.liyueyun.baselib.base.mvp.net.mInterface.MyCallback;
import cjx.liyueyun.baselib.base.mvp.permission.PermissionHelper;

public class MainActivity extends BaseActivity implements View.OnClickListener, PermissionHelper.PermissionListener {


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


        HttpUtils.getInstance().get().url("").enqueue(new MyCallback<String>() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onSuccess(String s) {

            }
        });

        HttpUtils.getInstance().post().url("").param("","").header("","").enqueue(new MyCallback<String>() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onSuccess(String s) {

            }
        });

    }




    @Override
    public void initData() {
        //申请权限
        String[] permissions=new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!PermissionHelper.hasPermissions(this, permissions)){
            PermissionHelper.requestPermissions(this,
                    permissions,
                    this);
        }

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
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public void onGranted() {

    }

    @Override
    public void onGranted(List<String> grantedPermission) {

    }

    @Override
    public void onDenied(List<String> deniedPermission) {

    }
}
