package com.netty.webscoket.nettyDemo.v7.server;

import com.netty.webscoket.nettyDemo.v7.entity.SubscribeReq;
import com.netty.webscoket.nettyDemo.v7.entity.SubscribeResp;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class SubReqServerHandler extends ChannelHandlerAdapter{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		SubscribeReq req = (SubscribeReq)msg;
		if("jiangxiaohuo".equalsIgnoreCase(req.getUserName())) {
			log.info("Service accpet client subscribeReq req:[{}]",req.toString());
			ctx.writeAndFlush(this.resp(req.getSubReqID()));
		}
	}

	private SubscribeResp resp(int subReqID) {
		return new SubscribeResp(subReqID, 0, "Netty book order succeed,3 day later,sent to the designated address");
	}
}
