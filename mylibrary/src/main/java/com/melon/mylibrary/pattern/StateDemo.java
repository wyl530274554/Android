package com.melon.mylibrary.pattern;

/**
 * Created by melon on 2017/8/7.
 * Email 530274554@qq.com
 */

public class StateDemo {
    public static void main(String[] args) {
        //创建状态
        State state = new ConcreteStateB();
        //创建环境
        Context1 context = new Context1();
        //将状态设置到环境中
        context.setState(state);
        //请求
        context.request("test");
    }
}

class Context1 {
    //持有一个State类型的对象实例
    private State state;

    public void setState(State state) {
        this.state = state;
    }

    /**
     * 用户感兴趣的接口方法
     */
    public void request(String sampleParameter) {
        //转调state来处理
        state.handle(sampleParameter);
    }
}

interface State {
    /**
     * 状态对应的处理
     */
    public void handle(String sampleParameter);
}

class ConcreteStateA implements State {

    @Override
    public void handle(String sampleParameter) {

        System.out.println("ConcreteStateA handle ：" + sampleParameter);
    }

}

class ConcreteStateB implements State {

    @Override
    public void handle(String sampleParameter) {

        System.out.println("ConcreteStateB handle ：" + sampleParameter);
    }

}
