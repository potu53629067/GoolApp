package com.peng.utils;

import com.peng.controller.LoadingPager;

import java.util.List;
import java.util.Map;

/**
 * Created by peng on 2017/11/13.
 */

public class CheckDataUtils {
    /**
     * 校验请求回来的数据,返回对应的状态
     */
    public static LoadingPager.LoadDataResult checkResData(Object resData) {
        if (resData == null) {
            return LoadingPager.LoadDataResult.EMPTY;
        }
        //resData 是集合==>List
        if (resData instanceof List) {
            if (((List) resData).size() == 0) {
                return LoadingPager.LoadDataResult.EMPTY;
            }
        }
        //resData 是集合==>Map
        if (resData instanceof Map) {
            if (((Map) resData).size() == 0) {
                return LoadingPager.LoadDataResult.EMPTY;
            }
        }
        return LoadingPager.LoadDataResult.SUCCESS;
    }
}
