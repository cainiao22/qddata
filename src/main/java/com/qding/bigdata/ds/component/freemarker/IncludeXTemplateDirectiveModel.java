package com.qding.bigdata.ds.component.freemarker;

import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.component.CommonDataSqlHandler;
import com.qding.bigdata.ds.dao.CommonDataSqlDao;
import com.qding.bigdata.ds.dao.CommonDataSummaryDao;
import com.qding.bigdata.ds.model.CommonDataSql;
import com.qding.bigdata.ds.model.CommonDataSummary;
import com.qding.bigdata.ds.service.impl.CommonDataHandleServiceImpl;
import com.qding.bigdata.ds.util.FreeMarkerUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by yanpf on 2017/10/19.
 */

public class IncludeXTemplateDirectiveModel implements TemplateDirectiveModel {

    private static final String TAG_PARAM = "name";




    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        System.out.println(env);
        String name = params.get(TAG_PARAM).toString();
        CommonDataSql commonDataSql = CommonDataSqlHandler.instance.getBySummaryName(name);
        if(commonDataSql == null){
            throw new IOException("include 的 name名称不存在:" + name);
        }

        Result<String> child = FreeMarkerUtil.process(commonDataSql.getQueryContent(), FreeMarkerUtil.getLocalParams());
        env.getOut().append(" ").append(child.getData()).append(" ");
        System.out.println(env.getOut().toString());
        System.out.println(params);
        System.out.println(loopVars);
    }
}
