package com.melon.mylibrary.pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melon on 2017/8/2.
 * Email 530274554@qq.com
 */

public class ComponentDemo {

    public static void main(String[] args) {
        Component root = new Composite("Root");
        root.add(new Leaf("Leaf 1"));
        root.add(new Leaf("Leaf 2"));

        Component com1 = new Composite("Composite 1");
        com1.add(new Leaf("Leaf 11"));
        com1.add(new Leaf("Leaf 12"));
        root.add(com1);

        Component com2 = new Composite("Composite 2");
        com2.add(new Leaf("Leaf 21"));
        com2.add(new Leaf("Leaf 22"));
        root.add(com2);

        Component com3 = new Composite("Composite 3");
        com3.add(new Leaf("Leaf 31"));
        com3.add(new Leaf("Leaf 32"));
        com2.add(com3);

        root.display(0);
    }
}

//各组件共有接口
abstract class Component {
    protected String name;

    public Component(String name) {
        this.name = name;
    }

    public abstract void add(Component component);

    public abstract void remove(Component component);

    public abstract void display(int depth);
}

//叶子
class Leaf extends Component {

    public Leaf(String name) {
        super(name);
    }

    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void display(int depth) {
        //显示自己
        String line = "";
        for(int i=0; i<depth; i++)
            line += "-";
        System.out.println(line + name);
    }
}

//树枝
class Composite extends Component {
    private List<Component> children = new ArrayList<Component>();

    public Composite(String name) {
        super(name);
    }

    @Override
    public void add(Component component) {
        children.add(component);
    }

    @Override
    public void remove(Component component) {
        children.remove(component);
    }

    @Override
    public void display(int depth) {
        //显示自己
        String line = "";
        for(int i=0; i<depth; i++)
            line += "-";
        System.out.println(line + name);
        //显示孩子
        for (Component c : children) {
            c.display(depth+3);
        }
    }
}

