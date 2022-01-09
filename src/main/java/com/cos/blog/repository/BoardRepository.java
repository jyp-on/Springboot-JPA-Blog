package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
//<User, Integer> user테이블이 관리하는 Repository고 pk는 integer이다.
//insert, update, delete 하기위해서는 해당 객체의 Repository가 필요
//여러 sql문을 포함하는 JpaRepository를 extends하여 사용
//자동으로 bean등록이 되서 @Repository 안해도됌(메모리에 자동으로 띄어줌)
public interface BoardRepository extends JpaRepository<Board, Integer>{
													//User객체, pk는 Integer
	//SELECT * FROM user WHERE username = 1?;
	
	
}

//JPA의 Naming 쿼리
	//SELECT * FROM user WHERE username = ?1 AND password = ?2;
	//User findByUsernameAndPassword(String username, String password);
	
	/*
	 * @Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2",
	 * nativeQuery = true) User login(String username, String password);
	 */