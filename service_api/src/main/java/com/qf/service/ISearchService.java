package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface ISearchService {

    // 添加到索引库
    int addGoods(Goods goods);

    // 根据关键字搜索
    List<Goods> searchByKey(String keyword);

}
