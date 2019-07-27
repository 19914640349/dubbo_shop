package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.entity.Order;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FileName: PayController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/26 21:20
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Reference
    private IOrderService orderService;

    /**
     * 使用支付宝进行支付
     *
     * @param orderId
     * @param response
     */
    @RequestMapping("/aliPay")
    public void aliPay(String orderId, HttpServletResponse response) {

        // 根据订单id查询到订单信息
        Order order = orderService.queryOrderByOid(orderId);

        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016101100657623",
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCFg64VkbEQjuteOqZNEh6EzKkHZI/ySZAYxwqD1bNd/KZkTi0BsRxpdvkwsIr+PWHpUPW/rIgyVlPAB1nDFLBkzJ0ANvctRdB772dsN06VSlIdlKVk9//JcZedu3MAwYjaS/rCZsYAMNRJDJhh7YgcvbpmIg/m+CU7c6MYEj0ISXw6l3ghZZswv0HxLsyibts75Q+bQKCbpqSBtDDh2h0EZG4wTB4mnI/brLUnO+HCMa6TfXf9VJQkm62F3CGhF+B9+0hok8qNE2GGZEhmma6PT4JkySTu/0Jf9hhCiv8yzrHjFCTUUy4trN8L1JgOYf3rBCbcCSuKGKkg2IeReAtJAgMBAAECggEBAIMyso5fbC9pQcCGwYRamcLFmhO5rfnYb9DpRlvmUyu4aYmHiJJxCApa7uP5l5vdlak/9T00vWE5k3LPxlT8r5ldt6nOWXYTdJtU1bxxXLc3IRoQR7bAPIonJWXKHWhQxbo9j1wYwdjOIszlo5gXYflcNx7FufqtPWj+aPXudir9H6gpXmqBLstpa7Qk5noWr5mgDr0BjG/efbens/QR+NMk46KaiOLUcpErSVkbntcnj00IMyVrDgSbt2cRS3hYPbS0du6APyjAtYvjwlDSVk1cIcW9F20foyLhNVZeolPYR+6h/CVvjHW5QbRmElk1NKEFAJvmbsZW1a0EeCBBkAECgYEAvjTrTENjH4dZ/Ls/MkXHCyG8pnJkVafPunoBnYvoYnbTA2SdXWHCvsLUoOo1RGjlN8lmnCd6aRCbTrKNHHueRSx0OON7LjQaTJNi/TxyvPY/CKR0PEdQIfgx2sXkaSSDn8ncAqVwGRClKXn3eYnaK17Cl8ocA8cN/UDrXM+mfaECgYEAs7KSYt4zvO5vayCgcHOw3pow5nfaibj2uZqpTpkmBbX29qCx0uf4UxQE0DrX9mGa7H/V2zV0MygwNyBcPUThQTS0/7wVcJ3rKN7OWut8NNOyOFxKG49cH9KqQfj6UOj64n8SLwBY3eKvFNsKDzE+sC2Dghduiz++bYdqQK5hnKkCgYAeiO5RQuwxzo/Ss9i0YGFQ1yyq5+hVm+gMPrPtr90xmXPMsqVUrXIPH8X0w0Sj49cQeqOfY/MR1Nmp654duXayEi3iSBqpUzH5oxucHc0pqrp5z7TdTHF/0pFmVqiRfIwo5cYNIrP5QiPRocPp3giJN+Azb/3JWXZUdsySDL/koQKBgQCDBSiGvcyrbI4AZgGU/EVxiKsu3ySQVjROj93yWOfIMiynFfOR+2Cr8mSwDrd4BSKYXXzoH8lho2PETevzIE285VkzPUfEnN/hJNMAp8IgIlpHjcLa5WQeL9PD1m1soPKMQmpXqsEgxy8w3aQv/4/dlNHWAI+xKAEIizuFfZ/eQQKBgEPXqn5VFNh7Z0FOU/8BlZaVUSYLxwRHdCzZ7fT5Lxp9qsIzE+xFqFp6BbFVCJNWchCWGgzgoTrvAee/I0kwSYHRjivliwEVZpHDhkwwtkzERtKAooiVv+tYIGkDZBRi75UGjiPTX4iwpobPvKKbhCNBmA/XCOTkgbThKVLDl1zG",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA33fiE9tdV0ls0M4iHw74zgLz0OiuJdMxTXZx54iB2/py9taW1gVl9vdXGdS49/MouXiiaBcGbLPY9sw2ZlbVoTm9f6K0ErQK4xmTNjTkZay0zhD2Gh8YdqJfgYdkXmPaVWYqyJ8+LAadPHfJCg2ToJ4HHWicZ1bel7F+trNvoEK68AFhp3GQqyyoHXfYwBmKBWfljrIWenHMD0+e9FSIANEycxQipT0tgJ847xsUqAraTvejXDurjU74Da8MOhJK3T336Xj9Je1WCWcd7bgy/SKc08iuZk/38k/xgiWQNi2gv7i3CQGiP8QANW9ry9l/mfPHb94ESfdckU8RMCeouQIDAQAB",
                "RSA2"); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        // 同步请求
        alipayRequest.setReturnUrl("http://localhost:8086/order/orderList");
        // 异步请求
        alipayRequest.setNotifyUrl("http://lonely085.wicp.vip/pay/payComplete");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orderId + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + order.getAllprice().doubleValue() + "," +
                "    \"subject\":\"" + orderId + "\"," +
                "    \"body\":\"" + orderId + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"" + orderId + "\"" +
                "    }" +
                "  }");//填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付宝的异步通知
     *
     * @param trade_status
     * @param out_trade_no
     */
    @RequestMapping("/payComplete")
    public void payComplete(String trade_status, String out_trade_no) {
        System.out.println("支付成功：" + trade_status + ",id:" + out_trade_no);
        if (trade_status.equals("TRADE_SUCCESS")) {
            // 付款成功，更改订单状态
            orderService.updateOrderStatus(out_trade_no, 1);
        }
    }

}
