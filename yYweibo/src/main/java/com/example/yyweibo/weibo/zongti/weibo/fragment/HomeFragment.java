package com.example.yyweibo.weibo.zongti.weibo.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import android.widget.Toast;

import com.example.yyweibo.R;
import com.example.yyweibo.weibo.zongti.weibo.BaseFragment;
import com.example.yyweibo.weibo.zongti.weibo.adapter.StatusAdapter;
import com.example.yyweibo.weibo.zongti.weibo.api.SimpleRequestListener;
import com.example.yyweibo.weibo.zongti.weibo.api.YYWeiboApi;
import com.example.yyweibo.weibo.zongti.weibo.constants.Config;
import com.example.yyweibo.weibo.zongti.weibo.entity.Status;
import com.example.yyweibo.weibo.zongti.weibo.entity.response.StatusTimeLineResponse;
import com.example.yyweibo.weibo.zongti.weibo.utils.TitleBuilder;
import com.example.yyweibo.weibo.zongti.weibo.utils.ToastUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan fuchang on 2016/10/17.
 */

public class HomeFragment extends BaseFragment {

    private View view;
    private View footView;

    private ListView lv_home;
    private PullToRefreshListView plv_home;

    private StatusAdapter adapter;
    private List<Status> statuses = new ArrayList<>();
    private int curPage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        loadData(1);
        return view;
    }

    private void loadData(final int page) {
        YYWeiboApi api = new YYWeiboApi(activity, Config.APP_KEY);
        api.statusesHome_timeline(page,
                new SimpleRequestListener(activity,null) {
                    @Override
                    public void onComplete(String s) {
                        super.onComplete(s);
                        if (page == 1) statuses.clear();
                        curPage = page;
                        StatusTimeLineResponse timeLineResponse = new Gson().fromJson(s,StatusTimeLineResponse.class);
//                        lv_home.setAdapter(new StatusAdapter(activity,timeLineResponse.getStatuses()));
                        addData(new Gson().fromJson(s,StatusTimeLineResponse.class));
                    }

                    @Override
                    public void onAllDone() {
                        super.onAllDone();
                        plv_home.onRefreshComplete();
                    }
                });

    }

    private void addData(StatusTimeLineResponse resBean) {
        for (Status status : resBean.getStatuses()) {
            if (!statuses.contains(status))
                statuses.add(status);
        }
        adapter.notifyDataSetChanged();
        if (curPage < resBean.getTotal_number()) {
            addFootView(plv_home, footView);
        }else {
            removeFootView(plv_home,footView);
        }
    }
    private void addFootView(PullToRefreshListView plv,View footView) {
        ListView lv = plv.getRefreshableView();
        if(lv.getFooterViewsCount() == 1) {
            lv.addFooterView(footView);
        }
    }
    private void removeFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if(lv.getFooterViewsCount() > 1) {
            lv.removeFooterView(footView);
        }
    }
    private void initView() {
        view = View.inflate(activity,R.layout.frag_home,null);
        new TitleBuilder(view).setTitleText("测试微博首页");
//                .setTitleText("首页")
//                .setLeftText("LEFT")
//                .setLeftOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ToastUtils.showToast(activity,"left onclick", Toast.LENGTH_SHORT);
//                    }
//                });
        plv_home = (PullToRefreshListView) view.findViewById(R.id.lv);
        adapter = new StatusAdapter(activity,statuses);
        plv_home.setAdapter(adapter);
        plv_home.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(1);
            }
        });

        plv_home.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                loadData(curPage + 1);
            }
        });
        footView = View.inflate(activity, R.layout.footview_loading, null);
    }
}
