package com.ssm.lv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssm.lv.entity.Course;
import com.ssm.lv.entity.Note;
import com.ssm.lv.mapper.NoteMapper;
import com.ssm.lv.service.NoteService;
import com.ssm.lv.vo.CourseVO;
import com.ssm.lv.vo.DataVO;
import com.ssm.lv.vo.NoteVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lv
 * @date 2020/10/27 - 22:36
 */
@Service("NoteService")
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteMapper noteMapper;

    @Override
    public DataVO<NoteVO> findData(Integer page, Integer limit) {
        DataVO dataVO=new DataVO();
        dataVO.setCode(0);
        dataVO.setMsg("");

        //分页
        IPage<Note> noteIPage=new Page<>(page,limit);
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.select().orderByDesc("id");
        IPage<Note> result=noteMapper.selectPage(noteIPage,wrapper);
        dataVO.setCount(result.getTotal());

        List<Note> noteList=result.getRecords();
        List<NoteVO> noteVOList=new ArrayList<>();
        for(Note note:noteList){
            NoteVO  noteVO=new NoteVO();
            BeanUtils.copyProperties(note,noteVO);
            // System.out.println(product.getName());
            noteVOList.add(noteVO);
        }
        dataVO.setData(noteVOList);
        return dataVO;
    }

    @Override
    public List<Note> listNote() {
        return noteMapper.selectList(null);
    }

    @Override
    public Note noteById(Integer id) {
        return noteMapper.selectById(id);
    }

    @Override
    public Note noteByTitle(String title) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("title",title);
        return noteMapper.selectOne(wrapper);
    }

    @Override
    public int addNote(Note note) {
        return noteMapper.insert(note);
    }

    @Override
    public int updateNote(Note note) {
        return noteMapper.updateById(note);
    }

    @Override
    public int deleteNoteById(Integer id) {
        return noteMapper.deleteById(id);
    }
}
