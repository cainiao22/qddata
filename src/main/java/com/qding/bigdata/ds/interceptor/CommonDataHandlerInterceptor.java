package com.qding.bigdata.ds.interceptor;

import com.alibaba.fastjson.JSON;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.dao.CommonDataSummaryDao;
import com.qding.bigdata.ds.model.CommonDataSummary;
import com.qding.bigdata.ds.util.AuthorityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yanpf
 * @date 2019/2/28 15:09
 * @description
 */

@Component
public class CommonDataHandlerInterceptor extends HandlerInterceptorAdapter {

    String reg = "dataApiQuery/([^?/&]*)";
    Pattern p = Pattern.compile(reg);

    @Autowired
    CommonDataSummaryDao commonDataSummaryDao;

    Result notAllowed = new Result(403, "您没有权限访问此接口");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getParameter("url");
        if(StringUtils.isEmpty(url)){
            return true;
        }
        Matcher m = p.matcher(url);
        if (m.find()) {
            String jobName = m.group(1);
            CommonDataSummary commonDataSummary = commonDataSummaryDao.getByName(jobName);
            if(commonDataSummary == null){
                return true;
            }
            boolean hasAuthority = false;
            String projectNames = commonDataSummary.getProjectName();
            if(!StringUtils.isEmpty(projectNames)){
                String[] projectList = projectNames.split(",");
                for(int i=0; i<projectList.length; i++){
                    hasAuthority = AuthorityUtil.hasAuthority(projectList[i]);
                    if(hasAuthority){
                        break;
                    }
                }

            }
            if(!hasAuthority){
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("utf-8");
                response.getWriter().println(JSON.toJSONString(notAllowed));
            }
            return  hasAuthority;
        }else{
            return true;
        }
    }
}
