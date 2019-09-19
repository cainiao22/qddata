package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.service.MisUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class MisUserController extends BasicController {

    @Autowired
    private MisUserService misUserService;

    /**
     * 罗盘权限查询全部用户信息
     * @return
     */
    @RequestMapping("/getMisUserList")
    @ResponseBody
    public List<Map<String,Object>> getMisUserList(){
        return  misUserService.getMisUserList();
    }

    /**
     * 将mis账户同步至compass系统
     * @return
     */
    @RequestMapping("/addUserByMis")
    @ResponseBody
    public Map<String,Object> addUserByMis(String start,String end){
        return  misUserService.addOrUpdateDsUserByMisScheduled(start,end);
    }

    /**
     * 初次同步mis账户至compass
     * @return
     */
    @RequestMapping("/addUserByMisFirst")
    @ResponseBody
    public Map<String,Object> addUserByMisFirst(){
        return  misUserService.addDsUserByMis();
    }

}
