package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.GoodsType;

import java.util.List;

/**
 * FileName: GoodsTypeMapper.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/5 16:35
 */
public interface GoodsTypeMapper extends BaseMapper<GoodsType> {

    List<GoodsType> queryAllGoodsType();

}
