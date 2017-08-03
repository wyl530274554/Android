package com.melon.myapp;

import java.util.Hashtable;

/**
 * Created by melon on 2017/8/2.
 * Email 530274554@qq.com
 */

public class FlyWeightDemo {
    public static void main(String[] args) {
        FlyweightFactory factory = FlyweightFactory.getInstance();

        Flyweight fly1 = factory.getFlyWeight("Google");
        Flyweight fly2 = factory.getFlyWeight("Qutr");
        Flyweight fly3 = factory.getFlyWeight("Google");
        Flyweight fly4 = factory.getFlyWeight("Google");
        Flyweight fly5 = factory.getFlyWeight("Google");
        Flyweight fly6 = factory.getFlyWeight("Google");

        int objSize = factory.getFlyweightSize();
        System.out.println("objSize = " + objSize);
    }
}

abstract class Flyweight {
    public abstract void operation();
}

class ConcreteFlyweight extends Flyweight {
    private String string;

    public ConcreteFlyweight(String str) {
        string = str;
    }

    public void operation() {
        System.out.println("Concrete---Flyweight : " + string);
    }

}

class FlyweightFactory {
    private Hashtable flyweights = new Hashtable();
    private static FlyweightFactory factory = new FlyweightFactory();

    private FlyweightFactory() {
    }

    public static FlyweightFactory getInstance() {
        return factory;
    }

    public Flyweight getFlyWeight(String str) {
        Flyweight flyweight = (Flyweight) flyweights.get(str);
        if (flyweight == null) {
            //产生新的ConcreteFlyweight
            flyweight = new ConcreteFlyweight(str);
            flyweights.put(str, flyweight);
        }
        return flyweight;
    }

    public int getFlyweightSize() {
        return flyweights.size();
    }
}
