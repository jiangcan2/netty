package com.netty.webscoket.nettyDemo.v10.xml.util;

import io.netty.handler.codec.http.FullHttpResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class HttpXmlResponse {

	private FullHttpResponse response;
	
	private Object result;

	public HttpXmlResponse(FullHttpResponse response, Object result) {
		this.response = response;
		this.result = result;
	}
	
}
