package com.qding.bigdata.ds.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;

@Controller
public class MainController extends BasicController {

  @RequestMapping("/index")
  public ModelAndView index(HttpServletRequest request) {
    ModelAndView modelAndView =initModelAndView();
    modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.欢迎页面);
    return modelAndView;

  }

  @RequestMapping("/recRequestReport")
  public ModelAndView recRequestReport(HttpServletRequest request) {
    ModelAndView modelAndView = initModelAndView();;
    return modelAndView;

  }

  @RequestMapping("/520")
  public ModelAndView subject520(HttpServletRequest request) {
    ModelAndView modelAndView = initModelAndView();
    return modelAndView;

  }
/*  @RequestMapping("/mainDashBoard")
  public ModelAndView mainDashBoard(HttpServletRequest request) {
    ModelAndView modelAndView = initModelAndView();
    modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.大屏数据);
    return modelAndView;
    
  }*/

  
}
