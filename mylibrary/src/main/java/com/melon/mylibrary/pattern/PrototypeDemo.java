package com.melon.mylibrary.pattern;

/**
 * Created by melon on 2017/6/16.
 * Email 530274554@qq.com
 */

public class PrototypeDemo {
    public static void main(String args[]) throws CloneNotSupportedException {
        Man man = new Man("张三");

        Man manCopy = (Man) man.clone();
        manCopy.setName("李四");

        System.out.print("man: "+man.getName() +", manCopy: "+manCopy.getName());
    }
}

class Man implements Cloneable {

    private String name;

    public Man(String name) {
        this.name = name;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
