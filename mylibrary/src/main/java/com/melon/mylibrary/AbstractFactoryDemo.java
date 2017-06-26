package com.melon.mylibrary;

/**
 * Created by melon on 2017/6/15.
 * Email 530274554@qq.com
 */

public class AbstractFactoryDemo {
    public static void main(String args[]){
        IFactory factory = new XiaoMiFactory();
        IMobile mobile = factory.createMobile();
        mobile.getName();
    }

}

//抽象工厂
interface IFactory{
    IMobile createMobile();
    ITv createTv();
}

//具体工厂1
class XiaoMiFactory implements IFactory{

    @Override
    public IMobile createMobile() {
        return new XiaoMiMobile();
    }

    @Override
    public ITv createTv() {
        return new XiaoMiTv();
    }
}
//具体工厂2
class HuaWeiFactory implements IFactory{

    @Override
    public IMobile createMobile() {
        return new HuaWeiMobile();
    }

    @Override
    public ITv createTv() {
        return new HuaWeiTv();
    }
}


//抽象产品A
interface IMobile {
    void getName();
}
//抽象产品B
interface ITv {
    void getName();
}

//具体产品A1
class XiaoMiMobile implements IMobile{

    @Override
    public void getName() {
        System.out.print("XiaoMi phone");
    }
}

//具体产品B1
class HuaWeiMobile implements IMobile{

    @Override
    public void getName() {
        System.out.print("HuaWei phone");
    }
}

//具体产品A2
class XiaoMiTv implements ITv{

    @Override
    public void getName() {
        System.out.print("XiaoMi Tv");
    }
}
//具体产品B2
class HuaWeiTv implements ITv{

    @Override
    public void getName() {
        System.out.print("HuaWei Tv");
    }
}

