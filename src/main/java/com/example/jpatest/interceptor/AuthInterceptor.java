package com.example.jpatest.interceptor;

import com.example.jpatest.common.JwtProvider;
import com.example.jpatest.dto.ErrorResponseDTO;
import com.example.jpatest.exception.CustomException;
import com.example.jpatest.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller 에 들어오는 요청 HttpRequest 와 controller 가 응답하는 HttpResponse 를 가로채는 역할을 하는 Interceptor 클래스
 *
 * @author  kimjh
 * @version 1.0
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    // JWT 처리를 위한 객체
    @Autowired
    private JwtProvider jwtProvider    ;

    /**
     * controller 에 들어오기전에 처리 하는 메소드
     * @param request  HttpServletRequest  객체
     * @param response HttpServletResponse 객체
     * @return 다음 Interceptor 로 체인닝 여부
     */
    @Override
    public boolean preHandle(   HttpServletRequest request
                            ,   HttpServletResponse response
                            ,   Object handler) throws Exception {

        String aaa = request.getRequestURI();

        // 인증 정보 조회
        Authentication authentication = getAuthentication(request);

        // 인증 정보가 없는 경우 에러 응답
        if(authentication == null ){

            // response 객체 설정
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            // 응답 데이터 설정
            ErrorResponseDTO responseDTO = ErrorResponseDTO.builder().error(new CustomException(ErrorCode.AUTH_INFO_FAIL).getMessage()).build();

            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(responseDTO));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        else{
            // SecurityContextHolder 에서 context 추출 해서 설정
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * controller 에서 filter 로 나가기 전에  처리 하는 메소드
     * @param request  HttpServletRequest  객체
     * @param response HttpServletResponse 객체
     * @param handler  핸들러 객체
     * @param modelAndView  ModelAndView 객체
     */
    @Override
    public void postHandle(   HttpServletRequest request
                            , HttpServletResponse response
                            , Object handler
                            , ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * controller 에서 filter 로 지났을 대 처리 하는 메소드
     * @param request  HttpServletRequest  객체
     * @param response HttpServletResponse 객체
     * @param handler  핸들러 객체
     * @param ex       Exception 객체
     */
    @Override
    public void afterCompletion(    HttpServletRequest request
                                ,   HttpServletResponse response
                                ,   Object handler
                                ,   Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    /**
     * HttpServletRequest 객체에서 토큰을 추출하여 인증 정보를 체크하는 메소드
     * @param request  HttpServletRequest  객체
     * @return 스프링 내부에서 사용하는 Authentication
     */
    private Authentication getAuthentication(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token == null) {
            return null;
        }

        int startIndex = token.indexOf("Bearer ");
        if(startIndex == -1){
            startIndex = 0;
        }
        else{
            startIndex = "Bearer ".length();
        }

        // 실제 토큰 정보
        token = token.substring(startIndex);

        // 토큰 정보 체크
        ErrorCode errorCode = jwtProvider.validateToken(token);
        if(errorCode != ErrorCode.SUCCESS){
            return null;
        }

        // 등록된 클레임(registered claims) 구하기
        Claims claims = jwtProvider.getClaims(token);

        // 스프링 내부에서 사용하는 Authentication 로 응답
        return new UsernamePasswordAuthenticationToken(claims, null);
    }
}