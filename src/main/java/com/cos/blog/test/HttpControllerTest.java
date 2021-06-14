package com.cos.blog.test;

import javax.servlet.jsp.tagext.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청시 html을 응답하는 어노테이션 -> @Controller

@RestController //사용자가 요청을 하게되면 응답(data)
public class HttpControllerTest {
	
	private static final String Tag = "HttpControllerTest";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		
		Member m = Member.builder().username("asd").password("asd").email("asd").build();
		System.out.println(Tag + "getter : " + m.getId());
		m.setId(5000);
		System.out.println(Tag + "setter : " + m.getId());
		
		return "lombok test완료";
		}
	
	//인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다.
	//http://localhost:8080/http/get - select
	@GetMapping("http/get")
	public String getTest(Member m) {	
		return "get요청 : " + m.getId() + m.getUsername();
	}
	
	//http://localhost:8080/http/post - insert
	@PostMapping("http/post")
	public String postTest() {
		return "post요청";
	}
	
	//http://localhost:8080/http/put - update
	@PutMapping("http/put")
	public String putTest() {
		return "put요청";
	}
	
	//http://localhost:8080/http/delete - delete
	@DeleteMapping("http/delete")
	public String deleteTest() {
		return "delete요청";
	}

}
