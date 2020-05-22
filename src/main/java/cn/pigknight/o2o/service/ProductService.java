package cn.pigknight.o2o.service;

import cn.pigknight.o2o.dto.ImageHolder;
import cn.pigknight.o2o.dto.ProductExecution;
import cn.pigknight.o2o.entity.Product;
import cn.pigknight.o2o.exceptions.ProductOperationException;

import java.io.InputStream;
import java.util.List;

public interface ProductService {
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
            throws ProductOperationException;
}
