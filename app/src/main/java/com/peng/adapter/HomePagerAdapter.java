package com.peng.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.peng.conf.Constants;
import com.peng.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peng on 2017/11/11.
 */

public class HomePagerAdapter extends PagerAdapter{
    List<String> pictureUrl = new ArrayList<>();
    public HomePagerAdapter(List<String> pictureUrl){
        this.pictureUrl = pictureUrl;
    }
    @Override
    public int getCount() {
        if (pictureUrl != null){
            //return pictureUrl.size();
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //0.处理postion
        position = position % pictureUrl.size();
        //1.data
        String url = pictureUrl.get(position);
        //2.view
        ImageView iv = new ImageView(UIUtils.getContext());
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        //3.data+view
        Picasso.with(UIUtils.getContext()).load(Constants.URL.IMAGBURL+url).into(iv);
        //4.加入容器
        container.addView(iv);
        return iv;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View) object);
    }
}
