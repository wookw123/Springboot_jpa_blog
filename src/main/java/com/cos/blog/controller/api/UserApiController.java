package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.DTO.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;


	
	//@Autowired
	//private HttpSession session;

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user  ) {
		System.out.println("UserApiController : save 호출됨");
		userService.회원가입(user); //1 - 성공 , -1 - 실패
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 JSON으로 변환해서 리턴 (Jackson이라는 라이브러리를 통해)
	}
	
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
//		System.out.println("UserApiController : login 호출됨");
//		User principal =  userService.로그인(user);
//
//		if(principal != null){
//			session.setAttribute("principal",principal);
//		}
//		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
//	} //전통적 로그인 방식 사용안하고 시큐리티로 로그인 할것이다.
	

}
