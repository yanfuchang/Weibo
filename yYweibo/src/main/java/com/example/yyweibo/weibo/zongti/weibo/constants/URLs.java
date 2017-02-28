package com.example.yyweibo.weibo.zongti.weibo.constants;

/**
 * Created by Yan fuchang on 2016/10/18.
 */

public interface URLs {
    // host
    String BASE_URL = "https://api.weibo.com/2/";

    // 首页微博列表
    String statusesHome_timeline = BASE_URL + "statuses/home_timeline.json";
    // 微博评论列表
    String commentsShow = BASE_URL + "comments/show.json";
    // 对一条微博进行评论
    String commentsCreate = BASE_URL + "comments/create.json";

    // 发布一条微博(带图片)
    String statusesUpload = BASE_URL + "statuses/upload.json";

    // 转发一条微博
    String statusesRepost = BASE_URL + "statuses/repost.json";

    // 发布一条微博(不带图片)
    String statusesUpdate = BASE_URL + "statuses/update.json";
}
