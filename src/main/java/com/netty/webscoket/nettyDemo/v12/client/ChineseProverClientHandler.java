package com.netty.webscoket.nettyDemo.v12.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChineseProverClientHandler extends SimpleChannelInboundHandler<DatagramPacket>{

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		String resp = msg.content().toString(CharsetUtil.UTF_8);
		if(resp.startsWith("谚语字典查询结果：")) {
			log.info(resp);
			ctx.close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
