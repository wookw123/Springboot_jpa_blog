package com.cos.blog.service;
import com.cos.blog.model.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해 Bean에 등록을 해줌. ioc를 해준다
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;



	@Transactional
	public void 회원가입(User user) {

		String rowPassword = user.getPassword(); //입력받은 비밀번호 원문
		String encPassword = encoder.encode(rowPassword); //비밀번호 해쉬화
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

	@Transactional
	public  void 회원수정(User user){
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화 시키고, 영속화된 User오브젝트를 수정
		//select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기위해서
		//영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준더
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원찾기 실패");
		});
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());

		//세션등록


		//회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 실행
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려준다.
	}

//	@Transactional(readOnly = true) //Select할때 트랜잭션 시작 , 서비스 종료시 트랜잭션 종료 (정합성을 유지시켜준다)
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}
