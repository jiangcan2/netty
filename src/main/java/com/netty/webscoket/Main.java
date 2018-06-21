package com.netty.webscoket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 程序入口，负责启动应用
 * @author admin
 *
 */
@Slf4j
public class Main {

	public static void main(String[] args) {
		EventLoopGroup boosGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(boosGroup, workGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new MyWebSocketChannelHandler());
			log.info("服务端等待客户端开启连接...");
			Channel ch = b.bind(8888).sync().channel();
			ch.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//优雅的退出程序
			boosGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}
