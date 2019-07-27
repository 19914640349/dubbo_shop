package com.qf.service_order;

import com.qf.utils.OrderUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceOrderApplicationTests {

    @Autowired
    private OrderUtil orderUtil;

    @Test
    public void contextLoads() {

        System.out.println(orderUtil.getFourUid(156785));
        System.out.println(orderUtil.getFourUid(16));

        System.out.println(orderUtil.createOIdByFourUid(156521));

        System.out.println(orderUtil.getFourUidByOid("190726038051"));

    }

}
