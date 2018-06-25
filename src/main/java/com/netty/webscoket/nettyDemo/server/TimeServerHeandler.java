package com.netty.webscoket.nettyDemo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class TimeServerHeandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	   ByteBuf buf = (ByteBuf) msg;
	   byte[] req = new byte[buf.readableBytes()];
	   buf.readBytes(req);
	   String body = new String(req,"UTF-8");
	   log.info("The time server receive order:{}",body);
	   String cuurrentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
	   ByteBuf resp = Unpooled.copiedBuffer(cuurrentTime.getBytes());
	   ctx.write(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

}
