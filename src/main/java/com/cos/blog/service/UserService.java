package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service //스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌(in IOC)
public class UserService {

	@Autowired//Repository에 CRUD시스템을 DI하여 Service구현
	private UserRepository userRepository; 
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	@Transactional//2개이상의 트랜잭션을 1개로 묶어줌
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); //원문
		String encPassword = encoder.encode(rawPassword); //해쉬화
		user.setPassword(encPassword);
		user.setRole(RoleType.USER); //자동으로 안넣어져서 Enum으로 만들었던거 삽입
		userRepository.save(user);
	}
	
	@Transactional
	public void 회원수정(User user) {
		//수정시에는 Jpa 영속성 컨텍스트에 User 오브젝트를 영속화를 시키고
		//영속화된 User 오브젝트를 수정
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(()->{
					return new IllegalArgumentException("회원 찾기 실패");
				});
		String rawPassword = user.getPassword(); //원문
		String encPassword = encoder.encode(rawPassword); //해쉬화
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		//회원 수정함수 종료시 = 서비스 종료시 = 트랜잭션 종료 = commit이 자동으로 됨
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 변화가 됬으면 update를 날려줌.
	}
	@Transactional(readOnly=true)
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
			//orEleGet -> 회원이 없으면 빈객체를 리턴해라. 
		});
		return user;
	}

}
