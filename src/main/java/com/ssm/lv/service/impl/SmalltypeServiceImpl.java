package com.ssm.lv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssm.lv.entity.BigType;
import com.ssm.lv.entity.Smalltype;
import com.ssm.lv.mapper.SmalltypeMapper;
import com.ssm.lv.service.SmalltypeService;
import com.ssm.lv.vo.BigTypeVO;
import com.ssm.lv.vo.DataVO;
import com.ssm.lv.vo.SmalltypeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lv
 * @date 2020/10/12 - 11:08
 */
@Service("SmalltypeService")
public class SmalltypeServiceImpl implements SmalltypeService {
    @Autowired
    private SmalltypeMapper smalltypeMapper;

    @Override
    public DataVO<SmalltypeVO> findData(Integer page, Integer limit) {
        DataVO dataVO=new DataVO();
        dataVO.setCode(0);
        dataVO.setMsg("");

        //分页
        IPage<Smalltype> smalltypeIPage=new Page<>(page,limit);
        IPage<Smalltype> result=smalltypeMapper.selectPage(smalltypeIPage,null);
        dataVO.setCount(result.getTotal());

        List<Smalltype> smalltypeList=result.getRecords();
        List<SmalltypeVO> smalltypeVOList=new ArrayList<>();
        for(Smalltype smalltype:smalltypeList){
            SmalltypeVO smalltypeVO=new SmalltypeVO();
            String str1=smalltype.getImgurl();
            String str2="/img/"+str1;
            smalltype.setImgShow(str2);
            BeanUtils.copyProperties(smalltype,smalltypeVO);
            // System.out.println(product.getName());
            smalltypeVOList.add(smalltypeVO);
        }
        dataVO.setData(smalltypeVOList);
        return dataVO;
    }

    @Override
    public List<Smalltype> listSmalltype() {
        return smalltypeMapper.selectList(null);
    }

    @Override
    public Smalltype smalltypeById(Integer id) {
        return smalltypeMapper.selectById(id);
    }

    @Override
    public List<Smalltype> listTypeByname(String blongtype) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("blongtype",blongtype);
        return smalltypeMapper.selectList(wrapper);
    }

    @Override
    public int addSmalltype(Smalltype smalltype) {
        return smalltypeMapper.insert(smalltype);
    }

    @Override
    public int updateSmalltype(Smalltype smalltype) {
        return smalltypeMapper.updateById(smalltype);
    }

    @Override
    public int deleteSmalltypeById(Integer id) {
        return smalltypeMapper.deleteById(id);
    }
}
