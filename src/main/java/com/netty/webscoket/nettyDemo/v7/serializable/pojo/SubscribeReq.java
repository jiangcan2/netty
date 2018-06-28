package com.netty.webscoket.nettyDemo.v7.serializable.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SubscribeReq implements Serializable{

	@Override
	public String toString() {
		return "SubscribeReq [ subReqID = "+ this.subReqID +","
				+ " userName = "+ this.userName +","
				+ " productName = "+ this.productName +","
				+ " phoneNumber = "+ this.phoneNumber +","
				+ " address = "+ this.address +"]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 订单编号
	 */
	private int subReqID;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 订购的产品名称
	 */
	private String productName;
	
	/**
	 * 订购者电话号码
	 */
	private String phoneNumber;
	
	/**
	 * 订购者的家庭住址
	 */
	private String address;

	public SubscribeReq(int subReqID, String userName, String productName, String phoneNumber, String address) {
		this.subReqID = subReqID;
		this.userName = userName;
		this.productName = productName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
	
}
