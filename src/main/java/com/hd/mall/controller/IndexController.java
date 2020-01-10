package com.hd.mall.controller;

import com.hd.mall.http.BaseResp;
import com.hd.mall.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

import java.util.Map;

@RestController
@RequestMapping("/index/")
public class IndexController {

    @Autowired
    IndexService indexService;
    @PostMapping("add")
    public BaseResp addLable(@RequestParam(value = "file",required = false) MultipartFile pic){
        BaseResp baseResp = new BaseResp();
        indexService.changeIndexPic(pic,baseResp);
        return baseResp;
    }

    //首页展示接口





    //点目录返回接口

}
