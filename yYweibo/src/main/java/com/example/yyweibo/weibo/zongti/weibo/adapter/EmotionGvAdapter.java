package com.example.yyweibo.weibo.zongti.weibo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.yyweibo.R;
import com.example.yyweibo.weibo.zongti.weibo.utils.EmotionUtils;

import java.util.List;

/**
 * Created by Yan fuchang on 2016/12/7.
 */

public class EmotionGvAdapter extends BaseAdapter{
    private Context context;
    private List<String> emotionNames;
    private int itemWidth;

    public EmotionGvAdapter(Context context, List<String> emotionNames, int itemWidth) {
        this.context = context;
        this.emotionNames = emotionNames;
        this.itemWidth = itemWidth;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv = new ImageView(context);
        /**
         *  此处有 bug
         *  java.lang.ClassCastException:
         *    android.view.ViewGroup$LayoutParams cannot be cast to android.widget.AbsListView$LayoutPara
         **/
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(itemWidth, itemWidth);
//        iv.setPadding(itemWidth/8, itemWidth/8, itemWidth/8, itemWidth/8);
//        iv.setLayoutParams(params);
        iv.setBackgroundResource(R.drawable.bg_tran2gray_sel);

        if(position == getCount() - 1) {
            iv.setImageResource(R.drawable.emotion_delete_icon);
        } else {
            String emotionName = emotionNames.get(position);
            iv.setImageResource(EmotionUtils.getImgByName(emotionName));
        }

        return iv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public String getItem(int position) {
        return emotionNames.get(position);
    }

    @Override
    public int getCount() {
        return emotionNames.size() + 1;
    }
}
