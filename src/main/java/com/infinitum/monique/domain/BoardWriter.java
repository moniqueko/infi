package com.infinitum.monique.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class BoardWriter {
    private String writer;
    private String title;
    private String txtContent;
    private int uuid;
    private MultipartFile uploadFile;

}
