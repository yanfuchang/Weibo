package com.example.yyweibo.weibo.zongti.weibo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Yan fuchang on 2016/10/15.
 */

public class ToastUtils {
    private static Toast mToast;
    public static void showToast(Context context,CharSequence text,int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context,text,duration);
        }else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}
