package com.github.springprac.web.filters;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter { // OncePerRequestFilter는 일회용으로 필터사용할때 쓰는 추상클래스

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        log.info(method + uri + " 요청이 들어왔습니다 .");

        filterChain.doFilter(request,response); // 필터체인에 우리가 정의한 리퀘스트 , 리스폰스 넣어줌

        log.info(method+uri+"가 상태 " + response.getStatus()+ "로 응답이 나갑니다.");
    }
}
