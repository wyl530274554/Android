package com.melon.mylibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {
    /**
     * 一般性配置	账号退出即消除
     */
    private static final String COMMON = "config";

    /**
     * 账号退出不会消除
     */
    public static final String NOT_CLEAR = "not_clear";

    /**
     * 收藏的网坛
     */
    public static final String COLLECT_WEB = "collect_web";


    /**
     * 默认sp
     */
    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(COMMON, Context.MODE_PRIVATE);
    }

    /**
     * 获取其它sp
     *
     * @param name sp配置文件名称
     */
    public static SharedPreferences getSharedPreference(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static void setInt(Context context, String name, String key, int value) {
        getSharedPreference(context, name).edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String name, String key, int defValue) {
        return getSharedPreference(context, name).getInt(key, defValue);
    }

    public static void setLong(Context context, String name, String key, long value) {
        getSharedPreference(context, name).edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String name, String key, long defValue) {
        return getSharedPreference(context, name).getLong(key, defValue);
    }

    public static void setString(Context context, String key, String value) {
        getSharedPreference(context).edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key) {
        return getSharedPreference(context).getString(key, "");
    }

    public static void setBoolean(Context context, String key, boolean value) {
        getSharedPreference(context).edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key) {
        return getSharedPreference(context).getBoolean(key, false);
    }

    public static void remove(Context context, String key) {
        getSharedPreference(context).edit().remove(key).commit();
    }

}
