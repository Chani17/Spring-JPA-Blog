package com.chani.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chani.blog.model.RoleType;
import com.chani.blog.model.User;
import com.chani.blog.repository.UserRepository;


@Service		// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void joinMemer(User user) {
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);		// 해쉬화
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	@Transactional
	public void update(User user) {
		
		/*
		 * 수정시에는 영속성 컨텍트스 USer 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정
		 * select를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!!
		 * 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
		 */
		
		User persistance = userRepository.findById(user.getId())
		.orElseThrow(() -> {
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		// Validate 체크 -> 카카로 연동 로그인자는 패스워드, 이메일 변경 절대 불가!
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String enPassword = encoder.encode(rawPassword);
			persistance.setPassword(enPassword);
			persistance.setEmail(user.getEmail());
		}
		
		
		// 회원수정 함수 종료시 = Service 종료 = 트랜잭션 종료 = commit이 자동으로 된다.
		// 영속회된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려준다.
	}
	
	@Transactional(readOnly = true)
	public User findMember(String username) {
		
		User user = userRepository.findByUsername(username)
				.orElseGet(() -> {
					return new User();
				});
		return user;
	}
}
