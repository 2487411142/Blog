package com.limemartini.handler.exception;

import com.limemartini.domain.ResponseResult;
import com.limemartini.enums.AppHttpCodeEnum;
import com.limemartini.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        // print msg
        log.error("exception raised! {}", e);
        // get prompt msg from exception
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult ExceptionHandler(Exception e){
        // print msg
        log.error("exception raised! {}", e);
        // get prompt msg from exception
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
