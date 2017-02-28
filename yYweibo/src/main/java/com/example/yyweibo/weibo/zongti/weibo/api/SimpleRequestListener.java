package com.example.yyweibo.weibo.zongti.weibo.api;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;


import com.example.yyweibo.weibo.zongti.weibo.utils.ToastUtils;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

/**
 * Created by Yan fuchang on 2016/10/18.
 */

public class SimpleRequestListener implements RequestListener {
    private Context context;
    private Dialog progressDialog;

    public SimpleRequestListener(Context context, Dialog progressDialog) {
        this.context = context;
        this.progressDialog = progressDialog;

    }

    @Override
    public void onWeiboException(WeiboException e) {
        onAllDone();
        ToastUtils.showToast(context,e.getMessage(), Toast.LENGTH_SHORT);
    }

    @Override
    public void onComplete(String s) {
        onAllDone();
        ToastUtils.showToast(context,s, Toast.LENGTH_SHORT);

    }

    public void onAllDone() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
