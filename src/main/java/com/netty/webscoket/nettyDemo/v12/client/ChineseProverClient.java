package com.netty.webscoket.nettyDemo.v12.client;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChineseProverClient {

	public static void main(String[] args) {
		new ChineseProverClient().run(8080);
	}
	
	public void run(int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioDatagramChannel.class)
			.option(ChannelOption.SO_BROADCAST, true)
			.handler(new ChineseProverClientHandler());
			Channel ch = b.bind(0).sync().channel();
			//向网段内的所有机器广播UDP消息
			ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语字典查询？",CharsetUtil.UTF_8), 
					new InetSocketAddress("255.255.255.255", port))).sync();
			if(!ch.closeFuture().await(15000)) {
				log.info("查询超时！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			group.shutdownGracefully();
		}
	}
}
