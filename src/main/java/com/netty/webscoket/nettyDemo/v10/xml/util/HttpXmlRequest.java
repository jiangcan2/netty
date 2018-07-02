package com.netty.webscoket.nettyDemo.v10.xml.util;

import io.netty.handler.codec.http.FullHttpRequest;
import lombok.Data;

@Data
public class HttpXmlRequest {

	//它包含两个成员变量FullHttpRequest和编码对象Object，用于实现和协议栈之间的解耦。
    private FullHttpRequest request;
    private Object body;
    
	public HttpXmlRequest(FullHttpRequest request, Object body) {
		this.request = request;
		this.body = body;
	}
    
}
