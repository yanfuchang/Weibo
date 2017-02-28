package com.example.yyweibo.weibo.zongti.weibo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yyweibo.R;
import com.example.yyweibo.weibo.zongti.weibo.BaseActivity;
import com.example.yyweibo.weibo.zongti.weibo.api.SimpleRequestListener;
import com.example.yyweibo.weibo.zongti.weibo.api.YYWeiboApi;
import com.example.yyweibo.weibo.zongti.weibo.constants.Config;
import com.example.yyweibo.weibo.zongti.weibo.entity.Status;
import com.example.yyweibo.weibo.zongti.weibo.utils.TitleBuilder;

public class WriteCommentActivity extends BaseActivity implements View.OnClickListener {

    // 评论输入框
    private EditText et_write_status;
    // 底部按钮
    private ImageView iv_image;
    private ImageView iv_at;
    private ImageView iv_topic;
    private ImageView iv_emoji;
    private ImageView iv_add;
    // 待评论的微博
    private Status status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);

        status = (Status)getIntent().getSerializableExtra("status");

        initView();
    }

    private void initView() {
        new TitleBuilder(this)
                .setTitleText("发评论")
                .setLeftText("取消")
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WriteCommentActivity.this.finish();
                    }
                })
                .setRightText("发送")
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendComment();
                    }
                });

        et_write_status = (EditText) findViewById(R.id.et_write_status);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        iv_at = (ImageView) findViewById(R.id.iv_at);
        iv_topic = (ImageView) findViewById(R.id.iv_topic);
        iv_emoji = (ImageView) findViewById(R.id.iv_emoji);
        iv_add = (ImageView) findViewById(R.id.iv_add);

        iv_image.setOnClickListener(this);
        iv_at.setOnClickListener(this);
        iv_topic.setOnClickListener(this);
        iv_emoji.setOnClickListener(this);
        iv_add.setOnClickListener(this);

    }

    private void sendComment() {
        String comment = et_write_status.getText().toString();
        if (TextUtils.isEmpty(comment)) {
            showToast("评论内容不能为空");
            return;
        }
        YYWeiboApi api = new YYWeiboApi(this,Config.APP_KEY);
        api.commentsCreate(status.getId(),comment,new SimpleRequestListener(this,null) {
            @Override
            public void onComplete(String s) {
                super.onComplete(s);
                Intent data = new Intent();
                data.putExtra("sendCommentSuccess",true);
                setResult(RESULT_OK,data);

                WriteCommentActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
