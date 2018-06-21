package com.netty.webscoket.conf;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * netty 全局配置
 * @author admin
 *
 */
public class NettyConfig {
	
	/**
	 * 存储每一个客户端进来的channel
	 */
	public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}
