package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
//<User, Integer> user���̺��� �����ϴ� Repository�� pk�� integer�̴�.
//insert, update, delete �ϱ����ؼ��� �ش� ��ü�� Repository�� �ʿ�
//���� sql���� �����ϴ� JpaRepository�� extends�Ͽ� ���
//�ڵ����� bean����� �Ǽ� @Repository ���ص���(�޸𸮿� �ڵ����� �����)
public interface BoardRepository extends JpaRepository<Board, Integer>{
													//User��ü, pk�� Integer
	//SELECT * FROM user WHERE username = 1?;
	
	
}

//JPA�� Naming ����
	//SELECT * FROM user WHERE username = ?1 AND password = ?2;
	//User findByUsernameAndPassword(String username, String password);
	
	/*
	 * @Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2",
	 * nativeQuery = true) User login(String username, String password);
	 */