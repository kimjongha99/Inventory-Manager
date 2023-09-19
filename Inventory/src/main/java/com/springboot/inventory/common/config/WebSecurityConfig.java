package com.springboot.inventory.common.config;

import com.springboot.inventory.common.jwt.JwtAuthFilter;
import com.springboot.inventory.common.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtProvider jwtProvider;
    @Autowired
    public WebSecurityConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/", "/resources/**", "/static/**", "/js/**",  "/css/**", "/scripts/**", "/fonts/**", "/plugin/**").permitAll()
                .antMatchers("/sign-api/**/**").permitAll()
                .antMatchers("/sign-api/roles/{email}").permitAll()
                .antMatchers("/signUpPage").permitAll()
                .antMatchers("/AdminPage").permitAll() // 임시로 진행중
                .antMatchers( "/","/index", "/LandingPage", "/LogInPage", "/logOut").permitAll()
                .antMatchers("/ManagerPage").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html",
                "/swagger-ui/*","/webjars/**", "/swagger/**", "/resources/**", "/static/**", "/static/css/**", "/js/**", "/imgs/**");
    }
}
