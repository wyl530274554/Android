package com.melon.mylibrary;

/**
 * Created by melon on 2017/6/28.
 * Email 530274554@qq.com
 */

public class ProxyDemo {
    public static void main(String[] args) {
        Subject subject = new SamsungShopProxy();
        String phone = subject.buyPhoneRequest();
        System.out.print(phone);
    }
}

interface Subject {
    String buyPhoneRequest();
}

class PhoneFactorySubject implements Subject {

    @Override
    public String buyPhoneRequest() {
        return "三星S8";
    }
}

class SamsungShopProxy implements Subject{

    @Override
    public String buyPhoneRequest() {
        PhoneFactorySubject factory = new PhoneFactorySubject();
        return factory.buyPhoneRequest();
    }
}

