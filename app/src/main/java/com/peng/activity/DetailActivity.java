package com.peng.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.peng.base.BaseActivity;
import com.peng.base.ItemAdapter;
import com.peng.bean.DetailBean;
import com.peng.controller.LoadingPager;
import com.peng.protocol.DetailProtocol;
import com.peng.utils.UIUtils;

import java.io.IOException;


/**
 * Created by peng on 2017/11/12.
 */

public class DetailActivity extends BaseActivity{
    private String mPackageName;
    private DetailBean mDetailBean;

    //1.加载成功视图-->并数据和视图进行绑定
    @Override
    public View initSuccessView() {
        //这里模拟数据
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(mDetailBean.toString());
        tv.setTextColor(Color.RED);
        return tv;
    }
    //2.在子线程中加载数据的数据
    @Override
    public LoadingPager.LoadDataResult onInitData() {
        //2.1 创建DetailPotocol
        DetailProtocol protocol = new DetailProtocol(mPackageName);
        try {
            mDetailBean = protocol.loadData(0);
            return checkResData(mDetailBean);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadDataResult.ERROR;
        }
    }
    //3.接收参数
    @Override
    public void init() {
        mPackageName = getIntent().getStringExtra(ItemAdapter.PACKAGE_NAME);
        super.init();
    }
}
