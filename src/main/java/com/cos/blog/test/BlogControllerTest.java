package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //스프링이 com.cos.blog 패키지 이하를 스캔해서 특정 어노테이션이 붙어있는 클래스파일들을 new()해서(ioc 제어역전) 스프링컨테이너에 관리.
public class BlogControllerTest {
	
	//http://localhost:8080/test/hello
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>hello Spring</h1>";
	}

}
