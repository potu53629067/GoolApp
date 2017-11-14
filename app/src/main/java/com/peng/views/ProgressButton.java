package com.peng.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by peng on 2017/11/14.
 */

@SuppressLint("AppCompatCustomView")
public class ProgressButton extends Button {
    private long mMax = 100;//进度条最大值
    private long mProcess = 25; //当前进度值
    private boolean isProgressEnable = true;//是否允许进度

    public void setMax(long max) {
        mMax = max;
    }

    public void setProcess(long process) {
        mProcess = process;
    }

    public void setProgressEnable(boolean progressEnable) {
        isProgressEnable = progressEnable;
    }
    public ProgressButton(Context context) {
        super(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //1.在这里主要是拓展绘制操作
    @Override
    protected void onDraw(Canvas canvas) {
     if(isProgressEnable) {
         //1.1 之前
         //canvas.drawText("haha",20,20,getPaint());
         Drawable progressBg = new ColorDrawable(Color.BLUE);
         //指定绘制范围
         int left = 0;
         int top = 0;
         int right = (int) (getMeasuredWidth() * mProcess * 1.0f /mMax+.5f);//进度条宽度动态计算
         int bottom = getBottom();
         progressBg.setBounds(left, top, right, bottom);
         progressBg.draw(canvas);
     }
        super.onDraw(canvas);//默认
        //1.2 之后


    }
}
