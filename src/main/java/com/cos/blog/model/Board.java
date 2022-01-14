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
	private int id; //������, auto_increament ���(������� �ڵ����� 1������)
	
	@Column(nullable = false, length=100)
	private String title;
	
	@Lob //��뷮 ������
	private String content; //���ӳ�Ʈ ���̺귯�� <html>�±װ� ������ �������� ��.
	
	private int count; //��ȸ��
	
	@ManyToOne(fetch = FetchType.EAGER) //@Many=Board, User=One 1���� ������ ���� �Խù� �ۼ����� PK�� ����
	@JoinColumn(name="userId") //�ʵ���� userId�� �������
	private User user; //DB�� ������Ʈ�� ������ �� ����. FK,�ڹٴ� ������Ʈ ���� ����
	
	//FetchType�� EAGER�� �����͸� ������ ������°Ű�
	//LAZY�� �ʿ��Ҷ��� ���ܿ��°�
	
	@OneToMany(mappedBy = "board", fetch=FetchType.LAZY)//���������� ������ �ƴϴ�.(PK�� �ƴϴ�)DB�� �÷��� ������ ������
	private List<Reply> reply;    //reply Ŭ������ board����
	
	@CreationTimestamp
	private Timestamp createDate;
}
