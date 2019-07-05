package com.qf.service;

import com.qf.entity.GoodsType;

import java.util.List;

public interface IGoodsTypeService {

    List<GoodsType> queryAllGoodsType();

    void addGoodsType(GoodsType goodsType);
}
