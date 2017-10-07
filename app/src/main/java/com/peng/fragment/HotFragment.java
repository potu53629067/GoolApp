package com.peng.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peng.base.BaseFragment;
import com.peng.controller.LoadingPager;
import com.peng.utils.UIUtils;

import java.util.Random;

/**
 * Created by peng on 2017/9/13.
 */
public class HotFragment extends BaseFragment {

    @Override
    protected LoadingPager.LoadDataResult initData() {
        LoadingPager.LoadDataResult[] loadDataResults = {LoadingPager.LoadDataResult.EMPTY, LoadingPager.LoadDataResult.EMPTY, LoadingPager.LoadDataResult.ERROR};
        Random random = new Random();
        int index = random.nextInt(3);
        return loadDataResults[index];
    }

    @Override
    protected View initSuccessView() {
        return null;
    }
}