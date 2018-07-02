package com.netty.webscoket.nettyDemo.v10.xml.util;

import java.util.List;

import org.springframework.http.HttpHeaders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object o, List out) throws Exception {
		HttpXmlResponse msg = (HttpXmlResponse) o;
        ByteBuf body = encode0(ctx, msg.getResult());
        FullHttpResponse response = msg.getResponse();
        if (response == null) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
        } else {
            response = new DefaultFullHttpResponse(msg.getResponse()
                    .getProtocolVersion(), msg.getResponse().getStatus(),
                    body);
        }
        response.headers().set(HttpHeaders.CONTENT_TYPE, "text/xml");
        io.netty.handler.codec.http.HttpHeaders.setContentLength(response, body.readableBytes());
        out.add(response);
	}

}
