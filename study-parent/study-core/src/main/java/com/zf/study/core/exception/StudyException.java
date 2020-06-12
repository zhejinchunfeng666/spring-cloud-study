package com.zf.study.core.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class StudyException extends RuntimeException{

    private StudyErrorCode code;
    private String exceptionErrorMsg;

    public StudyException(StudyErrorCode code) {
        super(code.getCode()+" "+code.getMsg());
        this.code = code;
    }

    public StudyException(StudyErrorCode code, String exceptionErrorMsg) {
        super(code.getCode()+" "+exceptionErrorMsg);
        this.code = code;
        this.exceptionErrorMsg = exceptionErrorMsg;
    }

    public StudyException(StudyErrorCode code,Exception e){
        super(code.getCode()+" "+code.getMsg(),e);
        this.code = code;
        this.exceptionErrorMsg = ExceptionUtils.getStackTrace(e);
    }

    public StudyErrorCode getCode() {
        return code;
    }

    public void setCode(StudyErrorCode code) {
        this.code = code;
    }

    public String getExceptionErrorMsg() {
        return exceptionErrorMsg;
    }

    public void setExceptionErrorMsg(String exceptionErrorMsg) {
        this.exceptionErrorMsg = exceptionErrorMsg;
    }
}
