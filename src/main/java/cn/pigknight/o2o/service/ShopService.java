package cn.pigknight.o2o.service;

import cn.pigknight.o2o.dto.ShopExecution;
import cn.pigknight.o2o.entity.Shop;
import cn.pigknight.o2o.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

public interface ShopService {
    /**
     * 根据shopCondition分页返回相应店铺列表
     * @param shopCondition
     * @return
     */
    public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize );
    /**
     * 通过店铺Id获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    ShopExecution modifyShop(Shop shop,InputStream shopImgInputStream,String fileName) throws ShopOperationException;

    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName);
}
