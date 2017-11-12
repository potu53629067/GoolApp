package com.peng.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.peng.utils.LogUtils;
import com.peng.utils.UIUtils;

import appmark.peng.com.myapplication.R;

/**
 * Created by peng on 2017/11/12.
 */

public class RatioFrameLayout extends FrameLayout {
    public static final int RELATIVE_WIDTH = 0;//2.1 已知宽度，动态计算高度
    public static final int RELATIVE_HEIGHT = 1;//2.2 已知高度，动态计算宽度

    //2.3 自定义属性:相对于谁计算？
    public int mCurRelative;
    //0.自定义属性:图片的宽高比
    public float mPicRatio;

    public int getCurRelative() {
        return mCurRelative;
    }

    public void setCurRelative(int curRelative) {
        mCurRelative = curRelative;
    }

    public float getPicRatio() {
        return mPicRatio;
    }

    public void setPicRatio(float picRatio) {
        mPicRatio = picRatio;
    }

    public RatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //2.取出图片宽高比的自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioFrameLayout);
        mPicRatio = typedArray.getFloat(R.styleable.RatioFrameLayout_picRatio,1);
        //2.5 取出根据宽或高枚举类型属性
        mCurRelative = (int) typedArray.getFloat(R.styleable.RatioFrameLayout_relative,0);
        typedArray.recycle();
    }

    public RatioFrameLayout(Context context) {
        this(context,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
         /**
        UNSPECIFIED:不确定 wrap_content
        EXACTLY:确定的 match_parent 100dp 100px
        AT_MOST:至多
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY && mCurRelative == RELATIVE_WIDTH){//2.4 已知宽度，动态计算高度
            //1.得到自身的宽度
            int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
            //1.1 动态计算自身的高度：图片的宽高比==自身的宽/自身的高度
            int selfHeight = (int) (selfWidth/mPicRatio + .5f);
            LogUtils.s("高度"+ UIUtils.px2Dp(selfHeight) + "dp");
            //1.2 保存测绘结果
            setMeasuredDimension(selfWidth,selfHeight);
            //1.3 计算孩子的宽度和高度
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingBottom() - getPaddingTop();
            //1.4 作为容器，还需要测绘孩子
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec,childHeightMeasureSpec);
        }else if (heightMode == MeasureSpec.EXACTLY && mCurRelative == RELATIVE_HEIGHT) {//2.4 已知高度，动态计算宽度
            //得到自身的高度
            int selfHeight = MeasureSpec.getSize(heightMeasureSpec);

            //动态计算自身的宽度
            //图片的宽高比==自身的宽/自身的高度
            int selfWidth = (int) (selfHeight * mPicRatio + .5f);

            //保存测绘结果
            setMeasuredDimension(selfWidth, selfHeight);

            //计算孩子的宽度和高度
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingBottom() - getPaddingTop();

            //作为容器,还需要测绘孩子
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
        } else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}