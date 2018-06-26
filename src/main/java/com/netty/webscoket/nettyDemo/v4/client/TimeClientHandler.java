package com.netty.webscoket.nettyDemo.v4.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeClientHandler extends ChannelHandlerAdapter {
	
	private int counter = 0;
	
	private byte[] req ;

//	private final ByteBuf firstMessage;

	public TimeClientHandler() {
		req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
//		firstMessage = Unpooled.buffer(req.length);
//		firstMessage.writeBytes(req);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	cause.getSuppressed();
    	//释放资源
    	log.info("Unexpected exception from downstream :{}",cause.getMessage());
    	ctx.close();
    }

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message = null;
		for (int i = 0; i < 100; i++) {
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}
	}

	/*@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		log.info("Now is:{}; the counter is:{}",body,++counter);
	}*/

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String)msg;
		log.info("Now is:{}; the counter is:{}",body,++counter);
	}
}
