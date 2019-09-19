package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.service.CommonDataHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommunityReportController extends BasicController {

	@Autowired
    CommonDataHandleService commonDataHandleService;

	@RequestMapping("communityReport")
	public ModelAndView communityReport(){
		ModelAndView modelAndView =initModelAndView();
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.社区日报);
		return modelAndView;
	}


}
