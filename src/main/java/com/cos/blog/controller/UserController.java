package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Controller
public class UserController {

	@Value("${cos.key}")
	private String cosKey;

	//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/**만 허용
	//그냥 주소가 /이면 index.jsp 허용
	//static이하에 있는 js파일이나 css, image이하의 폴더 허용
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
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
	public String kakaoCallback(String code){

		//POST방식으로 ket=value 데이터를 요청(카카오쪽으로)
		//Retrofit2 , OkHttp , RestTemplate 라이브러리가 있다

		RestTemplate rt = new RestTemplate();

		//httpHeader오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type" , "application/x-www-form-urlencoded;charset=utf-8"); //내가 지금 전송할 http body의 데이터가 key-value타입이라고 알려준다

		//httpBody오브젝트 생성
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

		//json을 오브젝트에 담을때 사용할 수 있는 라이브러리들
		//Gson, JsonSimple , objectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(),OAuthToken.class);
		}catch (JsonMappingException e){
			e.printStackTrace();
		}catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("카카오엑세스토큰 : " + oauthToken.getAccess_token());


		RestTemplate rt2 = new RestTemplate();

		//httpHeader오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization","Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type" , "application/x-www-form-urlencoded;charset=utf-8"); //내가 지금 전송할 http body의 데이터가 key-value타입이라고 알려준다


		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 =
				new HttpEntity<>(headers2);

		//Http요청하기 - Post방식으로. 그리고 Responst변수의 응답을 받음
		ResponseEntity<String> response2 = rt2.exchange(

				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
		);

		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoprofile = null;
		try {
			kakaoprofile = objectMapper2.readValue(response2.getBody(),KakaoProfile.class);
		}catch (JsonMappingException e){
			e.printStackTrace();
		}catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		//User 오브젝트 : username, password, email

		//UUID Garbagepassword = UUID.randomUUID();

		//UUID -> 중복되지 않는 특정 값을 만들어내는 알고리즘즘
	System.out.println("블로그서버 패스워드 : " +  cosKey);

		User kakaoUser = User.builder()
				.username(kakaoprofile.getKakao_account().getEmail()+"_"+kakaoprofile.getId())
				.password(cosKey)
				.email(kakaoprofile.getKakao_account().getEmail())
				.oauth("kakao")//회원가입시 oauth에 kakao라는 값을 넣어준다 이유는 회원정보수정시 카카오사용자는 회원정보를 수정할 수 없게.
				.build();


		User originuser = userService.회원찾기(kakaoUser.getUsername());

		//가입자 혹은 비 가입자 체크해서 회원가입처리해야한다
		if(originuser.getUsername()==null){//비가입자일경우
			System.out.println("기존회원이 아닙니다");
			userService.회원가입(kakaoUser);
		}


		//가입자일경우 로그인처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		System.out.println("로그인 완료 입니다");
		return "redirect:/";
	}

}
