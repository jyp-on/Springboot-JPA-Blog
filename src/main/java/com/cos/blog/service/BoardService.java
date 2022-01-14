package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

@Service //�������� ������Ʈ ��ĵ�� ���ؼ� Bean�� ����� ����(in IOC)
public class BoardService {

	@Autowired//Repository�� CRUD�ý����� DI�Ͽ� Service����
	private BoardRepository boardRepository; 
	
	@Transactional//2���̻��� Ʈ������� 1���� ������
	public void �۾���(Board board, User user) { //title, context
		board.setCount(0); //��ȸ�� 0
		board.setUser(user);
		
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true) //select���Ҷ�
	public Page<Board> �۸��(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true) //select���Ҷ�
	public Board �ۻ󼼺���(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("�� �󼼺��� ���� : ���̵� ã�� �� �����ϴ�.");
				});
	}
	
	@Transactional
	public void �ۻ����ϱ�(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void �ۼ����ϱ�(int id, Board requestBoard) {
		Board board = boardRepository.findById(id) //����ȭ
				.orElseThrow(()->{
					return new IllegalArgumentException("�� ã�� ���� : ���̵� ã�� �� �����ϴ�.");
				});
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//����ȭ �Ǿ��ִ� Board�� �����Ͱ� �޶����⋚���� ��Ƽüŷ���� �ڵ� ������Ʈ�� flush��
		
	}

}
