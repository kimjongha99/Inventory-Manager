package com.springboot.inventory.common.jwt;

import com.springboot.inventory.common.enums.TokenState;
import com.springboot.inventory.common.enums.TokenType;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.common.util.redis.RedisRepository;
import com.springboot.inventory.common.util.redis.RefreshToken;
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
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final RedisRepository redisRepository;

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
                String email = jwtProvider.getUserInfoFromToken(accessToken).getSubject();
                String storedRefreshToken = getRefreshTokenFromRedis(email);

                if (storedRefreshToken != null && jwtProvider.validateRefreshToken(storedRefreshToken)) {
                    // Redis에 저장된 리프레시 토큰이 유효한 경우
                    String newAccessToken = jwtProvider.createToken(email, UserRoleEnum.USER, TokenType.ACCESS);
                    setAccessTokenCookie(response, newAccessToken);  // 새로운 액세스 토큰을 설정
                } else {
                    // Redis에 저장된 리프레시 토큰이 유효하지 않은 경우 또는 없는 경우
                    removeAccessTokenCookie(response);  // 액세스 토큰 삭제
                    jwtExceptionHandler(response, "Access Token Expired. Please reauthenticate.", HttpStatus.UNAUTHORIZED);
                    return;
                }
            }
        } else if (refreshToken != null) {
            if (jwtProvider.validateRefreshToken(refreshToken)) {
                setAuthentication(jwtProvider.getUserInfoFromToken(refreshToken).getSubject());
            }
        }

        filterChain.doFilter(request, response);
    }
    // 레디스
    private String getRefreshTokenFromRedis(String email) {
        Optional<RefreshToken> savedRefreshToken = redisRepository.findById("refreshToken:" + email);
        return savedRefreshToken.map(RefreshToken::getRefreshToken).orElse(null);
    }

    private void setAccessTokenCookie(HttpServletResponse response, String accessToken) {
        ResponseCookie responseCookie = ResponseCookie.from(JwtProvider.AUTHORIZATION_HEADER, "Bearer " + accessToken)
                .path("/")
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .build();
        response.addHeader("Set-Cookie", responseCookie.toString());
    }

    private void removeAccessTokenCookie(HttpServletResponse response) {
        ResponseCookie responseCookie = ResponseCookie.from(JwtProvider.AUTHORIZATION_HEADER, null)
                .path("/")
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .maxAge(0)  // Expire immediately
                .build();
        response.addHeader("Set-Cookie", responseCookie.toString());
    }


    // 레디스

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