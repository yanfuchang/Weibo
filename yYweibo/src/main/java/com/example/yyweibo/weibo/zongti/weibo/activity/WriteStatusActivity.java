package com.example.yyweibo.weibo.zongti.weibo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.example.yyweibo.R;
import com.example.yyweibo.weibo.zongti.weibo.BaseActivity;
import com.example.yyweibo.weibo.zongti.weibo.adapter.EmotionGvAdapter;
import com.example.yyweibo.weibo.zongti.weibo.adapter.EmotionPagerAdapter;
import com.example.yyweibo.weibo.zongti.weibo.adapter.WriteStatusGridImgsAdapter;
import com.example.yyweibo.weibo.zongti.weibo.api.SimpleRequestListener;
import com.example.yyweibo.weibo.zongti.weibo.api.YYWeiboApi;
import com.example.yyweibo.weibo.zongti.weibo.constants.Config;
import com.example.yyweibo.weibo.zongti.weibo.entity.Status;
import com.example.yyweibo.weibo.zongti.weibo.utils.DisplayUtils;
import com.example.yyweibo.weibo.zongti.weibo.utils.EmotionUtils;
import com.example.yyweibo.weibo.zongti.weibo.utils.ImageUtils;
import com.example.yyweibo.weibo.zongti.weibo.utils.StringUtils;
import com.example.yyweibo.weibo.zongti.weibo.utils.TitleBuilder;
import com.example.yyweibo.weibo.zongti.weibo.widget.WrapHeightGridView;


import java.util.ArrayList;
import java.util.List;

public class WriteStatusActivity extends BaseActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{
    private static final String TAG = "WirteStatusActivity";
    // 输入框
    private EditText et_write_status;
    //添加图片九宫格
    private WrapHeightGridView gv_write_status;
    // 转发微博内容
    private View include_retweeted_status_card;
    private ImageView iv_rstatus_img;
    private TextView tv_rstatus_username;
    private TextView tv_rstatus_content;
    // 底部添加栏
    private ImageView iv_image;
    private ImageView iv_at;
    private ImageView iv_topic;
    private ImageView iv_emoji;
    private ImageView iv_add;
    // 表情选择面板
    private LinearLayout ll_emotion_dashboard;
    private ViewPager vp_emotion_dashboard;
    // 进度框
    private ProgressDialog progressDialog;
    //WrapHeightGridView 自适应的 GridView 适配器
    private WriteStatusGridImgsAdapter statusImgsAdapter;
    private ArrayList<Uri> imgUris = new ArrayList<Uri>();
    private EmotionPagerAdapter emotionPagerGvAdapter;

    private Status retweeted_status;
    private Status cardStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_status);

        retweeted_status = (Status) getIntent().getSerializableExtra("status");
        initView();
    }

    private void initView() {
        //初始化标题栏
        new TitleBuilder(this).setLeftText("取消")
                .setTitleText("发微博")
                .setRightText("发送")
                .setLeftOnClickListener(this)
                .setRightOnClickListener(this)
                .build();

        // 输入框
        et_write_status = (EditText) findViewById(R.id.et_write_status);
        // 添加的九宫格图片
        gv_write_status = (WrapHeightGridView) findViewById(R.id.gv_write_status);
        // 转发微博内容
        include_retweeted_status_card = findViewById(R.id.include_retweeted_status_card);
        iv_rstatus_img = (ImageView) findViewById(R.id.iv_rstatus_img);
        tv_rstatus_username = (TextView) findViewById(R.id.tv_rstatus_username);
        tv_rstatus_content = (TextView) findViewById(R.id.tv_rstatus_content);
        // 底部添加栏
        iv_image = (ImageView) findViewById(R.id.iv_image);
        iv_at = (ImageView) findViewById(R.id.iv_at);
        iv_topic = (ImageView) findViewById(R.id.iv_topic);
        iv_emoji = (ImageView) findViewById(R.id.iv_emoji);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        // 表情选择面板
        ll_emotion_dashboard = (LinearLayout) findViewById(R.id.ll_emotion_dashboard);
        vp_emotion_dashboard = (ViewPager) findViewById(R.id.vp_emotion_dashboard);
        // 进度框
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("微博发布中...");

        statusImgsAdapter = new WriteStatusGridImgsAdapter(this, imgUris, gv_write_status);
        gv_write_status.setAdapter(statusImgsAdapter);
        gv_write_status.setOnItemClickListener(this);

        iv_image.setOnClickListener(this);
        iv_at.setOnClickListener(this);
        iv_topic.setOnClickListener(this);
        iv_emoji.setOnClickListener(this);
        iv_add.setOnClickListener(this);

        initRetweetedStatus();
        initEmotion();

    }
    /**
     * 发送微博
     */
    private void sendStatus() {

        String statusContent = et_write_status.getText().toString();
        if(TextUtils.isEmpty(statusContent)) {
            showToast("微博内容不能为空");
            return;
        }

        String imgFilePath = null;
        if(imgUris.size() > 0) {
            Uri uri = imgUris.get(0);
            imgFilePath = ImageUtils.getImageAbsolutePath19(this, uri);
        }

        long retweetedStatusId = cardStatus == null ? -1 : cardStatus.getId();
        YYWeiboApi api = new YYWeiboApi(this, Config.APP_KEY);
        api.statusesSend(statusContent, imgFilePath, retweetedStatusId,
                new SimpleRequestListener(this, null){

                    @Override
                    public void onComplete(String response) {
                        super.onComplete(response);
                        Log.e(TAG,"微博发送成功");
                        WriteStatusActivity.this.finish();
                    }

                });

    }

    private void initRetweetedStatus() {
        if(retweeted_status != null) {
            Status rrStatus = retweeted_status.getRetweeted_status();
            if(rrStatus != null) {
                String content = "//@" + retweeted_status.getUser().getName()
                        + ":" + retweeted_status.getText();
                et_write_status.setText(
                        StringUtils.getWeiBoContent(this, et_write_status, content));
                cardStatus = rrStatus;
            } else {
                et_write_status.setText("转发微博");
                cardStatus = retweeted_status;
            }

            String imgUrl = cardStatus.getThumbnail_pic();
            if(TextUtils.isEmpty(imgUrl)) {
                iv_rstatus_img.setVisibility(View.GONE);
            } else {
                iv_rstatus_img.setVisibility(View.VISIBLE);
                imageLoader.displayImage(imgUrl, iv_rstatus_img);
            }

            tv_rstatus_username.setText("@" + cardStatus.getUser().getName());
            tv_rstatus_content.setText(cardStatus.getText());

            iv_image.setVisibility(View.GONE);

            include_retweeted_status_card.setVisibility(View.VISIBLE);
        } else {
            include_retweeted_status_card.setVisibility(View.GONE);
        }

    }

    /**
     *  初始化表情面板内容
     */
    private void initEmotion() {
        int screenWidth = DisplayUtils.getScreenWidthPixels(this);
        int spacing = DisplayUtils.dp2px(this, 8);

        int itemWidth = (screenWidth - spacing * 8) / 7;
        int gvHeight = itemWidth * 3 + spacing * 4;

        List<GridView> gvs = new ArrayList<>();
        List<String> emotionNames = new ArrayList<String>();
        for(String emojiName : EmotionUtils.emoMap.keySet()) {
            emotionNames.add(emojiName);

            if(emotionNames.size() == 20) {
                GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
                gvs.add(gv);

                emotionNames = new ArrayList<String>();
            }
        }

        if(emotionNames.size() > 0) {
            GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
            gvs.add(gv);
        }

        emotionPagerGvAdapter = new EmotionPagerAdapter(gvs);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        vp_emotion_dashboard.setLayoutParams(params);
        vp_emotion_dashboard.setAdapter(emotionPagerGvAdapter);
    }
    /**
     * 创建显示表情的GridView
     */
    private GridView createEmotionGridView(List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        GridView gv = new GridView(WriteStatusActivity.this);

        gv.setBackgroundResource(R.color.bg_gray);
        gv.setSelector(R.color.transparent);
        gv.setNumColumns(7);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding);
        EmotionGvAdapter adapter = new EmotionGvAdapter(this, emotionNames, itemWidth);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(this);


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);
        return gv;
    }

    /**
     * 更新图片显示
     */
    private void updateImgs() {
        if(imgUris.size() > 0) {
            gv_write_status.setVisibility(View.VISIBLE);
            statusImgsAdapter.notifyDataSetChanged();
        } else {
            gv_write_status.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_tv_left:
                finish();
                break;
            case R.id.titlebar_tv_right:
                sendStatus();
                break;
            case R.id.iv_image:
                ImageUtils.showImagePickDialog(this);
                break;
            case R.id.iv_at:
                break;
            case R.id.iv_topic:
                break;
            case R.id.iv_emoji:
                if(ll_emotion_dashboard.getVisibility() == View.VISIBLE) {
                    ll_emotion_dashboard.setVisibility(View.GONE);
                    iv_emoji.setImageResource(R.drawable.btn_insert_emotion);
                } else {
                    ll_emotion_dashboard.setVisibility(View.VISIBLE);
                    iv_emoji.setImageResource(R.drawable.btn_insert_keyboard);
                }
                break;
            case R.id.iv_add:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemAdapter = parent.getAdapter();
        if(itemAdapter instanceof WriteStatusGridImgsAdapter) {
            if(position == statusImgsAdapter.getCount() - 1) {
                ImageUtils.showImagePickDialog(this);
            }
        } else if(itemAdapter instanceof EmotionGvAdapter) {
            EmotionGvAdapter emotionAdapter = (EmotionGvAdapter) itemAdapter;

            if(position == emotionAdapter.getCount() - 1) {
                et_write_status.dispatchKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            } else {
                String emotionName = (String) emotionAdapter.getItem(position);

                int curPosition = et_write_status.getSelectionStart();
                StringBuilder sb = new StringBuilder(et_write_status.getText().toString());
                sb.insert(curPosition, emotionName);

                SpannableString weiboContent = StringUtils.getWeiBoContent(
                        this, et_write_status, sb.toString());
                et_write_status.setText(weiboContent);

                et_write_status.setSelection(curPosition + emotionName.length());
            }

        }
    }
}
