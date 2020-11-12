package com.ssm.lv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssm.lv.entity.Course;
import com.ssm.lv.entity.User;
import com.ssm.lv.mapper.UserMapper;
import com.ssm.lv.service.UserService;
import com.ssm.lv.vo.CourseVO;
import com.ssm.lv.vo.DataVO;
import com.ssm.lv.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lv
 * @date 2020/10/18 - 10:12
 */
@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public DataVO<UserVO> findData(Integer page, Integer limit) {
        DataVO dataVO=new DataVO();
        dataVO.setCode(0);
        dataVO.setMsg("");

        //分页
        IPage<User> userIPage=new Page<>(page,limit);
        IPage<User> result=userMapper.selectPage(userIPage,null);
        dataVO.setCount(result.getTotal());

        List<User> userList=result.getRecords();
        List<UserVO> userVOList=new ArrayList<>();
        for(User user:userList){
            UserVO userVO=new UserVO();
            BeanUtils.copyProperties(user,userVO);
            userVOList.add(userVO);
        }
        dataVO.setData(userVOList);
        return dataVO;
    }

    @Override
    public List<User> listUser() {
        return userMapper.selectList(null);
    }

    @Override
    public User userById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public User userByName(String name) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("username",name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteById(id);
    }
}
