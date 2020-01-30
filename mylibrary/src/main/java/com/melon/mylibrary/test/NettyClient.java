package com.melon.mylibrary.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by melon on 2017/9/26.
 * Email 530274554@qq.com
 */

public class NettyClient {
    public static void main(String[] args) throws Exception {
//        String a = "kilimall";
//        String b = "kili"+"mall";
//        String c = new String("kilimall");
//        System.out.print(a==b);
//        System.out.print(c==b);
//        System.out.print(!a.equals(b));
//        Timestamp ts = new Timestamp(System.currentTimeMillis());
//        System.out.println(ts.toString());
        connect();
    }

    private static void connect() throws Exception {
        EventLoopGroup workgroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();//客户端
        b.group(workgroup)
                .channel(NioSocketChannel.class)//客户端 -->NioSocketChannel
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {//handler
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });
        //创建异步连接 可添加多个端口
        ChannelFuture cf1 = b.connect("192.168.1.28", 24635).sync();
        cf1.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future != null && future.isSuccess()) {
                    System.out.println(new String("连接成功"));

                    byte[] bytes = new byte[(int)(1024)];
                    bytes[0] = 11;
                    bytes[1023] = 11;

                    ByteBuf content = Unpooled.copiedBuffer(bytes);
                    System.out.println("len: "+content.readableBytes());
                    future.channel().writeAndFlush(content);
                } else {
                    System.out.println(new String("连接失败"));
                }
            }
        });

        //client向server端发送数据  Buffer形式
        cf1.channel().closeFuture().sync();
        workgroup.shutdownGracefully();
    }

    private static class ClientHandler extends SimpleChannelInboundHandler {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            int readableBytesLen = buf.readableBytes();
            if (readableBytesLen == 0) {
                return;
            }

            byte[] serverData = new byte[readableBytesLen];
            buf.readBytes(serverData);

            System.out.println(new String(serverData, "GBK"));
        }
    }
}
