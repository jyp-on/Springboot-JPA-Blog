package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;

import com.cos.blog.config.auth.PrincipalDetailService;


//시큐리티 기본 3요소 어노테이션
@Configuration //빈등록
@EnableWebSecurity //시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true)//특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //IoC가 되요.
	public BCryptPasswordEncoder encodePWD() {
		 return new BCryptPasswordEncoder();
	}
	
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
	}
	
	 //시큐리티가 대신 로그인해주는데 password 가로채기를 하는데
	 //해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지를 알아야
	 //같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	} //username이 맞는지 확인, pwd는 인코딩 정보를 알려줘서 자동으로 알게해줌.
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //csrf토큰 비활성화 (테스트시 비활성화)
			.authorizeRequests()
				.antMatchers("/" , "/?page=**", "/auth/**", "/js/**", "/css/**", "/image/**") //이것들은 접근가능
				.permitAll()
				.anyRequest()    //auth외에 다른 페이지들은
				.authenticated() //인증이 되야함.
			.and()
				.formLogin()
				.loginPage("/auth/loginForm") //auth가 아닌 다른곳을 가면 이곳으로 이동
				.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 로그인을 가로챈다.
				.defaultSuccessUrl("/");
				
	}
}
