package com.zf.study.core.web.handler;

import com.zf.study.core.exception.StudyErrorCode;
import com.zf.study.core.exception.StudyException;
import com.zf.study.core.web.vo.StudyEcho;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = StudyException.class)
    public StudyEcho myExceptionHandler(StudyException e){
        log.info("处理自定义异常");
        return  new StudyEcho(null,e.getCode().getMsg(),e.getCode().getCode());
    }


    @ExceptionHandler(value = Exception.class)
    public StudyEcho exceptionHandler(Exception e){
        log.info("进入异常处理");
        return  new StudyEcho(null, StudyErrorCode.SYSTEM_ERROR.getMsg(),StudyErrorCode.SYSTEM_ERROR.getCode());
    }
}
