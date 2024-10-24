package com.limemartini.aspect;

import com.alibaba.fastjson2.JSON;
import com.limemartini.annotation.SystemLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.limemartini.annotation.SystemLog)")
    public void pt(){

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        Object proceed;
        try {
            handleBefore(joinPoint);
            proceed = joinPoint.proceed();
            handleAfter(proceed);
        } finally {
            log.info("=======End=======" + System.lineSeparator());
        }
//        proceed = joinPoint.proceed();
        return proceed;
    }

    private void handleBefore(ProceedingJoinPoint joinPoint){


        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //get annotation object
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // print request URL
        log.info("URL            : {}", request.getRequestURL());
        // print description
        log.info("BusinessName   : {}", systemLog.businessName());
        // print Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // print the signature of the method called by the controller
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // print IP
        log.info("IP             : {}", request.getRemoteHost());
        // print request arguments
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private void handleAfter(Object responseResult) {
        // print response arguments
        log.info("Response       : {}", JSON.toJSONString(responseResult));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(SystemLog.class);
    }
}
