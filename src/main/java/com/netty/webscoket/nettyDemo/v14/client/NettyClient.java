package com.netty.webscoket.nettyDemo.v14.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.netty.webscoket.nettyDemo.v14.handler.HeartBeatReqHandler;
import com.netty.webscoket.nettyDemo.v14.handler.LoginAuthReqHandler;
import com.netty.webscoket.nettyDemo.v14.util.NettyConstant;
import com.netty.webscoket.nettyDemo.v14.util.NettyMessageDecoder;
import com.netty.webscoket.nettyDemo.v14.util.NettyMessageEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NettyClient {

	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	EventLoopGroup group = new NioEventLoopGroup();
	
	public static void main(String[] args) {
		new NettyClient().connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
	}
	
	public void connect(int port, String host) {
		//配置客户端NIO线程池
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
					ch.pipeline().addLast("MessageEncoder",new NettyMessageEncoder());
					ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
					ch.pipeline().addLast("LoginAuthHandler",new LoginAuthReqHandler());
					ch.pipeline().addLast("HeartBeatHandler",new HeartBeatReqHandler());
				}
			});
			//发起异步连接操作
			ChannelFuture f =b.connect(new InetSocketAddress(host, port), 
					new InetSocketAddress(NettyConstant.LOCALIP, NettyConstant.LOCAL_PORT)).sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//所有资源释放完成之后，清空资源，再次发起重连操作
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(5);
						//发起重连操作
						connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
		}
	}
	
}
