package com.melon.myapp;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.melon.myapp.third.LoggerInterceptor;
import com.melon.mylibrary.util.LogUtils;
import com.squareup.leakcanary.LeakCanary;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;

/**
 * Created by melon on 2017/4/8.
 */

public class MyApplication extends Application {
    private static MyApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler());
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("Melon",true))
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

        mApplication = this;
    }

    public static Context getInstance() {
        return mApplication;
    }

    private class MyExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            record(ex);
            // 结束应用
            android.os.Process.killProcess(android.os.Process.myPid());

            LogUtils.e("uncaughtException: "+ex.getMessage());
        }

        private void record(Throwable ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);

            String str = sw.toString();
            try {
                StringBuffer sb = new StringBuffer();

                String dirPath = getExternalFilesDir("")+"";
                File file = new File(dirPath, "error.txt");
                FileOutputStream fos = new FileOutputStream(file, false);

                Field[] fields = Build.class.getDeclaredFields();
                for (Field field : fields) {
                    try {
                        String name = field.getName();
                        String value = field.get(null).toString();
                        sb.append(name + ":" + value + "\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                sb.append(str);

                PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                int versionCode = packageInfo.versionCode;
                String versionName = packageInfo.versionName;
                sb.append("\n\nVersionCode: " + versionCode + "\n");
                sb.append("VersionName: " + versionName + "\n");
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
                sb.append("ErrorTime: " + time + "\n\n\n\n\n");

                fos.write(sb.toString().getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
