package com.melon.myapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 消息
 * Created by melon on 2017/9/18.
 * Email 530274554@qq.com
 */
@Entity(indexes = {@Index(value = "text, date DESC", unique = true)})
public class Message {
    @Id()
    public Long id;
    @NotNull
    public String text;
    public Date date;
    @Generated(hash = 407972457)
    public Message(Long id, @NotNull String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }
    @Generated(hash = 637306882)
    public Message() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
