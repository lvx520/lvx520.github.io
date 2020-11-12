package com.ssm.lv.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @author lv
 * @date 2020/10/29 - 15:41
 */
@Data
@TableName("t_adver")
@ToString
public class Adver {
    /**主键id*/
    @TableId(value = "id",type =IdType.AUTO)
    private Integer id;
    /**投放广告公司名字*/
    private String advername;
    /**地址*/
    private String imgurl;

    /**网页广告地址*/
    private String position;

    @TableField(exist = false)
    private String imgShow;
}
