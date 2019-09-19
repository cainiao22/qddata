package com.qding.bigdata.ds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.MetaTable;
import com.qding.bigdata.ds.service.MetaTableService;
import com.qding.bigdata.ds.util.PageUtil;

@Controller
public class ReportController extends BasicController {

	@Autowired
	private MetaTableService metaTableService;

	/*@RequestMapping("/reportList")
	public ModelAndView reportList(MetaTable metaTableParam) {
		Integer totalCount = metaTableService.count(metaTableParam);
		List<MetaTable> metaTableList = metaTableService.list(metaTableParam);
		ModelAndView modelAndView = initModelAndView();
		modelAndView.addObject("metaTableTypes", Constant.META_TABLE_TYPES);
		modelAndView.addObject("totalCount", totalCount);
		modelAndView.addObject("totalPage", PageUtil.getTotalCount(totalCount, metaTableParam.getPageCount()));
		modelAndView.addObject("metaTableList", metaTableList);
		modelAndView.addObject("metaTableParam", metaTableParam);
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.报表中心);
		return modelAndView;
	}*/

	@RequestMapping("sqlProcessView")
	public ModelAndView sqlProcessList(){
		return new ModelAndView("sqlProcessView");
	}

	

}
