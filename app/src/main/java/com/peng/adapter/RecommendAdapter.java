package com.peng.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.peng.utils.UIUtils;
import com.peng.views.flyinout.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * Created by peng on 2017/11/12.
 */

public class RecommendAdapter implements StellarMap.Adapter {
    List<String> mDatas;
    public RecommendAdapter(List<String> datas){
        this.mDatas = datas;
    }
    //1.1 自己定义每页显示多少条
    private static final int PAGESIZE = 15;
    //1.得到一共多少组 size=32
    @Override
    public int getGroupCount() {
        if (mDatas.size() % PAGESIZE == 0){
            return mDatas.size() / PAGESIZE;
        }else{
            return mDatas.size() / PAGESIZE + 1;
        }
    }
    //2.每组多少个 比如 size=32---> 15 15 余2
    @Override
    public int getCount(int group) {
        if (mDatas.size() % PAGESIZE ==0){
            return PAGESIZE;
        }else{
            //2.1 考虑最后一组的余数情况
            if (group == getGroupCount() -1){
                return mDatas.size() % PAGESIZE;
            }else{
                return  PAGESIZE;
            }
        }
    }

    @Override
    public View getView(int group, int position, View convertView) {
        //3.1 data
        int index = group * PAGESIZE + position;
        String data = mDatas.get(index);
        //3.2 view
        TextView tv = new TextView(UIUtils.getContext());
        //随机大小
        Random random = new Random();
        int randomSize = random.nextInt(5) + 14;
        tv.setTextSize(randomSize);
        //随机颜色
        int alpha = 255;//0-255
        int red = random.nextInt(121) + 100; //100-220
        int green = random.nextInt(121) + 100; //100-220
        int blue = random.nextInt(121) + 100; //100-220
        int color = Color.argb(alpha,red,green,blue);
        tv.setTextColor(color);
        //3.3 data+view
        tv.setText(data);
        return tv;
    }

    @Override
    public int getNextGroupOnPan(int group, float degree) {
        return 0;
    }

    @Override
    public int getNextGroupOnZoom(int group, boolean isZoomIn) {
        return 0;
    }
}
