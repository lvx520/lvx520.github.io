package com.ssm.lv.test;

import com.ssm.lv.entity.Smalltype;
import com.ssm.lv.service.SmalltypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.UUID;

/**
 * @author lv
 * @date 2020/10/12 - 20:11
 */

public class UploadController {

    private SmalltypeService smalltypeService;


    //跳转类型页面
    //@RequestMapping("/admin/imgs")
    public String testq(){
        return "testImg";
    }

    //@PostMapping("to")//
    public String upload(@RequestParam(value="id",required = false)String id,MultipartFile face, Map map){
        //System.out.println("----"+id);
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
        System.out.println(smalltype);
        int i=smalltypeService.updateSmalltype(smalltype);
        return  "admin/smallType.html";
        //return  "lookphoto.html";
    }

}