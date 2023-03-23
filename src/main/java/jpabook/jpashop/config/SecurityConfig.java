package jpabook.jpashop.config;

import jpabook.jpashop.filter.JwtAuthenticationFilter;
import jpabook.jpashop.filter.JwtAuthorizationFilter;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsConfig corsConfig;
    private final MemberRepository memberRepository;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 사용 x
                .and()
                .addFilter(corsConfig.corsFilter()) // @CrossOrigin(인증 x), 시큐리티 필터에 등록 인증(o)
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManger 던져줘야됨
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), memberRepository)) // AuthenticationManger 던져줘야됨
                .httpBasic().disable()
                .formLogin().disable()
                .authorizeRequests()
                .antMatchers("/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user/**")
                .access("hasRole('ROLE_USER')")
                .anyRequest().permitAll();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
