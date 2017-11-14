package com.peng.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;

/**
 * Created by peng on 2017/11/14.
 */

public class ProgressView extends LinearLayout {
    @Bind(R.id.iv_icon)
    ImageView mIvIcon;
    @Bind(R.id.tv_note)
    TextView mTvNote;
    private boolean isProgressEnable = true; //是否允许进度
    private long mMax = 100;    //进度条最大值
    private long mProgress;    //当前进度值
    private RectF mOval;
    private Paint mPaint;

    public void setProgressEnable(boolean progressEnable) {
        isProgressEnable = progressEnable;
    }

    public void setMax(long max) {
        mMax = max;
    }

    public void setProgress(long progress) {
        mProgress = progress;
        //更新绘制
        invalidate();
    }

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //1.挂载布局
        View view = View.inflate(context, R.layout.inflate_progress, this);

    }
    //2.设置图标
    public void setIcon(int resId){
        mIvIcon.setImageResource(resId);
    }
    //3.设置文本
    public void setNote(String content){
        mTvNote.setText(content);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//4.绘制背景--->透明背景
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas); //5.绘制图标和文本
        if(isProgressEnable) {
            int left = mIvIcon.getLeft();
            int top = mIvIcon.getTop();
            int right = mIvIcon.getRight();
            int bottom = mIvIcon.getBottom();
            if (mOval == null){
                mOval = new RectF(left, top, right, bottom);
                }
            //起始角度
            float startAngle = -90;
            //扫过的角度
            float sweepAngle = mProgress * 360f/mMax;
            boolean useCenter = false;
            if (mPaint == null) {
                mPaint = new Paint();
                //属性设置
                mPaint.setColor(Color.BLUE);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(3);
                mPaint.setAntiAlias(true);
            }
            canvas.drawArc(mOval, startAngle, sweepAngle, useCenter, mPaint);
        }
    }
}
