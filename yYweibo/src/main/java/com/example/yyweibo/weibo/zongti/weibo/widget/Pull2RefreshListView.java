package com.example.yyweibo.weibo.zongti.weibo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
//com.example.yyweibo.weibo.zongti.weibo.widget.Pull2RefreshListView
/**
 * Created by Yan fuchang on 2016/10/28.
 */

public class Pull2RefreshListView extends PullToRefreshListView {
    public Pull2RefreshListView(Context context, Mode mode) {
        super(context, mode);
    }

    public Pull2RefreshListView(Context context) {
        super(context);
    }

    public Pull2RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public Pull2RefreshListView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    protected ListView createListView(Context context, AttributeSet attrs) {
        return super.createListView(context, attrs);
    }
}
