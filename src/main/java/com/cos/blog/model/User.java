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
@Entity //UserŬ������ MySQL�� ���̺� ����
//@DynamicInsert insert�ÿ� null�� �ʵ带 �ڵ����� ���� insert����
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //������, auto_increament ���(������� �ڵ����� 1������)
	
	@Column(nullable=false, length=50)
	private String username; //���̵�
	
	@Column(nullable=false, length=100) //123456 => �ؽ�(��й�ȣ ��ȣȭ)
	private String password;
	
	@Column(nullable=false, length=50)
	private String email;
	
	//@ColumnDefault("'user'") //���ڿ��� �����⶧���� ''�� ������.
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum�� ���� Ÿ�԰��� //ADMIN, USER
	
	@CreationTimestamp //�ð��� �ڵ� �Է�
	private Timestamp createDate;
}
