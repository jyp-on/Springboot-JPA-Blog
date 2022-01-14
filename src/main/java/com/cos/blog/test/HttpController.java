package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HttpController {

	//localhost:8080/http/get
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get��û : " + m.getId() + "," + m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	//localhost:8080/http/post
	//post�� RequestBody�� ���ܼ� �����ؾ���
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {//MessageConverter(��������Ʈ)�ڵ����� �Ľ��ؼ� ��ü�� ���ͷ� �ɾ���
		return "Post��û : "+ m.getId()+ "," + m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	//update
	//localhost:8080/http/put
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "Put��û"+ m.getId()+ "," + m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	//delete
	//localhost:8080/http/delete
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete��û";
	}
	
	
}
