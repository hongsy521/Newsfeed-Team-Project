package com.sparta.newsfeedteamproject.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j(topic = "LogAop")
@Aspect
@Component
@RequiredArgsConstructor
public class LogAop {

    // 모든 컨트롤러 API에 부가기능 실행
    @Pointcut("execution(* com.sparta.newsfeedteamproject.controller.*Controller.*(..))")
    private void callApi() {}

    // 핵심 기능 호출되기 전에 부가기능 실행
    @Before("callApi()")
    public void logBeforeCall(JoinPoint joinPoint) {
        // HttpServletRequest 가져오기
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // 요청 정보 가져오기
        String reqURL = request.getRequestURI();
        String reqMethod = request.getMethod();

        // 로깅
        log.info("Request URL : " + reqURL);
        log.info("Request Method : " + reqMethod);

    }
}
