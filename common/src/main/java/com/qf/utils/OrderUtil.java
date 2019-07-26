package com.qf.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileName: OrderUtil.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/25 23:30
 */
@Component
public class OrderUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据用户id获取后4位，不足前面补0
     * @param uid
     * @return
     */
    public String getUId(Integer uid){

        String uidStr = uid + "";

        StringBuffer buffer = new StringBuffer();
        // 用户id小于4位补0
        if (uidStr.length() < 4) {
            for (int i = 0; i < (4 - uidStr.length()); i++) {
                buffer.append("0");
            }
            buffer.append(uidStr);
        } else {
            // id大于4位，取后4位
            buffer.append(uidStr.substring(uidStr.length() - 4));
        }

        return buffer.toString();
    }

    /**
     * 生成订单id
     * @param uid
     * @return
     */
    public String createOrderIdByUid(Integer uid){

        StringBuffer buffer = new StringBuffer();
        // 拼接当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        buffer.append(sdf.format(new Date()));

        // 拼接用户id后4位
        buffer.append(getUId(uid));

        // 拼接流水号
        String orderNumber = stringRedisTemplate.opsForValue().get("order_number");
        if (orderNumber == null){
            stringRedisTemplate.opsForValue().set("order_number", "0");
        }
        //自增的流水号
        Long number = stringRedisTemplate.opsForValue().increment("order_number");
        buffer.append(number);

        return buffer.toString();
    }

    /**
     * 根据订单id解析出用户id后4位
     * @param orderId
     * @return
     */
    public Integer getUidByOid(String orderId){
        String uid = orderId.substring(6, 10);
        return Integer.parseInt(uid);
    }

}
