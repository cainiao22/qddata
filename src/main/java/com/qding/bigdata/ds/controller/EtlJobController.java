package com.qding.bigdata.ds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.model.EtlJobQueue;
import com.qding.bigdata.ds.model.MetaTable;
import com.qding.bigdata.ds.service.EtlJobQueueService;
import com.qding.bigdata.ds.service.MetaTableService;
import com.qding.bigdata.ds.util.PageUtil;

@Controller
public class EtlJobController extends BasicController {

	@Autowired
	private EtlJobQueueService etlJobQueueService;
	@Autowired
	private MetaTableService metaTableService;

	@RequestMapping("etlJobQueue")
	public ModelAndView etlJobQueue(EtlJobQueue etlJobQueueParam) {
		Integer totalCount = etlJobQueueService.count(etlJobQueueParam);
		List<EtlJobQueue> etlJobQueueList = etlJobQueueService.list(etlJobQueueParam);
		ModelAndView modelAndView = initModelAndView();
		modelAndView.addObject("totalCount", totalCount);
		modelAndView.addObject("totalPage", PageUtil.getTotalCount(totalCount, etlJobQueueParam.getPageCount()));
		modelAndView.addObject("etlJobQueueList", etlJobQueueList);
		modelAndView.addObject("etlJobQueueParam", etlJobQueueParam);
		MetaTable metaTable = new MetaTable();
		metaTable.setId(etlJobQueueParam.getEtlJobId());
		metaTable = metaTableService.getOne(metaTable);
		modelAndView.addObject("metaTable", metaTable);
		return modelAndView;
	}

	@RequestMapping("listExecuteLog")
	@ResponseBody
	public Object listExecuteLog(EtlJobQueue etlJobQueueParam) {
		return etlJobQueueService.listExecuteLog(etlJobQueueParam);
	}
}
