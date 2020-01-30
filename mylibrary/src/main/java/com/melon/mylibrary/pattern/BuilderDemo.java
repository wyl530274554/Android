package com.melon.mylibrary.pattern;

/**
 * Created by melon on 2017/6/16.
 * Email 530274554@qq.com
 */

public class BuilderDemo {
    public static void main(String[] args) {
        IBuilder builder = new WomanBuilder();
        Person person = Director.construct(builder);
        System.out.print(person);
    }
}

//总指挥
class Director {
    public static Person construct(IBuilder builder) {
        builder.buildHead();
        builder.buildBody();
        builder.buildFoot();
        return builder.build();
    }
}

//抽象构建者
interface IBuilder {
    void buildHead();

    void buildBody();

    void buildFoot();

    Person build();
}

//具体构建者A
class ManBuilder implements IBuilder {
    Person person;

    public ManBuilder() {
        this.person = new Person();
    }

    @Override
    public void buildHead() {
        person.setHead("男人的头");
    }

    @Override
    public void buildBody() {
        person.setBody("男人的身体");
    }

    @Override
    public void buildFoot() {
        person.setFoot("男人的脚");
    }

    @Override
    public Person build() {
        return person;
    }

}

//具体构建者B
class WomanBuilder implements IBuilder {
    Person person;

    public WomanBuilder() {
        person = new Person();
    }

    @Override
    public void buildHead() {
        person.setHead("女人的头");
    }

    @Override
    public void buildBody() {
        person.setBody("女人的身体");
    }

    @Override
    public void buildFoot() {
        person.setFoot("女人的脚");
    }

    @Override
    public Person build() {
        return person;
    }
}

//产品
class Person {
    private String body;
    private String head;
    private String foot;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
    }

    @Override
    public String toString() {
        return "Person{" +
                "body='" + body + '\'' +
                ", head='" + head + '\'' +
                ", foot='" + foot + '\'' +
                '}';
    }
}


