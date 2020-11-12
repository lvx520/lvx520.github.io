package com.ssm.lv.service;

import com.ssm.lv.entity.Course;
import com.ssm.lv.vo.CourseVO;
import com.ssm.lv.vo.DataVO;

import java.util.List;

/**
 * @author lv
 * @date 2020/10/15 - 8:54
 */
public interface CourseService {
    //后台页面显示单个教程数据
    public DataVO<CourseVO> findData(Integer page, Integer limit);

    /**查询所有*/
    public List<Course> listCourse();

    /**根据id查询*/
    public Course courseById(Integer id);

    /**根据v查询*/
    public Course courseByOrderno(Integer orderno,String smtype);


    /**根据title查询*/
    public Course courseByTitle(String title,String smtype);

    /**根据smtype所属教程名称查询*/
    public List<Course> courseBysmtype(String smtype);

    /**添加一个type*/
    public int addCourse(Course course);

    /**修改type*/
    public int updateCourse(Course course);

    /**删除一个type*/
    public int deleteCourseById(Integer id);
}
