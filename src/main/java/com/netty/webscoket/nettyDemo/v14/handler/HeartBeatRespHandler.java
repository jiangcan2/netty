package com.netty.webscoket.nettyDemo.v14.handler;

import java.util.concurrent.TimeUnit;

import com.netty.webscoket.nettyDemo.v14.pojo.Header;
import com.netty.webscoket.nettyDemo.v14.pojo.MessageType;
import com.netty.webscoket.nettyDemo.v14.pojo.NettyMessage;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatRespHandler extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage)msg;
		//返回心跳应答消息
		if(message.getHeader() != null 
				&& message.getHeader().getType() != MessageType.LOGIN_REQ.value()) {
			log.info("Receive client heart beat message : --> {}",message);
			NettyMessage heatBeat = buildHeatBeat();
			log.info("send heart beat response message to client : --> {}", heatBeat);
			ctx.writeAndFlush(heatBeat);
		}else {
			ctx.fireChannelRead(msg);
		}
	}

	private NettyMessage buildHeatBeat() {
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.HEARTBEAT_RESP.value());
		message.setHeader(header);
		return message;
	}
	
}
