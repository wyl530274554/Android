package com.melon.mylibrary.pattern;

/**
 * Created by melon on 2017/7/7.
 * Email 530274554@qq.com
 */

public class FacadeDemo {
    public static void main(String[] args) {
        Facade facade = new Facade(new LightManager(), new TvManager(), new FanManager());
        facade.close();
    }
}

class Facade {
    LightManager lightManager;
    TvManager tvManager;
    FanManager fanManager;

    public Facade(LightManager lightManager, TvManager tvManager, FanManager fanManager) {
        this.lightManager = lightManager;
        this.tvManager = tvManager;
        this.fanManager = fanManager;
    }

    public void close() {
        lightManager.close();
        tvManager.close();
        fanManager.close();
    }
}

class LightManager {
    public void close() {
        System.out.print("关灯");
    }

    public void open() {
        System.out.print("开灯");
    }
}

class TvManager {
    public void close() {
        System.out.print("关电视");
    }

    public void open() {
        System.out.print("开电视");
    }
}

class FanManager {
    public void close() {
        System.out.print("关风扇");
    }

    public void open() {
        System.out.print("开风扇");
    }
}
