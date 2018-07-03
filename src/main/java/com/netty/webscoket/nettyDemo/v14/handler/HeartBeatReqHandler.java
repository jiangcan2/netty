package com.netty.webscoket.nettyDemo.v14.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;

public class HeartBeatReqHandler extends ChannelHandlerAdapter {

	private volatile ScheduledFuture<?> heartBeat;
	
}
