package com.melon.mylibrary.pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melon on 2017/8/7.
 * Email 530274554@qq.com
 */

public class VisitorDemo {
    public static void main(String[] args) {
        //创建一个结构对象
        ObjectStructure os = new ObjectStructure();
        //给结构增加一个节点
        os.add(new NodeA());
        //给结构增加一个节点
        os.add(new NodeB());
        //创建一个访问者
        Visitor visitor = new VisitorA();
        os.action(visitor);
    }
}

interface Visitor {
    void visit(NodeA node);

    void visit(NodeB node);
}

class VisitorA implements Visitor {
    @Override
    public void visit(NodeA node) {
        System.out.println(node.operationA());
    }

    @Override
    public void visit(NodeB node) {
        System.out.println(node.operationB());
    }
}

abstract class Node {
    public abstract void accept(Visitor visitor);
}

class NodeA extends Node {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String operationA() {
        return "NodeA";
    }
}

class NodeB extends Node {
    /**
     * 接受方法
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String operationB() {
        return "NodeB";
    }
}

class ObjectStructure {

    private List<Node> nodes = new ArrayList<Node>();

    /**
     * 执行方法操作
     */
    public void action(Visitor visitor) {

        for (Node node : nodes) {
            node.accept(visitor);
        }

    }

    /**
     * 添加一个新元素
     */
    public void add(Node node) {
        nodes.add(node);
    }
}