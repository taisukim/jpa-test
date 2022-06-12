package com.example.jpatest.config;

import com.example.jpatest.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Interceptor 등을 설정하기 위한 클래스
 * 요청 패턴에 따른 interceptor 사용 구분 설정
 * @author  kimjh
 * @version 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // swagger 를 위해서 interceptor 를 사용하지 않기 위한 요청 패턴들
    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/favicon.ico"
    };

    // interceptor 로 사용하는 객체
    @Autowired
    private AuthInterceptor authInterceptor;

    /**
     * interceptor 를 등록하는 메소드
     * interceptor 객체를 등록하고, 해당 객체가 사용할 패턴들에 대해서 등록
     * @param registry interceptor 를 관리하는 레지스트리
     * @see InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/signup")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/error")
                .excludePathPatterns(PERMIT_URL_ARRAY);
    }
}
