package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyController {
	
	@Autowired// DI - ������Ʈ�ѷ��� �޸𸮿� ������ ���� ����
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e){
			return "������ �����Ͽ����ϴ�. �ش� id�� DB�� �����ϴ�.";
		}
		
		return "���� �Ǿ����ϴ�. id" + id;
	}
	
	//password, email ����
	//@RequestBody - json�����͸� ��û -> Java Object�� ��ȯ�ؼ� �޾���
	@Transactional //�Լ������ �ڵ� commit
	@PutMapping("/dummy/user/{id}") 
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : "+id );
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : " +requestUser.getEmail());
		//User��ü�� �ش� ���̵� �÷��� �ҷ��ͼ� ������ �Ŀ�
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("������ �����Ͽ����ϴ�.");
		});
		//�ٲ� ���鸸 user�� ����
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//save�Լ��� id�� �������� ������ insert�� ���ְ�
		//save�Լ��� id�� �����ϰ� �ش� id�� ���� �����Ͱ� ������ update�� ���ְ�
		//save�Լ��� id�� �����ϰ� �ش� id�� ���� �����Ͱ� ������ insert�� ����.
		//userRepository.save(user);
	
		
		//��Ƽ üŷ
		//����ȭ��(findById) user ��ü�� ���� ����Ǹ� ������ �����ؼ� db�� update�� ����
		return user;
	}
	
	//http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list(){ 
		//findAll�� list<T>�� ��ȯ�ϴ� �޼ҵ�
		return userRepository.findAll();
	}
	
	//����¡��� - ���������� 2���� �����͸� ������ ����
	@GetMapping("/dummy/user") 
	  public List<User> pageList(@PageableDefault(size=2,sort="id",direction = Sort.Direction.DESC) Pageable pageable){ 
		Page<User> pagingUser = userRepository.findAll(pageable);
		//����¡���� �޾Ƽ�
		if(pagingUser.isLast()) {
			System.out.println("������ �������Դϴ�!");
		}
		else {
			System.out.println("ù �������Դϴ�!");
		}
		List<User> users = pagingUser.getContent();
		//List�� �������ִ°� ����.
		return users;
	}
	//{id} �ּҷ� �Ķ���͸� ���� ������ ����.
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//user/4�� ã���� db���� ��ã�Ƽ� user�� null�̵ǰ� return null�� �Ǵϱ�
		//���α׷��� �����̻���, �׷��� Optional�� User��ü�� ���μ� �����ͼ�
		//null���� �ƴ��� �Ǵ��ؼ� return����
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("�ش� ������ �����ϴ�. <br>id : "+id);
			}
		});
		//��û:��������
		//user ��ü = �ڹ� ������Ʈ
		//��ȯ(���η������� �����Ҽ��ִ� ������) json
		//���࿡ �ڹ� ��ü�� �����ϰԵǸ� MessageConveter�� Jackson ���̺귯���� ���ؼ�
		//user ������Ʈ�� json���� ��ȯ�ؼ� ���������� ����
		return user;
	}
	@PostMapping("dummy/join")
	public String join(User user) {
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername() );
		System.out.println("password : " + user.getPassword() );
		System.out.println("email : " + user.getEmail() );
		
		user.setRole(RoleType.USER);
		
		System.out.println("RoleType" + user.getRole());
		userRepository.save(user); //insert
		return "ȸ�������� �Ϸ�Ǿ����ϴ�.";
	}
}
