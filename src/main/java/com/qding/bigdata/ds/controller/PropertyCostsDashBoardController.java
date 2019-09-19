package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PropertyCostsDashBoardController extends BasicController {

	@RequestMapping("/propertyCostsDashboard")
	public ModelAndView propertyCostsDashboard(HttpServletRequest request) {
		ModelAndView modelAndView =initModelAndView();
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.物业费数据仪表盘);
		return modelAndView;

	}

}
