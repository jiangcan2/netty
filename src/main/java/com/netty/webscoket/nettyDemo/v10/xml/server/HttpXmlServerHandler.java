package com.netty.webscoket.nettyDemo.v10.xml.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;

import com.netty.webscoket.nettyDemo.v10.xml.pojo.Address;
import com.netty.webscoket.nettyDemo.v10.xml.pojo.Order;
import com.netty.webscoket.nettyDemo.v10.xml.util.HttpXmlRequest;
import com.netty.webscoket.nettyDemo.v10.xml.util.HttpXmlResponse;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {
	
	@Override
	protected void messageReceived(final ChannelHandlerContext ctx, HttpXmlRequest msg) throws Exception {
		HttpXmlRequest xmlRequest = (HttpXmlRequest)msg;
        HttpRequest request = xmlRequest.getRequest();
        Order order = (Order) xmlRequest.getBody();
        log.info("Http server receive request : " + order);
        dobusiness(order);
        ChannelFuture future = ctx.writeAndFlush(new HttpXmlResponse(null,
                order));
        if (!io.netty.handler.codec.http.HttpHeaders.isKeepAlive(request)) {
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
				@Override
				public void operationComplete(Future<? super Void> future) throws Exception {
	                    ctx.close();
				}
			});
        }
	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, Unpooled.copiedBuffer("失败: " + status.toString()
                + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
	
	private void dobusiness(Order order) {
        order.getCustomer().setFirstName("狄");
        order.getCustomer().setLastName("仁杰");
        List<String> midNames = new ArrayList<>();
        midNames.add("李元芳");
        order.getCustomer().setMiddleNames(midNames);
        Address address = order.getBillTo();
        address.setCity("洛阳");
        address.setCountry("大唐");
        address.setState("河南道");
        address.setPostCode("123456");
        order.setBillTo(address);
        order.setShipTo(address);
    }

}
