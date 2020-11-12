package com.ssm.lv.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author lv
 * @date 2020/10/11 - 19:45
 */
@Data
@TableName("t_type")
@ToString
public class BigType implements Serializable {
    /**主键id*/
    @TableId(value = "id",type =IdType.AUTO)
    private Integer id;
    /**教程类型名称*/
    private String type;
    /**类型排序*/
    private Integer orderno;
}
