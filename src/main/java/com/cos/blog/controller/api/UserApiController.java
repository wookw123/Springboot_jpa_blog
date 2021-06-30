package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import com.cos.blog.config.auth.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    //@Autowired
    //private HttpSession session;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController : save 호출됨");
        userService.회원가입(user); //1 - 성공 , -1 - 실패
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //자바오브젝트를 JSON으로 변환해서 리턴 (Jackson이라는 라이브러리를 통해)
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user) { //@RequestBody - Json데이터를 받기위해 적음
        userService.회원수정(user);
        //여기서는 트랜잭션이 종료되기때문에 DB의 값은 변경이 됐음.
        //하지만 세션값은 변경되지 않아 바로적용되지 않는다.(다시 로그인을 해야 변경됨)
        //따라서 직접 세션값을 변경해주어야 한다.
        //세션등록

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
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
