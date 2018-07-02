package com.netty.webscoket.nettyDemo.v10.xml.util;

import java.util.List;

import org.springframework.http.HttpHeaders;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder{

	public HttpXmlRequestDecoder(Class clazz) {
		this(clazz, false);
	}

	public HttpXmlRequestDecoder(Class clazz, boolean isPrint) {
		super(clazz, isPrint);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
		FullHttpRequest arg1 = (FullHttpRequest)msg;
        //首先对HTTP请求消息本身的解码结果进行判断，如果已经解码失败，再对消息体进行二次解码已经没有意义。
		if (!arg1.getDecoderResult().isSuccess()) {
            //如果HTTP消息本身解码失败，则构造处理结果异常的HTTP应答消息返回给客户端。
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        //通过HttpXmlRequest和反序列化后的Order对象构造HttpXmlRequest实例，最后将它添加到解码结果List列表中。
        HttpXmlRequest request = new HttpXmlRequest(arg1, decode0(ctx,arg1.content()));
        out.add(request);
	}
	
	private static void sendError(ChannelHandlerContext ctx,
            HttpResponseStatus status) {
	FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
	status, Unpooled.copiedBuffer("Failure: " + status.toString()
	+ "\r\n", CharsetUtil.UTF_8));
	response.headers().set(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8");
	ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

}
