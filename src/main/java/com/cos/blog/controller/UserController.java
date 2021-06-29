package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class UserController {

	//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/**만 허용
	//그냥 주소가 /이면 index.jsp 허용
	//static이하에 있는 js파일이나 css, image이하의 폴더 허용
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {

		return "user/updateForm";
	}

	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code){

		//POST방식으로 ket=value 데이터를 요청(카카오쪽으로)
		//Retrofit2 , OkHttp , RestTemplate 라이브러리가 있다

		RestTemplate rt = new RestTemplate();

		//http오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type" , "application/x-www-form-urlencoded;charset=utf-8"); //내가 지금 전송할 http body의 데이터가 key-value타입이라고 알려준다
		MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
		params.add("grant_type","authorization_code");
		params.add("client_id","7c4da66cf5f7789db2a26014686d07be");
		params.add("redirect_uri ","http://localhost:8000/auth/kakao/callback");
		params.add("code",code);

		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
			new HttpEntity<>(params,headers);

		//Http요청하기 - Post방식으로. 그리고 Responst변수의 응답을 받음
	ResponseEntity<String> response = rt.exchange(

				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
		);

		return "카카오 토큰 요청 완료 : 토큰요청에 대한 응답 = " + response;
	}

}
