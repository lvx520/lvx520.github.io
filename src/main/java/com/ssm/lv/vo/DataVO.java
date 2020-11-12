package com.ssm.lv.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lv
 * @date 2020/8/16 - 17:22
 */
@Data
public class DataVO<T> {
    private Integer code;
    private String msg;
    private long count;
    private List<T> data;
}
