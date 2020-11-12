package com.ssm.lv.service;

import com.ssm.lv.entity.Adver;
import com.ssm.lv.entity.Smalltype;
import com.ssm.lv.vo.AdverVO;
import com.ssm.lv.vo.DataVO;
import com.ssm.lv.vo.SmalltypeVO;

import java.util.List;

/**
 * @author lv
 * @date 2020/10/29 - 15:44
 */
public interface AdverService {
    //后台页面显示所有教程数据
    public DataVO<AdverVO> findData(Integer page, Integer limit);

    /**查询所有*/
    public List<Adver> listAdver();

    /**查询所有根据位置*/
    public List<Adver> listAdverByPosition(String position);

    /**根据id查询*/
    public Adver adverById(Integer id);


    /**添加一个Adver*/
    public int addAdver(Adver adver);

    /**修改Adver*//**修改封面图片*/
    public int updateAdver(Adver adver);

    /**删除一个Adver*/
    public int deleteAdverById(Integer id);
}
