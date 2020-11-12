package com.ssm.lv.controller;

import com.ssm.lv.entity.BigType;
import com.ssm.lv.service.BigTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lv
 * @date 2020/10/10 - 10:07
 */
@Controller
public class test {
@Autowired
private BigTypeService bigTypeService;
    //跳转后台
    @RequestMapping("/admin/main")
    public String testq(){

        return "admin/main";
    }
    @RequestMapping("/admin/aaaaa")
    public String testaaa(){

        return "lookphoto";
    }


    //@RequestMapping("/coustom/showOneType")
    public String test(){

        return "coustom/onetype";
    }
    @RequestMapping("skip/main2")
    public String test2(HttpServletRequest request){
            //读取教程类型
//        List<BigType> bigTypeList=bigTypeService.listBigType();
//        request.getSession().setAttribute("bigTypeList",bigTypeList);
        return "coustom/main";
    }

    //@RequestMapping("/coustom/showOnetitle")
    public String test3(){

        return "coustom/Onetitle";
    }

    //上传图片
    @RequestMapping("/test/qq")
    public String testqq(){

        return "testImg";
    }
    //上传图片
    @RequestMapping("/aa/qq")
    public String testaaqq(){

        return "testImg";
    }


}
