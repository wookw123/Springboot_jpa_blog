package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
	private int id;
	
	@Column(nullable = false , length = 200)
	private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨
	
	@ManyToOne
	@JoinColumn(name = "boardid")
	private Board board;
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	
	
	

}
