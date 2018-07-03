package com.netty.webscoket.nettyDemo.v14.pojo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public final class NettyMessage {
	
	private Header header;//消息头
	
	private Object body;//消息体

}
