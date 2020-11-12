package com.ssm.lv.service;

import com.ssm.lv.entity.Course;
import com.ssm.lv.entity.User;
import com.ssm.lv.vo.CourseVO;
import com.ssm.lv.vo.DataVO;
import com.ssm.lv.vo.UserVO;

import java.util.List;

/**
 * @author lv
 * @date 2020/10/18 - 10:06
 */
public interface UserService {
    //后台页面显示用户数据
    public DataVO<UserVO> findData(Integer page, Integer limit);

    /**查询所有用户*/
    public List<User> listUser();

    /**根据id查询*/
    public User userById(Integer id);

    /**根据用户名查询*/
    public User userByName(String name);

    /**添加一个用户*/
    public int addUser(User user);

    /**修改用户*/
    public int updateUser(User user);

    /**删除一个用户*/
    public int deleteUserById(Integer id);
}
