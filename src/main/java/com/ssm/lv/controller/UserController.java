package com.ssm.lv.controller;

import com.ssm.lv.entity.BigType;
import com.ssm.lv.entity.User;
import com.ssm.lv.service.UserService;
import com.ssm.lv.utils.Msg;
import com.ssm.lv.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author lv
 * @date 2020/10/18 - 10:36
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    //跳转类型页面
    @RequestMapping("/admin/user")
    public String skipUser(){
        return "admin/user";
    }
    //查询出所有数据
    //显示类型内容
    @RequestMapping("/admin/userList")
    @ResponseBody
    public DataVO list(Integer page, Integer limit){
        return userService.findData(page,limit);
    }

    //添加用户
    @ResponseBody
    @RequestMapping(value = "/admin/addUser",method = RequestMethod.POST)
    public Msg addUser(@RequestParam(value="username",required = false)String username,
                       @RequestParam(value="password",required = false)String password) {
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("ordinary");
        user.setPermission("ordinary");
        //添加用户之前查询是否已经重复
        User user1=userService.userByName(username);
        if(user1==null){
            int i=userService.addUser(user);
            return Msg.success();
        }else{
         return Msg.fail();
        }

    }
        //删除用户
    //根据id删除或批量删除
    @ResponseBody
    @RequestMapping(value = "/admin/userDelByAllorId",method = RequestMethod.POST)
    public Msg deleteUser(@RequestParam(value="str",required = false)String str){

        if(str.contains("-")) {
            String[] str_ids = str.split("-");
            for (String strid : str_ids) {
                int i=userService.deleteUserById(Integer.parseInt(strid));
            }
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    //更新用户信息
    //id,,password,role,permission
    //修改类型数据
    @ResponseBody
    @RequestMapping(value="/admin/updateUser",method=RequestMethod.POST)
    public Msg updateUser(@RequestParam(value="id",required = false)String id,
                          @RequestParam(value="username",required = false)String username,
                          @RequestParam(value="role",required = false)String role,
                          @RequestParam(value="permission",required = false)String permission){

        User user=new User();
        user.setId(Integer.parseInt(id));
        user.setUsername(username);
        user.setRole(role);
        user.setPermission(permission);
        int i=userService.updateUser(user);
        System.out.println(user);
        if(i>0) {
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

}
