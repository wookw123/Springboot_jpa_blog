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

@Entity //user클래스가 mysql에 테이블이 생성된다
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴!!
//@DynamicInsert insert시에 null필드를 제외시킨다
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB(현재 mysql)의 넘버링 전략을 따라간다
	private int id; //오라클의 sequence , mysql 의 auto_increment
	
	@Column(nullable = false , length = 30 , unique = true)//컬럼은 null이면 안되고 길이는 20자로 제한 그리고 중복을 제한한다.
	private String username; //아이디
	
	@Column(nullable = false , length = 100) //비밀번호를 해쉬로 변경(암호화) 하기위해 넉넉하게 한다
	private String password;	
	
	@Column(nullable = false , length = 50)
	private String email;	
	
	//@ColumnDefault("'user'")
	//DB는 Role타입이 없다
	@Enumerated(EnumType.STRING)
	private RoleType role;//권한 - enum을 쓰는게 좋다. 
	
	@CreationTimestamp //시간이 자동으로 입력된다
	private Timestamp createDate;

}
