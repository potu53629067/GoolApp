package com.peng.protocol;

import com.google.gson.Gson;
import com.peng.base.BaseProtocol;
import com.peng.bean.CategoryBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peng on 2017/11/12.
 */

public class CategoryProtocol extends BaseProtocol<List<CategoryBean>> {
    @Override
    public String getInterfaceKey() {
        return "category";
    }

    @Override
    public List<CategoryBean> parseJson(String resJsonString, Gson gson) {
        //1.结点解析
        List<CategoryBean> result = new ArrayList<>();
        try {
            JSONArray rootJsonArray = new JSONArray(resJsonString);
            //1.1 遍历rootJsonArray
            for (int i = 0; i < rootJsonArray.length();i++) {
                JSONObject itemJsonObject = rootJsonArray.getJSONObject(i);
                String title = itemJsonObject.getString("title");

                CategoryBean titleCategoryBean = new CategoryBean();
                titleCategoryBean.title = title;
                titleCategoryBean.isTitle = true;

                //1.2 加入结果集合
                result.add(titleCategoryBean);

                JSONArray infosJsonArray = itemJsonObject.getJSONArray("infos");
                for (int j = 0; j < infosJsonArray.length() ; j++) {
                    JSONObject infoJsonObject = infosJsonArray.getJSONObject(j);
                    String name1 = infoJsonObject.getString("name1");
                    String name2 = infoJsonObject.getString("name2");
                    String name3 = infoJsonObject.getString("name3");

                    String url1 = infoJsonObject.getString("url1");
                    String url2 = infoJsonObject.getString("url2");
                    String url3 = infoJsonObject.getString("url3");

                    CategoryBean infoCategoryBean = new CategoryBean();
                    infoCategoryBean.name1 = name1;
                    infoCategoryBean.name2 = name2;
                    infoCategoryBean.name3 = name3;
                    infoCategoryBean.url1 = url1;
                    infoCategoryBean.url2 = url2;
                    infoCategoryBean.url3 = url3;
                    //1.3 加入结果集合
                    result.add(infoCategoryBean);
                }
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
