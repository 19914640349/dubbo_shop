package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.GoodsTypeMapper;
import com.qf.entity.GoodsType;
import com.qf.service.IGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * FileName: GoodsTypeServiceImpl.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/5 16:34
 */
@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService {

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    /**
     * 查询所有类别
     * @return
     */
    @Override
    public List<GoodsType> queryAllGoodsType() {
        return goodsTypeMapper.queryAllGoodsType();
    }

    /**
     * 添加商品类别
     * @param goodsType
     */
    @Override
    public void addGoodsType(GoodsType goodsType) {
        goodsTypeMapper.insert(goodsType);
    }
}
