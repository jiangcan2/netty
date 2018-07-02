package com.netty.webscoket.nettyDemo.v10.xml.pojo;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Customer {

	@XStreamAsAttribute
    private long customerNumber;

    private String firstName;

    private String lastName;

    private List<String> middleNames;
}
