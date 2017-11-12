package com.peng.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.peng.base.BaseHolder;
import com.peng.bean.CategoryBean;
import com.peng.conf.Constants;
import com.peng.utils.UIUtils;
import com.squareup.picasso.Picasso;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;

/**
 * Created by peng on 2017/11/12.
 */

public class CategoryNormalHolder extends BaseHolder<CategoryBean> {
    @Bind(R.id.item_category_icon_1)
    ImageView mItemCategoryIcon1;
    @Bind(R.id.item_category_name_1)
    TextView mItemCategoryName1;
    @Bind(R.id.item_category_item_1)
    LinearLayout mItemCategoryItem1;
    @Bind(R.id.item_category_icon_2)
    ImageView mItemCategoryIcon2;
    @Bind(R.id.item_category_name_2)
    TextView mItemCategoryName2;
    @Bind(R.id.item_category_item_2)
    LinearLayout mItemCategoryItem2;
    @Bind(R.id.item_category_icon_3)
    ImageView mItemCategoryIcon3;
    @Bind(R.id.item_category_name_3)
    TextView mItemCategoryName3;
    @Bind(R.id.item_category_item_3)
    LinearLayout mItemCategoryItem3;
    //1.填充布局
    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_category_normal, null);
        return holderView;
    }
    //2.数据和视图的绑定
    @Override
    public void refreshHolderView(CategoryBean data) {
    /*mItemCategoryName1.setText(data.name1);
    mItemCategoryName2.setText(data.name2);
    mItemCategoryName3.setText(data.name3);
    Picasso.with(UIUtils.getContext()).load(Constants.URL.IMAGBURL+data.url1).into(mItemCategoryIcon1);
    Picasso.with(UIUtils.getContext()).load(Constants.URL.IMAGBURL+data.url2).into(mItemCategoryIcon2);
    Picasso.with(UIUtils.getContext()).load(Constants.URL.IMAGBURL+data.url3).into(mItemCategoryIcon3);*/
    setData(data.name1,data.url1,mItemCategoryName1,mItemCategoryIcon1);
    setData(data.name2,data.url2,mItemCategoryName2,mItemCategoryIcon2);
    setData(data.name3,data.url3,mItemCategoryName3,mItemCategoryIcon3);
    }
    //3.数据视图的绑定做个封装---->1.这样抽取的好处?如果name和url没有回来数据的情况下,把布局空白小方块给隐藏起来：在抽取的setData方法里就可以统一处理此逻辑
    private void setData(final String name, String url, TextView tvName, ImageView ivIcon) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(url)){
            ViewParent parent = tvName.getParent();
            ((ViewGroup)parent).setVisibility(View.INVISIBLE);
        }else{
            ViewParent parent = tvName.getParent();
            ((ViewGroup)parent).setVisibility(View.VISIBLE);

            tvName.setText(name);
            Picasso.with(UIUtils.getContext()).load(Constants.URL.IMAGBURL+url).into(ivIcon);

            //3.1 设置点击事件
            ((ViewGroup)parent).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(),name,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
