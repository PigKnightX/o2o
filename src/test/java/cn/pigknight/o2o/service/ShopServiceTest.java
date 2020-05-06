package cn.pigknight.o2o.service;

import cn.pigknight.o2o.BaseTest;
import cn.pigknight.o2o.dto.ShopExecution;
import cn.pigknight.o2o.entity.Area;
import cn.pigknight.o2o.entity.PersonInfo;
import cn.pigknight.o2o.entity.Shop;
import cn.pigknight.o2o.entity.ShopCategory;
import cn.pigknight.o2o.enums.ShopStateEnum;
import com.sun.org.apache.xpath.internal.operations.Equals;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺1");
        shop.setShopDesc("test1");
        shop.setShopAddr("test1");
        shop.setPhone("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("F://o2o//image//xiaohuangren.jpg");

        ShopExecution se = shopService.addShop(shop, shopImg);
        assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
    }
}
