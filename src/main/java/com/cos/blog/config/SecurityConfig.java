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


//��ť��Ƽ �⺻ 3��� ������̼�
@Configuration //����
@EnableWebSecurity //��ť��Ƽ ���Ͱ� ����� �ȴ�.
@EnableGlobalMethodSecurity(prePostEnabled = true)//Ư�� �ּҷ� ������ �ϸ� ���� �� ������ �̸� üũ�ϰڴٴ� ��
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //IoC�� �ǿ�.
	public BCryptPasswordEncoder encodePWD() {
		 return new BCryptPasswordEncoder();
	}
	
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
	}
	
	 //��ť��Ƽ�� ��� �α������ִµ� password ����ä�⸦ �ϴµ�
	 //�ش� password�� ���� �ؽ��� �Ǿ� ȸ�������� �Ǿ������� �˾ƾ�
	 //���� �ؽ��� ��ȣȭ�ؼ� DB�� �ִ� �ؽ��� ���� �� ����
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	} //username�� �´��� Ȯ��, pwd�� ���ڵ� ������ �˷��༭ �ڵ����� �˰�����.
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //csrf��ū ��Ȱ��ȭ (�׽�Ʈ�� ��Ȱ��ȭ)
			.authorizeRequests()
				.antMatchers("/" , "/?page=**", "/auth/**", "/js/**", "/css/**", "/image/**") //�̰͵��� ���ٰ���
				.permitAll()
				.anyRequest()    //auth�ܿ� �ٸ� ����������
				.authenticated() //������ �Ǿ���.
			.and()
				.formLogin()
				.loginPage("/auth/loginForm") //auth�� �ƴ� �ٸ����� ���� �̰����� �̵�
				.loginProcessingUrl("/auth/loginProc") //������ ��ť��Ƽ�� �α����� ����æ��.
				.defaultSuccessUrl("/");
				
	}
}
