package com.netty.webscoket.nettyDemo.v7.protobuf.client;

import java.util.Arrays;

import com.netty.webscoket.nettyDemo.v7.protobuf.pojo.SubscribeReqProto;
import com.netty.webscoket.nettyDemo.v7.protobuf.pojo.SubscribeResqProto;

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
			ctx.write(this.subReq(i));
		}
		ctx.flush();
	}
	
	private SubscribeReqProto.SubscribeReq subReq(int subReqID){
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(subReqID);
		builder.setUserName("jiangxiaohuo");
		builder.setProductName("Netty Book For Protobuf");
		builder.addAllAddress(Arrays.asList("NanJing YuHuaTai","BeiJing LiuLiChang","ShenZhen HongShuLin"));
		return builder.build();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		SubscribeResqProto.SubscribeResq resp = (SubscribeResqProto.SubscribeResq)msg;
		log.info("Receive server response:[{}]",resp.toString());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

}
