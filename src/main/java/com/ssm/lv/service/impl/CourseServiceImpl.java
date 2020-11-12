package com.ssm.lv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssm.lv.entity.Course;
import com.ssm.lv.mapper.CourseMapper;
import com.ssm.lv.service.CourseService;
import com.ssm.lv.vo.CourseVO;
import com.ssm.lv.vo.DataVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lv
 * @date 2020/10/15 - 9:00
 */
@Service("CourseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public DataVO<CourseVO> findData(Integer page, Integer limit) {
        DataVO dataVO=new DataVO();
        dataVO.setCode(0);
        dataVO.setMsg("");

        //分页
        IPage<Course> courseIPage=new Page<>(page,limit);
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.select().orderByDesc("id");
        IPage<Course> result=courseMapper.selectPage(courseIPage,wrapper);
        dataVO.setCount(result.getTotal());

        List<Course> courseList=result.getRecords();
        List<CourseVO> courseVOList=new ArrayList<>();
        for(Course course:courseList){
            CourseVO courseVO=new CourseVO();
            BeanUtils.copyProperties(course,courseVO);
            // System.out.println(product.getName());
            courseVOList.add(courseVO);
        }
        dataVO.setData(courseVOList);
        return dataVO;
    }

    @Override
    public List<Course> listCourse() {
        return courseMapper.selectList(null);
    }

    @Override
    public Course courseById(Integer id) {
        return courseMapper.selectById(id);
    }

    @Override
    public Course courseByOrderno(Integer orderno,String smtype) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("orderno",orderno);
        wrapper.eq("smtype",smtype);
        return courseMapper.selectOne(wrapper);
    }

    @Override
    public Course courseByTitle(String title,String smtype) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("title",title);
        wrapper.eq("smtype",smtype);
        return courseMapper.selectOne(wrapper);
    }

    @Override
    public List<Course> courseBysmtype(String smtype) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("smtype",smtype);
        return courseMapper.selectList(wrapper);
    }

    @Override
    public int addCourse(Course course) {
        return courseMapper.insert(course);
    }

    @Override
    public int updateCourse(Course course) {
        return courseMapper.updateById(course);
    }

    @Override
    public int deleteCourseById(Integer id) {
        return courseMapper.deleteById(id);
    }
}
