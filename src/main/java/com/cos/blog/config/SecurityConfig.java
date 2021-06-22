package com.cos.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//bean 등록 - 스프링컨테이너에서 객체를 관리할 수 있게 해주는것
@Configuration //bean등록 (IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다. 시큐리티라는 필터 추가 - 스프링 시큐리티가 활성화 되어있는데 어떤 설정을 해당파일에서 하겠다. (현재파일)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()//리퀘스트가 들어오면
            .antMatchers("/auth/**")
               .permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .formLogin()
               .loginPage("/auth/loginForm");
    }

}
