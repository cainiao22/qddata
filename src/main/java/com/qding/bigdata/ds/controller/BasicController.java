package com.qding.bigdata.ds.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.enums.SideBarMenu;

public class BasicController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	protected ModelAndView initModelAndView() {
		ModelAndView modelAndView = new ModelAndView();
		addSidebarMenuList(modelAndView);

		return modelAndView;
	}

	protected ModelAndView initModelAndView(String view) {
		ModelAndView modelAndView = this.initModelAndView();
		modelAndView.setViewName(view);
		return modelAndView;
	}

	private void addSidebarMenuList(ModelAndView modelAndView) {
		Map<SideBarMenu, List<SideBarMenu>> sidebarMenuList = new LinkedHashMap<SideBarMenu, List<SideBarMenu>>();
		SideBarMenu current = null;
		for (SideBarMenu sideBarMenu : SideBarMenu.values()) {
			if (StringUtils.isEmpty(sideBarMenu.getParentName())) {
				sidebarMenuList.put(sideBarMenu, new ArrayList<SideBarMenu>());
				current = sideBarMenu;
			} else {
				sidebarMenuList.get(current).add(sideBarMenu);
			}
		}
		modelAndView.addObject("sidebarMenuList", sidebarMenuList);

	}
}