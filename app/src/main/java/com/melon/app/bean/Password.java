package com.melon.app.bean;

import java.io.Serializable;

/**
 * Created by melon on 2017/11/30.
 * Email 530274554@qq.com
 */

public class Password implements Serializable {
    public int id;
    public String title;
    public String user;
    public String password;
    public String desc;

    public Password(String title, String user, String password, String desc) {
        this.title = title;
        this.user = user;
        this.password = password;
        this.desc = desc;
    }

    public Password(String title) {
        this.title = title;
    }
}
