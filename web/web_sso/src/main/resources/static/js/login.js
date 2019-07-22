$(function () {

    //验证是否登录
    $.ajax({
        url: "http://localhost:8084/sso/checkLogin",
        dataType: "jsonp",
        jsonpCallback: "loginJson",
        success: function (data) {
            if (data != null) {
                $("#checkLogin").html(data.nickname + "您好，欢迎来到<b><a href=''>ShopCZ商城</a> <a href='http://localhost:8084/sso/logout'>注销</a>");
            } else {
                $("#checkLogin").html("[<a id='loginJs' onclick='loginJs();' href=''>登录</a>][<a href='http://localhost:8084/sso/toRegister'>注册</a>]");
            }
        }
    });
});

// 获取当前页面的url
function loginJs() {
    // location.href ：获取当前页面
    // 对当前页面的url地址进行编码
    var returnUrl = encodeURIComponent(location.href);

    // 跳转
    //location.href = "http://localhost:8084/sso/toLogin?returnUrl=" + returnUrl;
    $("#loginJs").attr("href", "http://localhost:8084/sso/toLogin?returnUrl=" + returnUrl);
};
