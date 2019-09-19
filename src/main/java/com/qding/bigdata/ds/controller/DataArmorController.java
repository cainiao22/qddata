package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.service.DataArmorCommonService;
import com.qding.bigdata.ds.service.ModuleService;
import com.qding.bigdata.ds.service.UserService;
import com.qding.bigdata.ds.util.CommonUtil;
import com.qding.bigdata.ds.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/*测试数据盔甲*/
@Controller
public class DataArmorController extends BasicController {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private UserService userService;
    @Autowired
    private DataArmorCommonService dataArmorCommonService;

    /*基础档案*/
    @RequestMapping("/basicfiles")
    public ModelAndView basicfiles(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = initModelAndView();
        setModelAndView(modelAndView,request,response);
        modelAndView.addObject("requestDataArmorUrl","basicfiles/index.do");
        return modelAndView;
    }

    /*审批列表*/
    @RequestMapping("/approvalList")
    public ModelAndView approvalList(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = initModelAndView();
        setModelAndView(modelAndView,request,response);
        modelAndView.addObject("requestDataArmorUrl","testspringmvcmap/getApprovalListNew");
        return modelAndView;
    }

    /*物业公司*/
    @RequestMapping("/property")
    public ModelAndView property(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = initModelAndView();
        setModelAndView(modelAndView,request,response);
        modelAndView.addObject("requestDataArmorUrl","property/list");
        return modelAndView;
    }

    /*社区信息*/
    @RequestMapping("/communitylist")
    public ModelAndView communitylist(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = initModelAndView();
        setModelAndView(modelAndView,request,response);
        modelAndView.addObject("requestDataArmorUrl","community/list");
        return modelAndView;
    }

    public void setModelAndView(ModelAndView modelAndView,HttpServletRequest request, HttpServletResponse response){
        String username = CookieUtil.getCookieValue(request, "username");
        String userId   = CookieUtil.getCookieValue(request, "userId");
        if(!dataArmorCommonService.getToten(request)){
            dataArmorCommonService.setToten(request,response);
        }
        modelAndView.addObject("dataArmorHost",dataArmorCommonService.getDataArmorHost());
        modelAndView.addObject("username", username);
        modelAndView.addObject("userId", userId);
        //2.生成token,token加密方式是用户+用户ID+当日时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());//为获取当前系统时间
        //3.加密token
        String token = CommonUtil.md5(username + userId + date);
        modelAndView.addObject("qdArmorToken", token);
        modelAndView.setViewName("dataArmorAll");
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.数据盔甲);
    }
}
