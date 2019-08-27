package com.melon.mylibrary;

import java.sql.Timestamp;

/**
 * Created by melon on 2017/9/26.
 * Email 530274554@qq.com
 */

public class Demo {
    public static void main(String[] args){
//        String a = "kilimall";
//        String b = "kili"+"mall";
//        String c = new String("kilimall");
//        System.out.print(a==b);
//        System.out.print(c==b);
//        System.out.print(!a.equals(b));
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        System.out.println(ts.toString());
    }
}
