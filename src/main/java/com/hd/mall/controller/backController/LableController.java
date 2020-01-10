package com.hd.mall.controller.backController;

import com.hd.mall.http.BaseResp;
import com.hd.mall.service.backService.LableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/lable/")
public class LableController {

    @Autowired
    LableService lableService;

    /**
     * 添加目录
     * @param pic
     * @param level
     * @param name
     * @param lableParentid
     * 1.目录等级,如果为1，不用传输3,4
     * 2.目录名称
     * 3.父目录的id
     * 4.目录图片
     */
    @PostMapping("add")
    public BaseResp addLable(@RequestParam(value = "file",required = false)  MultipartFile pic,
                             @RequestParam("level") String level,
                             @RequestParam("name") String name,
                             @RequestParam(value = "lable_parentid") String lableParentid
                              ){
        Map mapData = new HashMap();
        mapData.put("level",level);
        mapData.put("name",name);
        mapData.put("lable_parentid",lableParentid);
        BaseResp baseResp = new BaseResp();
        lableService.addLable(mapData,pic,baseResp);
        return baseResp;
    }

    /**
     * 编辑目录
     * @param pic

     */
    @PostMapping("update")
    public BaseResp updateLable(@RequestParam(value = "file",required = false)  MultipartFile pic,
                             @RequestParam("name") String name,
                             @RequestParam(value = "id") String id
    ){
        Map mapData = new HashMap();
        mapData.put("name",name);
        mapData.put("id",id);
        BaseResp baseResp = new BaseResp();
        lableService.updateLable(mapData,pic,baseResp);
        return baseResp;
    }

    /**
     * 查询目录
     * @param id
     */
    @GetMapping("select")
    public BaseResp selectLable(@RequestParam(value = "id",required = false) String id
    ){
        Map mapData = new HashMap();
        mapData.put("id",id);
        BaseResp baseResp = new BaseResp();
        lableService.selectLable(mapData,baseResp);
        return baseResp;
    }

    /**
     * 删除目录
     * @param dataMap
     * 1.id:id
     * 2.type dir:目录 pro：产品
     */
    @PostMapping("delete")
    public BaseResp deleteLable(@RequestBody Map dataMap){
        BaseResp baseResp = new BaseResp();
        lableService.deleteLable(dataMap,baseResp);
        return baseResp;
    }





}
