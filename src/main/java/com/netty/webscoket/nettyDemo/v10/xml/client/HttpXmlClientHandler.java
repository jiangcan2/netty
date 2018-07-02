package com.netty.webscoket.nettyDemo.v10.xml.client;

import com.netty.webscoket.nettyDemo.v10.xml.pojo.OrderFactory;
import com.netty.webscoket.nettyDemo.v10.xml.util.HttpXmlRequest;
import com.netty.webscoket.nettyDemo.v10.xml.util.HttpXmlResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse> {

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
		HttpXmlResponse response = (HttpXmlResponse)msg;
		log.info("The client receive response of http header is : " + response.getResponse().headers().names());
		log.info("The client receive response of http body is : " + response.getResult());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		HttpXmlRequest request = new HttpXmlRequest(null,OrderFactory.create(123));
        ctx.writeAndFlush(request);
	}

}
