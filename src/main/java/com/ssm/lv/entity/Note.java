package com.ssm.lv.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.sql.DataSource;
import java.util.Date;

/**
 * @author lv
 * @date 2020/10/27 - 22:30
 */
@Data
@TableName("t_note")
public class Note {
    /**主键id*/
    @TableId(value = "id",type =IdType.AUTO)
    private int id;

    private String content;

    private String title;

    private String summary;

    private Date releasedate;

    private int click;

    @TableField(exist = false)
    private String reldate;

}
