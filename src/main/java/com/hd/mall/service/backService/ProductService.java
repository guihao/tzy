package com.hd.mall.service.backService;

import com.alibaba.fastjson.JSONArray;
import com.hd.mall.Constant.DataConstant;
import com.hd.mall.domain.Product_Info;
import com.hd.mall.domain.repo.ProductRepo;
import com.hd.mall.http.BaseResp;
import com.hd.mall.util.FileUtil;
import com.hd.mall.util.RandGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
@Slf4j
public class ProductService {

    @Value("${pic.productpath}")
    String picPath;

    @Autowired
    ProductRepo productRepo;

    public void addProduct(Map mapData, BaseResp baseResp) {
        String productId = mapData.get("product_id").toString();
        int lableId = Integer.valueOf(mapData.get("lable_id").toString()) ;
        String productPrice = mapData.get("product_price").toString();
        String productDesp = mapData.get("product_desp").toString();
        String productName = mapData.get("product_name").toString();
        List list = new ArrayList();
        MultipartFile[] files = (MultipartFile[]) mapData.get("files");
        for(int i=0;i<files.length;i++) {
            String suffixName = FileUtil.getSuffixName(files[i].getOriginalFilename());
            if (!(DataConstant.picList.contains(suffixName))){
                baseResp.msg = "请上传jpg,jpeg或png";
                baseResp.code = -1;
                return ;
            }
            String filePath = picPath+File.separator+productName+File.separator+productName+"_"+i +"."+suffixName;
            String str = FileUtil.saveFile(files[i], filePath);
            list.add(str);
        }
        if ("-1".equals(productId)){
            log.info("添加产品");
            Product_Info product_info = new Product_Info();
            product_info.setLableId(lableId);
            product_info.setProductDesp(productDesp);
            product_info.setProductName(productName);
            product_info.setProductPrice(productPrice);
            product_info.setProductPic(list.toString());
            product_info.setPriceSize(list.size());
            productRepo.save(product_info);
            baseResp.msg = "添加成功";
            baseResp.code = 0;
            return;
        }else {
            try {
                log.info("修改信息");
                Integer pId = Integer.valueOf(productId);
                Product_Info product_info = productRepo.findById(pId).get();
                String oldName = product_info.getProductName();
                product_info.setProductDesp(productDesp);
                product_info.setProductName(productName);
                product_info.setProductPrice(productPrice);
                product_info.setProductPic(list.toString());
                product_info.setPriceSize(list.size());
                productRepo.save(product_info);
                baseResp.msg = "修改成功";
                baseResp.code = 0;
                deleteOldPic(oldName);

                return;
            }catch (Exception e){
                baseResp.msg = "修改失败";
                baseResp.code = 0;
                return;
            }

        }


    }

    private void deleteOldPic(String oldName) {
        String filePath = picPath+File.separator+oldName+File.separator;
        log.info("文件夹路径"+filePath);
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    file2.delete();
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        file.delete();


        log.info("老文件删除成功");
    }

    public void deleteLable(Map dataMap, BaseResp baseResp) {
        try {
            Integer id = Integer.valueOf(dataMap.get("id").toString());
            Product_Info product_info = productRepo.findById(id).get();
            String oldName = product_info.getProductName();
            productRepo.deleteById(id);
            baseResp.msg="删除成功";
            baseResp.code=0;
            deleteOldPic(oldName);
            return;
        }catch (Exception e){
            baseResp.msg="删除失败"+e.getMessage();
            baseResp.code=0;
            return;
        }



    }
}
