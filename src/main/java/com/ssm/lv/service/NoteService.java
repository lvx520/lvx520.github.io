package com.ssm.lv.service;

import com.ssm.lv.entity.Course;
import com.ssm.lv.entity.Note;
import com.ssm.lv.vo.CourseVO;
import com.ssm.lv.vo.DataVO;
import com.ssm.lv.vo.NoteVO;

import java.util.List;

/**
 * @author lv
 * @date 2020/10/27 - 22:33
 */
public interface NoteService {
    //后台页面显示单个教程数据
    public DataVO<NoteVO> findData(Integer page, Integer limit);

    /**查询所有*/
    public List<Note> listNote();

    /**根据id查询*/
    public Note noteById(Integer id);

    /**根据title查询*/
    public Note noteByTitle(String title);


    /**添加一个Note*/
    public int addNote(Note note);

    /**修改Note*/
    public int updateNote(Note note);

    /**删除一个Note*/
    public int deleteNoteById(Integer id);
}
