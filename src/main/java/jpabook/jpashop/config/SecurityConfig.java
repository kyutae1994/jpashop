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
                .authorizeRequests()
                .antMatchers("/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user/**")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/login").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()				// form기반의 로그인인 경우
                .loginPage("/")		// 인증이 필요한 URL에 접근하면 /loginForm으로 이동
                .usernameParameter("email")		// 로그인 시 form에서 가져올 값(id, email 등이 해당)
                .passwordParameter("password")		// 로그인 시 form에서 가져올 값
                .loginProcessingUrl("/login")		// 로그인을 처리할 URL 입력
                .defaultSuccessUrl("/")			// 로그인 성공하면 "/" 으로 이동
                .failureUrl("/")		//로그인 실패 시 /loginForm으로 이동
                .and()
                .logout()					// logout할 경우
                .logoutUrl("/logout")			// 로그아웃을 처리할 URL 입력
                .logoutSuccessUrl("/");			// 로그아웃 성공 시 "/"으로 이동;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
