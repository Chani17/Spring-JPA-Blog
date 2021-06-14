package com.chani.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.chani.blog.dto.ResponseDto;

@ControllerAdvice	// 모든 exception이 발생하면 이 클래스로 들어오게 된다.
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());	// 500
	}
}
