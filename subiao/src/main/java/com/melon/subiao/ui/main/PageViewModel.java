package com.melon.subiao.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.melon.subiao.Message;

public class PageViewModel extends ViewModel {

    private MutableLiveData<String> mContent = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mContent, new Function<String, String>() {
        @Override
        public String apply(String content) {
            //解析
            return Message.parse(content);
        }
    });

    public void setContent(String content) {
        mContent.setValue(content);
    }

    public LiveData<String> getText() {
        return mText;
    }
}