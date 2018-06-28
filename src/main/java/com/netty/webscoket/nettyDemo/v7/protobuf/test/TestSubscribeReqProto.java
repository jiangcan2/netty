package com.netty.webscoket.nettyDemo.v7.protobuf.test;

import java.util.Arrays;

import com.google.protobuf.InvalidProtocolBufferException;
import com.netty.webscoket.nettyDemo.v7.protobuf.SubscribeReqProto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSubscribeReqProto {
	
	private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
		return req.toByteArray();
	}
	
	private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException{
		return SubscribeReqProto.SubscribeReq.parseFrom(body);
	}
	
	private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(1);
		builder.setUserName("jiangxiaohuo");
		builder.setProductName("Netty book");
		builder.addAllAddress(Arrays.asList("NanJing YuHuaTai","BeiJing LiuLiChang","ShenZhen HongShuLin"));
		return builder.build();
	}
	
	public static void main(String[] args) throws InvalidProtocolBufferException {
		SubscribeReqProto.SubscribeReq req = createSubscribeReq();
		log.info("Before encode : {}" ,req.toString());
		SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
		log.info("After decode : {}" ,req2.toString());
		log.info("Assert equal : --> {}" ,req2.equals(req));
	}

}
