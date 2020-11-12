package com.ssm.lv.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author lv
 * @date 2020/10/28 - 12:45
 */
@Data
@TableName("t_note")
public class NoteVO {
    /**主键id*/
    @TableId(value = "id",type =IdType.AUTO)
    private int id;

    private String content;

    private String title;

    private String summary;

    private Date releasedate;

    private int click;
}
