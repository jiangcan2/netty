package com.netty.webscoket.nettyDemo.v4.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


public class TimeClient {

    public static void main(String[] args) {
        int prot = 8080;
        if (args != null && args.length > 0) {
            prot = Integer.parseInt(args[0]);
        }
        new TimeClient().connect(prot, "127.0.0.1");
    }

    public void connect(int port,String host){
        //配置客户端NIO线程池
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
							ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new TimeClientHandler());
						}
                   });
            //发起异步连接操作
            ChannelFuture f = b.connect(host,port).sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //优雅退出,NIO线程池
            group.shutdownGracefully();
        }

    }
}
