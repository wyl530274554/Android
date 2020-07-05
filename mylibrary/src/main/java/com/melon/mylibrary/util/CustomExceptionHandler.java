package com.melon.mylibrary.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;

    public CustomExceptionHandler(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LogUtils.e("uncaughtException: " + ex.getMessage());
        record(ex);
        // 结束应用
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 记录到文件中
     */
    private void record(Throwable ex) {
        FileOutputStream fos = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);

            String str = sw.toString();
            StringBuilder sb = new StringBuilder();

            String dirPath = mContext.getExternalFilesDir("") + "";
            File file = new File(dirPath, "error.txt");
            fos = new FileOutputStream(file, false);

            sb.append(SystemUtils.getDeviceInfo()).append("\n\n");
            sb.append(str).append("\n\n");

            sb.append("Version: ").append(CommonUtil.getVersion(mContext)).append("\n");
            sb.append("ErrorTime: ").append(CommonUtil.getCurrentDataTime()).append("\n\n");

            fos.write(sb.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    //Nothing
                }
            }
        }
    }
}
