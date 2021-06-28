package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board 글상세보기(int id){
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다");
				});
	}

	@Transactional
	public void 삭제하기(int id){

		boardRepository.deleteById(id);
	}

	@Transactional
	public void 글수정하기(int id , Board requBoard){
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다");
				}); //영속화 완료
		board.setTitle(requBoard.getTitle());
		board.setContent(requBoard.getContent());
		//해당함수 종료시 (Service가 종료될때) 트랜잭션이 종료. 이때 더티체킹-자동업데이트가 됨.DB Flush
	}

//	@Transactional(readOnly = true) //Select할때 트랜잭션 시작 , 서비스 종료시 트랜잭션 종료 (정합성을 유지시켜준다)
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}
