package com.cos.blog.controller.api;

import com.cos.blog.DTO.ReplySaveDto;
import com.cos.blog.DTO.ResponseDto;
import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;

	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board , @AuthenticationPrincipal PrincipalDetail principal) {

		boardService.글쓰기(board,principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 JSON으로 변환해서 리턴 (Jackson이라는 라이브러리를 통해)
	}

	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteByid(@PathVariable int id){
		boardService.삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //1을 리턴하면 정상
	}

	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id , @RequestBody Board board){

		boardService.글수정하기(id,board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);

	}

	
	//데이터를 만들때 컨트롤러에서 DTO를 만들어서 받는것이 좋다
	//현재까지 DTO를 사용하지 않은 이유는?(현재 프로젝트가 작아서 그렇지만 큰 프로젝트에서 많은 데이터들이 오고갈땐 사용해야 좋다)
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveDto replySaveDto) {

		boardService.댓글작성(replySaveDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 JSON으로 변환해서 리턴 (Jackson이라는 라이브러리를 통해)
	}





}
