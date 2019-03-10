package com.melon.myapp.functions.architecture.mvvm.model;

public interface NetListener {
    void onSucc(String tips);

    void onFail();

    void onAfter();

    void onBefore();
}
