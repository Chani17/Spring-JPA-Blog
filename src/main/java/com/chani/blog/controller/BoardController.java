package com.chani.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.chani.blog.config.auth.PrincipalDetail;
import com.chani.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	// 컨트롤러에서 세션을 어떻게 찾는지?
	@GetMapping({"","/"})	// "" : 아무것도 적지 않았을 때랑 "/": /를 붙였을 때 index()로 이동
	public String index(Model model, @PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {		/*@AuthenticationPrincipal PrincipalDetail principal*/
	
		model.addAttribute("boards", boardService.boardList(pageable));
		// /WEB-INF/views/index.jsp
		return "index";		// viewResolver 작동!!
	}
	
	// USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		// 글 상세보기
		model.addAttribute("board", boardService.boardMore(id));
		
		return "board/detail";	
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		// 글 수정하기
		model.addAttribute("board", boardService.boardMore(id));
		return "board/updateForm";
	}
}
