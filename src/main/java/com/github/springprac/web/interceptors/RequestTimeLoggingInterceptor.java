package com.github.springprac.web.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
public class RequestTimeLoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime); // requestStartTime 에 시작시간 넣어줌
        return true; // 동작하는 것 의미
    }
    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("requestStartTime"); // 넣어놓은 시간 받아와서
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;  // 끝시간 - 처음시간으로 시간계산

        log.info("{} {} executed in {} ms", request.getRequestURI(), request.getMethod(), executeTime);
    }

}
