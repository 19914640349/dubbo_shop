package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface IGoodsService {

    List<Goods> queryAllGoods();

    void addGoods(Goods goods);

    Goods queryGoodsById(Integer id);
}
