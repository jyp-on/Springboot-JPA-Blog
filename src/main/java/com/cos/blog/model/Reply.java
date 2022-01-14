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
	private int id; //������, auto_increament
	
	@Column(nullable=false, length=200)
	private String content;
	
	@ManyToOne//�������� �亯�� �ϳ��� �Խù��� ���� board���̺� pk�� �̿��ؼ� �ڵ����� ��������
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne//�������� �亯�� �Ѹ��� ������ ���ؼ� �������
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createdate;
	
}
