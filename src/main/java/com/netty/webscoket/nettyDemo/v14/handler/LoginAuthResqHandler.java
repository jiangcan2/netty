package com.netty.webscoket.nettyDemo.v14.handler;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.netty.webscoket.nettyDemo.v14.pojo.Header;
import com.netty.webscoket.nettyDemo.v14.pojo.MessageType;
import com.netty.webscoket.nettyDemo.v14.pojo.NettyMessage;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginAuthResqHandler extends ChannelHandlerAdapter{

	private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();
	
	private String[]  whitekList = {"127.0.0.1"};

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage)msg;
		//如果是握手请求消息，处理，其他消息透传
		if(message != null 
				&& message.getHeader().getType() ==  MessageType.LOGIN_REQ.value()) {
			String nodeIndex = ctx.channel().remoteAddress().toString();
			NettyMessage loginResp = null;
			//重复登录，拒绝
			if(nodeCheck.containsKey(nodeIndex)) {
				loginResp = buildResponse((byte)-1);
			}else {
				InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
				String ip = address.getAddress().getHostAddress();
				boolean isOK = false;
				for (String WIP:whitekList) {
					if(WIP.equals(ip)) {
						isOK = true;
						break;
					}
				}
				loginResp = isOK? buildResponse((byte)0): buildResponse((byte)-1);
				
				if(isOK) {
					nodeCheck.put(nodeIndex, true);
				}
				log.info("The login response is :{} body :[{}]",loginResp,loginResp.getBody());
				ctx.writeAndFlush(loginResp);
			}
		}else {
			ctx.fireChannelRead(message);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		nodeCheck.remove(ctx.channel().remoteAddress().toString());
		ctx.fireExceptionCaught(cause);
	}



	private NettyMessage buildResponse(byte result) {
		NettyMessage nettyMessage = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.LOGIN_RESP.value());
		nettyMessage.setHeader(header);
		return nettyMessage;
	}
	
}
