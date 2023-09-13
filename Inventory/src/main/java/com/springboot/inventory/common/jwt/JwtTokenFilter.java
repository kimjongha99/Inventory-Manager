package com.springboot.inventory.common.jwt;

//
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;

//
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//
import java.io.IOException;
import javax.servlet.ServletException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider provider) {
        this.jwtTokenProvider = provider;
    }

    /*
    doFilterInternal: 이 메소드는 필터의 주요 로직을 수행합니다. 클라이언트의 HTTP 요청이 서버로 들어올 때마다 호출됩니다. 각 요청을 가로채서 JWT 토큰의 추출, 검증 및 인증을 처리합니다.

    HttpServletRequest request: HTTP 요청을 나타내는 객체로, 클라이언트가 서버로 보낸 요청에 대한 정보를 포함합니다. 이를 통해 요청 헤더, 본문 등의 정보를 얻을 수 있습니다.

    HttpServletResponse response: HTTP 응답을 나타내는 객체로, 서버가 클라이언트로 응답할 때 사용됩니다. 이를 통해 응답 상태 코드, 헤더 등을 설정할 수 있습니다.

    FilterChain filterChain: 필터 체인을 통해 다음 필터로 요청을 전달할 수 있습니다. 필터 체인을 통해 요청의 처리 흐름을 제어할 수 있습니다.
    */

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = jwtTokenProvider.getTokenFromCookie(req);

        if(token != null && jwtTokenProvider.checkValidationOfToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        try {
            filterChain.doFilter(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
