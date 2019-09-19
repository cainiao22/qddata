package com.qding.bigdata.ds.controller;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.MetaField;
import com.qding.bigdata.ds.model.MetaTable;
import com.qding.bigdata.ds.model.SmartQueryParam;
import com.qding.bigdata.ds.model.SqlQueryParam;
import com.qding.bigdata.ds.model.SqlQueryResult;
import com.qding.bigdata.ds.service.MakeSqlService;
import com.qding.bigdata.ds.service.MetaFieldService;
import com.qding.bigdata.ds.service.MetaTableService;
import com.qding.bigdata.ds.service.QueryBySqlService;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.ExportExcelUtil;
import com.qding.bigdata.ds.util.StringUtil;

@Controller
public class SmartQueryController extends BasicController {

	@Autowired
	private MetaTableService metaTableService;
	@Autowired
	private MetaFieldService metaFieldService;

	@Resource(name = "gpQueryBySqlServiceImpl")
	private QueryBySqlService gpQueryBySqlService;
	@Resource(name = "gPMakeSqlServiceImpl")
	private MakeSqlService gPMakeSqlServiceImpl;
/*	@RequestMapping("/smartQuery")
	public ModelAndView smartQuery(MetaTable metaTableParam) {
		log.debug("metaTableParam:{}",metaTableParam);
		ModelAndView mav = this.initModelAndView();
		mav.addObject(Constant.SIDEBAR_MENU, SideBarMenu.自助分析);
		MetaTable metaTableFilter = new MetaTable();
		metaTableFilter.setQueryEnable(1);
		List<MetaTable> metaTableList = metaTableService.listAll(metaTableFilter);
		mav.addObject("metaTableList", metaTableList);
		if(StringUtils.isEmpty(metaTableParam.getId())){
			return mav;
		}
		metaTableParam=metaTableService.getOne(metaTableParam);
		MetaField metaFieldParam = new MetaField();
		metaFieldParam.setTableId(metaTableParam.getId());
		List<MetaField> metaFieldList = metaFieldService.listAll(metaFieldParam);
		mav.addObject("metaTableParam", metaTableParam);
		mav.addObject("metaFieldList", metaFieldList);
		
		
		return mav;
	}*/
	@RequestMapping("/smartQuerySubmit")
	public ModelAndView smartQuerySubmit(SmartQueryParam smartQueryParam) {
		long from = System.currentTimeMillis(); 
		ModelAndView mav = this.initModelAndView("queryBySqlSubmit");
		SqlQueryResult sqlQueryResult = new SqlQueryResult();
		String sql = gPMakeSqlServiceImpl.make(smartQueryParam);
		List<Object[]> resultList = null;
		try {
			resultList = gpQueryBySqlService.queryBySql(sql);
		} catch (Exception e) {
			sqlQueryResult.setResCode(-1);
			sqlQueryResult.setResMsg(e.getLocalizedMessage());
			mav.addObject("sqlQueryResult", sqlQueryResult);
			return mav;
		}
		long to = System.currentTimeMillis();
		sqlQueryResult.setTimeUsed(StringUtil.decimal((to-from)/1000.00,"0.00"));
		sqlQueryResult.setResultList(resultList.size()>501?resultList.subList(0, 501):resultList);
		sqlQueryResult.setTotalCnt(resultList.isEmpty()?0:resultList.size()-1);
		sqlQueryResult.setSql(sql);
		mav.addObject("sqlQueryResult", sqlQueryResult);
	
		return mav;
	}
	@RequestMapping("/smartQueryExport")
	public void smartQueryExport(SmartQueryParam smartQueryParam, HttpServletResponse response) throws Exception {

		SqlQueryResult sqlQueryResult = new SqlQueryResult();
		String sql = gPMakeSqlServiceImpl.make(smartQueryParam);
		List<Object[]> resultList = null;
		try {
			resultList = gpQueryBySqlService.queryBySql(sql);
		} catch (Exception e) {
			sqlQueryResult.setResCode(-1);
			sqlQueryResult.setResMsg(e.getLocalizedMessage());
			return ;
		}
		
		response.setContentType("application/csv;charset=gbk");
		response.setHeader("Content-disposition",
				"attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".csv");
		sqlQueryResult.setTotalCnt(resultList.size() - 1);
		OutputStream os = response.getOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(os,"gbk");
		ExportExcelUtil.writeNewCsv(writer, resultList);
		writer.flush();
		os.flush();
		writer.close();
		os.close();
	}
	
}
