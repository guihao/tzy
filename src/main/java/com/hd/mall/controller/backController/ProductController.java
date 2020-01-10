package com.hd.mall.controller.backController;

import com.alibaba.fastjson.JSONArray;
import com.hd.mall.domain.Product_Info;
import com.hd.mall.domain.repo.ProductRepo;
import com.hd.mall.http.BaseResp;
import com.hd.mall.service.backService.LableService;
import com.hd.mall.service.backService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepo productRepo;

    /**
     * 添加产品
     * @param pics
     * @param lableId
     * @param productPrice
     * @param productDesp
     * 1.目录等级,如果为1，不用传输3,4
     * 2.目录名称
     * 3.父目录的id
     * 4.目录图片
     * 5.productId为-1时，表示新增，否则为修改
     */
    @PostMapping("add")
    public BaseResp addProduct(@RequestParam(value = "files") MultipartFile[] pics,
                             @RequestParam("lable_id") String lableId,
                             @RequestParam("product_name") String productName,
                             @RequestParam("product_price") String productPrice,
                             @RequestParam(value = "product_desp",required = false) String productDesp,
                             @RequestParam(value = "product_id") String productId
    ){
        Map mapData = new HashMap();
        mapData.put("files",pics);
        mapData.put("lable_id",lableId);
        mapData.put("product_name",productName);
        mapData.put("product_price",productPrice);
        mapData.put("product_desp",productDesp);
        mapData.put("product_id",productId);
        BaseResp baseResp = new BaseResp();
        productService.addProduct(mapData,baseResp);
        return baseResp;
    }



    @PostMapping("delete")
    public BaseResp deleteLable(@RequestBody Map dataMap){
        BaseResp baseResp = new BaseResp();
        productService.deleteLable(dataMap,baseResp);
        return baseResp;
    }

    @PostMapping("select")
    public BaseResp deleteLable(){
        BaseResp baseResp = new BaseResp();
        int id = 4;
        Optional<Product_Info> byId = productRepo.findById(4);
        Product_Info product_info = byId.get();
        int priceSize = product_info.getPriceSize();
        String productPrice = product_info.getProductPrice();
        String[] split = productPrice.split(",");
        List<String> strings = Arrays.asList(split);
        System.out.println(strings.toString());
        return baseResp;
    }
}
