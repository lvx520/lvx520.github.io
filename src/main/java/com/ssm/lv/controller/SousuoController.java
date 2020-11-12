package com.ssm.lv.controller;

import com.ssm.lv.entity.Note;
import com.ssm.lv.entity.Smalltype;
import com.ssm.lv.lucene.BlogIndex;
import com.ssm.lv.lucene.NoteIndex;
import com.ssm.lv.service.NoteService;
import com.ssm.lv.service.SmalltypeService;
import com.ssm.lv.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lv
 * @date 2020/10/28 - 17:44
 */
@Controller
public class SousuoController {

    @Autowired
    private SmalltypeService smalltypeService;
    @Autowired
    private NoteService noteService;

    private BlogIndex blogIndex=new BlogIndex();
    private NoteIndex noteIndex=new NoteIndex();
    //搜索跳转到smalltype页面
    @RequestMapping(value = "/coustom/mainSousuo", method = RequestMethod.GET)
    public String coustomSouSuo(String sousuoFont,
                                Model model) throws Exception {
        model.addAttribute("keySouSuo",sousuoFont);
        return "coustom/sousuoSmPage";
    }
    //返回smalltype搜索值
    @ResponseBody
    @RequestMapping(value = "/coustomer/sousuoAllsmalltype", method = RequestMethod.GET)
    public Msg SouSuoPage(@RequestParam(value = "keysousuo", required = false) String strSouSuo
            , Model model) throws Exception {
        List<Smalltype> blogListq = blogIndex.searchBlog(strSouSuo.trim());
        int blogTotal = blogListq.size();
        model.addAttribute("totals",blogTotal);
        List<Smalltype> smalltypeList=new ArrayList<>() ;
       for(Smalltype smalltype2:blogListq){
           Smalltype smalltype=smalltypeService.smalltypeById(smalltype2.getId());
           smalltypeList.add(smalltype);
       }
        return Msg.success().add("stList",smalltypeList).add("totals",blogTotal);
    }
    //跳转到搜索note笔记显示
    @RequestMapping(value = "/coustom/mainnoteSousuo", method = RequestMethod.GET)
    public String coustomnoteSouSuo(String sousuoFont,
                                Model model) throws Exception {
        model.addAttribute("keySouSuo",sousuoFont);
        //System.out.println(sousuoFont);
        return "coustom/sousuonotePage";
    }
    //搜索note笔记显示
    @ResponseBody
    @RequestMapping(value = "/coustomer/sousuoAllnote", method = RequestMethod.GET)
    public Msg SouSuoNOtePage(@RequestParam(value = "keysousuo", required = false) String strSouSuo
            , Model model) throws Exception {
        List<Note> blogListq = noteIndex.searchBlog(strSouSuo.trim());
        int blogTotal = blogListq.size();
        model.addAttribute("totals",blogTotal);
        return Msg.success().add("ntList",blogListq).add("totals",blogTotal);
    }
}
