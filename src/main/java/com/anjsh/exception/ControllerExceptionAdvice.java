package com.anjsh.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author anjsh
 * @date 2021/4/12 14:36
 */
@RestControllerAdvice(basePackages = "com.anjsh.web")
@Slf4j
public class ControllerExceptionAdvice {

    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String businessException(BizException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String unknownException(Exception ex) {
        if (ex.getCause() instanceof BizException) {
            String message = ex.getCause().getMessage();
            log.error(message);
            return message;
        }

        log.error(ex.getMessage(), ex);
        return "系统繁忙,请联系管理员或稍后再试!";
    }
}