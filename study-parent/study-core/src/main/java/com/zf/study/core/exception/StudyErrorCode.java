package com.zf.study.core.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author fang.zhou
 */
public enum StudyErrorCode {
    /**
     *
     */

    PARAM_MISSING(100001,"参数缺失"),
    SYSTEM_ERROR(100002,"系统异常"),
    STOCK_NONUM(200001,"库存不足"),
    ORDER_ERR(200002,"秒杀失败"),
    SPEED_SLOW(200003,"手速太快了，慢点儿吧~")
    ;
    private int code;

    private String msg;

    StudyErrorCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static int getCode(String msg){
        for(StudyErrorCode errorCode:StudyErrorCode.values()){
            if(StringUtils.equals(msg,errorCode.getMsg())){
                return errorCode.getCode();
            }
        }
        return -1;
    }

    public static String getMsg(int code){
        for(StudyErrorCode errorCode:StudyErrorCode.values()){
            if(code == errorCode.getCode()){
                return errorCode.getMsg();
            }
        }
        return null;
    }
}
