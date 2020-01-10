package com.hd.mall.service.backService;

import com.hd.mall.Constant.DataConstant;
import com.hd.mall.domain.Lable_Info;
import com.hd.mall.domain.Product_Info;
import com.hd.mall.domain.repo.LableRepo;
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
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class LableService {

    @Autowired
    LableRepo lableRepo;

    @Autowired
    ProductRepo productRepo;

    @Value("${pic.dirpath}")
    String picPath;

    public void addLable(Map mapData,MultipartFile file,BaseResp baseResp) {
        log.info("添加目录");
        Lable_Info lable_info = new Lable_Info();
        //等级为1为根目录
        String levle = mapData.get("level").toString();
        //lable_parentid为-1,该目录为根目录
        String lableParentid = mapData.get("lable_parentid").toString();
        if ("1".equals(levle)){
            log.info("添加根目录");
            String name = mapData.get("name").toString();
            lable_info.setIsDir("0");
            lable_info.setLableName(name);
            lable_info.setLableLevel(levle);
            lable_info.setLableParentid(lableParentid);
            lableRepo.save(lable_info);
            baseResp.msg = "存储成功";
            baseResp.code = 0;
            return;
        }else {
            log.info("添加"+levle+"级目录");
            if (file.isEmpty()) {
                baseResp.msg = "文件不存在";
                baseResp.code = -1;
                return ;
            }

            String name = mapData.get("name").toString();
            String suffixName = FileUtil.getSuffixName(file.getOriginalFilename());
            if (!(DataConstant.picList.contains(suffixName))){
                baseResp.msg = "请上传jpg,jpeg或png";
                baseResp.code = -1;
                return ;
            }
            String filePath = picPath+File.separator+name+"_"+RandGen.fourRandomGen() +"."+suffixName;
            String newPath = FileUtil.saveFile(file, filePath);
            lable_info.setIsDir("0");
            lable_info.setLableLevel(levle);
            lable_info.setLableName(name);
            lable_info.setPicPath(newPath);
            lable_info.setLableParentid(lableParentid);
            try {
                lableRepo.save(lable_info);
                baseResp.msg = "存储完成";
                baseResp.code = 0;
                return;
            }catch (Exception e){
                baseResp.msg = "存储失败:" +e.getMessage();
                baseResp.code = -1;
                return ;
            }
        }

    }


    public void updateLable(Map mapData, MultipartFile pic, BaseResp baseResp) {
        log.info("修改目录");
        int id = Integer.valueOf(mapData.get("id").toString());
        Optional<Lable_Info> opt = lableRepo.findById(id);
        if (!opt.isPresent()){
            baseResp.msg = "目录不存在";
            baseResp.code = -1;
            return ;
        }
        Lable_Info lable_info = opt.get();
        String levle = lable_info.getLableLevel();
        String name = mapData.get("name").toString();
        if ("1".equals(levle)){
            lable_info.setLableName(name);
            lableRepo.save(lable_info);
            baseResp.msg = "更新成功";
            baseResp.code = 0;
            return ;
        }else {
            lable_info.setLableName(name);
            if (pic.isEmpty()){
                lableRepo.save(lable_info);
                baseResp.msg = "更新成功";
                baseResp.code = 0;
                return ;
            }
            String oldPath = lable_info.getPicPath();
            String suffixName = FileUtil.getSuffixName(pic.getOriginalFilename());
            if (!(DataConstant.picList.contains(suffixName))){
                baseResp.msg = "请上传jpg,jpeg或png";
                baseResp.code = -1;
                return ;
            }
            String filePath = picPath+File.separator+name+"_"+RandGen.fourRandomGen() +"."+suffixName;
            try {
                FileUtil.saveFile(pic,filePath);
                log.info("新图片存储成功");
                File file = new File(oldPath);
                file.delete();
                log.info("老图片删除成功");
                lable_info.setPicPath(filePath);
                lableRepo.save(lable_info);
                baseResp.msg = "更新成功";
                baseResp.code = 0;
                return ;
            }catch (Exception e){
                log.info("更新失败");
                baseResp.msg = "更新失败";
                baseResp.code = -1;
                return ;
            }
        }
    }

    public void deleteLable(Map dataMap, BaseResp baseResp) {
        log.info("删除目录");
        int id = Integer.valueOf(dataMap.get("id").toString());
        String type = dataMap.get("type").toString();
        List<Lable_Info> lableList = lableRepo.findByParentId(id);
        List<Product_Info> productList = productRepo.findByLableId(id);
        if (lableList.size()>0||productList.size()>0){
            log.info("删除失败");
            baseResp.msg = "删除失败，目录下有目录或者产品";
            baseResp.code = -1;
            return;
        }
        if ("dir".equals(type)){
            log.info("删除的是目录");
            lableRepo.deleteById(id);
            baseResp.msg = "删除成功";
        }else {
            log.info("删除的是产品");
            productRepo.deleteById(id);
            baseResp.msg = "删除成功";
        }

    }

    public void selectLable(Map mapData, BaseResp baseResp) {

        String id = mapData.get("id").toString();
        if ("-1".equals(id)){
            log.info("查询根目录");
            List<Lable_Info> byLevel = lableRepo.findByLevel();
            baseResp.msg = "查询成功";
            baseResp.code = 0;
            baseResp.result=byLevel;
            return;
        }else {
            List<Lable_Info> lableInfo = lableRepo.findByParentId(Integer.valueOf(id));
            if (lableInfo.size()>0){
                log.info("该目录下是目录");
                baseResp.msg = "查询成功";
                baseResp.code = 0;
                baseResp.result=lableInfo;
                return;
            }else {
                List<Product_Info> byLableId = productRepo.findByLableId(Integer.valueOf(id));
                if (byLableId.size()==0){
                    log.info("该目录为空");
                    baseResp.msg = "该目录为空";
                    baseResp.code = 0;
                    baseResp.result=byLableId;
                    return;
                }else {
                    log.info("该目录为产品");
                    baseResp.msg = "查询成功";
                    baseResp.code = 0;
                    baseResp.result=byLableId;
                    return;
                }
            }
        }
    }
}
