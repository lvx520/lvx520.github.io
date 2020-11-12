package com.ssm.lv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssm.lv.entity.Adver;
import com.ssm.lv.entity.Smalltype;
import com.ssm.lv.mapper.AdverMapper;
import com.ssm.lv.service.AdverService;
import com.ssm.lv.vo.AdverVO;
import com.ssm.lv.vo.DataVO;
import com.ssm.lv.vo.SmalltypeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lv
 * @date 2020/10/29 - 15:46
 */
@Service("AdverService")
public class AdverServiceImpl implements AdverService {

    @Autowired
    private AdverMapper adverMapper;

    @Override
    public DataVO<AdverVO> findData(Integer page, Integer limit) {
        DataVO dataVO=new DataVO();
        dataVO.setCode(0);
        dataVO.setMsg("");

        //分页
        IPage<Adver> adverIPage=new Page<>(page,limit);
        IPage<Adver> result=adverMapper.selectPage(adverIPage,null);
        dataVO.setCount(result.getTotal());

        List<Adver> adverList=result.getRecords();
        List<AdverVO> adverVOList=new ArrayList<>();
        for(Adver adver:adverList){
            AdverVO adverVO=new AdverVO();
            String str1=adver.getImgurl();
            //服务器图片读取
            String str2="/img/"+str1;
            adver.setImgShow(str2);
            BeanUtils.copyProperties(adver,adverVO);
            adverVOList.add(adverVO);
        }
        dataVO.setData(adverVOList);
        return dataVO;
    }

    @Override
    public List<Adver> listAdver() {
        return adverMapper.selectList(null);
    }

    @Override
    public List<Adver> listAdverByPosition(String position) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("position",position);
        return  adverMapper.selectList(wrapper);
    }

    @Override
    public Adver adverById(Integer id) {
        return adverMapper.selectById(id);
    }

    @Override
    public int addAdver(Adver adver) {
        return adverMapper.insert(adver);
    }

    @Override
    public int updateAdver(Adver adver) {
        return adverMapper.updateById(adver);
    }

    @Override
    public int deleteAdverById(Integer id) {
        return adverMapper.deleteById(id);
    }
}
