package com.melon.mylibrary.pattern;

/**
 * Created by melon on 2017/8/7.
 * Email 530274554@qq.com
 */

public class MediatorDemo {
    public static void main(String[] args) {
        ConcreteMediator mediator=new ConcreteMediator();
        Colleague1 colleague1=new Colleague1(mediator);
        Colleague2 colleague2=new Colleague2(mediator);

        mediator.setColleague1(colleague1);
        mediator.setColleague2(colleague2);

        colleague1.send("最近还好吗？");
        colleague2.send("还不错");
    }
}

//抽象中介者类
abstract class Mediator {
    public abstract void send(String message, Colleague colleague);
}

class ConcreteMediator extends Mediator {

    private Colleague1 colleague1;
    private Colleague2 colleague2;

    public void setColleague1(Colleague1 colleague1) {
        this.colleague1 = colleague1;
    }

    public void setColleague2(Colleague2 colleague2) {
        this.colleague2 = colleague2;
    }

    public void send(String message, Colleague colleague) {
        if (colleague == colleague1) {
            colleague2.notify(message);
        } else {
            colleague1.notify(message);
        }
    }
}

//抽象同事类
abstract class Colleague {
    protected Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }
}

class Colleague1 extends Colleague {

    public Colleague1(Mediator mediator) {
        super(mediator);
    }

    public void send(String message) {
        mediator.send(message, this);
    }

    public void notify(String message) {
        System.out.println("同事1得到消息:" + message);
    }
}

class Colleague2 extends Colleague {

    public Colleague2(Mediator mediator) {
        super(mediator);
    }

    public void send(String message) {
        mediator.send(message, this);
    }

    public void notify(String message) {
        System.out.println("同事2得到消息:" + message);
    }
}