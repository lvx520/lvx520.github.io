package com.ssm.lv.controller;

import com.ssm.lv.entity.Adver;
import com.ssm.lv.entity.BigType;
import com.ssm.lv.entity.Smalltype;
import com.ssm.lv.service.AdverService;
import com.ssm.lv.utils.Msg;
import com.ssm.lv.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author lv
 * @date 2020/10/29 - 15:54
 */
@Controller
public class AdverController {

    @Autowired
    private AdverService adverService;

    //跳转到广告页面
    @RequestMapping("/admin/advertisLink")
    public String skipAdver(){
        return "admin/adversi";
    }

    //显示类型内容
    @RequestMapping("/admin/adverList")
    @ResponseBody
    public DataVO list(Integer page, Integer limit){
        return adverService.findData(page,limit);
    }

    //删除广告
    @ResponseBody
    @RequestMapping(value = "/admin/adverDelByAllorId",method = RequestMethod.POST)
    public Msg deleteadverByAll(@RequestParam(value="str",required = false)String str) throws Exception {
        if(str.contains("-")) {
            String[] str_ids = str.split("-");
            for (String strid : str_ids) {
                int i=adverService.deleteAdverById(Integer.parseInt(strid));
            }
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

    //添加广告
    //添加教程
    @ResponseBody
    @RequestMapping(value="/admin/addadver",method=RequestMethod.POST)
    public Msg addadver(@RequestParam(value="advername",required = false)String advername,
                            @RequestParam(value="position",required = false)String position
                            ) throws Exception {
        Adver adver=new Adver();
        adver.setAdvername(advername);
        adver.setPosition(position);
        int i=adverService.addAdver(adver);
        if(i>0) {
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    //添加广告图片
    @PostMapping("advertoImg")//
    public String upload(@RequestParam(value="id",required = false)String id, MultipartFile face, Map map) throws Exception {
        String fileName=face.getOriginalFilename();
        // String filePath = "D://temp-rainy//"; // 上传后的路径
        String filePath = "D://IDEAJava//IdeaProject//springboot-learn-network//src//main//resources//static//image//imgs//";
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath);
        if(!dest.exists()){
            dest.mkdir();
        }
        //等待接收数据流的文件
        File file2=new File(dest,fileName);
        try {
            //传入数据
            face.transferTo(file2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("img",fileName);
        map.put("imgs","/img/"+fileName);

        Adver adver=new Adver();
        adver.setId(Integer.parseInt(id));
        adver.setImgurl(fileName);
        int i=adverService.updateAdver(adver);
        return  "admin/adversi.html";
    }
    //更新
    @ResponseBody
    @RequestMapping(value="/admin/updateadver",method=RequestMethod.POST)
    public Msg updatesmalltype(@RequestParam(value="id",required = false)Integer id,
                               @RequestParam(value="advername",required = false)String advername,
                               @RequestParam(value="position",required = false)String position) throws Exception {

        Adver adver=new Adver();
        adver.setId(id);
        adver.setPosition(position);
        adver.setAdvername(advername);
        int i=adverService.updateAdver(adver);
        if(i>0) {
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

    //侧边广告位
    //根据类型查询所有教程
    @RequestMapping(value = "/coustomer/selectrightadver",method = RequestMethod.GET)
    @ResponseBody
    public Msg selectRightAdver(@RequestParam(value="position",required = false)String position){

        //读取教程类型
        List<Adver> adverList=adverService.listAdverByPosition(position);
        return Msg.success().add("adverList",adverList);
    }
}
