package com.melon.myapp;

/**
 * Created by melon on 2017/8/7.
 * Email 530274554@qq.com
 */

public class CommandDemo {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        ICommand command = new ConcreteCommand(receiver);
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.invoke();
    }
}

//接收者类，知道如何实施与执行一个请求相关的操作，任何类都可能作为一个接收者。
class Receiver {
    public void action() {
        System.out.println("Receiver: 收到命令并执行");
    }

    public void cancelAction() {
        System.out.println("Receiver: 收到取消命令并执行");
    }
}

interface ICommand {
    void execute();
    void undo();
}

class ConcreteCommand implements ICommand {
    private Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        System.out.println("Command: 命令接收者");
        receiver.action();
    }

    @Override
    public void undo() {
        receiver.cancelAction();
    }
}

class Invoker {
    private ICommand command;

    public void setCommand(ICommand command) {
        this.command = command;
    }

    public void invoke() {
        System.out.println("Invoker 开始调度");
        command.execute();
        command.undo();
    }
}




