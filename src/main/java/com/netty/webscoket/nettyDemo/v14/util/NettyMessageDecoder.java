package com.netty.webscoket.nettyDemo.v14.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.netty.webscoket.nettyDemo.v14.pojo.Header;
import com.netty.webscoket.nettyDemo.v14.pojo.NettyMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder{
	
	MarshallingDecoder marshallingDecoder;

	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
			int initialBytesToStrip) throws IOException {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
		marshallingDecoder = new MarshallingDecoder();
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf frame = (ByteBuf)super.decode(ctx, in);
		if(frame == null) {
			return null;
		}
		NettyMessage message = new NettyMessage();
		Header header = new Header(in.readInt(), in.readInt(), in.readLong(), in.readByte(), in.readByte());
		int size = in.readInt();
		if(size > 0) {
			Map<String, Object> attch = new HashMap<>(size);
			int keySize = 0;
			byte[] keyArray = null;
			String key = null;
			for (int i = 0; i < size; i++) {
				keySize = in.readInt();
				keyArray = new byte[keySize];
				in.readBytes(keyArray);
				key = new String(keyArray, "UTF-8");
				attch.put(key, marshallingDecoder.decode(in));
			}
			keyArray = null;
			key = null;
			header.setAttachment(attch);
		}
		
		if(in.readableBytes() > 4 ) {
			message.setBody(marshallingDecoder.decode(in));
		}
		message.setHeader(header);
		return message;
	}

}
