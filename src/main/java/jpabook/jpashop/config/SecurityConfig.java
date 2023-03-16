package jpabook.jpashop.config;

import jpabook.jpashop.filter.MyFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CorsFilter corsFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .antMatchers("/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .addFilterBefore(new MyFilter(), BasicAuthenticationFilter.class)  // security 필터가 먼저 실행되고 다른 필터 실행
//                .addFilterBefore(new MyFilter(), SecurityContextHolderAwareRequestFilter.class)  // security 필터 이전에 실행되는 필터
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션 방법 안씀
                .and()
                .addFilter(corsFilter) // @CrossOrigin(인증 x), 시큐리티 필터에 등록(인증 o)
                .formLogin().disable()
                .httpBasic().disable() // 기본적인 http 로그인 방식 안씀
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManger를 던져줘야됨.
                .authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/js/**").permitAll()
                .antMatchers("/members/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
