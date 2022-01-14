package com.cos.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

			//�������� com.cos.blog��Ű�� ���ϸ� ��ĵ�ؼ� Ư�� ������̼��� �پ��ִ� Ŭ�������ϵ��� new�ؼ�
@RestController		// IOD ������ �����̳ʿ��� �������ݴϴ�.
public class BlogControllerTest { 
	
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>hello spring boot</h1>";
	}
	

	
}
