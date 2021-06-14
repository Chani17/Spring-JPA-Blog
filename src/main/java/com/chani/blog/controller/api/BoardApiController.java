package com.chani.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chani.blog.config.auth.PrincipalDetail;
import com.chani.blog.dto.ReplySaveRequestDto;
import com.chani.blog.dto.ResponseDto;
import com.chani.blog.model.Board;
import com.chani.blog.model.Reply;
import com.chani.blog.model.RoleType;
import com.chani.blog.model.User;
import com.chani.blog.service.BoardService;
import com.chani.blog.service.UserService;

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal ) {
		boardService.write(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);	
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		boardService.deleteById(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);	
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
		System.out.println("boardapicontroller : " + id);
		System.out.println("boardapicontroller : " + board.getTitle());
		System.out.println("boardapicontroller : " + board.getContent());
		boardService.update(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	/*
	 * 데이터를 받을 때, 컨트롤러에서 dto를 만들어서 받는게 좋다.
	*/
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
		
		boardService.writeComment(replySaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);	
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer>replyDelete(@PathVariable int replyId) {
		boardService.deleteComment(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
