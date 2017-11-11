package com.peng.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.peng.adapter.HomePagerAdapter;
import com.peng.base.BaseHolder;
import com.peng.utils.UIUtils;

import java.util.List;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;

/**
 * Created by peng on 2017/11/11.
 */

public class HomePictureHolder extends BaseHolder<List<String>> {
    List<String> mPictureUrls;

    @Bind(R.id.item_home_picture_pager)
    android.support.v4.view.ViewPager mItemHomePicturePager;
    @Bind(R.id.item_home_picture_container_indicator)
    LinearLayout mItemHomePictureContainerIndicator;

    //1.决定HomePictureHolder提供的视图是啥
    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_home_picture, null);
        return holderView;
    }

    //2.数据和视图绑定的方法,传过来的数据是图片的url
    @Override
    public void refreshHolderView(List<String> pictureUrls) {
        mPictureUrls = pictureUrls;
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(pictureUrls);
           mItemHomePicturePager.setAdapter(homePagerAdapter);
    }
}
