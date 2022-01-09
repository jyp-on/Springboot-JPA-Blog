package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

@Service //스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌(in IOC)
public class BoardService {

	@Autowired//Repository에 CRUD시스템을 DI하여 Service구현
	private BoardRepository boardRepository; 
	
	@Transactional//2개이상의 트랜잭션을 1개로 묶어줌
	public void 글쓰기(Board board, User user) { //title, context
		board.setCount(0); //조회수 0
		board.setUser(user);
		
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true) //select만할때
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true) //select만할때
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id) //영속화
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				});
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//영속화 되어있는 Board의 데이터가 달라졌기떄문에 더티체킹으로 자동 업데이트가 flush됌
		
	}

}
