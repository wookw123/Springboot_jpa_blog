package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//스프링이 컴포넌트 스캔을 통해 Bean에 등록을 해줌. ioc를 해준다
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public void 글쓰기(Board board , User user) {

		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

//	@Transactional(readOnly = true) //Select할때 트랜잭션 시작 , 서비스 종료시 트랜잭션 종료 (정합성을 유지시켜준다)
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}
