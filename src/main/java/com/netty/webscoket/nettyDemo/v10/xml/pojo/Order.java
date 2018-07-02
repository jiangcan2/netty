package com.netty.webscoket.nettyDemo.v10.xml.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@XStreamAlias("order")
public class Order {

	@XStreamAsAttribute
    private long orderNumber;

    private Customer customer;

    private Address billTo;

    private Shipping shipping;

    private Address shipTo;
    @XStreamAsAttribute
    private Float total;
}
