package com.ssm.lv.controller;

import com.ssm.lv.entity.Note;
import com.ssm.lv.lucene.NoteIndex;
import com.ssm.lv.service.NoteService;
import com.ssm.lv.utils.Msg;
import com.ssm.lv.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author lv
 * @date 2020/10/27 - 22:20
 */
@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    private NoteIndex noteIndex=new NoteIndex();
    //添加笔记
    @RequestMapping("/admin/addnote")
    public String  skipaddNotes(){
        return "admin/addnote";
    }
    //管理笔记
    //显示类型内容
    @RequestMapping("/admin/note")
    public String  skipNotes(){
        return "admin/note";
    }
    @RequestMapping("/admin/noteList")
    @ResponseBody
    public DataVO list(Integer page, Integer limit){
        return noteService.findData(page,limit);
    }
    ///删除笔记
    //根据id删除或批量删除
    @ResponseBody
    @RequestMapping(value = "admin/noteDelByAllorId",method = RequestMethod.POST)
    public Msg deleteBlogByAll(@RequestParam(value="str",required = false)String str) throws Exception {
        System.out.println(str);
        if(str.contains("-")) {
            String[] str_ids = str.split("-");
            for (String strid : str_ids) {
                noteIndex.deleteIndex(strid);
                int i=noteService.deleteNoteById(Integer.parseInt(strid));
            }
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    //前台
    //跳转笔记显示页面
    @RequestMapping("/skip/note")
    public String skipnote(){
        return "coustom/note";
    }
    //查询所有笔记
    @RequestMapping("/coustomer/selectAllnote")
    @ResponseBody
    public Msg allnote(){
        List<Note> noteList=noteService.listNote();
        return Msg.success().add("noteList",noteList);
    }
    //添加笔记
    @RequestMapping(value = "/admin/writeSavenote",method = RequestMethod.POST)
    @ResponseBody
    public Msg noteadd(@RequestParam(value="title",required = false)String title,
                          @RequestParam(value="summary",required = false)String summary,
                          @RequestParam(value="content",required = false)String content) throws Exception {

        Note note=new Note();
        note.setTitle(title);
        note.setSummary(summary);
        note.setContent(content);
        Date date=new Date();
        note.setReleasedate(date);
        int i=noteService.addNote(note);
       if(i>0){
           noteIndex.addIndex(note);
           return Msg.success();
       }else{
           return Msg.fail();
       }
    }
   //显示笔记
   //添加笔记
   @RequestMapping(value = "/coustomer/showOnenote",method = RequestMethod.GET)
   @ResponseBody
   public Msg noteshow(@RequestParam(value="id",required = false)Integer id) throws Exception {

       System.out.println(id);
        //阅读量
       Note note=noteService.noteById(id);
       note.setClick(note.getClick()+1);
       noteService.updateNote(note);
           return Msg.success().add("note",note);
   }
//修改笔记
    @RequestMapping("/admin/editnote")
    public String skipeditnote(@RequestParam(value="id",required = false)Integer id,
                               Model model){
        Note note=noteService.noteById(id);
        model.addAttribute("note",note);
        return "admin/editnote";
    }
    //更新
    @RequestMapping(value = "/admin/writeupdatenote",method = RequestMethod.POST)
    @ResponseBody
    public Msg noteupdate(@RequestParam(value="id",required = false)Integer id,
            @RequestParam(value="title",required = false)String title,
                       @RequestParam(value="summary",required = false)String summary,
                       @RequestParam(value="content",required = false)String content) throws Exception {

        Note note = new Note();
        note.setId(id);
        note.setTitle(title);
        note.setSummary(summary);
        note.setContent(content);
        Date date = new Date();
        note.setReleasedate(date);
        int i = noteService.updateNote(note);
        if (i > 0) {
            noteIndex.updateIndex(note);
            return Msg.success();
        } else {
            return Msg.fail();
        }
    }
}
