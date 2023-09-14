package com.springboot.inventory.common.config;

//import com.springboot.inventory.common.jwt.JwtTokenFilter;
import com.springboot.inventory.common.jwt.JwtTokenFilter;
import com.springboot.inventory.common.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(@Lazy JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain condefaultSecurityFilterChainfigure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /*
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        세션 사용 X, 브라우저 종료 시 쿠키 초기화,
        토큰이 삭제되면, 접근 권한 사라짐
        
        활성화 시 토큰을 삭제해도, 1회 인증된 유저는 세션에 접근 권한이 유지
        */


        http.authorizeRequests(authReq -> authReq
                .antMatchers("/", "/signup", "/signin")
                .permitAll()
                .antMatchers("/master/**").hasAuthority("MASTER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**", "/request/**", "request-api/**").hasAuthority("USER")
                .anyRequest().authenticated());

        // JWT 토큰 필터를 Spring Security 필터 체인에 추가
        http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//
//    @Bean
//    public JwtTokenFilter jwtTokenFilter() {
//        return new JwtTokenFilter();
//    }

}
