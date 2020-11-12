package com.ssm.lv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author lv
 * @date 2020/10/29 - 22:42
 */
@Controller
public class OtherController {

    //跳转到关于我们界面
    //跳转到广告页面
    @RequestMapping(value="/skip/ourJianYi",method = RequestMethod.GET)
    public String skipourJianyi(){
        return "coustom/ourJianYi";
    }


    @RequestMapping(value="/qq",method = RequestMethod.GET)
    public String mainheader(){
        return "/admin/header";
    }
}
