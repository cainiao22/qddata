package com.qding.bigdata.ds.controller;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.SqlQueryParam;
import com.qding.bigdata.ds.model.SqlQueryResult;
import com.qding.bigdata.ds.service.QueryBySqlService;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.ExportExcelUtil;
import com.qding.bigdata.ds.util.StringUtil;

@Controller
public class QueryBySqlController extends BasicController {

	@Resource(name = "gpQueryBySqlServiceImpl")
	private QueryBySqlService gpQueryBySqlService;

	/*@RequestMapping("/queryBySql")
	public ModelAndView queryBySql() {

		ModelAndView mav = this.initModelAndView();
		mav.addObject(Constant.SIDEBAR_MENU, SideBarMenu.SQL查询);
		return mav;
	}*/

	@RequestMapping("/queryBySqlSubmit")
	public ModelAndView queryBySqlSubmit(SqlQueryParam sqlQueryParam) {
		long from = System.currentTimeMillis();
		ModelAndView mav = this.initModelAndView();
		SqlQueryResult sqlQueryResult = new SqlQueryResult();
		boolean isAllowed = checkAllow(sqlQueryParam);
		if (!isAllowed) {
			sqlQueryResult.setResCode(-2);
			sqlQueryResult.setResMsg("该sql被禁止！");
			mav.addObject("sqlQueryResult", sqlQueryResult);
			return mav;
		}
		List<Object[]> resultList = null;
		try {
			if ("1".equals(sqlQueryParam.getDataSource())) {
				resultList = gpQueryBySqlService.queryBySql(sqlQueryParam.getSql());
			} else if ("2".equals(sqlQueryParam.getDataSource())) {
				// TODO
			}
		} catch (Exception e) {
			sqlQueryResult.setResCode(-1);
			sqlQueryResult.setResMsg(e.getLocalizedMessage());
			mav.addObject("sqlQueryResult", sqlQueryResult);
			return mav;
		}
		long to = System.currentTimeMillis();
		sqlQueryResult.setTimeUsed(StringUtil.decimal((to - from) / 1000.00, "0.00"));
		if(null != resultList){
			sqlQueryResult.setResultList(resultList.size() > 101 ? resultList.subList(0, 101) : resultList);
			sqlQueryResult.setTotalCnt(resultList.isEmpty() ? 0 : resultList.size() - 1);
		}else {
			sqlQueryResult.setResultList(resultList);
			sqlQueryResult.setTotalCnt(0);
		}

		sqlQueryResult.setDataSource(sqlQueryParam.getDataSource());
		sqlQueryResult.setSql(sqlQueryParam.getSql());
		mav.addObject("sqlQueryResult", sqlQueryResult);
		return mav;
	}

	@RequestMapping("/queryBySqlExport")
	public void queryBySqlExport(SqlQueryParam sqlQueryParam, HttpServletResponse response) throws Exception {
		SqlQueryResult sqlQueryResult = new SqlQueryResult();
		boolean isAllowed = checkAllow(sqlQueryParam);
		if (!isAllowed) {
			sqlQueryResult.setResCode(-2);
			sqlQueryResult.setResMsg("该sql被禁止！");
			return;
		}
		List<Object[]> resultList = null;
		try {
			if ("1".equals(sqlQueryParam.getDataSource())) {
				resultList = gpQueryBySqlService.queryBySql(sqlQueryParam.getSql());
			} else if ("2".equals(sqlQueryParam.getDataSource())) {
				// TODO
			}
		} catch (Exception e) {
			sqlQueryResult.setResCode(-1);
			sqlQueryResult.setResMsg(e.getLocalizedMessage());
		}
		response.setContentType("application/csv;charset=gbk");
		response.setHeader("Content-disposition",
				"attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".csv");
		sqlQueryResult.setTotalCnt(null!=resultList?resultList.size() - 1:0);
		OutputStream os = response.getOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(os,"gbk");
		ExportExcelUtil.writeNewCsv(writer, resultList);
		writer.flush();
		os.flush();
		writer.close();
		os.close();
	}

	private boolean checkAllow(SqlQueryParam sqlQueryParam) {
		if (!sqlQueryParam.getSql().trim().toLowerCase().startsWith("select")) {
			return false;
		}
		return true;
	}

}
