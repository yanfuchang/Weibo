package com.example.yyweibo.weibo.zongti.weibo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yyweibo.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class TestActivity extends AppCompatActivity {
    private PullToRefreshListView pullToRefreshListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        pullToRefreshListView = (PullToRefreshListView)findViewById(R.id.lv);
    }
}
