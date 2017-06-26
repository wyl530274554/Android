package com.melon.mylibrary;

/**
 * Created by melon on 2017/6/9.
 * Email 530274554@qq.com
 */

public class SimpleFactoryDemo {
    public static void main(String[] args) {
        Food food = FoodFactory.createFood("chips");
        food.getName();
    }
}

class FoodFactory {
    public static Food createFood(String type) {
        Food food = null;
        switch (type) {
            case "chips":
                food = new Chips();
                break;
            case "bread":
                food = new Bread();
                break;
        }

        return food;
    }
}

abstract class Food {
    abstract void getName();
}

class Chips extends Food {
    @Override
    void getName() {
        System.out.print("我是Chips");
    }
}

class Bread extends Food {
    @Override
    void getName() {
        System.out.print("我是Bread");
    }
}
