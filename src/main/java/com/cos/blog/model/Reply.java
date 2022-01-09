package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import antlr.collections.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //시퀀스, auto_increament
	
	@Column(nullable=false, length=200)
	private String content;
	
	@ManyToOne//여러개의 답변은 하나의 게시물에 존재 board테이블에 pk를 이용해서 자동으로 연결해줌
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne//여러개의 답변은 한명의 유저에 의해서 만들어짐
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createdate;
	
}
