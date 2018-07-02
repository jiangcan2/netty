package com.netty.webscoket.nettyDemo.v12.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket>{

	private static final String[] DICTIONARY= {"只要功夫深，铁棒也能磨成针",
			"旧时王谢堂前燕，飞入寻常百姓家",
			"洛阳亲友如相问，一片冰心在玉壶",
			"一寸光阴一寸金，寸金难买寸光阴",
			"老骥伏枥，志在千里，烈士暮年，壮心不已！"};
	
	private String nextCode() {
		int quoteId= ThreadLocalRandom.current().nextInt(DICTIONARY.length);
		return DICTIONARY[quoteId];
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		String req = msg.content().toString(CharsetUtil.UTF_8);
		log.info(req);
		if("谚语字典查询？".equals(req)) {
			ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语字典查询结果："+nextCode(),CharsetUtil.UTF_8), msg.sender()));
		}
	}
}
