package com.example.yyweibo.weibo.zongti.weibo.activity;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import com.example.yyweibo.weibo.zongti.weibo.BaseActivity;

import com.example.yyweibo.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import com.example.yyweibo.weibo.zongti.weibo.constants.AccessTokenKeeper;

public class SplashActivity extends BaseActivity {
    private final int INTENT2MAIN = 1;
    private final int INTENT2LOGIN = 0;
    private final int DELAY = 2000;
    private Oauth2AccessToken mAccessToken;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INTENT2MAIN:
                    intent2Activity(MainActivity.class);
                    finish();
                    break;
                case INTENT2LOGIN:
                    intent2Activity(LoginActivity.class);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /**获取本地保存的token**/
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        /**token可用，则跳转到主页面，不可用跳转到登陆页面**/
        if (mAccessToken.isSessionValid()) {
            handler.sendEmptyMessageDelayed(INTENT2MAIN,DELAY);
        } else {
            handler.sendEmptyMessageDelayed(INTENT2LOGIN,DELAY);
        }

    }
}
