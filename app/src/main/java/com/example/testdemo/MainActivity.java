package com.example.testdemo;

import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testdemo.activity.CollectionActivity;
import com.example.testdemo.activity.custom_view.CustomViewActivity;
import com.example.testdemo.activity.handler.HandlerActivity;
import com.example.testdemo.been.Data;
import com.example.testdemo.utils.SignUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.prefs.PreferencesFactory;

import cjx.liyueyun.baselib.base.mvp.BaseActivity;
import cjx.liyueyun.baselib.base.mvp.bean.Book;
import cjx.liyueyun.baselib.base.mvp.db.DBUtils;
import cjx.liyueyun.baselib.base.mvp.log.logUtil;
import cjx.liyueyun.baselib.base.mvp.net.HttpUtils;
import cjx.liyueyun.baselib.base.mvp.net.mInterface.MyCallback;
import cjx.liyueyun.baselib.base.mvp.permission.PermissionHelper;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private Map<String, String> hashMap;
    private Button btnCollection, btnThread, btCustomView,btAddBook,btQueryBook;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btnCollection = (Button) findViewById(R.id.btnCollection);
        btnThread = (Button) findViewById(R.id.btnThread);
        btCustomView = (Button) findViewById(R.id.btCustomView);
        btAddBook= (Button) findViewById(R.id.btAddBook);
        btQueryBook= (Button) findViewById(R.id.btQueryBook);

        btnCollection.setOnClickListener(this);
        btnThread.setOnClickListener(this);
        btCustomView.setOnClickListener(this);
        btAddBook.setOnClickListener(this);
        btQueryBook.setOnClickListener(this);


        Toast.makeText(this,"app 启动 ",Toast.LENGTH_SHORT).show();
        logUtil.d("TAG","打印一条日志");
        Data data=new Data("2020",26,false,new Data.People("caicai",26));
        logUtil.i(TAG,data);
        logUtil.v(TAG,data);

    }




    @Override
    public void initData() {
//        List<Book> books=new ArrayList<>();
//        for (int i=0;i<5;i++){
//
//        }

    }

    private int count=0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.btnThread:
//                startActivity(ThreadActivity.class);
                startActivity(HandlerActivity.class);
                break;

            case R.id.btCustomView:
                startActivity(CustomViewActivity.class);
//                startActivity(StepViewActivity.class);
                break;

            case R.id.btnCollection:
                startActivity(CollectionActivity.class);
                break;

            case R.id.btAddBook:
                Book book=new Book();
                book.name="book"+(count++);
                book.price=count*2;
                book.isJava=true;
                DBUtils.insertBook(this,book);
                break;


            case R.id.btQueryBook:
                List<Book> books=DBUtils.queryAllBook(this);
                Log.d(TAG, "onClick: queryAllBook  size:"+books.size());
                break;
        }
    }

}
