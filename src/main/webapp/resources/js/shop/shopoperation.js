/**
 *
 *
 */
$(function () {
    var shopId = getQueryString("shopId");
    var isEdit = shopId? true:false;
    var initUrl = '/o2o_war_exploded/shopadmin/getshopinitinfo';
    var registerShopUrl = '/o2o_war_exploded/shopadmin/registershop';
    var shopInfoUrl = "/o2o_war_exploded/shopadmin/getshopbyid?shopId=" + shopId;
    var editShopUrl = '/o2o_war_exploded/shopadmin/modifyshop';
    if (!isEdit){
        getShopInitInfo();
    } else {
        getShopInfo(shopId);
    }

    // 通过店铺Id获取店铺信息
    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl, function(data) {
            if (data.success) {
                // 若访问成功，则依据后台传递过来的店铺信息为表单元素赋值
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                // 给店铺类别选定原先的店铺类别值
                var shopCategory = '<option data-id="'
                    + shop.shopCategory.shopCategoryId + '" selected>'
                    + shop.shopCategory.shopCategoryName + '</option>';
                var tempAreaHtml = '';
                // 初始化区域列表
                data.areaList.map(function(item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#shop-category').html(shopCategory);
                // 不允许选择店铺类别
                $('#shop-category').attr('disabled', 'disabled');
                $('#area').html(tempAreaHtml);
                // 给店铺选定原先的所属的区域
                $("#area option[data-id='" + shop.area.areaId + "']").attr(
                    "selected", "selected");
            }
        });
    }
    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';//获取店铺分类信息
                var tempAreaHtml = '';//获取区域信息
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">'
                        + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id = "' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }
        });
    }
        $('#submit').click(function () {
            // 创建shop对象
            var shop = {};
            if (isEdit){
                shop.shopId = shopId;
            }
            // 获取表单里的数据并填充进对应的店铺属性中
            shop.shopName = $('#shop-name').val();
            shop.shopAddr = $('#shop-addr').val();
            shop.phone = $('#shop-phone').val();
            shop.shopDesc = $('#shop-desc').val();
            // 选择选定好的店铺类别
            shop.shopCategory = {
                shopCategoryId : $('#shop-category').find('option').not(function() {
                    return !this.selected;
                }).data('id')
            };
            // 选择选定好的区域信息
            shop.area = {
                areaId : $('#area').find('option').not(function() {
                    return !this.selected;
                }).data('id')
            };
            //获取上传的图片文件流
            var shopImg = $('#shop-img')[0].files[0];
            // 生成表单对象，用于接收参数并传递给后台
            var formData = new FormData();
            // 添加图片流进表单对象里
            formData.append('shopImg', shopImg);
            // 将shop json对象转成字符流保存至表单对象key为shopStr的的键值对里
            formData.append('shopStr', JSON.stringify(shop));

            var verifyCodeActual = $('#j_captcha').val();
            if (!verifyCodeActual){
                $.toast('请输入验证码！');
                return;
            }
            formData.append('verifyCodeActual',verifyCodeActual);

            $.ajax({
                url:(isEdit? editShopUrl:registerShopUrl),
                type:'POST',
                data:formData,
                contentType:false,
                processData:false,
                cache:false,
                success:function (data) {
                    if (data.success){
                        $.toast('提交成功！');
                    }else {
                        $.toast('提交失败！' + data.errMsg);
                    }
                    $('#captcha_img').click();
                }
            });
        });

})

