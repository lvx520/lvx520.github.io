package com.ssm.lv.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lv
 * @date 2020/10/11 - 19:46
 */
@Data
@TableName("t_user")
public class User implements Serializable {
    /**id*/
    @TableId(value = "id",type =IdType.AUTO)
    private Integer id;
    /**用户名称*/
    private String username;
    /**用户密码*/
    private String password;
    /**用户角色*/
    private String role;
    /**用户权限*/
    private String permission;
}
