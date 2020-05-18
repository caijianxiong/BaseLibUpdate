package com.example.testdemo;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testdemo.activity.CollectionActivity;
import com.example.testdemo.activity.custom_view.CustomViewActivity;
import com.example.testdemo.activity.handler.HandlerActivity;
import com.example.testdemo.been.Data;
import com.example.testdemo.dbmanager.BookDbManager;

import java.util.List;
import java.util.Map;

import cjx.baselib.BaseActivity;
import cjx.baselib.bean.Book;
import cjx.baselib.db.DBUtils;
import cjx.baselib.db.manager.DBManagerFactory;
import cjx.baselib.log.logUtil;
import cjx.baselib.net.HttpUtils;
import cjx.baselib.net.mInterface.MyCallback;
import cjx.baselib.sp.SpBuilder;
import cjx.baselib.sp.SpUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private Map<String, String> hashMap;
    private Button btnCollection, btnThread, btCustomView, btAddBook, btQueryBook, btDeleteBook;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btnCollection = (Button) findViewById(R.id.btnCollection);
        btnThread = (Button) findViewById(R.id.btnThread);
        btCustomView = (Button) findViewById(R.id.btCustomView);
        btAddBook = (Button) findViewById(R.id.btAddBook);
        btQueryBook = (Button) findViewById(R.id.btQueryBook);
        btDeleteBook = (Button) findViewById(R.id.btDeleteBook);

        btnCollection.setOnClickListener(this);
        btnThread.setOnClickListener(this);
        btCustomView.setOnClickListener(this);
        btAddBook.setOnClickListener(this);
        btQueryBook.setOnClickListener(this);
        btDeleteBook.setOnClickListener(this);


        Toast.makeText(this, "app 启动 ", Toast.LENGTH_SHORT).show();
        logUtil.d("TAG", "打印一条日志");
        Data data = new Data("2020", 26, false, new Data.People("caicai", 26));
        logUtil.i(TAG, data);
        logUtil.v(TAG, data);


        HttpUtils.getInstance().get().doOnMain().enqueue(new MyCallback<String>() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onSuccess(String s) {

            }
        });

//        HttpUtils.getInstance().post().doOnMain().header().param()

    }


    @Override
    public void initData() {
        BookDbManager bookDbManager = DBManagerFactory.createDBManager(BookDbManager.class);
    }

    private int count = 0;

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
                Book book = new Book();
                int n = ++count;
                book.name = "book" + (n);
                book.price = count * 2;
                book.isJava = true;
                book.other = "other" + (n);
                DBUtils.saveBook(this, book);
                break;


            case R.id.btQueryBook:
                List<Book> books = DBUtils.queryAllBook(this);
                Log.d(TAG, "onClick: queryAllBook  size:" + MyApplication.getmGson().toJson(books));
                break;

            case R.id.btDeleteBook:
                break;
        }
    }

}
