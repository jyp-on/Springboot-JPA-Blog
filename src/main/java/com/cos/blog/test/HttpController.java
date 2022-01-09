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
		return "get요청 : " + m.getId() + "," + m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	//localhost:8080/http/post
	//post는 RequestBody에 숨겨서 전달해야함
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {//MessageConverter(스프링부트)자동으로 파싱해서 객체에 세터로 꽃아줌
		return "Post요청 : "+ m.getId()+ "," + m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	//update
	//localhost:8080/http/put
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "Put요청"+ m.getId()+ "," + m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	//delete
	//localhost:8080/http/delete
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete요청";
	}
	
	
}
