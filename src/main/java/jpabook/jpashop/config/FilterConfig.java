package jpabook.jpashop.config;

import jpabook.jpashop.filter.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> filter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new MyFilter());
        bean.addUrlPatterns("/**");
        bean.setOrder(0); // 낮은 번호가 필터중에서 가장 먼저 실행
        return bean;
    }
}
