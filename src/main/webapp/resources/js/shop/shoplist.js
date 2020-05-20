$(function(){
    getlist();
    function getlist(e) {
        sessionStorage.setItem("user", "测试");
        $.ajax({
            url:"/o2o_war_exploded/shopadmin/getshoplist",
            data: {user:sessionStorage.getItem("user")},
            type:"get",
            dataType:"json",
            success:function (data) {
                if (data.success){
                    handleList(data.shopList);
                    handleUser(data.user);
                }
            }
        });
    }
    function handleUser(data) {
        console.log(111, data);
        document.getElementById("user-name").innerHTML = "123";
        // $("user-name").text("123");
    }

    function handleList(data) {
        var html = '';
        data.map(function(item, index) {
            html += '<div class="row row-shop"><div class="col-40">'
                + item.shopName + '</div><div class="col-40">'
                + shopStatus(item.enableStatus)
                + '</div><div class="col-20">'
                + goShop(item.enableStatus, item.shopId) + '</div></div>';

        });
        $('.shop-wrap').html(html);
    }

    function shopStatus(status) {
        if (status === 0){
            return '审核中';
        } else if (status === -1){
            return '店铺非法'
        } else if (status === 1) {
            return '审核通过';
        }
    }

    function goShop(status, id) {
        if (status === 1) {
            return '<a href="/o2o_war_exploded/shopadmin/shopmanagement?shopId=' + id
                + '">进入</a>';
        } else {
            return '';
        }
    }
});