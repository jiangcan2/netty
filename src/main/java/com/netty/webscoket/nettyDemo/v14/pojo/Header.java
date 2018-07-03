package com.netty.webscoket.nettyDemo.v14.pojo;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class Header {

	private int crcCode = 0xabef0101;
	
	private int length;//消息长度
	
	private long sessionID;//会话ID
	
	private byte type;//消息类型
	
	private byte priority;//消息优先级
	
	private Map<String, Object> attachment = new HashMap<>();//附件

	public Header(int crcCode, int length, long sessionID, byte type, byte priority) {
		this.crcCode = crcCode;
		this.length = length;
		this.sessionID = sessionID;
		this.type = type;
		this.priority = priority;
	}
}
