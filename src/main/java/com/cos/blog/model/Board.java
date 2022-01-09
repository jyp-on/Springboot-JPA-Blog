package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //시퀀스, auto_increament 사용(비워놔도 자동으로 1씩증가)
	
	@Column(nullable = false, length=100)
	private String title;
	
	@Lob //대용량 데이터
	private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨.
	
	private int count; //조회수
	
	@ManyToOne(fetch = FetchType.EAGER) //@Many=Board, User=One 1명의 유저는 많은 게시물 작성가능 PK를 따라감
	@JoinColumn(name="userId") //필드명은 userId로 만들어짐
	private User user; //DB는 오브젝트를 저장할 수 없다. FK,자바는 오브젝트 저장 가능
	
	//FetchType이 EAGER면 데이터를 무조건 끌고오는거고
	//LAZY면 필요할때만 떙겨오는것
	
	@OneToMany(mappedBy = "board", fetch=FetchType.LAZY)//연관관계의 주인이 아니다.(PK가 아니다)DB에 컬럼을 만들지 마세요
	private List<Reply> reply;    //reply 클래스에 board변수
	
	@CreationTimestamp
	private Timestamp createDate;
}
