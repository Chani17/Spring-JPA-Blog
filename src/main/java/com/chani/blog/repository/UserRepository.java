package com.chani.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chani.blog.model.User;

// DAO
// 자동으로 Bean 등록이 된다.
// @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer>{
	
	// SELECT * FROM user WHERE username = ?1;
	Optional<User> findByUsername(String username);
}

	//JPA Naming 쿼리
	// select * from user where username = ?1 and password = ?2;
	//User findByUsernameAndPassword(String username, String password);
	
	//	@Query(value="select * from user where username =?1 and password = ?2", nativeQuery = true)
	//	User login(String username, String password);
