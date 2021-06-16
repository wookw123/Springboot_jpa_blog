package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {

	//해당jpa레파지토리는 User테이블을 관리하고 primarykey는 integer타입이다
}
