package com.example.yyweibo.weibo.zongti.weibo.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObservable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yyweibo.R;
import com.example.yyweibo.weibo.zongti.weibo.activity.StatusDetailActivity;
import com.example.yyweibo.weibo.zongti.weibo.activity.WriteCommentActivity;
import com.example.yyweibo.weibo.zongti.weibo.activity.WriteStatusActivity;
import com.example.yyweibo.weibo.zongti.weibo.entity.PicUrls;
import com.example.yyweibo.weibo.zongti.weibo.entity.Status;
import com.example.yyweibo.weibo.zongti.weibo.entity.User;
import com.example.yyweibo.weibo.zongti.weibo.utils.DateUtils;
import com.example.yyweibo.weibo.zongti.weibo.utils.StringUtils;
import com.example.yyweibo.weibo.zongti.weibo.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan fuchang on 2016/10/19.
 */

public class StatusAdapter extends BaseAdapter {
    private Context context;
    private List<Status> datas;
    private ImageLoader imageLoader;

    public StatusAdapter(Context context,List<Status> datas) {
        this.context = context;
        this.datas = datas;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_status, null);
            holder.ll_card_content = (LinearLayout) convertView
                    .findViewById(R.id.ll_card_content);
            holder.iv_avatar = (ImageView) convertView
                    .findViewById(R.id.iv_avatar);
            holder.rl_content = (RelativeLayout) convertView
                    .findViewById(R.id.rl_content);
            holder.tv_subhead = (TextView) convertView
                    .findViewById(R.id.tv_subhead);
            holder.tv_caption = (TextView) convertView
                    .findViewById(R.id.tv_caption);

            holder.tv_content = (TextView) convertView
                    .findViewById(R.id.tv_content);
            holder.include_status_image = (FrameLayout) convertView
                    .findViewById(R.id.include_status_image);
            holder.gv_images = (GridView) holder.include_status_image
                    .findViewById(R.id.gv_images);
            holder.iv_image = (ImageView) holder.include_status_image
                    .findViewById(R.id.iv_image);

            holder.include_retweeted_status = (LinearLayout) convertView
                    .findViewById(R.id.include_retweeted_status);
            holder.tv_retweeted_content = (TextView) holder.include_retweeted_status
                    .findViewById(R.id.tv_retweeted_content);
            holder.include_retweeted_status_image = (FrameLayout) holder.include_retweeted_status
                    .findViewById(R.id.include_status_image);
            holder.gv_retweeted_images = (GridView) holder.include_retweeted_status_image
                    .findViewById(R.id.gv_images);
            holder.iv_retweeted_image = (ImageView) holder.include_retweeted_status_image
                    .findViewById(R.id.iv_image);

            holder.ll_share_bottom = (LinearLayout) convertView
                    .findViewById(R.id.ll_share_bottom);
            holder.iv_share_bottom = (ImageView) convertView
                    .findViewById(R.id.iv_share_bottom);
            holder.tv_share_bottom = (TextView) convertView
                    .findViewById(R.id.tv_share_bottom);
            holder.ll_comment_bottom = (LinearLayout) convertView
                    .findViewById(R.id.ll_comment_bottom);
            holder.iv_comment_bottom = (ImageView) convertView
                    .findViewById(R.id.iv_comment_bottom);
            holder.tv_comment_bottom = (TextView) convertView
                    .findViewById(R.id.tv_comment_bottom);
            holder.ll_like_bottom = (LinearLayout) convertView
                    .findViewById(R.id.ll_like_bottom);
            holder.iv_like_bottom = (ImageView) convertView
                    .findViewById(R.id.iv_like_bottom);
            holder.tv_like_bottom = (TextView) convertView
                    .findViewById(R.id.tv_like_bottom);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        // bind data
        final Status status = (Status) getItem(position);
        User user = status.getUser();
        imageLoader.displayImage(user.getProfile_image_url(), holder.iv_avatar);
        holder.tv_subhead.setText(user.getName());
        /**
         * status.getSource() 微博来源html信息
         * status.getCreated_at() 发微博的时间
         *
         * */
        holder.tv_caption.setText(DateUtils.getShortTime(status.getCreated_at())
                + " 来自 " + Html.fromHtml(status.getSource()));
        holder.tv_content.setText(StringUtils.getWeiBoContent(context,holder.tv_content,status.getText()));

        setImages(status, holder.include_status_image, holder.gv_images, holder.iv_image);

        Status retweeted_status = status.getRetweeted_status();
        if(retweeted_status != null) {
            User retUser = retweeted_status.getUser();

            holder.include_retweeted_status.setVisibility(View.VISIBLE);
            String retweetedContent = "@" + retUser.getName() + ":"
                    + retweeted_status.getText();
            holder.tv_retweeted_content.setText(StringUtils.getWeiBoContent(
                    context, holder.tv_retweeted_content, retweetedContent));

            setImages(retweeted_status,
                    holder.include_retweeted_status_image,
                    holder.gv_retweeted_images, holder.iv_retweeted_image);
        } else {
            holder.include_retweeted_status.setVisibility(View.GONE);
        }

        holder.tv_share_bottom.setText(status.getReposts_count() == 0 ?
                "转发" : status.getReposts_count() + "");

        holder.tv_comment_bottom.setText(status.getComments_count() == 0 ?
                "评论" : status.getComments_count() + "");

        holder.tv_like_bottom.setText(status.getAttitudes_count() == 0 ?
                "赞" : status.getAttitudes_count() + "");

        /**
         * 转发评论赞按钮监听，点击listView的一个View跳转的微博详情页StatusDetailActivity
         * 点击转发的微博，跳转到转发微博的详情页StatusDetailActivity
         * 点击转发，跳转到转发，点击评论跳转到评论页
         *
         *
         * */
        holder.ll_card_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StatusDetailActivity.class);
                intent.putExtra("status",status);
                context.startActivity(intent);
            }
        });

        /**
         * 点击引用微博的时候会，跳转的是整个微博的详情，而不是跳转到引用微博的详情，这里需要解决
         * 事件分发冲突
         *
         * */
        holder.include_retweeted_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,StatusDetailActivity.class);
                intent.putExtra("status",status);
                context.startActivity(intent);
            }
        });

        holder.ll_share_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WriteStatusActivity.class);
                intent.putExtra("status",datas.get(position));
                context.startActivity(intent);
                ToastUtils.showToast(context,"我要跳到转发页面，但是还没有写好", Toast.LENGTH_SHORT);
            }
        });

        holder.ll_comment_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.getComments_count() > 0) {
                    Intent intent = new Intent(context, StatusDetailActivity.class);
                    intent.putExtra("status",status);
                    intent.putExtra("scroll2Comment",true);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, WriteCommentActivity.class);
                    intent.putExtra("status",status);
                    context.startActivity(intent);
                }

                ToastUtils.showToast(context,"我要跳到评论页面，但是还没有写好", Toast.LENGTH_SHORT);

            }
        });

        holder.ll_like_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(context,"zan", Toast.LENGTH_SHORT);
            }
        });

        return convertView;
    }
    private void setImages(Status status, FrameLayout imgContainer,
                           GridView gv_images, ImageView iv_image) {
        ArrayList<PicUrls> pic_urls = status.getPic_urls();
        String thumbnail_pic = status.getThumbnail_pic();

        if(pic_urls != null && pic_urls.size() > 1) {
            imgContainer.setVisibility(View.VISIBLE);
            gv_images.setVisibility(View.VISIBLE);
            iv_image.setVisibility(View.GONE);

            StatusGridImgsAdapter gvAdapter = new StatusGridImgsAdapter(context, pic_urls);
            gv_images.setAdapter(gvAdapter);
        } else if(thumbnail_pic != null) {
            imgContainer.setVisibility(View.VISIBLE);
            gv_images.setVisibility(View.GONE);
            iv_image.setVisibility(View.VISIBLE);

            imageLoader.displayImage(thumbnail_pic, iv_image);
        } else {
            imgContainer.setVisibility(View.GONE);
        }
    }


    public static class ViewHolder {
        public LinearLayout ll_card_content;
        public ImageView iv_avatar;
        public RelativeLayout rl_content;
        public TextView tv_subhead;
        public TextView tv_caption;

        public TextView tv_content;
        public FrameLayout include_status_image;
        public GridView gv_images;
        public ImageView iv_image;

        public LinearLayout include_retweeted_status;
        public TextView tv_retweeted_content;
        public FrameLayout include_retweeted_status_image;
        public GridView gv_retweeted_images;
        public ImageView iv_retweeted_image;

        public LinearLayout ll_share_bottom;
        public ImageView iv_share_bottom;
        public TextView tv_share_bottom;
        public LinearLayout ll_comment_bottom;
        public ImageView iv_comment_bottom;
        public TextView tv_comment_bottom;
        public LinearLayout ll_like_bottom;
        public ImageView iv_like_bottom;
        public TextView tv_like_bottom;
    }
}
