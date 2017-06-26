package com.melon.mylibrary;
//
///**
// * Created by melon on 2017/6/15.
// * Email 530274554@qq.com
// */
//
public class FactoryMethodDemo {
//    public static void main(String[] args) {
//        MobileFactory xmFactory = new XiaoMiFactory();
//        Mobile xmMobile = xmFactory.create("XiaoMi 6");
//        xmMobile.getName();
//
//        //若想生产HuaWei 手机，这里同理。增加一个具体的HuaWeiFactory 和 具体的Mobil即可。
//    }
}
//
////抽象工厂
//abstract class MobileFactory {
//    public Mobile create(String type) {
//        //....可以处理一些公共事务
//        Mobile produce = produce(type);
//        //....可以处理一些公共事务
//        return produce;
//    }
//
//    abstract Mobile produce(String type);
//}
//
////抽象产品
//abstract class Mobile {
//    abstract String getName();
//}
//
////具体工厂
//class XiaoMiFactory extends MobileFactory {
//
//    @Override
//    Mobile produce(String type) {
//        switch (type) {
//            case "XiaoMi 6":
//                return new XiaoMiMobile6();
//            case "XiaoMi 5":
//                break;
//        }
//        return null;
//    }
//}
//
////具体产品
//class XiaoMiMobile6 extends Mobile {
//    @Override
//    String getName() {
//        System.out.print("小米6");
//        return "小米6";
//    }
//}
//
//
