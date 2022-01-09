package com.cos.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;

@Controller
public class BoardController {
	//@AuthenticationPrincipal PrincipalDetail principal
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({"","/"})
	public String index(Model model, User user,@PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable) { //스프링에서 데이터 가져올 때 Model 객체 param으로 받으면됌
		model.addAttribute("boards", boardService.글목록(pageable)); //index페이지에서 model정보 사용가능
		model.addAttribute("Users", user.getUsername());
		return "index";
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute(boardService.글상세보기(id));
		
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
	
	//USER권한이 필요
	@GetMapping("/board/saveForm") //글쓰기
	public String saveForm() {
		return "board/saveForm";	
	}
}
