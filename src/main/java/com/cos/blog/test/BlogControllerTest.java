package com.cos.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

			//스프링이 com.cos.blog패키지 이하를 스캔해서 특정 어노테이션이 붙어있는 클래스파일들을 new해서
@RestController		// IOD 스프링 컨테이너에서 관리해줍니다.
public class BlogControllerTest { 
	
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>hello spring boot</h1>";
	}
	

	
}
