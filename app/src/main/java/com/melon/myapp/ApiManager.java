package com.melon.myapp;

import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.SpUtil;

/**
 * 接口地址管理
 * Created by melon on 2017/10/24.
 * Email 530274554@qq.com
 */

public class ApiManager {
    public static final String API_PROTOCOL = "http://";
    public static final String API_IP = "xiazhilu.top";
    public static final String API_PORT = "2020";
    public static String API_BASE = getApiBase()+"/";

    public static final String API_NOTE_ADD = API_BASE + "note/addNote";
    public static final String API_NOTE_DEL = API_BASE + "note/delNote";
    public static final String API_NOTE_UPDATE = API_BASE + "note/updateNote";
    public static final String API_NOTE_QUERY = API_BASE + "note/queryNote";
    public static final String API_NOTE_ALL = API_BASE + "note/queryAllNote";

    public static String getApiBase() {
        String host = SpUtil.getString(MyApplication.getInstance(),"api_host");
        if(CommonUtil.isEmpty(host)){
            return API_PROTOCOL+API_IP+":"+API_PORT;
        } else {
            return API_PROTOCOL+host;
        }
    }

    public static String getApiHost() {
        String host = SpUtil.getString(MyApplication.getInstance(),"api_host");
        if(CommonUtil.isEmpty(host)){
            return API_IP+":"+API_PORT;
        } else {
            return host;
        }
    }

    public static String getApiIp() {
        String host = SpUtil.getString(MyApplication.getInstance(),"api_host");
        if(!CommonUtil.isEmpty(host)){
            String[] split = host.split(":");
            return split[0];
        }
        return API_IP;
    }

    public static String getApiPort() {
        String host = SpUtil.getString(MyApplication.getInstance(),"api_host");
        if(!CommonUtil.isEmpty(host)){
            String[] split = host.split(":");
            return split[1];
        }
        return API_PORT;
    }
}
