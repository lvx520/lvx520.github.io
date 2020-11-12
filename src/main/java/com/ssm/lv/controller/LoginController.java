package com.ssm.lv.controller;

import com.ssm.lv.entity.User;
import com.ssm.lv.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author lv
 * @date 2020/10/27 - 18:49
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    //登录
    @RequestMapping("/skip/login")
    public String skipLogin(){
        return "coustom/login";
    }
    //注册
    @RequestMapping("/regist")
    public String skipRight(){
        return "coustom/regist";
    }
    @GetMapping("/logout")
    public String logout(Model model,HttpServletRequest request){
        org.apache.shiro.subject.Subject subject=SecurityUtils.getSubject();
        subject.logout();
        return "coustom/main";
    }
    //登录成功
    @RequestMapping(value = "/Userlogin",method = RequestMethod.GET)
    public String login(String username, String password, Model model){
        org.apache.shiro.subject.Subject subject=SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            subject.getSession().setAttribute("user", user);
            return "coustom/main";
        } catch (UnknownAccountException e){
            e.printStackTrace();
            model.addAttribute("errorstr","用户名或密码错误!");
            return "coustom/login";
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            model.addAttribute("errorstr","用户名或密码错误!");
            return "coustom/login";
        }
//        catch (Exception  e){
//            e.printStackTrace();
//            model.addAttribute("errorstr","用户名或密码错误!");
//        }
//        //认证失败返回失败信息
//        return "coustom/login";
    }
    //注册验证userRegist
    // @ResponseBody
    @RequestMapping(value = "/userRegist",method = RequestMethod.GET)
    public String coustomRegist(String username,String phone, String password,String confirmPassword, HttpServletRequest request, Model model){

        User u1=new User();
        u1.setUsername(username);
        u1.setPassword(password);
        u1.setRole("admin");
        u1.setPermission("permission");
        User ul=userService.userByName(username);
        if(ul==null){
            int i = userService.addUser(u1);
            return "coustom/login";
        }
        else{
            String str="账号已存在，请重新注册";
            model.addAttribute("errorstr",str);
            return "coustom/regist";
        }

    }
}
