package com.peng.factory;

import android.support.v4.app.Fragment;

import com.peng.base.BaseFragment;
import com.peng.fragment.AppFragment;
import com.peng.fragment.CategoryFragment;
import com.peng.fragment.GameFragment;
import com.peng.fragment.HomeFragment;
import com.peng.fragment.HotFragment;
import com.peng.fragment.RecomendFragment;
import com.peng.fragment.SubjectFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peng on 2017/9/13.
 */
public class FragmentFactory {
    public static final int FRAGMENT_HOME      = 0;//首页
    public static final int FRAGMENT_APP       = 1;//应用
    public static final int FRAGMENT_GAME      = 2;//游戏
    public static final int FRAGMENT_SUBJECT   = 3;//专题
    public static final int FRAGMENT_RECOOMEND = 4;//推荐
    public static final int FRAGMENT_CATEGORY  = 5;//分类
    public static final int FRAGMENT_HOT       = 6;//排行
    public static Map<Integer,BaseFragment> mCacheFragments = new HashMap<>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = null;
        //优先从集合中取出来
        if (mCacheFragments.containsKey(position)){
            fragment = mCacheFragments.get(position);
            return fragment;
        }
        switch (position){
            case FRAGMENT_HOME:
                fragment = new HomeFragment();
            break;
            case FRAGMENT_APP:
                fragment = new AppFragment();
                break;
            case FRAGMENT_GAME:
                fragment = new GameFragment();
                break;
            case FRAGMENT_SUBJECT:
                fragment = new SubjectFragment();
                break;
            case FRAGMENT_RECOOMEND:
                fragment = new RecomendFragment();
                break;
            case FRAGMENT_CATEGORY:
                fragment = new CategoryFragment();
                break;
            case FRAGMENT_HOT:
                fragment = new HotFragment();
                break;
            default:
                break;
        }
        mCacheFragments.put(position,fragment);
        return fragment;
    }
}
