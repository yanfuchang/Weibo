package com.example.yyweibo.weibo.zongti.weibo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.yyweibo.R;
import com.example.yyweibo.weibo.zongti.weibo.entity.PicUrls;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Yan fuchang on 2016/10/19.
 */

public class StatusGridImgsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PicUrls> datas;
    private ImageLoader imageLoader;

    public StatusGridImgsAdapter(Context context, ArrayList<PicUrls> datas) {
        this.context = context;
        this.datas = datas;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public PicUrls getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_grid_image, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GridView gv = (GridView) parent;
        int horizontalSpacing = gv.getHorizontalSpacing();
        int numColumns = gv.getNumColumns();
        int itemWidth = (gv.getWidth() - (numColumns-1) * horizontalSpacing
                - gv.getPaddingLeft() - gv.getPaddingRight()) / numColumns;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itemWidth, itemWidth);
        holder.iv_image.setLayoutParams(params);

        PicUrls urls = getItem(position);
        imageLoader.displayImage(urls.getThumbnail_pic(), holder.iv_image);

        return convertView;
    }

    public static class ViewHolder {
        public ImageView iv_image;
    }
}
