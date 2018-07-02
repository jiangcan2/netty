package com.netty.webscoket.nettyDemo.v10.xml.util;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public abstract class AbstractHttpXmlEncoder extends MessageToMessageEncoder{
	
	IBindingFactory factory = null;
    StringWriter writer = null;
    final static String CHARSET_NAME = "UTF-8";
    final static Charset UTF_8 = Charset.forName(CHARSET_NAME);
    
    protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) throws JiBXException, IOException {
    	//在此将业务的Order实例序列化为XML字符串。
    	factory = BindingDirectory.getFactory(body.getClass());
    	writer = new StringWriter();
    	IMarshallingContext mctx = factory.createMarshallingContext();
    	mctx.setIndent(2);
    	mctx.marshalDocument(body, CHARSET_NAME, null, writer);
    	String xmlStr = writer.toString();
    	writer.close();
    	writer = null;
    	//将XML字符串包装成Netty的ByteBuf并返回，实现了HTTP请求消息的XML编码。
    	ByteBuf buf =Unpooled.copiedBuffer(xmlStr, UTF_8);
    	return buf;
    }
    
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(writer != null) {
			writer.close();
			writer = null;
		}
	}

}
