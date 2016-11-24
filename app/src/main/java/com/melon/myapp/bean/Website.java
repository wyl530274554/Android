package com.melon.myapp.bean;

import java.io.Serializable;

/**
 * Created by admin on 2016/11/24.
 * Email 530274554@qq.com
 */

public class Website implements Serializable {
    private String url;
    private int img;

    public Website(String url, int img) {
        this.url = url;
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
