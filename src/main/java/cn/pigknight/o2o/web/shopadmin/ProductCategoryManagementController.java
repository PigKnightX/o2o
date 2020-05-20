package cn.pigknight.o2o.web.shopadmin;


import cn.pigknight.o2o.dto.ProductCategoryExecution;
import cn.pigknight.o2o.dto.Result;
import cn.pigknight.o2o.entity.ProductCategory;
import cn.pigknight.o2o.entity.Shop;
import cn.pigknight.o2o.enums.ProductCategoryStateEnum;
import cn.pigknight.o2o.exceptions.ProductCategoryOperationException;
import cn.pigknight.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
        //To be removed
        //        Shop shop = new Shop();
        //        shop.setShopId(7L);
        //        request.getSession().setAttribute("currentShop",shop);

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (currentShop !=null && currentShop.getShopId() > 0){
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true,list);
        }else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false,ps.getState(),ps.getStateInfo());
        }
    }

    @RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
        }
        if (productCategoryList.size() > 0){
            try{
                ProductCategoryExecution pe = new ProductCategoryExecution();
                pe = productCategoryService.batchAddProductCategory(productCategoryList);
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("essMsg", pe.getStateInfo());
                    return modelMap;
                }
            }catch (ProductCategoryOperationException e){
                modelMap.put("success", false);
                modelMap.put("essMsg",e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别");
        }
        return modelMap;
    }


}