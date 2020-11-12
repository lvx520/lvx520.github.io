package com.ssm.lv.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lv
 * @date 2020/10/15 - 8:57
 */
@Data
@TableName("t_course")
public class CourseVO {
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
