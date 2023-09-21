package com.springboot.inventory.common.security;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private final User user;
    private final String email;
    private final String password;

    public UserDetailsImpl(User user, String email, String password) {
        this.user = user;
        this.email = email;
        this.password = password;
    }

    public User getUser() {return user;}

    // 해당 유저의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum roles = user.getRoles();
        String authority = roles.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;    // 계정 만료 체크
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;    // 계정이 잠겨있는지 체크
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;    // 비밀번호 만료 체크
    }

    @Override
    public boolean isEnabled() {
        return false;    // 계정이 활성화 되어있는지 체크
    }
}
