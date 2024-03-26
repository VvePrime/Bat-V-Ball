package com.vivek.batvball.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class RequestLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.vivek.batvball.controllers.*.*(..))")
    public void logRequestBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("Request -- Method: {}, URI: {}, Parameters: {}, Body: {}", 
                    request.getMethod(), 
                    request.getRequestURI(), 
                    request.getQueryString(), 
                    Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.vivek.batvball.controllers.*.*(..))", returning = "result")
    public void logResponseAfter(JoinPoint joinPoint, Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("Response -- Status: {}, Body: {}", 
                    attributes.getResponse().getStatus(), 
                    result != null ? result.toString() : "No Content");
    }
}

