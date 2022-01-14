package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service //�������� ������Ʈ ��ĵ�� ���ؼ� Bean�� ����� ����(in IOC)
public class UserService {

	@Autowired//Repository�� CRUD�ý����� DI�Ͽ� Service����
	private UserRepository userRepository; 
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	@Transactional//2���̻��� Ʈ������� 1���� ������
	public void ȸ������(User user) {
		String rawPassword = user.getPassword(); //����
		String encPassword = encoder.encode(rawPassword); //�ؽ�ȭ
		user.setPassword(encPassword);
		user.setRole(RoleType.USER); //�ڵ����� �ȳ־����� Enum���� ��������� ����
		userRepository.save(user);
	}
	
	@Transactional
	public void ȸ������(User user) {
		//�����ÿ��� Jpa ���Ӽ� ���ؽ�Ʈ�� User ������Ʈ�� ����ȭ�� ��Ű��
		//����ȭ�� User ������Ʈ�� ����
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(()->{
					return new IllegalArgumentException("ȸ�� ã�� ����");
				});
		String rawPassword = user.getPassword(); //����
		String encPassword = encoder.encode(rawPassword); //�ؽ�ȭ
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		//ȸ�� �����Լ� ����� = ���� ����� = Ʈ����� ���� = commit�� �ڵ����� ��
		//����ȭ�� persistance ��ü�� ��ȭ�� �����Ǹ� ��Ƽüŷ�� �Ǿ� ��ȭ�� ������ update�� ������.
	}
	@Transactional(readOnly=true)
	public User ȸ��ã��(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
			//orEleGet -> ȸ���� ������ ��ü�� �����ض�. 
		});
		return user;
	}

}
