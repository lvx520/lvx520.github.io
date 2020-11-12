package com.ssm.lv.controller;

import com.ssm.lv.entity.BigType;
import com.ssm.lv.service.BigTypeService;
import com.ssm.lv.utils.Msg;
import com.ssm.lv.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lv
 * @date 2020/10/11 - 21:24
 */
@Controller
public class BigTypeController {

    @Autowired
    private BigTypeService bigTypeService;

    //跳转类型页面
    @RequestMapping("/admin/bigType")
    public String testq(){
        return "admin/bigtype";
    }

    //显示类型内容
    @RequestMapping("/admin/bigTypeList")
    @ResponseBody
    public DataVO list(Integer page, Integer limit){
        return bigTypeService.findData(page,limit);
    }

    //添加类型
    @RequestMapping(value ="/admin/addbigType",method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(BigType bigType){
        System.out.println(bigType);
        int i=bigTypeService.addBigType(bigType);
        if(i>0){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    //修改类型数据
    @ResponseBody
    @RequestMapping(value="/admin/updateBigType",method=RequestMethod.POST)
    public Msg updateSaveBlogType(BigType bigType){
        int i=bigTypeService.updateBigType(bigType);
        if(i>0) {
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    //根据id删除
//    @ResponseBody
//    @RequestMapping(value = "/admin/bigTypeDelById",method = RequestMethod.POST)
//    public Msg deleteBlogById(@RequestParam(value="id",required = false)Integer id){
//        int i=bigTypeService.deleteBigTypeById(id);
//        if(i>0) {
//            return Msg.success();
//        }else{
//            return Msg.fail();
//        }
//    }
    //根据id删除或批量删除
    @ResponseBody
    @RequestMapping(value = "/admin/bigTypeDelByAllorId",method = RequestMethod.POST)
    public Msg deleteBlogByAll(@RequestParam(value="str",required = false)String str){
        System.out.println(str);
        if(str.contains("-")) {
            String[] str_ids = str.split("-");
            for (String strid : str_ids) {
                int i=bigTypeService.deleteBigTypeById(Integer.parseInt(strid));
            }
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

    //主页教程类型
    @RequestMapping(value = "/coustomer/selectAllBigType",method = RequestMethod.GET)
    @ResponseBody
    public Msg selectAllType(HttpServletRequest request){
        //读取教程类型
        List<BigType> bigTypeList=bigTypeService.listBigType();
        //request.getSession().setAttribute("bigTypeList",bigTypeList);

        return Msg.success().add("bigTypeList",bigTypeList);
    }
}
