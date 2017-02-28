package com.example.yyweibo.weibo.zongti.weibo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.yyweibo.weibo.zongti.weibo.constants.CommonConstants;
import com.example.yyweibo.weibo.zongti.weibo.utils.Logger;
import com.example.yyweibo.weibo.zongti.weibo.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Yan fuchang on 2016/10/16.
 */

public abstract class BaseActivity extends Activity {
    protected String TAG;
    protected ImageLoader imageLoader;
    protected BaesApplication application;
    protected SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        TAG = this.getClass().getSimpleName();
        application = (BaesApplication) getApplication();
        sp = getSharedPreferences(CommonConstants.SP_NAME, MODE_PRIVATE);
    }

    protected void intent2Activity(Class<? extends Activity> targetActivity) {
        Intent intent = new Intent(this,targetActivity);
        startActivity(intent);
    }

    protected void showToast(String msg){
        ToastUtils.showToast(this,msg, Toast.LENGTH_SHORT);
    }
    protected void showLog(String msg) {
        Logger.show(TAG, msg);
    }
}