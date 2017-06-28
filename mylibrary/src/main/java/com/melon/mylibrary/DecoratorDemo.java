package com.melon.mylibrary;

/**
 * Created by melon on 2017/6/28.
 * Email 530274554@qq.com
 */

public class DecoratorDemo {
    public static void main(String[] args) {
        IHamburger hamburger = new ChickenBurger();
        System.out.println(hamburger.getName() + ": " + hamburger.getPrice());

        IHamburger hamburgerA = new Lettuce(hamburger);
        System.out.println(hamburgerA.getName() + ": " + hamburgerA.getPrice());

        IHamburger hamburgerB = new Chilli(hamburger);
        System.out.println(hamburgerB.getName() + ": " + hamburgerB.getPrice());

        IHamburger hamburgerC = new Chilli(hamburgerA);
        System.out.println(hamburgerC.getName() + ": " + hamburgerC.getPrice());

    }
}

//抽象组件
interface IHamburger {
    String getName();

    double getPrice();
}

//具体组件
class ChickenBurger implements IHamburger {

    @Override
    public String getName() {
        return "鸡腿堡";
    }

    @Override
    public double getPrice() {
        return 10;
    }
}

//抽象装饰者 调味品
abstract class Condiment implements IHamburger {
}

//具体装饰者A
class Lettuce extends Condiment {
    IHamburger decoratee;

    public Lettuce(IHamburger decoratee) {
        this.decoratee = decoratee;
    }

    @Override
    public String getName() {
        return decoratee.getName() + " 加 生菜";
    }

    @Override
    public double getPrice() {
        return decoratee.getPrice() + 0.5;
    }

}

//具体装饰者B
class Chilli extends Condiment {
    IHamburger decoratee;

    public Chilli(IHamburger decoratee) {
        this.decoratee = decoratee;
    }

    @Override
    public String getName() {
        return decoratee.getName() + " 加 辣椒";
    }

    @Override
    public double getPrice() {
        return decoratee.getPrice() + 0.5;
    }

}

