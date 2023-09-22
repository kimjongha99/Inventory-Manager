package com.springboot.inventory.common.jwt;

import com.springboot.inventory.common.enums.TokenState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        String refreshToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie == null) {
                    continue;
                }
                if (cookie.getName().equals(JwtProvider.AUTHORIZATION_HEADER)) {
                    accessToken = jwtProvider.resolveToken(cookie);
                } else if (cookie.getName().equals(JwtProvider.REFRESH_HEADER)) {
                    refreshToken = jwtProvider.resolveToken(cookie);
                }
            }
        }

        if (accessToken != null) {
            if (jwtProvider.validateToken(accessToken) == TokenState.VAILD) {
                setAuthentication(jwtProvider.getUserInfoFromToken(accessToken).getSubject());
            } else if (jwtProvider.validateToken(accessToken) == TokenState.EXPIRED) {
                // Access Token Cookie 삭제
                ResponseCookie responseCookie = ResponseCookie.from(JwtProvider.AUTHORIZATION_HEADER, null)
                        .path("/")
                        .httpOnly(true)
                        .sameSite("None")
                        .secure(true)
                        .maxAge(1)
                        .build();
                response.addHeader("Set-Cookie", responseCookie.toString());
                jwtExceptionHandler(response, "NEED REISSUE", HttpStatus.SEE_OTHER);
                return;
            }
        } else if (refreshToken != null) {
            if (jwtProvider.validateRefreshToken(refreshToken)) {
                setAuthentication(jwtProvider.getUserInfoFromToken(refreshToken).getSubject());
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtProvider.createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String message, HttpStatus httpStatus) {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json;charset=UTF-8");
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}