package com.ssm.lv.controller;

import com.ssm.lv.entity.BigType;
import com.ssm.lv.entity.Course;
import com.ssm.lv.service.CourseService;
import com.ssm.lv.service.SmalltypeService;
import com.ssm.lv.utils.Msg;
import com.ssm.lv.vo.DataVO;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lv
 * @date 2020/10/15 - 9:25
 */
@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private SmalltypeService smalltypeService;
    //跳转页面
    //跳转类型页面
    @RequestMapping("/admin/course")
    public String skipCourse(){
        return "admin/course";
    }

    //显示类型内容
    @RequestMapping("/admin/courseList")
    @ResponseBody
    public DataVO list(Integer page, Integer limit){
        return courseService.findData(page,limit);
    }

    //删除文章
    //根据id删除或批量删除
    @ResponseBody
    @RequestMapping(value = "/admin/courseDelByAllorId",method = RequestMethod.POST)
    public Msg deleteBlogByAll(@RequestParam(value="str",required = false)String str){
        System.out.println(str);
        if(str.contains("-")) {
            String[] str_ids = str.split("-");
            for (String strid : str_ids) {
                int i=courseService.deleteCourseById(Integer.parseInt(strid));
            }
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    //添加
    @RequestMapping("/admin/addCourse")
    public String skipAddCourse(@RequestParam(value="type",required = false)String type,Model model){
        //存储教程名称
        model.addAttribute("typename",type);
        //查询已存在的该教程文章总数,根据教程名称查询
        List i=courseService.courseBysmtype(type);
        model.addAttribute("orderno",i.size()+1);
        return "admin/addcourse";
    }
    //保存教程文章
    @ResponseBody
    @RequestMapping(value = "/admin/writeSavecourse",method = RequestMethod.POST)
    public Msg writeSave(@RequestParam(value="title",required = false)String title
        ,@RequestParam(value="smtype",required = false)String smtype
        ,@RequestParam(value="content",required = false)String content
        ,@RequestParam(value="orderno",required = false)String orderno){

        Course course=new Course();
        course.setTitle(title);
        course.setSmtype(smtype);
        course.setContent(content);
        course.setOrderno(Integer.parseInt(orderno));
   // System.out.println(course);
        int i=courseService.addCourse(course);
        if(i>0){
            return Msg.success();
     }else{
        return Msg.fail();
        }
    }
    //跳转文章教程
    @RequestMapping(value = "/admin/skipCourse",method = RequestMethod.GET)
    public String skipSave(@RequestParam(value="id",required = false)String id
            ,Model model){
        Course course=courseService.courseById(Integer.parseInt(id));
        model.addAttribute("course",course);
        return "admin/editCourse";
    }
    //编辑文章教程
    @ResponseBody
    @RequestMapping(value = "/admin/updateSaveartic",method = RequestMethod.POST)
    public Msg editSave(@RequestParam(value="id",required = false)String id
        ,@RequestParam(value="title",required = false)String title
        ,@RequestParam(value="content",required = false)String content){

    Course course=new Course();
    course.setId(Integer.parseInt(id));
    course.setTitle(title);
    course.setContent(content);
    int i=courseService.updateCourse(course);

  if(i>0){
      return Msg.success();
  }
  else{
      return Msg.fail();
  }
}


//获取所属教程的全部章节名称，并显示第一页
@RequestMapping("/coustom/showOnetitle")
public String skipOnetitles(@RequestParam(value="typename",required = false)String typename,
                          Model model){
     //去除html标签
    String regEx="<.+?>";
    Pattern p=Pattern.compile(regEx);
    Matcher m=p.matcher(typename);
    typename=m.replaceAll("");
    model.addAttribute("typename",typename);

    Course course=courseService.courseByOrderno(1, typename);
    model.addAttribute("course",course);

    return "coustom/Onetitle";
}
//跳转到所有章节页面并显示该文章
 ///?typename="+title+"&typename="+typename
@RequestMapping("coustom/showOneCourseByTitle")
public String skipOnetitle(@RequestParam(value="title",required = false)String title,
                           @RequestParam(value="typename",required = false)String typename,
                            Model model){
    System.out.println(title+"---"+typename);
    model.addAttribute("typename",typename);
    Course course=courseService.courseByTitle(title,typename);
    model.addAttribute("course",course);
    return "coustom/Onetitle";
}
//查询所有
@ResponseBody
@RequestMapping(value = "/coustomer/selectAllCourse",method = RequestMethod.GET)
public Msg selectAllCouse(@RequestParam(value="typena",required = false)String typena){
        List<Course> courseList=courseService.courseBysmtype(typena);
        return Msg.success().add("courseList",courseList);
}

}
