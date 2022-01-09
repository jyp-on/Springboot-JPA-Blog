package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity //User클래스가 MySQL에 테이블 생성
//@DynamicInsert insert시에 null인 필드를 자등으로 빼고 insert해줌
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //시퀀스, auto_increament 사용(비워놔도 자동으로 1씩증가)
	
	@Column(nullable=false, length=50)
	private String username; //아이디
	
	@Column(nullable=false, length=100) //123456 => 해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable=false, length=50)
	private String email;
	
	//@ColumnDefault("'user'") //문자열로 쓸꺼기때문에 ''가 들어가야함.
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum을 쓰면 타입강제 //ADMIN, USER
	
	@CreationTimestamp //시간이 자동 입력
	private Timestamp createDate;
}
