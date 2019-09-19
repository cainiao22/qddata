package com.qding.bigdata.ds.util;

import com.alibaba.fastjson.JSON;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.component.freemarker.IncludeXTemplateDirectiveModel;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by yanpf on 2017/10/18.
 */

public class FreeMarkerUtil {

    private static Logger logger = LoggerFactory.getLogger(FreeMarkerUtil.class);
    private static final String FREE_MARKER_KEY = "free_marker_conf_key";

    private static ThreadLocal<Map<String, ? extends Object>> localParams = new ThreadLocal<Map<String, ? extends Object>>();

    public static Map<String, ? extends Object> getLocalParams(){
        return localParams.get();
    }

    public static Result<String> process(String content, Map<String, ? extends Object> params){
        logger.info("========freeMaker 解析内容:{}, 参数:{}==========", content, JSON.toJSONString(params));
        localParams.set(params);
        Configuration conf = new Configuration(Configuration.VERSION_2_3_0);
        conf.setNumberFormat("#");
        conf.setSharedVariable("includeX", new IncludeXTemplateDirectiveModel());
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate(FREE_MARKER_KEY, content);
        conf.setTemplateLoader(stringLoader);
        try {
            Template template = conf.getTemplate(FREE_MARKER_KEY,"utf-8");
            StringWriter writer = new StringWriter();
            Map<String, Object> real = new HashMap<String, Object>();
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                if(entry.getKey().equals(Constant.PAGE_SIZE)){
                    real.put(Constant.PAGE_SIZE, Integer.valueOf(entry.getValue().toString()));
                }else if(entry.getKey().equals(Constant.CURRENT_PAGE)){
                    real.put(Constant.CURRENT_PAGE, Integer.valueOf(entry.getValue().toString()));
                }else {
                    real.put(entry.getKey(), entry.getValue());
                }

            }
            template.process(real, writer);

            logger.info("===========解析器解析结果:{}============", writer.toString());
            return Result.success(writer.toString());
        } catch (Exception e) {
            logger.error("===========解析器解析失败:{}===========", e);
            return Result.failed(100, e.getMessage());
        }
    }
}
