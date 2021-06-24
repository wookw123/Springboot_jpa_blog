package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;

@Service//빈등록
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    //스프링이 로그인 요청을 가로챌 때 username , password 변수 2개를 가로채는데
    //password부분 처리는 알아서 한다.
    //username이 DB에 있는지만 확인해주면 된다.이 함수에서 확인한다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username)
        .orElseThrow(()->{
            return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다");
        });
        return new PrincipalDetail(principal); //시쿠리티 세션에 유저정보가 저장된다 UserDetails 타입으로 저장
    }
}
