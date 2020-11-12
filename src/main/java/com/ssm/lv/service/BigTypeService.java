package com.ssm.lv.service;

import com.ssm.lv.entity.BigType;
import com.ssm.lv.vo.BigTypeVO;
import com.ssm.lv.vo.DataVO;

import java.util.List;

/**
 * @author lv
 * @date 2020/10/11 - 20:55
 */
public interface BigTypeService {

    //后台页面显示所有教程数据
    public DataVO<BigTypeVO> findData(Integer page, Integer limit);

    /**查询所有*/
    public List<BigType> listBigType();

    /**根据id查询*/
    public BigType bigTypeById(Integer id);

    /**添加一个type*/
    public int addBigType(BigType bigType);

    /**修改type*/
    public int updateBigType(BigType bigType);

    /**删除一个type*/
    public int deleteBigTypeById(Integer id);
}
