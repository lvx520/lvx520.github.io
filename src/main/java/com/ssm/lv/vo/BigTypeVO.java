package com.ssm.lv.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lv
 * @date 2020/10/11 - 21:02
 */
@Data
@TableName("t_type")
public class BigTypeVO {
    /**主键id*/
    @TableId(value = "id",type =IdType.AUTO)
    private Integer id;
    /**教程类型名称*/
    private String type;
    /**类型排序*/
    private Integer orderno;
}
