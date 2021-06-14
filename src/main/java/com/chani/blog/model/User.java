package com.chani.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM -> JAVA(다른 언어들 포함) Object -> 테이블로 매핑해주는 기술

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder		// 빌더 패턴!!
//@DynamicInsert		// insert시에 null인 필드를 제외시켜준다.
@Entity	// User 클래스가 자동으로 MySQL에 테이블이 생성된다.
public class User {
	
	@Id		// Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)		// 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id;		//sequence, auto_increment
	
	@Column(nullable = false, length = 100, unique = true)
	private String username;		// 아이디
	
	@Column(nullable = false, length = 100)	// 비밀번호를 암호화(해쉬)를 해야하기 때문에 길이를 넉넉하게 잡음!
	private String password;
	
	@Column(nullable = false, length = 50) 
	private String email;
	
	//@ColumnDefault("'user'")
	@Enumerated(EnumType.STRING)		// DB는 RoleType라는게 없다.
	private RoleType role;		// Enum을 쓰는게 좋다.
	
	private String oauth;			// kakao, google
	
	@CreationTimestamp   // 시간이 자동 입력
	private Timestamp createDate;
}
