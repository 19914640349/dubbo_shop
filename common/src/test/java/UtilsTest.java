import com.qf.utils.OrderUtil;
import org.junit.Test;

/**
 * FileName: UtilsTest.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/25 23:47
 */
public class UtilsTest {

    private OrderUtil orderUtil = new OrderUtil();

    @Test
    public void testGetUid(){
        String uid = orderUtil.getFourUid(489456448);
        System.out.println(uid);
    }

    @Test
    public void testGetOrderIdByUid(){
        String orderId = orderUtil.createOIdByFourUid(456456);
        System.out.println(orderId);
    }
}
