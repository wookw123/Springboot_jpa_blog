package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가되면 UserDetails타입의 오브젝트를
// 스프링 시쿠리티의 고유한 세션저장소에 저장을 해준다.
@Getter
public class PrincipalDetail implements UserDetails {
    private User user; //컴포지션


    public PrincipalDetail(User user){ this.user = user; }



    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //계정이 만료되지 않았는지 리턴한다 (true:만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정이 잠겼는지 않았는지 리턴한다 (true:만료안됨)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되지 않았는지 리턴한다 (true:만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화(사용가능) 되었는지 않았는지 리턴한다 (true:만료안됨)
    @Override
    public boolean isEnabled() {
        return true;
    }


    //계정이 갖고있는 권한 목록을 리턴한다.(권한이 여러개 있을 수 도 있어서 루프를 돌아야하는데 여기서는 한개만한다)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        //collectors.add(new GrantedAuthority() {
        //    @Override
        //    public String getAuthority() {
        //        return "ROLE_" + user.getRole();//ROLE_USER . ROLE_를 붙여줘야 인식할 수 있다.
        //    }
        //}); 
        collectors.add(()->{return "ROLE_" + user.getRole();});//위 주석처리한 부분을 람다식 익명함수로 한줄로 만들수 있다.
        return collectors;
    }
}
