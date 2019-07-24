$(function () {
    $.ajax({
        url: "http://localhost:8085/cart/showCart",
        dataType: "jsonp",
        jsonpCallBack: "showCart",
        success: function (data) {
            if (data != null) {
                // 购物车的数量
                $("#cartnum").html(data.length);

                // 显示购物车
                var html = "<ul style=\"width: 200px; height: 200px\" >";
                for(var i = 0; i < data.length; i++){
                    html += "<li style='width: 200px; margin-bottom: 20px'><span>";

                    html += "<img style='width: 40px; height: 30px' " +
                        "src='http://192.168.245.199:8080/" + data[i].goods.gimage.split("\|")[0] + "'/> <a>" + data[i].goods.gname + "</a> x " + data[i].gnumber;
                    html += "</span></li>";
                }
                html += "</ul>";

                $("#cartid").html(html);
            }
        }
    });
})