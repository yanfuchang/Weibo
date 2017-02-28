package com.example.yyweibo.weibo.zongti.weibo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Yan fuchang on 2016/10/19.
 */

public class WrapHeightGridView extends GridView {

    public WrapHeightGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WrapHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapHeightGridView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, heightSpec);
    }

}
