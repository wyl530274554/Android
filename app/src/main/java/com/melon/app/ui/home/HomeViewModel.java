package com.melon.app.ui.home;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.melon.mylibrary.util.Constants;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mUrl;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mUrl = new MutableLiveData<>();
    }

    public MutableLiveData<String> getText() {
        return mText;
    }

    public void setText(String text) {
        mText.setValue(text);
    }

    public MutableLiveData<String> getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        if (TextUtils.isEmpty(url)) return;
        if (!url.startsWith(Constants.NET_PROTOCOL_HTTP)) {
            url = Constants.URL_BAI_DU + url;
        }
        mUrl.setValue(url);
    }
}