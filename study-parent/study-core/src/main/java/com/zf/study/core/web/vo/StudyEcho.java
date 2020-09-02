package com.zf.study.core.web.vo;

import lombok.Data;

@Data
public class StudyEcho<T> {
    private T data;
    private String message = "success";
    private int status = 0;

    public StudyEcho(T data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public StudyEcho(T data) {
        this.data = data;
    }
}
