package com.melon.mylibrary.pattern;

/**
 * Created by melon on 2017/8/3.
 * Email 530274554@qq.com
 */

public class StrategyDemo {
    public static void main(String[] args) {
        Context context = new Context();

        context.setStrategy(new StrategyA());
        context.method();

        context.setStrategy(new StrategyB());
        context.method();

        context.setStrategy(new StrategyC());
        context.method();
    }
}

//策略总接口
interface Strategy {
    //方法
    void algorithm();
}

//具体策略
class StrategyA implements Strategy {

    @Override
    public void algorithm() {
        System.out.println("采用策略A计算");
    }
}

class StrategyB implements Strategy {

    @Override
    public void algorithm() {
        System.out.println("采用策略B计算");
    }
}

class StrategyC implements Strategy {

    @Override
    public void algorithm() {
        System.out.println("采用策略C计算");
    }
}

//环境
class Context {
    Strategy strategy;

    public void method() {
        strategy.algorithm();
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}



