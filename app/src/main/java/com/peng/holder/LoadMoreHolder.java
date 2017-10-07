package com.peng.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peng.base.BaseHolder;
import com.peng.utils.UIUtils;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;

/**
 * Created by peng on 2017/10/3.
 */
public class LoadMoreHolder extends BaseHolder<Integer> {
    public static final int LOADMORE_LOADING = 0;//显示正在加载更多
    public static final int LOADMORE_ERROR = 1;//显示加载更多失败,点击重试
    public static final int LOADMORE_NONE = 2;//没有加载更多的情况
    @Bind(R.id.item_loadmore_container_loading)
    LinearLayout mItemLoadmoreContainerLoading;
    @Bind(R.id.item_loadmore_tv_retry)
    TextView mItemLoadmoreTvRetry;
    @Bind(R.id.item_loadmore_container_retry)
    LinearLayout mItemLoadmoreContainerRetry;


    //决定LoadMoreHolder所能提供的视图是啥，以及找出孩子
    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);
        return holderView;
    }

    //进行数据和视图的绑定
    @Override
    public void refreshHolderView(Integer state) {
    mItemLoadmoreContainerLoading.setVisibility(View.GONE);
    mItemLoadmoreContainerRetry.setVisibility(View.GONE);
        switch (state){
            case LOADMORE_ERROR:
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_LOADING:
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_NONE:

                break;

            default:
                break;
        }
    }
}
