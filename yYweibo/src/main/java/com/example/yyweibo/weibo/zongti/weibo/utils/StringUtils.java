package com.example.yyweibo.weibo.zongti.weibo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yyweibo.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yan fuchang on 2016/10/21.
 */

public class StringUtils {
    public static SpannableString getWeiBoContent(final Context context, final TextView tv, String source) {
        /**
         * 正则表达式
         * */
        String regexAt = "@[\u4e00-\u9fa5\\w]+";
        String regexTopic = "#[\u4e00-\u9fa5\\w]+#";
        String regexEmoji = "\\[[\u4e00-\u9fa5\\w]+\\]";

        String regex = "(" + regexAt + ")|(" + regexTopic + ")|(" + regexEmoji + ")";
        /**
         * 获得可变字符串对象并传入字符资源
         * */
        SpannableString spannableString = new SpannableString(source);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(spannableString);
        /**
         *
         *
         * */
        if (matcher.find()) {
            tv.setMovementMethod(LinkMovementMethod.getInstance());
            matcher.reset();
        }

        while(matcher.find()) {
            final String atStr = matcher.group(1);
            final String topicStr = matcher.group(2);
            String emojiStr = matcher.group(3);

            if (atStr != null) {
                int start = matcher.start(1);
                WeiboClickableSpan weiboClickableSpan = new WeiboClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {
                        ToastUtils.showToast(context, "用户: " + atStr, Toast.LENGTH_SHORT);
                    }
                };
                spannableString.setSpan(weiboClickableSpan,start,start + atStr.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (topicStr != null) {
                int start = matcher.start(2);
                WeiboClickableSpan weiboClickableSpan = new WeiboClickableSpan(context) {

                    @Override
                    public void onClick(View widget) {
                        ToastUtils.showToast(context,"话题： " + topicStr,Toast.LENGTH_SHORT);
                    }
                };
                spannableString.setSpan(weiboClickableSpan,start,start + topicStr.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (emojiStr != null) {
                int start = matcher.start(3);
                int imgRes = EmotionUtils.getImgByName(emojiStr);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),imgRes);

                if (bitmap != null) {
                    int size = (int)tv.getTextSize();
                    bitmap = Bitmap.createScaledBitmap(bitmap,size,size,true);
                    ImageSpan imageSpan = new ImageSpan(context,bitmap);
                    spannableString.setSpan(imageSpan,start,start + emojiStr.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return spannableString;

    }

    static class WeiboClickableSpan extends ClickableSpan {
        private Context context;
        public WeiboClickableSpan(Context context) {
            /******/
            this.context = context;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(context.getResources().getColor(R.color.txt_at_blue));
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {

        }
    }
}
