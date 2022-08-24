package com.infinitum.monique.domain;

import lombok.Data;

import java.util.Date;

@Data
public class BoardWriter {
    private String writer;
    private String title;
    private String txtContent;
    private int uuid;

}
