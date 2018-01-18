package com.melon.mylibrary.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2016/5/17.
 */
public class CommonUtil {
    /**
     * 启动Activity
     *
     * @param ctx   上下文
     * @param clazz 要跳转页面
     */
    public static void enterActivity(Context ctx, Class<?> clazz) {
        Intent intent = new Intent(ctx, clazz);
        if (ctx instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        ctx.startActivity(intent);
    }

    /**
     * 启动Fragment
     *
     * @param ctx    上下文
     * @param target CommonFragmentActivity中定义的常量 目标fragment
     */
    public static void enterFragment(Context ctx, Class commonFragmentClazz, int target) {
        Intent intent = new Intent(ctx, commonFragmentClazz);
        intent.putExtra("target", target);
        if (ctx instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        ctx.startActivity(intent);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getScreenHeight(Context ctx) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static int getScreenWidth(Context ctx) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    //全屏
    public static void fullScreen(Activity act) {
        act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 空判断
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0 || "null".equalsIgnoreCase(str.toString()))
            return true;
        else
            return false;
    }

    /**
     * 计算图片高度
     */
    public static int getPicHeight(Context ctx, int img) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), img, options);
        return options.outHeight;
    }

    /**
     * 复制到剪切板
     */
    public static void addToClipboard(Context ctx, String value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager cmb2 = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb2.setText(value);
            ToastUtil.toast(ctx, "已复制");
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     * @param flag     true    隐藏
     */
    public static void hideInputMode(Activity activity, boolean flag) {
        if (flag) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } else {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view != null) {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }
    }

    /**
     * 时间截转日期、时间
     *
     * @param time 秒
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateTime(String time) {
        String date = "";
        try {
            date = new SimpleDateFormat("MM-dd-yyyy  HH:mm:ss").format(new Date(Long.parseLong(time) * 1000)); // * 1000
        } catch (Exception e) {
            if (!isEmpty(time)) {
                return time;
            }
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 友好时间
     *
     * @param time 秒
     * @return
     */
    public static String getMyDateFormat(String time) {
        String result = "未知";
        try {
            long dataTime = Long.parseLong(time) * 1000;

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
            String today = sdfDate.format(new Date());
            long zeroTime = sdfDate.parse(today).getTime(); // 今天0点，毫秒
            long dTime = zeroTime - dataTime; // 时间间隔
            if (dTime < 0) {
                result = "今天 " + sdfTime.format(dataTime);
            } else if (dTime < 86400000) {
                result = "昨天 " + sdfTime.format(dataTime);
            } else if (dTime < 86400000 * 6) {
                int day = (int) (dTime / 86400000);
                result = (day + 1) + "天前";
            } else if (dTime < 86400000 * 13) {
                result = "1周前";
            } else if (dTime < 86400000 * 20) {
                result = "2周前";
            } else if (dTime < 86400000 * 27L) {
                result = "3周前";
//            } else {
//                result = "更早";
            } else {
                result = getDateTime(time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void setTransparentStateBar(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = act.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            act.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //系统下载文件
    public static void downloadFile(Context ctx, String url) {
        int start = url.lastIndexOf("/");
        String fileName = url.substring(start+1);

        DownloadManager downloadManager = ((DownloadManager) ctx.getSystemService(Activity.DOWNLOAD_SERVICE));
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 在通知栏中显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);//文件存储路径 绝对路径
        request.setTitle("正在下载 "+fileName);
        //下载时在通知栏显示的文字
        downloadManager.enqueue(request);//执行下载
    }
}
