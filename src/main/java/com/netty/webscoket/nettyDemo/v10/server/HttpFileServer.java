package com.netty.webscoket.nettyDemo.v10.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpFileServer {
//	private static final String DEFAULT_URL ="/src/main/java/com/netty/webscoket/nettyDemo/v10/server/";
//	private static final String DEFAULT_URL ="/src/main/java/com/netty/webscoket/";
	private static final String DEFAULT_URL ="/";
	public static void main(String[] args) {
		new HttpFileServer().run(8080, DEFAULT_URL);
	}
	
	public void run(final int port,final String url) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
					ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
					ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
					ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
					ch.pipeline().addLast("fileServerHandler",new HttpFileServerHandler(url));
				}
			});
			ChannelFuture f = b.bind("localhost", port).sync();
			log.info("HTTP 文件目录服务器启动，网址是：{}:{}{}","http://localhost",port,DEFAULT_URL);
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}
