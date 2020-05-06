package cn.pigknight.o2o.service;

import cn.pigknight.o2o.dto.ShopExecution;
import cn.pigknight.o2o.entity.Shop;

import java.io.File;

public interface ShopService {
    ShopExecution addShop(Shop shop, File shopImg);
}
