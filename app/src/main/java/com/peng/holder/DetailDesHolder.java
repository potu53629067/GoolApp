package com.peng.holder;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.peng.base.BaseHolder;
import com.peng.bean.DetailBean;
import com.peng.utils.UIUtils;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;

/**
 * Created by peng on 2017/11/13.
 */

public class DetailDesHolder extends BaseHolder<DetailBean> implements View.OnClickListener {
    @Bind(R.id.app_detail_des_tv_des)
    TextView mAppDetailDesTvDes;
    @Bind(R.id.app_detail_des_tv_author)
    TextView mAppDetailDesTvAuthor;
    @Bind(R.id.app_detail_des_iv_arrow)
    ImageView mAppDetailDesIvArrow;
    //3.1 折叠还是展开动画的标记
    private boolean isOpen = true;
    //高度取一次
    private int mTvDesMeasuredHeight;
    //赋值到成员变量
    private DetailBean mData;

    //1.填充视图，找到控件
    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_des, null);
        holderView.setOnClickListener(this);
        return holderView;
    }
    //2.data+view
    @Override
    public void refreshHolderView(DetailBean data) {
        //3.4 保存数据到成员变量
        mData = data;
        mAppDetailDesTvAuthor.setText(data.author);
        mAppDetailDesTvDes.setText(data.des);
        //4.一进来详情页面是拿不到高度的,如果测量是有局限性,针对TextView需要告知具体的行高之后才可以通过measure(0,0)方法测量到具体高度
        //4.1 想提前得到下面描述部分容器的高度,需要测量一下,用观察者
        mAppDetailDesTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //默认折叠
                changeTvDesHeight(false);
                mAppDetailDesTvDes.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
    //3.动画的点击事件
    @Override
    public void onClick(View v) {
        //3.10 默认折叠动画，抽取方法
        changeTvDesHeight(true);

    }
    //3.10 默认折叠动画
    private void changeTvDesHeight(boolean isAnimation) {
        if (mTvDesMeasuredHeight == 0){
            mTvDesMeasuredHeight = mAppDetailDesTvDes.getMeasuredHeight();
        }
        //3.1 详情内容的高度取一次就行
        if (mTvDesMeasuredHeight == 0){
            mTvDesMeasuredHeight = mAppDetailDesTvDes.getMeasuredHeight();
        }
        if (isOpen){
            //3.2 折叠
            int start = mTvDesMeasuredHeight;
            int end = getTextViewHeight(7,mData.des);
            if (isAnimation){
            //3.6 抽取折叠展开动画
            doAinmatio(start,end);
            }else {
                //3.11 直接修改高度
                mAppDetailDesTvDes.setHeight(end);
            }
        }else {
            //3.3 展开
            int start = getTextViewHeight(7,mData.des);
            int end = mTvDesMeasuredHeight;
            doAinmatio(start,end);
        }
        isOpen = !isOpen;
    }

    //3.6 抽取折叠展开动画
    private void doAinmatio(int start, int end) {
        ObjectAnimator heightAnimator = ObjectAnimator.ofInt(mAppDetailDesTvDes, "height", start, end);
        heightAnimator.start();
        //3.7 箭头跟着旋转
        if (isOpen){
            ObjectAnimator.ofFloat(mAppDetailDesIvArrow,"rotation",0,180).start();
        }else{
            ObjectAnimator.ofFloat(mAppDetailDesIvArrow,"rotation",180,0).start();
        }
        //3.8 监听动画的执行完成
        heightAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //3.9 找到外层scrollView进行滚动
                ViewParent parent = mAppDetailDesTvDes.getParent();//第一层
                while (true){
                    parent = parent.getParent();//第二层
                    if(parent == null){//无限循环找到最上层
                        break;
                    }
                    if (parent instanceof ScrollView){
                        ((ScrollView)parent).fullScroll(View.FOCUS_DOWN);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    //3.5 拿到折叠的end高度
    private int getTextViewHeight(int lines, String content) {
        TextView tempTextView = new TextView(UIUtils.getContext());
        tempTextView.setText(content);
        //设置多少行--->重要操作
        tempTextView.setLines(lines);
        //测量
        tempTextView.measure(0,0);
        //重新得到高度
        int measuredHeight = tempTextView.getMeasuredHeight();
        return  measuredHeight;
    }

}
