package com.example.yyweibo.weibo.zongti.weibo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.yyweibo.R;

import com.example.yyweibo.weibo.zongti.weibo.fragment.FragmentController;
import com.example.yyweibo.weibo.zongti.weibo.utils.ToastUtils;


/**
 * Created by Yan fuchang on 2016/10/15.
 */

public class MainActivity extends FragmentActivity implements
        View.OnClickListener,RadioGroup.OnCheckedChangeListener{

    private RadioGroup rg_tab;
    private ImageView iv_add;
    private FragmentController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        controller = FragmentController.getInstance(this,R.id.fl_content);
        controller.showFragment(0);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.onDestroy();
    }

    private void initView() {
        rg_tab = (RadioGroup) findViewById(R.id.rg_tab);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        rg_tab.setOnCheckedChangeListener(this);
        iv_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:

                Intent intent = new Intent(MainActivity.this,WriteStatusActivity.class);
                startActivity(intent);
                ToastUtils.showToast(this,"add",Toast.LENGTH_SHORT);
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.rb_home:
                controller.showFragment(0);
                break;
        }
    }
}
