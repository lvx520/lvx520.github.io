package com.ssm.lv.service;

import com.ssm.lv.entity.BigType;
import com.ssm.lv.entity.Smalltype;
import com.ssm.lv.vo.BigTypeVO;
import com.ssm.lv.vo.DataVO;
import com.ssm.lv.vo.SmalltypeVO;

import java.util.List;

/**
 * @author lv
 * @date 2020/10/12 - 10:56
 */
public interface SmalltypeService {

    //后台页面显示所有教程数据
    public DataVO<SmalltypeVO> findData(Integer page, Integer limit);

    /**查询所有*/
    public List<Smalltype> listSmalltype();

    /**根据id查询*/
    public Smalltype smalltypeById(Integer id);

    /**根据教程名称查询*/
    public List<Smalltype> listTypeByname(String blongtype);


    /**添加一个type*/
    public int addSmalltype(Smalltype smalltype);

    /**修改type*//**修改封面图片*/
    public int updateSmalltype(Smalltype smalltype);

    /**删除一个type*/
    public int deleteSmalltypeById(Integer id);
}
