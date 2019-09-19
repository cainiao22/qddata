package com.qding.bigdata.ds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class RealtimeController extends BasicController {

	@RequestMapping("realtime_sales")
	public ModelAndView realtimeSales() {

		return this.initModelAndView();
	}
}
