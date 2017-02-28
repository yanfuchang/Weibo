package com.example.yyweibo.weibo.zongti.weibo.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.example.yyweibo.weibo.zongti.weibo.constants.AccessTokenKeeper;
import com.example.yyweibo.weibo.zongti.weibo.constants.Config;
import com.example.yyweibo.weibo.zongti.weibo.constants.URLs;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.openapi.AbsOpenAPI;

/**
 * Created by Yan fuchang on 2016/10/18.
 */

public class YYWeiboApi extends AbsOpenAPI {

    /****创建在主线程的Handler*********/
    private Handler mainLooperHandler = new Handler(Looper.getMainLooper());

    public YYWeiboApi(Context context,String appKey) {
        this(context,appKey, AccessTokenKeeper.readAccessToken(context));
    }

    public YYWeiboApi(Context context, String appKey, Oauth2AccessToken accessToken) {
        super(context, appKey, accessToken);
    }

    public void requestInMainLooper(String url, WeiboParameters params, String httpMethod, final RequestListener listener){
        requestAsync(url, params, httpMethod, new RequestListener() {
            @Override
            public void onComplete(final String s) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onComplete(s);
                    }
                });
            }
            @Override
            public void onWeiboException(final WeiboException e) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onWeiboException(e);
                    }
                });
            }
        });
    }

    @Override
    protected void requestAsync(String url, WeiboParameters params, String httpMethod, RequestListener listener) {
        super.requestAsync(url, params, httpMethod, listener);
    }

    public void statusesHome_timeline(int page,RequestListener listener) {
        WeiboParameters parameters = new WeiboParameters(Config.APP_KEY);
        parameters.add("page", page);
        requestInMainLooper(URLs.statusesHome_timeline, parameters, HTTPMETHOD_GET, listener);
    }

    /**
     * 根据微博ID返回某条微博的评论列表
     *
     * @param id
     *            需要查询的微博ID。
     * @param page
     *            返回结果的页码。(单页返回的记录条数，默认为50。)
     * @param listener
     *
     */
    public void commentsShow(long id, long page, RequestListener listener) {
        WeiboParameters params = new WeiboParameters(Config.APP_KEY);
        params.add("id", id);
        params.add("page", page);
        requestInMainLooper(URLs.commentsShow, params , AbsOpenAPI.HTTPMETHOD_GET, listener);
    }

    /**
     * 对一条微博进行评论
     *
     * @param id
     *            需要评论的微博ID。
     * @param comment
     *            评论内容
     * @param listener
     */

    public void commentsCreate(long id, String comment, RequestListener listener) {
        WeiboParameters params = new WeiboParameters(Config.APP_KEY);
        params.add("id", id);
        params.add("comment", comment);
        requestInMainLooper(URLs.commentsCreate, params , AbsOpenAPI.HTTPMETHOD_POST, listener);
    }

    /**
     * 发布或转发一条微博
     *
     *
     * @param status
     *            要发布的微博文本内容。
     * @param imgFilePath
     *            要上传的图片文件路径(为空则代表发布无图微博)。
     * @param retweetedStatusId
     *            要转发的微博ID(<=0时为原创微博)。
     * @param listener
     */
    public void statusesSend(String status, String imgFilePath, long retweetedStatusId, RequestListener listener) {
        String url;

        WeiboParameters params = new WeiboParameters(Config.APP_KEY);
        params.add("status", status);
        if(retweetedStatusId > 0) {
            url = URLs.statusesRepost;
            params.add("id", retweetedStatusId);
        } else if(!TextUtils.isEmpty(imgFilePath)) {
            params.add("pic", imgFilePath);
            url = URLs.statusesUpload;
        } else {
            url = URLs.statusesUpdate;
        }
        requestInMainLooper(url, params, AbsOpenAPI.HTTPMETHOD_POST, listener);
    }
}
