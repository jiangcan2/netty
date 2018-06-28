package com.netty.webscoket.nettyDemo.v7.serializable.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SubscribeResp implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "SubscribeResp [subReqID = "+ this.subReqID +","
				+ " respCode = "+ this.respCode +","
				+ " desc ="+ this.desc +"]";
	}
	
	/**
	 * 订单编号
	 */
	private int subReqID;


	/**
	 * 订购结果：0表示成功
	 */
	private int respCode;
	
	/**
	 * 可选的详细描述信息
	 */
	private String desc;

	public SubscribeResp(int subReqID, int respCode, String desc) {
		this.subReqID = subReqID;
		this.respCode = respCode;
		this.desc = desc;
	}

}
