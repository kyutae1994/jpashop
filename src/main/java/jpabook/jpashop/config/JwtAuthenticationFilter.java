package jpabook.jpashop.config;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음.
// login 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 동작을 함.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attempAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("로그인 시도중");

        // 1. username, password 받아서

        // 2. 정상인지 로그인 시도 authenticationManager로 로그인 시도를 하면
        // CustomUserDetailsService가 호출 loadUserByUsername() 함수 실행됨.

        // 3. PrincipalDetails를 세션에 담고 (권한 관리 위해서)

        // 4. JWT 토큰을 만들어서 응답
        return super.attemptAuthentication(request, response);
    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // 1. Request Header 에서 JWT 토큰 추출
//        String token = resolveToken((HttpServletRequest) request);
//
//        // 2. validateToken 으로 토큰 유효성 검사
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        chain.doFilter(request, response);
//    }
//
//    // Request Header 에서 토큰 정보 추출
//    private String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
}
