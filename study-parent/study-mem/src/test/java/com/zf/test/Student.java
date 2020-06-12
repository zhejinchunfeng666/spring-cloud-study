package com.zf.test;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    private Integer id;
    private String userNo;
    private String userName;
    private String passWord;
}
