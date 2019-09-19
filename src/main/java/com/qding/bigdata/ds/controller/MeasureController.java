package com.qding.bigdata.ds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.model.Measure;
import com.qding.bigdata.ds.service.MeasureService;
import com.qding.bigdata.ds.util.CommonUtil;
import com.qding.bigdata.ds.util.PageUtil;

@Controller
public class MeasureController extends BasicController {

	@Autowired
	private MeasureService measureService;

	@RequestMapping("/measureList")
	public ModelAndView metaTableList(Measure measureParam) {
		Integer totalCount = measureService.count(measureParam);
		List<Measure> measureList = measureService.list(measureParam);
		ModelAndView modelAndView = initModelAndView();
		modelAndView.addObject("totalCount", totalCount);
		modelAndView.addObject("totalPage", PageUtil.getTotalCount(totalCount, measureParam.getPageCount()));
		modelAndView.addObject("measureStatus", Constant.MEASURE_STATUS);
		modelAndView.addObject("measureList", measureList);
		modelAndView.addObject("measureParam", measureParam);
		return modelAndView;
	}

	@RequestMapping("/measureAdd")
	public ModelAndView measureAdd() {
		ModelAndView modelAndView = initModelAndView();
		modelAndView.addObject("measureStatus", Constant.MEASURE_STATUS);
		return modelAndView;
	}

	@RequestMapping("/measureSave")
	public String measureSave(Measure measureParam) {
		if (CommonUtil.isEmpty(measureParam.getId())) {
			measureService.save(measureParam);
		} else {
			measureService.update(measureParam);
		}
		return "redirect:./measureList";
	}

	@RequestMapping("/measureManage")
	public ModelAndView measureManage(Measure measureParam) {
		Measure measure = measureService.getOne(measureParam);
		ModelAndView modelAndView = initModelAndView();
		modelAndView.addObject("measure", measure);
		modelAndView.addObject("measureStatus", Constant.MEASURE_STATUS);
		modelAndView.setViewName("measureAdd");
		return modelAndView;
	}

	@RequestMapping("/measureDelete")
	public String measureDelete(Measure measureParam) {
		measureService.delete(measureParam);
		return "redirect:./measureList";
	}

}
