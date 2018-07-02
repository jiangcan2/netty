package com.netty.webscoket.nettyDemo.v10.xml.util;

import java.io.StringReader;
import java.nio.charset.Charset;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractHttpXmlDecoder extends MessageToMessageDecoder {

	private IBindingFactory factory;
	
	private StringReader reader;
	
	private Class clazz;
	
	private boolean isPrint;
	
	private final static String CHARSET_NAME = "UTF-8";
    
	private final static Charset UTF_8 = Charset.forName(CHARSET_NAME);

	protected AbstractHttpXmlDecoder(Class clazz) {
        this(clazz, false);
    }
    protected AbstractHttpXmlDecoder(Class clazz, boolean isPrint) {
        this.clazz = clazz;
        this.isPrint = isPrint;
    }
    
    protected Object decode0(ChannelHandlerContext arg0, ByteBuf body) throws JiBXException {
    	factory = BindingDirectory.getFactory(clazz);
    	String content = body.toString();
    	 //根据码流开关决定是否打印消息体码流。
        //增加码流开关往往是为了方便问题定位，在实际项目中，需要打印到日志中。
    	if(isPrint) {
    		log.info("The body is : {}" , content);
    	}
    	reader = new StringReader(content);
    	IUnmarshallingContext uctx = factory.createUnmarshallingContext();
    	Object result = uctx.unmarshalDocument(reader);
        reader.close();
        reader = null;
        return result;
    }
    
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(reader != null) {
			reader.close();
			reader = null;
		}
	}
	
}
