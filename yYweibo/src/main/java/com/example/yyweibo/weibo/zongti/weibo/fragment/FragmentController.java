package com.example.yyweibo.weibo.zongti.weibo.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

/**
 * Created by Yan fuchang on 2016/10/17.
 */

public class FragmentController {
    private int containerId;
    private android.support.v4.app.FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private static FragmentController controller;

    public static FragmentController getInstance(FragmentActivity activity, int containerId) {
        if (controller == null) {
            controller = new FragmentController(activity,containerId);
        }

        return controller;
    }
    private FragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    public static void onDestroy() {
        controller = null;
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments)
            ft.add(containerId,fragment);
        ft.commit();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    public void hideFragments() {
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

}
