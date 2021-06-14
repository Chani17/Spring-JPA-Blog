package com.chani.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chani.blog.config.auth.PrincipalDetail;
import com.chani.blog.dto.ResponseDto;
import com.chani.blog.model.RoleType;
import com.chani.blog.model.User;
import com.chani.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save 호출됨");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 된다.
		userService.joinMemer(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);	// 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) {		// json -> @RequestBody,  x-www-form-urlencoded -> key=value
		userService.update(user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB의 값은 변경이 되었음!
		// 하지만 세션값은 변경되지 않았기 때문에 직접 세션값을 변경해줘야 함!
		// 세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
}
