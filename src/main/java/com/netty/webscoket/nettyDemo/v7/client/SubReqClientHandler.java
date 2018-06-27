package com.netty.webscoket.nettyDemo.v7.client;

import com.netty.webscoket.nettyDemo.v7.entity.SubscribeReq;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubReqClientHandler extends ChannelHandlerAdapter{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 10; i++) {
			ctx.write(subReq(i));
		}
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("Receive server response :[{}]",msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	private SubscribeReq subReq(int i) {
		return new SubscribeReq(i, "jiangxiaohuo", "Netty 权威指南", "138888888", "湖南长沙");
	}
}
