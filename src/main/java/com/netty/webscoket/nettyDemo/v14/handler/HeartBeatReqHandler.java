package com.netty.webscoket.nettyDemo.v14.handler;

import java.util.concurrent.TimeUnit;

import com.netty.webscoket.nettyDemo.v14.pojo.Header;
import com.netty.webscoket.nettyDemo.v14.pojo.MessageType;
import com.netty.webscoket.nettyDemo.v14.pojo.NettyMessage;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatReqHandler extends ChannelHandlerAdapter {

	private volatile ScheduledFuture<?> heartBeat;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage)msg;
		//握手成功，主动发送心跳消息
		if(message.getHeader() != null 
				&& message.getHeader().getType() != MessageType.LOGIN_RESP.value()) {
			ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HeartBeatTask(ctx), 0, 5000 , TimeUnit.MILLISECONDS);
		}else if(message.getHeader() != null 
				&& message.getHeader().getType() != MessageType.HEARTBEAT_RESP.value()) {
			log.info("Client receive server heart beat message : --> {}", message);
		}else {
			ctx.fireChannelRead(msg);
		}
	}
	
	
	private class HeartBeatTask implements Runnable{

		private final ChannelHandlerContext ctx;
		
		
		public HeartBeatTask(ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}



		@Override
		public void run() {
			NettyMessage heatBeat = buildHeatBeat();
			log.info("Client send heart beat message to server: --> {}",heatBeat);
			ctx.writeAndFlush(heatBeat);
		}
		
		private NettyMessage buildHeatBeat() {
			NettyMessage message = new NettyMessage();
			Header header = new Header();
			header.setType(MessageType.HEARTBEAT_REQ.value());
			message.setHeader(header);
			return message;
		}
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(heartBeat != null) {
			heartBeat.cancel(true);
			heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
	}
	
}
