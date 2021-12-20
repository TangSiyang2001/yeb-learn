package com.tsy.yebserver.handler;

import com.tsy.yebserver.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Steven.T
 * @date 2021/11/19
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result dealException(Exception ex){
        log.error("=====================log start================================");
        log.error("Exception occurred:{}",ex.getMessage());
        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            log.error(String.valueOf(stackTraceElement));
        }
        log.error("=====================log end==================================");
        return Result.fail(Result.CodeMsg.INTERNAL_SERVER_ERROR);
    }
}
