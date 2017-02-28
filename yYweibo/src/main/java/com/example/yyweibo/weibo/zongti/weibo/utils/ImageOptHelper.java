package com.example.yyweibo.weibo.zongti.weibo.utils;

import android.graphics.Bitmap;

import com.example.yyweibo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by Yan fuchang on 2016/10/19.
 */

public class ImageOptHelper {
    public static DisplayImageOptions getImgOptions() {
        DisplayImageOptions imgOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc()
                .cacheInMemory()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showStubImage(R.drawable.timeline_image_loading)
                .showImageForEmptyUri(R.drawable.timeline_image_loading)
                .showImageOnFail(R.drawable.timeline_image_failure)
                .build();
        return imgOptions;
    }


    public static DisplayImageOptions getAvatarOptions() {
        DisplayImageOptions	avatarOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc()
                .cacheInMemory()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showStubImage(R.drawable.avatar_default)
                .showImageForEmptyUri(R.drawable.avatar_default)
                .showImageOnFail(R.drawable.avatar_default)
                .displayer(new RoundedBitmapDisplayer(999))
                .build();
        return avatarOptions;
    }


    public static DisplayImageOptions getCornerOptions(int cornerRadiusPixels) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisc()
                .cacheInMemory()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showStubImage(R.drawable.timeline_image_loading)
                .showImageForEmptyUri(R.drawable.timeline_image_loading)
                .showImageOnFail(R.drawable.timeline_image_loading)
                .displayer(new RoundedBitmapDisplayer(cornerRadiusPixels)).build();
        return options;
    }
}
