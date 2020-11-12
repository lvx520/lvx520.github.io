package com.ssm.lv.entity;

/**
 * @author lv
 * @date 2020/10/11 - 19:51
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_smalltype")
public class Smalltype implements Serializable {
    /**主键id*/
    @TableId(value = "id",type =IdType.AUTO)
    private Integer id;
    /**教程名称*/
    private String typename;
    /**所属教程类型名称*/
    private String blongtype;
    /**教程介绍*/
    private String summary;
    /**所属教程图片地址*/
    private String imgurl;
    @TableField(exist = false)
    private String imgShow;


}
