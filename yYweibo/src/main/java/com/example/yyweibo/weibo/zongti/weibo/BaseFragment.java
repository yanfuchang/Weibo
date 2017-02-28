package com.example.yyweibo.weibo.zongti.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.yyweibo.weibo.zongti.weibo.activity.MainActivity;
import com.sina.weibo.sdk.openapi.legacy.ActivityInvokeAPI;

/**
 * Created by Yan fuchang on 2016/10/17.
 */

public class BaseFragment extends Fragment {
    protected MainActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();
    }

    public void intent2Activity(Class<? extends Activity> targetActivity) {
        Intent intent = new Intent(activity,targetActivity);
        startActivity(intent);
    }
}
