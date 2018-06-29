package com.netty.webscoket.nettyDemo.v7.protobuf.server;

import io.netty.channel.ChannelHandler.Sharable;

import com.netty.webscoket.nettyDemo.v7.protobuf.pojo.SubscribeReqProto;
import com.netty.webscoket.nettyDemo.v7.protobuf.pojo.SubscribeResqProto;

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
		SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)msg;
		if("jiangxiaohuo".equalsIgnoreCase(req.getUserName())) {
			log.info("Service accept client subscribe req:[{}]",req.toString());
			ctx.writeAndFlush(this.resp(req.getSubReqID()));
		}
	}

	private SubscribeResqProto.SubscribeResq resp(int subReqId){
		SubscribeResqProto.SubscribeResq.Builder builder = SubscribeResqProto.SubscribeResq.newBuilder();
		builder.setSubReqID(subReqId);
		builder.setRespCode(0);
		builder.setDesc("Netty book order succeed, 3 day later, send to the designated address");
		return builder.build();
	}
}
