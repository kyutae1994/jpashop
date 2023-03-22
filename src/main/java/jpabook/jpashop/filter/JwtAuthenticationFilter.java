package jpabook.jpashop.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.config.auth.PrincipalDetails;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음.
// login 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 동작을 함.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter : 로그인 시도중");

        // 1. username, password 받아서
//        ObjectMapper om = new ObjectMapper();
        System.out.println(request.getParameter("email"));
        System.out.println(request.getParameter("password"));
//            Member member = om.readValue(request.getInputStream(), Member.class);
//            System.out.println(member);

        String email = request.getParameter("email");
        String password = request.getParameter("password");

//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // PrincipalDetailsService의 loadUserByUsername() 함수가 실행됨
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // authentication 객체가 session 영역에 저장됨. => 로그인이 됨.
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("로그인 완료됨 : " + principalDetails.getMember().getEmail());

        // authentication 객체가 session 영역에 저장해야하고 그 방법이 return 해주면 됨.
        // 리턴의 이유는 권한 관리를 security가 대신 해주기 대문에 편하려고 하는 것임.
        // 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리 때문에 session에 넣어줌
        return authentication;
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨.
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨 : 인증완료");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // RSA 방식은 아니고 Hash 암호 방식
        String jwtToken = JWT.create()
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 10))) // 만료시간 언제까지 유효할지, 10분
                .withClaim("id", principalDetails.getMember().getId())
                .withClaim("username", principalDetails.getMember().getEmail())
                .sign(Algorithm.HMAC512("cos"));

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
