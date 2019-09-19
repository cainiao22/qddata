package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.model.HttpDataParam;
import com.qding.bigdata.ds.util.HttpClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author yanpf
 * @date 2018/5/30 18:30
 * @description
 */

@Controller
public class HttpController {

    @ResponseBody
    @RequestMapping("getHttpData")
    public Object getHttpData(HttpDataParam httpDataParam) throws Exception {
        Object result = null;
        if(httpDataParam.getPost() != null && httpDataParam.getPost()) {
            result = HttpClientUtil.doPost(httpDataParam.getUrl(), httpDataParam.getParams());
        }else {
            result = HttpClientUtil.doGet(httpDataParam.getUrl(), httpDataParam.getParams());
        }

        return result;
    }

}
