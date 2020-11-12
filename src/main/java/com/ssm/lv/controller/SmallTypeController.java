package com.ssm.lv.controller;

import com.ssm.lv.entity.BigType;
import com.ssm.lv.entity.Course;
import com.ssm.lv.entity.Smalltype;
import com.ssm.lv.lucene.BlogIndex;
import com.ssm.lv.service.BigTypeService;
import com.ssm.lv.service.SmalltypeService;
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
 * @date 2020/10/12 - 11:45
 */
@Controller
public class SmallTypeController {
    @Autowired
    private SmalltypeService smalltypeService;
    @Autowired
    private BigTypeService bigTypeService;

    private BlogIndex blogIndex=new BlogIndex();

    //跳转类型页面
    @RequestMapping("/admin/smallType")
    public String testq(Model model){
        List<BigType> bigTypeList=bigTypeService.listBigType();
        model.addAttribute("bigTypeList",bigTypeList);
        return "admin/smallType";
    }
    //显示类型内容
    @RequestMapping("/admin/smallTypeList")
    @ResponseBody
    public DataVO list(Integer page, Integer limit){
        return smalltypeService.findData(page,limit);
    }

    //上传封面
    @PostMapping("toImg")//
    public String upload(@RequestParam(value="id",required = false)String id,MultipartFile face, Map map) throws Exception {
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
        System.out.println(fileName);
        Smalltype smalltype=new Smalltype();
        smalltype.setId(Integer.parseInt(id));
        smalltype.setImgurl(fileName);
       // System.out.println(smalltype);

        int i=smalltypeService.updateSmalltype(smalltype);
        return  "admin/smallType.html";
    }
//添加教程
@ResponseBody
@RequestMapping(value="/admin/addsmallType",method=RequestMethod.POST)
public Msg addsmalltype(@RequestParam(value="typename",required = false)String typename,
                        @RequestParam(value="blongtype",required = false)String blongtype,
                        @RequestParam(value="summary",required = false)String summary) throws Exception {
        Smalltype smalltype=new Smalltype();
        smalltype.setTypename(typename);
        smalltype.setBlongtype(blongtype);
        smalltype.setSummary(summary);

    int i=smalltypeService.addSmalltype(smalltype);
    if(i>0) {
        blogIndex.addIndex(smalltype);
        return Msg.success();
    }else{
        return Msg.fail();
    }
}
    //删除教程
    @ResponseBody
    @RequestMapping(value = "/admin/smallTypeDelByAllorId",method = RequestMethod.POST)
    public Msg deleteBlogByAll(@RequestParam(value="str",required = false)String str) throws Exception {
        if(str.contains("-")) {
            String[] str_ids = str.split("-");
            for (String strid : str_ids) {
                int i=smalltypeService.deleteSmalltypeById(Integer.parseInt(strid));
            blogIndex.deleteIndex(strid);
            }
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    //更新教程
    @ResponseBody
    @RequestMapping(value="/admin/updatesmallType",method=RequestMethod.POST)
    public Msg updatesmalltype(@RequestParam(value="id",required = false)String id,
                            @RequestParam(value="typename",required = false)String typename,
                            @RequestParam(value="blongtype",required = false)String blongtype,
                            @RequestParam(value="summary",required = false)String summary) throws Exception {
        Smalltype smalltype=new Smalltype();
        smalltype.setId(Integer.parseInt(id));
        smalltype.setTypename(typename);
        smalltype.setBlongtype(blongtype);
        smalltype.setSummary(summary);

        int i=smalltypeService.updateSmalltype(smalltype);
        if(i>0) {
            blogIndex.updateIndex(smalltype);
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    //根据类型查询所有教程
    @RequestMapping(value = "/coustomer/selectAllsmalltype",method = RequestMethod.GET)
    @ResponseBody
    public Msg selectAllType(@RequestParam(value="type",required = false)String type,
                             HttpServletRequest request){
        //读取教程类型
        List<Smalltype> smalltypeList=smalltypeService.listTypeByname(type);
        return Msg.success().add("smalltypeList",smalltypeList);
    }

    //根据大类型跳转到所属页面
    @RequestMapping("/coustom/showOneType")
    public String skipOneType(@RequestParam(value="type",required = false)String type,
                              Model model){
        model.addAttribute("type",type);
        return "coustom/onetype";
    }
}
