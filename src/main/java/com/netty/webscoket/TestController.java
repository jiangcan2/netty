package com.netty.webscoket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {
	
	@GetMapping("/hello")
	public String hello() {
		log.info("hello");
		return "hello";
	}

}
