package com.melon.mylibrary;

/**
 * Created by melon on 2017/6/16.
 * Email 530274554@qq.com
 */

public class SingletonDemo {
    public static void main(String args[]) {
        Singleton instance = Singleton.getInstance();
        System.out.print(instance.getClass().getSimpleName());
    }
}

//懒汉式
class Singleton {
    private static Singleton singleton;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) { //只让第一次同步。
            synchronized (Singleton.class) {
                if (singleton == null) { //再次防止实例已创建
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
////饿汉式
//class Singleton {
//    private static Singleton singleton = new Singleton();
//
//    private Singleton() {
//    }
//
//    public static Singleton getInstance() {
//        return singleton;
//    }
//}
