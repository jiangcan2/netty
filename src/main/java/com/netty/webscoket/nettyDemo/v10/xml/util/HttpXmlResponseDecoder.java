package com.netty.webscoket.nettyDemo.v10.xml.util;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder{

	public HttpXmlResponseDecoder(Class clazz) {
		this(clazz, false);
	}

	public HttpXmlResponseDecoder(Class clazz, boolean isPrintlog) {
        super(clazz, isPrintlog);
    }

	@Override
	protected void decode(ChannelHandlerContext ctx, Object o, List out) throws Exception {
		DefaultFullHttpResponse msg = (DefaultFullHttpResponse)o;
		HttpXmlResponse httpXmlResponse  = new HttpXmlResponse(msg,decode0(ctx, msg.content()));
		out.add(httpXmlResponse);
	}

}
