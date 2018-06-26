package com.netty.webscoket.nettyDemo.v5.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class EchoServerHandler extends ChannelHandlerAdapter{
	
	int counter = 0;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	/*DelimiterBasedFrameDecoder 测试时实用
	 * @Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String)msg;
		log.info("This is {} times receive client:[{}]",++counter,body);
		body+="$_";
		ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
		ctx.writeAndFlush(echo);
	}*/

	/**
	 * FixedLengthFrameDecoder 时使用代码
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("Receive client:[{}]",msg);
	}
}
