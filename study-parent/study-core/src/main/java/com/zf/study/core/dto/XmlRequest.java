package com.zf.study.core.dto;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
@XmlRootElement(name = "xml")
public class XmlRequest {
    private Integer id;
    private String userName;
    private String passWord;
}
