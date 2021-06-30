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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
	private int id;
	
	@Column(nullable = false , length = 100)
	private String title;
	
	@Lob
	//대용량 데이터
	private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨

	private int count; //조회수
	
	@ManyToOne(fetch = FetchType.EAGER) //many = board , one = user 다 대 일 관계 1명의 유저는 여러개의 board를 작성가능.
	@JoinColumn(name = "userid") //자동으로 FK가 생성된다
	private User user; //DB는 오브젝트를 저장할 수 없다 따라서 fk를 사용. java는 오브젝트 저장가능.
	
	@OneToMany(mappedBy = "board" , fetch = FetchType.EAGER)//mappedBy - 연관관계의 주인이 아니다 DB에 컬럼을 만들지 않는다.
	@JsonIgnoreProperties({"board"})
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	

}
