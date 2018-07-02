package com.netty.webscoket.nettyDemo.v10.xml.client;

import java.net.InetSocketAddress;

import com.netty.webscoket.nettyDemo.v10.xml.pojo.Order;
import com.netty.webscoket.nettyDemo.v10.xml.util.HttpXmlRequestEncoder;
import com.netty.webscoket.nettyDemo.v10.xml.util.HttpXmlResponseDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

/**
 * 没安装ant  
 * 待测
 * @author admin
 *
 */
public class HttpXmlClient {
	
	public static void main(String[] args) {
		new HttpXmlClient().connect(8080);
	}
	
	private void connect(int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("http-decoder",new HttpResponseDecoder());
					ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
					//xml解码器
					ch.pipeline().addLast("xml-decoder",new HttpXmlResponseDecoder(Order.class,true));
					ch.pipeline().addLast("http-encoder",new HttpRequestEncoder());
					ch.pipeline().addLast("xml-encoder",new HttpXmlRequestEncoder());
					ch.pipeline().addLast("xmlClientHandler", new HttpXmlClientHandler());
				}
			});
			ChannelFuture f = b.connect(new InetSocketAddress(port)).sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			group.shutdownGracefully();
		}
		
	}

}
