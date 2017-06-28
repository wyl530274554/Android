package com.melon.mylibrary;

/**
 * Created by melon on 2017/6/27.
 * Email 530274554@qq.com
 */

public class AdapterDemo {
    /**
     * 客户：中国插头
     * 目标：IGBSocket
     * 适配器：SocketAdapter
     * 被适配者：GermanyHotelSocket
     */
    public static void main(String[] args){
        //场景，带着中国两插插头，去德国的旅馆充电。需要使用德国的三插插座
        SocketAdapter adapter = new SocketAdapter(new GermanyHotelSocket());
        adapter.charge();
    }
}

//国标
interface IGBSocket {
    void charge();
}

//具体插座
class MySocket implements IGBSocket{

    @Override
    public void charge() {
        System.out.print("使用国标三插");
    }
}

//德标
interface IGermanySocket {
    void charge();
}
//德国旅馆的插座
class GermanyHotelSocket  implements IGermanySocket{

    @Override
    public void charge() {
        System.out.print("使用德标两插");
    }
}

//适配器
class SocketAdapter implements IGBSocket{
    GermanyHotelSocket germanyHotelSocket;
    public SocketAdapter(GermanyHotelSocket germanyHotelSocket) {
        this.germanyHotelSocket = germanyHotelSocket;
    }

    @Override
    public void charge() {
        germanyHotelSocket.charge();
    }
}
