package com.peng.holder;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.peng.adapter.HomePagerAdapter;
import com.peng.base.BaseHolder;
import com.peng.utils.MyApplication;
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
    //2.3 指示器容器
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
        //2.1 保存数据到成员变量
        mPictureUrls = pictureUrls;
        //2.2绑定viewPager
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(pictureUrls);
           mItemHomePicturePager.setAdapter(homePagerAdapter);
        //2.3 绑定mItemHomePictureContainerIndicator
        for (int i = 0; i < mPictureUrls.size() ; i++) {
            ImageView ivIndicator = new ImageView(UIUtils.getContext());
            ivIndicator.setImageResource(R.drawable.indicator_normal);
            //2.4 默认选中第一个
            if(i==0){
                ivIndicator.setImageResource(R.drawable.indicator_selected);
            }
            //2.5设置间距
            int width = UIUtils.dp2Px(5);//px---->dp
            int height = UIUtils.dp2Px(5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
            params.leftMargin = UIUtils.dp2Px(5);
            params.bottomMargin = UIUtils.dp2Px(5);
            mItemHomePictureContainerIndicator.addView(ivIndicator);
        }
        //3.监听轮播图的切换
        mItemHomePicturePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                //0.处理position
                position = position % mPictureUrls.size();
                //3.1 修改indicator的效果
                for (int i = 0; i < mPictureUrls.size() ; i++) {
                    ImageView ivIndicator = (ImageView) mItemHomePictureContainerIndicator.getChildAt(i);
                    //3.2 还原所有的孩子
                    ivIndicator.setImageResource(R.drawable.indicator_normal);
                    //3.2 选中应该选中的孩子
                    if(i == position){
                        ivIndicator.setImageResource(R.drawable.indicator_selected);
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        //4.左右无限轮播
        int initIndex = Integer.MAX_VALUE / 2;
        int diff = initIndex % mPictureUrls.size();
        initIndex = initIndex - diff;
        mItemHomePicturePager.setCurrentItem(initIndex);
        //5.3 自动轮播
        final AutoScrollTask autoScrollTask = new AutoScrollTask();
        autoScrollTask.start();
        //5.4 按下去的时候停止轮播
        mItemHomePicturePager.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        autoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        autoScrollTask.start();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
    //5.自动轮播的内部类
    class AutoScrollTask implements Runnable{
       //5.1 开始轮播
        public void start(){
            stop();
            MyApplication.getmHandler().postDelayed(this,3000);
        }
        //5.2 结束轮播
        public void stop(){
        MyApplication.getmHandler().removeCallbacks(this);
        }
        @Override
        public void run() {
        //5.1 切换
          int currentItem = mItemHomePicturePager.getCurrentItem();
          currentItem ++;
        //5.1 设置
          mItemHomePicturePager.setCurrentItem(currentItem);
        //5.1 再次开始
          start();
        }
    }
}
