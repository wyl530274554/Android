package com.melon.mylibrary.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by melon on 2017/9/26.
 * Email 530274554@qq.com
 */

public class NettyServer {
    public static void main(String[] args) throws Exception {
        init();
    }

    private static void init() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();//客户端
        b.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)//客户端 -->NioSocketChannel
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {//handler
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });
        //创建异步连接 可添加多个端口
        ChannelFuture cf1 = b.bind(24635).sync();
        cf1.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("服务端启动成功");
                } else {
                    System.out.println("服务端启动失败");
                }
            }
        });
        cf1.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

    private static int receivedTotalSize = 0;

    private static class ClientHandler extends SimpleChannelInboundHandler {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            int readableBytesLen = buf.readableBytes();

            receivedTotalSize += readableBytesLen;

            System.out.println("received: " + readableBytesLen);
            System.out.println("receivedTotalSize: " + receivedTotalSize);
            if (readableBytesLen == 0 || readableBytesLen > 100) {
                return;
            }
            byte[] serverData = new byte[readableBytesLen];
            buf.readBytes(serverData);

            System.out.println(new String(serverData));
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            System.out.println("客户端连上了...");
        }
    }
}
