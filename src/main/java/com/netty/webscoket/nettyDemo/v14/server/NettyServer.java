package com.netty.webscoket.nettyDemo.v14.server;

import com.netty.webscoket.nettyDemo.v14.handler.HeartBeatRespHandler;
import com.netty.webscoket.nettyDemo.v14.handler.LoginAuthResqHandler;
import com.netty.webscoket.nettyDemo.v14.util.NettyConstant;
import com.netty.webscoket.nettyDemo.v14.util.NettyMessageDecoder;
import com.netty.webscoket.nettyDemo.v14.util.NettyMessageEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

	public static void main(String[] args) throws InterruptedException {
		 new NettyServer().bind();
	}
	
	public void bind() throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workGroup)
		.channel(NioServerSocketChannel.class)
		.option(ChannelOption.SO_BACKLOG, 100)
		.handler(new LoggingHandler(LogLevel.INFO))
		.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
				ch.pipeline().addLast(new NettyMessageEncoder());
				ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
				ch.pipeline().addLast(new LoginAuthResqHandler());
				ch.pipeline().addLast("HeartBeatHandler",new HeartBeatRespHandler());
			}
		
		});
		//绑定端口同步等待
		b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
		log.info("Netty server start ok : "
	                + (NettyConstant.REMOTEIP + " : " + NettyConstant.PORT));
	}
}
