package com.peng.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.peng.utils.LogUtils;
import com.peng.utils.UIUtils;

/**
 * Created by peng on 2017/11/12.
 */

public class RatioFrameLayout extends FrameLayout {
    //0.图片的宽高比
    public float mPicRatio = 2.43f;

    public RatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioFrameLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
         /**
        UNSPECIFIED:不确定 wrap_content
        EXACTLY:确定的 match_parent 100dp 100px
        AT_MOST:至多
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY){
            //1.得到自身的宽度
            int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
            //2.动态计算自身的高度：图片的宽高比==自身的宽/自身的高度
            int selfHeight = (int) (selfWidth/mPicRatio + .5f);
            LogUtils.s("高度"+ UIUtils.px2Dp(selfHeight) + "dp");
            //3.保存测绘结果
            setMeasuredDimension(selfWidth,selfHeight);
            //4.计算孩子的宽度和高度
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingBottom() - getPaddingTop();
            //5.作为容器，还需要测绘孩子
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec,childHeightMeasureSpec);
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}