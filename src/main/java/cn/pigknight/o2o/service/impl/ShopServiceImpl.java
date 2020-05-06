package cn.pigknight.o2o.service.impl;

import cn.pigknight.o2o.dao.ShopDao;
import cn.pigknight.o2o.dto.ShopExecution;
import cn.pigknight.o2o.entity.Shop;
import cn.pigknight.o2o.enums.ShopStateEnum;
import cn.pigknight.o2o.exceptions.ShopOperationException;
import cn.pigknight.o2o.service.ShopService;
import cn.pigknight.o2o.util.ImageUtil;
import cn.pigknight.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, File shopImg) {
        if (shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum<=0){
                throw new ShopOperationException("店铺创建失败");//只有用ShopOperationException的时候，事务才会回滚
            }else {
                if (shopImg != null){
                    //存储图片
                    try {
                        addShopImg(shop,shopImg);
                    }catch (Exception e){
                        throw new ShopOperationException("addShopImg error:" + e.getMessage());
                    }
                    //更新店铺图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum<=0){
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        }catch (Exception e){
            throw new ShopOperationException("addShop error:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, File shopImg) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnails(shopImg,dest);
        shop.setShopImg(shopImgAddr);
    }
}
