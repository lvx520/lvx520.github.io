package com.ssm.lv.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lv
 * @date 2020/10/11 - 19:56
 */
@Data
@TableName("t_course")
public class Course  implements Serializable {
    /**主键id*/
    @TableId(value = "id",type =IdType.AUTO)
    private Integer id;
    /**教程文章标题*/
    private String title;
    /**教程文章内容*/
    private String content;
    /**所属教程名称*/
    private String smtype;
    /**教程文章排序*/
    private Integer  orderno;

}
