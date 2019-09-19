package com.qding.bigdata.ds.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;

@Controller
public class DashBoardController  extends BasicController {
	  @RequestMapping("/zhihuishi")
	  public ModelAndView zhihuishi(HttpServletRequest request) {
	    ModelAndView modelAndView =initModelAndView();
	    modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.高管驾驶舱);
	    return modelAndView;

	  }

	@RequestMapping("/zhihuishiByHive")
	public ModelAndView zhihuishiByHive(HttpServletRequest request) {
		ModelAndView modelAndView =initModelAndView();
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.高管驾驶舱);
		return modelAndView;

	}


	  @RequestMapping("travelDashboard")
	  public ModelAndView travelDashboard(){
		  ModelAndView modelAndView =initModelAndView();
		  modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.旅游驾驶舱);
		  return modelAndView;
	  }

	@RequestMapping("/intelligentVessel")
	public ModelAndView intelligentVessel(HttpServletRequest request) {
		ModelAndView modelAndView =initModelAndView();
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.智器航行舱);
		return modelAndView;

	}
}
