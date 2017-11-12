package com.peng.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.peng.base.BaseFragment;
import com.peng.controller.LoadingPager;
import com.peng.protocol.HotProtocol;
import com.peng.utils.UIUtils;
import com.peng.views.FlowLayout;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import appmark.peng.com.myapplication.R;

/**
 * Created by peng on 2017/9/13.
 */
public class HotFragment extends BaseFragment {

    private List<String> mDatas;

    //1.初始化数据
    @Override
    protected LoadingPager.LoadDataResult initData() {
        HotProtocol protocol = new HotProtocol();
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
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
        for (int i = 0; i < mDatas.size(); i++) {
            //view
            TextView childView = new TextView(UIUtils.getContext());
            //data
            final String data = mDatas.get(i);
            //data+View
            childView.setText(data);
            childView.setGravity(Gravity.CENTER);
            int padding = UIUtils.dp2Px(5);
            childView.setPadding(padding, padding, padding, padding);

            childView.setTextColor(Color.WHITE);

            //普通状态下的背景
            GradientDrawable normalBg = new GradientDrawable();
            normalBg.setCornerRadius(UIUtils.dp2Px(4));

            Random random = new Random();
            int alpha = 255;
            int red = random.nextInt(101) + 100;//100-200
            int green = random.nextInt(101) + 100;//100-200
            int blue = random.nextInt(101) + 100;//100-200
            int argb = Color.argb(alpha, red, green, blue);
            normalBg.setColor(argb);

            //按下去时候的背景
            GradientDrawable pressedBg = new GradientDrawable();
            pressedBg.setCornerRadius(UIUtils.dp2Px(4));
            pressedBg.setColor(Color.DKGRAY);


            StateListDrawable selectorBg = new StateListDrawable();
            selectorBg.addState(new int[]{-android.R.attr.state_pressed}, normalBg);
            selectorBg.addState(new int[]{android.R.attr.state_pressed}, pressedBg);

            childView.setBackgroundDrawable(selectorBg);

            childView.setClickable(true);

            flowLayout.addView(childView);

            childView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), data, Toast.LENGTH_SHORT).show();
                }
            });
        }
        scrollView.addView(flowLayout);
        return scrollView;
    }
}