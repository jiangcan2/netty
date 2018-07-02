package com.netty.webscoket.nettyDemo.v10.xml.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@XStreamAlias("billTo")
public class Address {

	private String street1;

    private String street2;

    private String city;

    private String state;

    private String postCode;

    private String country;
}
