package com.infinitum.monique.domain;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CommonResult { //restapi에서 결과값 반환시 필요

    private int status;
    private String code;
    private String message;

}
