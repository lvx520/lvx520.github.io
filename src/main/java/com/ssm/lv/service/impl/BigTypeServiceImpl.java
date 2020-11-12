package com.ssm.lv.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssm.lv.entity.BigType;
import com.ssm.lv.mapper.BigTypeMapper;
import com.ssm.lv.service.BigTypeService;
import com.ssm.lv.vo.BigTypeVO;
import com.ssm.lv.vo.DataVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lv
 * @date 2020/10/11 - 21:06
 */
@Service("BigTypeService")
public class BigTypeServiceImpl implements BigTypeService {

    @Autowired
    private BigTypeMapper bigTypeMapper;

    @Override
    @Transactional
    public DataVO<BigTypeVO> findData(Integer page, Integer limit) {
        DataVO dataVO=new DataVO();
        dataVO.setCode(0);
        dataVO.setMsg("");

        //分页
        IPage<BigType> bigTypeIPage=new Page<>(page,limit);
        IPage<BigType> result=bigTypeMapper.selectPage(bigTypeIPage,null);
        dataVO.setCount(result.getTotal());

        List<BigType> bigTypeList=result.getRecords();
        List<BigTypeVO> bigTypeVOList=new ArrayList<>();
        for(BigType bigType:bigTypeList){
            BigTypeVO bigTypeVO=new BigTypeVO();
            BeanUtils.copyProperties(bigType,bigTypeVO);
            // System.out.println(product.getName());
            bigTypeVOList.add(bigTypeVO);
        }
        dataVO.setData(bigTypeVOList);
        return dataVO;
    }

    @Override
    public List<BigType> listBigType() {
        return bigTypeMapper.selectList(null);
    }

    @Override
    public BigType bigTypeById(Integer id) {
        return bigTypeMapper.selectById(id);
    }

    @Override
    @Transactional
    public int addBigType(BigType bigType) {
        return bigTypeMapper.insert(bigType);
    }

    @Override
    public int updateBigType(BigType bigType) {
        return bigTypeMapper.updateById(bigType);
    }

    @Override
    public int deleteBigTypeById(Integer id) {
        return bigTypeMapper.deleteById(id);
    }

}
