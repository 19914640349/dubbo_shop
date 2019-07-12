package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.GoodsMapper;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import com.qf.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * FileName: GoodsServiceImpl.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/5 15:58
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Reference
    private ISearchService searchService;

    /**
     * 查询所有商品
     * @return
     */
    @Override
    public List<Goods> queryAllGoods() {
        return goodsMapper.queryAllGoods();
    }

    /**
     * 添加商品
     * @param goods
     */
    @Override
    public void addGoods(Goods goods) {
        // 添加到数据库
        goodsMapper.insert(goods);
        // 添加到索引库
        searchService.addGoods(goods);
    }

    /**
     * 根绝id查询商品
     * @param id
     * @return
     */
    @Override
    public Goods queryGoodsById(Integer id) {
        return goodsMapper.selectById(id);
    }
}
