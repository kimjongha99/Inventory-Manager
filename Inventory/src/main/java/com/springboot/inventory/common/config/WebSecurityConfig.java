package com.springboot.inventory.common.config;

import com.springboot.inventory.common.jwt.JwtAuthFilter;
import com.springboot.inventory.common.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

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

                // board
                .antMatchers("/board/**","/replies/**","/board/**/**").hasAnyRole("MANAGER","USER")

                // users
                    .antMatchers( "/index", "/LoginPage", "/logout", "/signUpPage").permitAll()
                .antMatchers("/ManagerPage","/managerdashboard").hasRole("MANAGER")
                .antMatchers("/AdminPage").hasRole("ADMIN")
                .antMatchers("/user/sign-up", "/user/sign-in", "/user/**/**").permitAll()

                .antMatchers("/user/**/**").permitAll()

                // supply
                .antMatchers("/supply/mySupply", "/supply/stock").hasRole("USER")
                .antMatchers("/supply/supplyList", "/supply/supply-details",
                        "/supply/supplyCreate",
                        "/supply/supplyUpdate/**","/supply/supplyDetailsFragment").hasRole(
                        "MANAGER")

                // request
                .antMatchers("/category-api/**", "/request-api/**").permitAll()
                .antMatchers("/request-user/**").hasRole("USER")
                .antMatchers("/admin-requestlist/**", "/admin-request-accept/rental", "/admin-requestInfo").hasRole(
                        "MANAGER")

                // resources
                .antMatchers("/", "/resources/**", "/static/**", "/js/**",  "/css/**", "/scripts" +
                        "/**", "/fonts/**", "/plugin/**").permitAll()

//                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html",
                "/swagger-ui/*","/webjars/**", "/swagger/**", "/resources/**", "/static/**", "/static/css/**", "/js/**", "/imgs/**","/img/**", "/image/**");
    }
}
