package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.ProtectionDomain;

//bean 등록 - 스프링컨테이너에서 객체를 관리할 수 있게 해주는것
@Configuration //bean등록 (IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다. 시큐리티라는 필터 추가 - 스프링 시큐리티가 활성화 되어있는데 어떤 설정을 해당파일에서 하겠다. (현재파일)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean //IoC가 된다 - 스프링이 관리한다
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    }

    //시큐리티가 대신 로그인을 해서 패스워드를 가로채는데
    // 해당 패스워드가 뭘로 해쉬가 돼서 회원가입이 됐는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
          .csrf().disable()//csrf 토큰 비활성화
          .authorizeRequests()//리퀘스트가 들어오면
               .antMatchers( "/","/auth/**" , "/js/**" , "/css/**" , "/js/image/**" , "/dummy/**") //이 주소의 경로가 아닌것들은 인증(로그인)을 해야 들어올 수 있다.
               .permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .formLogin()
               .loginPage("/auth/loginForm")
               .loginProcessingUrl("/auth/loginProc")//스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로챈다. 이후 대신 로그인
                .defaultSuccessUrl("/"); //이후 해당페이지로 이동

    }

}
