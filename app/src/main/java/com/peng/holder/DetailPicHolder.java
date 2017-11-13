package com.peng.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.peng.base.BaseHolder;
import com.peng.bean.DetailBean;
import com.peng.conf.Constants;
import com.peng.utils.UIUtils;
import com.peng.views.RatioFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;

/**
 * Created by peng on 2017/11/13.
 */

public class DetailPicHolder extends BaseHolder<DetailBean> {
    @Bind(R.id.app_detail_pic_iv_container)
    LinearLayout mAppDetailPicIvContainer;

    //1.view：填充，找出控件
    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_pic, null);
        return holderView;
    }
    //2.1 data+view
    @Override
    public void refreshHolderView(DetailBean data) {
        //2.1 data
        List<String> scrennUrls = data.screen;
        for (int i = 0;i<scrennUrls.size();i++){
            String url = scrennUrls.get(i);
            //2.2 view
            RatioFrameLayout ratioFrameLayout = new RatioFrameLayout(UIUtils.getContext());
            //属性设置
            ratioFrameLayout.setPicRatio((float)150/250);
            ratioFrameLayout.setCurRelative(RatioFrameLayout.RELATIVE_WIDTH);
            ImageView ivPic = new ImageView(UIUtils.getContext());
            ratioFrameLayout.addView(ivPic);
            //2.3 加载图片
            Picasso.with(UIUtils.getContext()).load(Constants.URL.IMAGBURL + url).into(ivPic);
            //定义LayoutParams
            int screenWidth = UIUtils.getResources().getDisplayMetrics().widthPixels;
            screenWidth = screenWidth - UIUtils.dp2Px(18); //减去间距的计算
            int width = screenWidth / 3;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
            //第一张图片不需要间距
            if (i != 0){
                params.leftMargin = UIUtils.px2Dp(5);
            }
            //2.4 加入容器
            mAppDetailPicIvContainer.addView(ratioFrameLayout,params);
        }
    }
}
