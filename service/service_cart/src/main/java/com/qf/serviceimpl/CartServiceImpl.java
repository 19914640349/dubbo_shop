package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.CartMapper;
import com.qf.entity.Goods;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.ICartService;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * FileName: CartServiceImpl.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/23 17:55
 */
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Reference
    private IGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加到购物车
     *
     * @param shopCart  购物车
     * @param user  用户信息
     * @param cartToken 购物车cookie
     * @return
     */
    @Override
    public int addCart(ShopCart shopCart, User user, String cartToken) {

        Goods goods = goodsService.queryGoodsById(shopCart.getGid());
        // 计算出每种商品的小计
        BigDecimal sprice = goods.getGprice().multiply(BigDecimal.valueOf(shopCart.getGnumber()));

        // 小计
        shopCart.setSprice(sprice);
        // 创建时间
        shopCart.setCreateTime(new Date());

        if (user != null) {
            // 登陆了，存到数据库
            shopCart.setUid(user.getId());
            return cartMapper.insert(shopCart);
        }

        // 没登录，直接存进Redis中
        redisTemplate.opsForList().leftPush(cartToken, shopCart);
        // 返回状态
        return 1;
    }

    /**
     * 查询购物车列表
     * @param user
     * @param cartToken
     * @return
     */
    @Override
    public List<ShopCart> queryCartList(User user, String cartToken) {

        List<ShopCart> cartList = null;

        // 判断是否登录
        if (user != null) {

            // 已登录，拿数据库的数据
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("uid", user.getId());
            cartList = cartMapper.selectList(queryWrapper);
        } else {

            // 未登录，获取redis中的购物车
            if (cartToken != null) {
                Long size = redisTemplate.opsForList().size(cartToken);
                cartList = redisTemplate.opsForList().range(cartToken, 0, size);
            }
        }

        // 根据商品id获取商品信息
        if (cartList != null) {
            for (ShopCart shopCart : cartList) {
                Goods goods = goodsService.queryGoodsById(shopCart.getGid());
                shopCart.setGoods(goods);
            }
        }

        return cartList;
    }

    /**
     * 登录后合并临时购物车
     * @param cartToken
     * @param user
     * @return
     */
    @Override
    public int mergeCart(String cartToken, User user) {
        if (cartToken != null) {
            // 获取未登录时的购物车列表
            Long size = redisTemplate.opsForList().size(cartToken);
            List<ShopCart> cartList = redisTemplate.opsForList().range(cartToken, 0, size);

            // 插入数据库
            for (ShopCart shopCart : cartList) {
                shopCart.setUid(user.getId());
                cartMapper.insert(shopCart);
            }

            // 清空临时购物车
            redisTemplate.delete(cartToken);
            return 1;

        }
        return 0;
    }
}
