package com.melon.myapp;

import android.view.View;
import android.widget.EditText;

import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.ToastUtil;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NotificationActivity extends BaseActivity {

    private EditText et_notify_input;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_notification);

        et_notify_input = (EditText) findViewById(R.id.et_notify_input);
        findViewById(R.id.et_notify_send).setOnClickListener(this);
        findViewById(R.id.et_notify_clear).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_notify_send:
                sendNotify();
                break;
            case R.id.et_notify_clear:
                et_notify_input.setText("");
                break;
        }
    }

    private void sendNotify() {
        String content = et_notify_input.getText().toString().trim();
        if (!CommonUtil.isEmpty(content)) {
            send(content);
        } else {
            ToastUtil.showShortToast(this, "请输入内容");
        }
    }

    private void send(final String content) {
        new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject dataObj = new JSONObject();
                    dataObj.put("platform", "all");
                    dataObj.put("audience", "all");
                    JSONObject notifyObj = new JSONObject();
                    notifyObj.put("alert", content);
                    dataObj.put("notification", notifyObj);

                    String data = dataObj.toString();

                    URL url = new URL("https://api.jpush.cn/v3/push");
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url
                            .openConnection();
                    urlConnection.setRequestMethod("POST");
                    // 设置请求的头
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Authorization", "Basic N2NlMTNjOWFjNmRlMDc4ODQ1MWVjZTRlOjQ0YzM3MDJjZjY1YTg0NTU5NzkzYWM2OQ==");
                    urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
                    urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
                    //获取输出流
                    OutputStream os = urlConnection.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == 200) {
                        os.close();
                        success();
                    } else {
                        failure(1 + "---responseCode: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    failure("2" + "---" + e.getMessage());
                }
            }
        }.start();
    }

    private void failure(final String errorStr) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(getApplicationContext(), "failure " + errorStr);
            }
        });
    }

    private void success() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(getApplicationContext(), "success");
            }
        });
    }

}
