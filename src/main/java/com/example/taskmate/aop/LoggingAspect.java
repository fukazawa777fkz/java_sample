package com.example.taskmate.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.taskmate.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        this.outputString("処理開始", joinPoint);
    }
    @AfterReturning("execution(* com.example.taskmate.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        this.outputString("処理終了", joinPoint);
    }

    private void outputString(String str, JoinPoint joinPoint) {
        System.out.println(str);
    }

}
