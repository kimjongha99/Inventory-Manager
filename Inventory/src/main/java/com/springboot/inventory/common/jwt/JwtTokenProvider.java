package com.springboot.inventory.common.jwt;

//
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

//
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

//
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

//


//
import java.util.Base64;
import java.util.Date;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("${springboot.jwt.secret}")
    private String salt = "confidentail";

    private final long expTime = 1000L * 60 * 60 * 2;

    @PostConstruct
    protected void init() {
        salt = Base64.getEncoder().encodeToString(salt.getBytes(StandardCharsets.UTF_8));
    }

    public String genToken(String email, String role) {

    Claims claims = Jwts.claims().setSubject(email);

    claims.put("role", role);

    Date issuedAt = new Date();

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(issuedAt)
            .setExpiration(new Date(issuedAt.getTime() + expTime))
            .signWith(SignatureAlgorithm.HS256, salt)
            .compact();
    }

    public Map<String, String> getUserInfoByToken(String token) {

        Claims claims = Jwts
                .parser()
                .setSigningKey(salt)
                .parseClaimsJws(token)
                .getBody();

        String email = claims.getSubject();
        String role = claims.get("role", String.class);

        Map<String, String> userInfo = new HashMap<>();

        userInfo.put("email", email);
        userInfo.put("role", role);

        return userInfo;
    }

    public Authentication getAuthentication(String token) {

        // 현재 사용자 객체
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(getUserInfoByToken(token).get("email"));

        // 사용자 객체, 인증 정보, 사용자 권한을 포함한 UsernamePasswordAuthenticationToken 반환
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    public String getTokenFromCookie(HttpServletRequest req) {

        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authentication".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public boolean checkValidationOfToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(salt).parseClaimsJws(token);

            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
