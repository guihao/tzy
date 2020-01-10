package com.hd.mall.service;

import com.hd.mall.Constant.DataConstant;
import com.hd.mall.http.BaseResp;
import com.hd.mall.util.FileUtil;
import com.hd.mall.util.RandGen;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class IndexService {

    @Value("${pic.productpath}")
    String picPath;

    public void changeIndexPic(MultipartFile file, BaseResp baseResp) {
        try {
            String suffixName = FileUtil.getSuffixName(file.getOriginalFilename());
            if (!(DataConstant.picList.contains(suffixName))){
                baseResp.msg = "请上传jpg,jpeg或png";
                baseResp.code = -1;
                return ;
            }
            String filePath = picPath+File.separator+"indexpic" +"."+suffixName;
            FileUtil.saveFile(file, filePath);
            baseResp.msg = "存储完成";
            baseResp.code = 0;
            return;
        }catch (Exception e){
            baseResp.msg = "存储失败";
            baseResp.code = -1;
            return;
        }

    }
}
