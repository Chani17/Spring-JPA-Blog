package com.chani.blog.test;

import org.junit.jupiter.api.Test;

import com.chani.blog.model.Reply;

public class ReplyObjectTest {
	
	@Test
	public void toStringTest() {
		Reply reply = Reply.builder()
				.id(1)
				.user(null)
				.board(null)
				.content("안녕")
				.build();
		
		System.out.println(reply);		// 오브젝트 출력시에 toString()이 자동 호출됨.
	}
}
