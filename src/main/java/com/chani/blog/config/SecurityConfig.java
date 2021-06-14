package com.chani.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.chani.blog.config.auth.PrincipalDetailService;

// Bean 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration		// 빈 등록 (IoC관리)
@EnableWebSecurity		// security 필터로 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true)	// 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean		// IoC가 된다!!
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	 /*
	  * security가 대신 로그인을 해주는데 password를 가로채기를 하는데
	  *  해당 password가 뭘로 해쉬가 되어서 회원가입이 되었는지 알아야
	  *  같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	  */
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// controller에서 request를 처리하기 전 이 부분을 먼저 수행한다.
		http
			.csrf().disable()							// csrf 토큰 비활성화(테스트 시 걸어두는게 좋음!)
			.authorizeRequests()					//	 request가 들어오는데
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")		// '/auth'를 타고 들어오는 것은 
				.permitAll()								// 누구나 들어올 수 있다.
				.anyRequest()							// 위의 것이 아닌 다른 request는 
				.authenticated()						// 인증이 필요하다.
			.and()
				.formLogin()							// 인증이 되지 않은 어떠한 요청이 들어오면
				.loginPage("/auth/loginForm")	// 해당 주소로 이동
				.loginProcessingUrl("/auth/loginProc")		// 스프링 security가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
				.defaultSuccessUrl("/");				// 정상적으로 요청(로그인)이 완료가 되면 "/"로 이동
				
	}

	
	

}
