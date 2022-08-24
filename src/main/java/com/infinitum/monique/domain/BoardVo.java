package com.infinitum.monique.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class BoardVo {
    private int uuid;
    private String name;
    private String subject;
    private String content;
    private MultipartFile file;
    private Date editDate;
    private Date regiDate;
    private int inuse;
    private String thumbnail;

}
