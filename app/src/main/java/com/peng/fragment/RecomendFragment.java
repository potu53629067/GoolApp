package com.peng.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peng.adapter.RecommendAdapter;
import com.peng.base.BaseFragment;
import com.peng.controller.LoadingPager;
import com.peng.protocol.RecommendProtocol;
import com.peng.utils.UIUtils;
import com.peng.views.flyinout.StellarMap;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by peng on 2017/9/13.
 */
public class RecomendFragment extends BaseFragment {

    private List<String> mDatas;
    //1.初始化数据
    @Override
    protected LoadingPager.LoadDataResult initData() {
        RecommendProtocol protocol = new RecommendProtocol();
        try {
            mDatas = protocol.loadData(0);
            return checkResData(mDatas);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadDataResult.ERROR;
        }
    }
    //2.初始化成功视图
    @Override
    protected View initSuccessView() {
        StellarMap stellarMap = new StellarMap(UIUtils.getContext());
        RecommendAdapter adapter = new RecommendAdapter(mDatas);
        stellarMap.setAdapter(adapter);
        //2.1 手动选中第一页
        stellarMap.setGroup(0,true);
        //2.2 每页显示15条有问题，需要对屏幕进行拆分
        stellarMap.setRegularity(15,20);
        return stellarMap;
    }
}