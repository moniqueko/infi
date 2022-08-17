package com.infinitum.monique.domain;

import lombok.Data;

import java.util.Date;

@Data
public class BoardVo {
    private int uuid;
    private String name;
    private String subject;
    private String content;
    private String file;
    private Date editDate;
    private Date regiDate;
    private int inuse;

}
