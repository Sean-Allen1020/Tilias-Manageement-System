package com.xu.handler;

import com.xu.exception.BaseException;
import com.xu.exception.BusinessException;
import com.xu.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result exceptionHandler(BaseException e) {
        log.error("异常：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(BusinessException e) {
        log.error("业务异常：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(java.sql.SQLIntegrityConstraintViolationException e) {
        //完整错误信息：Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = e.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");

            //split[2]为username
            return Result.error("重复信息" + split[2]);
        }else{
            return Result.error("未知错误");
        }
    }
}
