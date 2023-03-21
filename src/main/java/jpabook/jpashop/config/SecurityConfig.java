package jpabook.jpashop.config;

import jpabook.jpashop.filter.JwtAuthenticationFilter;
import jpabook.jpashop.filter.MyFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsConfig corsConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 사용 x
                .and()
                .addFilter(corsConfig.corsFilter()) // @CrossOrigin(인증 x), 시큐리티 필터에 등록 인증(o)
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManger 던져줘야됨
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user/**")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/login").permitAll()
                .anyRequest().permitAll();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
