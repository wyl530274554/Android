package com.melon.myapp.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by admin on 2017/5/10.
 * Email 530274554@qq.com
 * 笔记
 */
@DatabaseTable(tableName = "note")
public class Note implements Serializable {
    @DatabaseField(generatedId = true)
    public int _id;
    @DatabaseField(canBeNull = false)
    public String time;//创建时间
    @DatabaseField(canBeNull = false)
    public String content; //内容

    public Note() {
    }

    public Note(String time, String content) {
        this.time = time;
        this.content = content;
    }
}
