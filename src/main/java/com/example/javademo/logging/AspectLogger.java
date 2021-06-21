package com.example.javademo.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Slf4j
@Aspect
@Component
public class AspectLogger {
    private static final String DEBUG_POINT_CUT =
            "execution(* com.example.javademo..*.*(..)) && !execution(* com.example.javademo.logging..*.*(..))";
    private static final String INFO_POINT_CUT =
            "@annotation(com.example.javademo.logging.Logging)";

    @Before(DEBUG_POINT_CUT)
    public void beginDebug(JoinPoint joinPoint) {
        String args = Arrays.toString(joinPoint.getArgs());
        log.debug("Begin, joinPoint: {}, args: {}", joinPoint, args);
    }

    @AfterReturning(value = DEBUG_POINT_CUT, returning = "returnValue")
    public void endDebug(JoinPoint joinPoint, Object returnValue) {
        String args = Arrays.toString(joinPoint.getArgs());
        log.debug("End, joinPoint: {}, args: {}, return: {}", joinPoint, args, returnValue);
    }

    @Before(INFO_POINT_CUT)
    public void beginInfo(JoinPoint joinPoint) {
        String args = Arrays.toString(joinPoint.getArgs());
        log.info("Begin, joinPoint: {}, args: {}", joinPoint, args);
    }

    @AfterReturning(value = INFO_POINT_CUT, returning = "returnValue")
    public void endInfo(JoinPoint joinPoint, Object returnValue) {
        String args = Arrays.toString(joinPoint.getArgs());
        log.info("End, joinPoint: {}, args: {}, return: {}", joinPoint, args, returnValue);
    }
}