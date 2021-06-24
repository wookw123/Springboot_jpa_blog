package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	//@AuthenticationPrincipal PrincipalDetail principal
	@GetMapping({"","/"})
	public String index() {//

		return "index";
	}

	//USER권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm(){
		return "board/saveForm";
	}
}
