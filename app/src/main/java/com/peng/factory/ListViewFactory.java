package com.peng.factory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import com.peng.utils.UIUtils;

/**
 * Created by peng on 2017/10/3.
 */
public class ListViewFactory {

    public static ListView createListView() {
        ListView listView = new ListView(UIUtils.getContext());
        //统一属性设置
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        listView.setFastScrollEnabled(true);
        return listView;
    }
}
