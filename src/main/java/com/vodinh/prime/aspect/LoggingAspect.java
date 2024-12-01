package com.vodinh.prime.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    @Before("restControllerMethods()")
    public void logRequest(JoinPoint joinPoint) {
        logger.info("Method: {}", joinPoint.getSignature());
        logger.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "restControllerMethods()", returning = "response")
    public void logResponse(Object response) {
        logger.info("Response: {}", response);
    }
}

