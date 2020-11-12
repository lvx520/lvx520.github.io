package com.ssm.lv.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lv
 * @date 2020/10/12 - 11:02
 */
@Data
@TableName("t_smalltype")
public class SmalltypeVO {
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
