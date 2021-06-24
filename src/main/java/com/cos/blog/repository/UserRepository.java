package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.cos.blog.model.User;

import java.util.Optional;


//DAO
//자동으로 bean등록
// @Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {

    //SELECT * FROM user WHERE username = 1?;
    Optional<User> findByUsername(String username);

    //해당jpa레파지토리는 User테이블을 관리하고 primarykey는 integer타입이다
	
	//JPA Naming전략
	//SELECT * FROM user WHERE username = ?1 AND password = ?2
	//User findByUsernameAndPassword(String username , String password);
	
	//@Query(value = "SELECT * FROM user WHERE username = ? AND password = ?" , nativeQuery = true )
	//User login(String username , String password);
}
