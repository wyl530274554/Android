package com.melon.mylibrary.pattern;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by melon on 2017/8/7.
 * Email 530274554@qq.com
 */

public class ExplainDemo {
    public static void main(String[] args) {
        Context2 ctx = new Context2();
        Variable va = new Variable();
        Variable vb = new Variable();
        Constant cc = new Constant(8);

        ctx.addValue(va, 9);
        ctx.addValue(vb, 3);

        Expression ex = new Division(new Multiply(va, vb), new Add(new Subtract(va, vb), cc));
        ex.interpret(ctx);
    }
}

class Context2

{

    private Map valueMap = new HashMap();

    public void addValue(Variable x, int y)

    {

        Integer yi = new Integer(y);

        valueMap.put(x, yi);

    }

    public int lookupValue(Variable x)

    {

        int i = ((Integer) valueMap.get(x)).intValue();

        return i;

    }

}

//抽象表达式角色，也可以用接口来实现

abstract class Expression

{

    public abstract int interpret(Context2 con);

}

class Add extends Expression

{

    private Expression left, right;

    public Add(Expression left, Expression right)

    {

        this.left = left;

        this.right = right;

    }

    public int interpret(Context2 con)

    {

        return left.interpret(con) + right.interpret(con);

    }

}

class Subtract extends Expression

{

    private Expression left, right;

    public Subtract(Expression left, Expression right)

    {

        this.left = left;

        this.right = right;

    }

    public int interpret(Context2 con)

    {

        return left.interpret(con) - right.interpret(con);

    }

}

class Division extends Expression

{

    private Expression left, right;

    public Division(Expression left, Expression right)

    {

        this.left = left;

        this.right = right;

    }

    public int interpret(Context2 con)

    {

        try {
            System.out.println(left.interpret(con) + "/" + right.interpret(con) + "=" + (left.interpret(con) / right.interpret(con)));
            return left.interpret(con) / right.interpret(con);

        } catch (ArithmeticException ae)

        {

            System.out.println("被除数为0！");

            return -11111;

        }

    }

}

class Multiply extends Expression

{

    private Expression left, right;

    public Multiply(Expression left, Expression right)

    {

        this.left = left;

        this.right = right;

    }

    public int interpret(Context2 con)

    {

        return left.interpret(con) * right.interpret(con);

    }

}

class Constant extends Expression

{

    private int i;

    public Constant(int i)

    {

        this.i = i;

    }

    //解释
    public int interpret(Context2 con)

    {

        return i;

    }

}

class Variable extends Expression

{

    public int interpret(Context2 con)

    {

        //this为调用interpret方法的Variable对象

        return con.lookupValue(this);

    }

}
