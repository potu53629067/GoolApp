package com.peng.holder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peng.base.BaseHolder;
import com.peng.bean.DetailBean;
import com.peng.conf.Constants;
import com.peng.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;

/**
 * Created by peng on 2017/11/13.
 */

public class DetailSafeHolder extends BaseHolder<DetailBean> implements View.OnClickListener {
    @Bind(R.id.app_detail_safe_iv_arrow)
    ImageView mAppDetailSafeIvArrow;
    @Bind(R.id.app_detail_safe_pic_container)
    LinearLayout mAppDetailSafePicContainer;
    @Bind(R.id.app_detail_safe_des_container)
    LinearLayout mAppDetailSafeDesContainer;

    //1.2 折叠动画设置flag标记：表示2种情况
    private boolean isOpen = true;//是否打开
    //1.4 动画测量的高度，保证只初始化一次
    private int mMeasuredHeight;

    //1.填充布局,找出控件对象
    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_safe, null);
        //1.1 折叠动画，为整个容器设置点击事件
        holderView.setOnClickListener(this);
        return holderView;
    }
    //2.data+view
    @Override
    public void refreshHolderView(DetailBean data) {
        //等于说此模块是由2个容器组成的
        List<DetailBean.ItemSafeBean> itemSafeBeans = data.safe;
        for(DetailBean.ItemSafeBean itemSafeBean : itemSafeBeans){
            String safeDes = itemSafeBean.safeDes;
            int safeDesColor = itemSafeBean.safeDesColor;
            String safeDesUrl = itemSafeBean.safeDesUrl;
            String safeUrl = itemSafeBean.safeUrl;
            //2.1 绑定mAppDetailSafePicContainer容器的数据(是否安全和广告图标容器)
            ImageView ivSafeIcon = new ImageView(UIUtils.getContext());
            Picasso.with(UIUtils.getContext()).load(Constants.URL.IMAGBURL + safeUrl).into(ivSafeIcon);

            mAppDetailSafePicContainer.addView(ivSafeIcon);

            //2.2 绑定mAppDetailSafeDesContainer容器的数据(是否安全和广告的描述容器)
            LinearLayout line = new LinearLayout(UIUtils.getContext());
            int padding = UIUtils.dp2Px(3);
            line.setPadding(padding,padding,padding,padding);
            line.setGravity(Gravity.CENTER_VERTICAL);

            //定义孩子
            ImageView ivSafeDesIcon = new ImageView(UIUtils.getContext());
            TextView tvSafeDesNote = new TextView(UIUtils.getContext());

            tvSafeDesNote.setTextSize(UIUtils.sp2px(20));//px
            tvSafeDesNote.setSingleLine(true);

            //设置数据
            tvSafeDesNote.setText(safeDes);
            if (safeDesColor == 0){//正常的
                tvSafeDesNote.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
            }else{//警告色
                tvSafeDesNote.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
            }
            Picasso.with(UIUtils.getContext()).load(Constants.URL.IMAGBURL + safeDesUrl).into(ivSafeDesIcon);

            //加入到孩子
            line.addView(ivSafeDesIcon);
            line.addView(tvSafeDesNote);
            mAppDetailSafeDesContainer.addView(line);
        }
        //3.2 进入页面的时候，默认折叠
        changeDesContainerHeight(false);
    }
    //1.1 动画的点击事件
    @Override
    public void onClick(View v) {
        //3.1 为了让DetailSafeHolder模块默认是折叠动画:把点击事件里的折叠动画代码抽取方法changeDesContainerHeight();
        changeDesContainerHeight(true);
    }
    //3.2 进入页面的时候希望他不要有动画,点击的时候要有动画，所以设置一个参数isAnimation(标记)
    private void changeDesContainerHeight(boolean isAnimation) {
        //3.3 提前得到下面描述容器的高度,需要测量一下(不然进去点击箭头没有折叠展开动画)
        if (mMeasuredHeight == 0){
            mAppDetailSafeDesContainer.measure(0,0);
            mMeasuredHeight = mAppDetailSafeDesContainer.getMeasuredHeight();
        }
        //1.3 执行动画
        if (isOpen){ //折叠
        //1.4 折叠 mAppDetailSafeDesContainer高度-->从应有高度-->到0
        //应有高度
         mMeasuredHeight = mAppDetailSafeDesContainer.getMeasuredHeight();
        int start = mMeasuredHeight;
        int end = 0;
            //3.2 进入页面为false默认就是折叠
            if (isAnimation) {
                doAnimation(start, end);
             }else{//直接修改高度
                ViewGroup.LayoutParams curLayoutParams = mAppDetailSafeDesContainer.getLayoutParams();
                curLayoutParams.height = end;
                //重新设置
                mAppDetailSafeDesContainer.setLayoutParams(curLayoutParams);
            }
        }else {//展开
        //1.8 展开mAppDetailSafeDesContainer高度 从 0-->应有高度
            int start = 0;
            int end = mMeasuredHeight;//保证只测量一次(只初始化一次)
            //3.2
            if (isAnimation) {
                doAnimation(start, end);
            } else {//直接修改高度
                ViewGroup.LayoutParams curLayoutParams = mAppDetailSafeDesContainer.getLayoutParams();
                curLayoutParams.height = end;
                //重新设置
                mAppDetailSafeDesContainer.setLayoutParams(curLayoutParams);
            }
        }
        //1.9 需要取反
        isOpen = !isOpen;
    }

    private void doAnimation(int start, int end) {
        ValueAnimator heightAnimator = ValueAnimator.ofInt(start,end);
        heightAnimator.start();

        //1.5 然后得到动画执行过程中的值
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int tempHeight = (int) valueAnimator.getAnimatedValue();
            //1.6 然后通过layoutParams修改高度（得到临时高度）
                ViewGroup.LayoutParams curLayoutParams = mAppDetailSafeDesContainer.getLayoutParams();
                curLayoutParams.height = tempHeight;
            //1.7 然后容器重新设置高度
                mAppDetailSafeDesContainer.setLayoutParams(curLayoutParams);
            }
        });
        //3.箭头旋转动画，跟着折叠动画旋转
        if(isOpen){
            ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 180, 0).start();
        } else {
            ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 0, 180).start();
        }
    }
}
