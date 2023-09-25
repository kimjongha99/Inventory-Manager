package com.springboot.inventory.common.jwt;

import com.springboot.inventory.common.enums.TokenState;
import com.springboot.inventory.common.enums.TokenType;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.common.security.UserDetailsServiceImpl;
import com.springboot.inventory.common.util.redis.RedisRepository;
import com.springboot.inventory.common.util.redis.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    private final UserDetailsServiceImpl userDetailsService;
    private final RedisRepository redisRepository;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer-";

    public static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L;   // 1시간
    public static final long REFRESH_TOKEN_TIME = 14 * 24 * 60 * 60 * 1000L;    // 2주

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header Token을 가져오기
    public String resolveToken(Cookie cookie) throws UnsupportedEncodingException {
        String bearerToken = URLDecoder.decode(cookie.getValue(), "UTF-8");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String email, UserRoleEnum roles, TokenType tokenType) {
        Date date = new Date();
        long time = tokenType == TokenType.ACCESS ? ACCESS_TOKEN_TIME : REFRESH_TOKEN_TIME;

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(email)
                .claim(AUTHORIZATION_KEY, roles)
                .setExpiration(new Date(date.getTime() + time))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public TokenState validateToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return TokenState.VAILD;
        } catch (ExpiredJwtException e) {
            LOGGER.info("[JwtProvider - validateToken - Fail]");
            return TokenState.EXPIRED;
            // 현재는 EXPIRED라고 보는 것 x 검증 실패 o
        }
    }

    // refreshToken 검증
    public Boolean validateRefreshToken(String refreshToken) {
        // 1차 토큰 검증
        if (validateToken(refreshToken) != TokenState.VAILD) {
            return false;
        }

        // DB에 저장한 토큰 비교
        Optional<RefreshToken> savedRefreshToken = redisRepository.findById(getUserInfoFromToken(refreshToken).getSubject());

        return savedRefreshToken.isPresent() && refreshToken.equals(savedRefreshToken.get().getRefreshToken().substring(7));
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        System.out.println(userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


}
