package com.melon.myapp;

import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.SpUtil;

/**
 * 接口地址管理
 * @author melon.wang
 * @date 2019/1/7 17:28
 * Email 530274554@qq.com
 */
public class ApiManager {
    private static final String API_PROTOCOL = "http://";
    private static final String API_IP = "host";
    private static final String API_PORT = "80";
    public static String API_BASE = getApiBase()+"/";


    public static final String API_NOTE_ADD = API_BASE + "note/addNote";
    public static final String API_NOTE_DEL = API_BASE + "note/delNote";
    public static final String API_NOTE_UPDATE = API_BASE + "note/updateNote";
    public static final String API_NOTE_QUERY = API_BASE + "note/queryNote";
    public static final String API_NOTE_ALL = API_BASE + "note/queryAllNote";

    public static final String API_PASSWORD_ADD = API_BASE + "index/addPassword";
    public static final String API_PASSWORD_UPDATE = API_BASE + "index/updatePassword";
    public static final String API_PASSWORD_ALL = API_BASE + "index/queryAllPassword";

    public static final String API_GET_LOCAL_IP = API_BASE + "index/getLocalIp";

    private static String getApiBase() {
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
