package com.melon.myapp;

/**
 * Created by melon on 2017/8/3.
 * Email 530274554@qq.com
 */

public class TemplateDemo {
    public static void main(String[] args) {
        AbstractClass mc = new ConcreteClass();
        mc.templateMethod();
    }
}

abstract class AbstractClass {
    protected abstract void operation1();
    protected abstract void operation2();
    public void templateMethod() {
        operation1();
        operation2();
        System.out.println("模板方法结束...");
    }
}

class ConcreteClass extends AbstractClass {

    @Override
    public void operation1() {
        System.out.println("具体方法1");
    }

    @Override
    public void operation2() {
        System.out.println("具体方法2");
    }
}
