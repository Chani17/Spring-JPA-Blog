package com.chani.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.chani.blog.model.User;

import lombok.Getter;

 /*
  * 스프링 security가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면
  * UserDetails 타입의 오브젝트를 스프링 security의 고유한 세션 저장소에 저장을 해준다.
  */
@Getter
public class PrincipalDetail implements UserDetails{
	
	private User user;		// 컴포지션 : 객체를 품고 있는 것을 말함

	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지를 리턴한다. (true : 만료 안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨 있는지 아닌지를 리턴 (true : 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되었는지 아닌지를 리턴 (true : 만료 안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화(사용가능)인지 리턴한다. (true : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// 계정이 갖고 있는 권한 목록을 리런한다.
	// 권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 여기서는 한개만 존재하기 때문!
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(() -> { return "ROLE_" + user.getRole(); });
		return collectors;
	}
	
}
