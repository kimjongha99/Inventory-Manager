package com.springboot.inventory.common.config;

import com.springboot.inventory.common.jwt.JwtTokenFilter;
import com.springboot.inventory.common.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // PreAuthorize 쓰기위해 추가
public class SecurityConfig  {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(@Lazy JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain condefaultSecurityFilterChainfigure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests(authReq -> authReq
                .antMatchers("/", "/signup", "/signin")
                .permitAll()

                //css, js
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/board/**","/replies/**").hasAnyAuthority("ADMIN","USER")

                .antMatchers("/master/**").hasAuthority("MASTER")
                .antMatchers("/admin/**", "/admin-main", "/admin-requestlist/**", "/register" +
                        "-supply/**", "/admin-request-accept/rental").hasAuthority(
                        "ADMIN")
                .antMatchers("/user/**", "/request-user/**", "/request-api/user-request/**",
                        "/request-user/history/**" ).hasAuthority(
                        "USER")


                .anyRequest().authenticated());

        http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
