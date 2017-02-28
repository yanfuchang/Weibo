package com.example.yyweibo.weibo.zongti.weibo.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yyweibo.R;
import com.example.yyweibo.weibo.zongti.weibo.entity.Comment;
import com.example.yyweibo.weibo.zongti.weibo.entity.User;
import com.example.yyweibo.weibo.zongti.weibo.utils.DateUtils;
import com.example.yyweibo.weibo.zongti.weibo.utils.StringUtils;
import com.example.yyweibo.weibo.zongti.weibo.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Yan fuchang on 2016/10/28.
 */

public class StatusCommentAdapter extends BaseAdapter{
    private Context context;
    private List<Comment> comments;
    private ImageLoader imageLoader;

    public StatusCommentAdapter() {

    }
    public StatusCommentAdapter(Context context,List<Comment> comments) {
        this.comments = comments;
        this.context = context;
        this.imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderList holder;
        if (convertView == null) {
            holder = new ViewHolderList();
            convertView = View.inflate(context, R.layout.item_comment,null);
            holder.ll_comments = (LinearLayout) convertView.findViewById(R.id.ll_comments);
            holder.iv_avatar = (ImageView) convertView
                    .findViewById(R.id.iv_avatar);
            holder.tv_userName = (TextView) convertView
                    .findViewById(R.id.tv_subhead);
            holder.tv_caption = (TextView) convertView
                    .findViewById(R.id.tv_caption);
            holder.tv_comment = (TextView) convertView
                    .findViewById(R.id.tv_comment);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolderList) convertView.getTag();
        }

        Comment comment = (Comment) getItem(position);
        User user = comment.getUser();

        imageLoader.displayImage(user.getProfile_image_url(),holder.iv_avatar);
        holder.tv_userName.setText(user.getName());
        holder.tv_caption.setText(DateUtils.getShortTime(user.getCreated_at()));

//        SpannableString weiboComment = StringUtils.getWeiBoContent(context,holder.tv_comment,comment.getText());

        holder.tv_comment.setText(comment.getText());

        holder.ll_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(context, "回复评论", Toast.LENGTH_SHORT);
            }
        });


        return convertView;
    }

    public static class ViewHolderList {
        public LinearLayout ll_comments;
        public ImageView iv_avatar;
        public TextView tv_userName;
        public TextView tv_caption;
        public TextView tv_comment;
    }
}
