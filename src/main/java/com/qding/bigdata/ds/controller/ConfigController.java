package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.model.Config;
import com.qding.bigdata.ds.service.ConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yanpf on 2017/9/5.
 */
@Controller
public class ConfigController extends BasicController {

    @Autowired
    ConfigService configService;

    @ResponseBody
    @RequestMapping("getConfig")
    public Config getConfig(String type, String key){
        Config config = configService.getConfig(type, key);
        return config;
    }

    @ResponseBody
    @RequestMapping("queryConfig")
    public SearchResult<Config> queryConfig(Config param){
        return configService.query(param);
    }


    @ResponseBody
    @RequestMapping("/configSave")
    public Result configSave(Config config) {
        if(StringUtils.isEmpty(config.getConfigType())){
            return Result.failed(1, "类型为必填");
        }
        if(StringUtils.isEmpty(config.getConfigKey())){
            return Result.failed(1, "key为必填");
        }
        if(StringUtils.isEmpty(config.getConfigValue())){
            return Result.failed(1, "value为必填");
        }
        if(StringUtils.isEmpty(config.getId())) {
            configService.save(config);
        }else {
            configService.update(config);
        }
        return Result.success();
    }

    @ResponseBody
    @RequestMapping("configDelete")
    public Result delete(String id){
        configService.delete(id);
        return Result.success();
    }
}
