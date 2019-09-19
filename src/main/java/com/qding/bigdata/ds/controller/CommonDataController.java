package com.qding.bigdata.ds.controller;

import com.alibaba.fastjson.JSON;
import com.qding.bigdata.ds.common.CommonDataResult;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.enums.RedisDataTypeEnum;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.CommonDataDetail;
import com.qding.bigdata.ds.model.CommonDataExportParam;
import com.qding.bigdata.ds.model.SqlQueryResult;
import com.qding.bigdata.ds.service.CommonDataHandleService;
import com.qding.bigdata.ds.service.QueryBySqlService;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.DownLoadPOIUtil;
import com.qding.bigdata.ds.util.ExportExcelUtil;
import com.qding.bigdata.ds.util.FreeMarkerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * Created by yanpf on 2017/7/27.
 */

@Controller
public class CommonDataController extends BasicController {

	@Autowired
	CommonDataHandleService commonDataHandleService;
	@Resource(name = "gpQueryBySqlServiceImpl")
	private QueryBySqlService gpQueryBySqlService;

	@ResponseBody
	@RequestMapping("dataApiSave")
	public Result save(CommonDataDetail commonDataDetail) {
		if(commonDataDetail.getCommonDataSummaryId()!=null){
			log.info("更新任务:{}", JSON.toJSONString(commonDataDetail));
			return commonDataHandleService.update(commonDataDetail);
		}else{
			log.info("保存任务:{}", JSON.toJSONString(commonDataDetail));
			return commonDataHandleService.save(commonDataDetail);
		}


	}
	@ResponseBody
	@RequestMapping("dataApiDelete")
	public Result dataApiDelete(CommonDataDetail commonDataDetail) {
		if(commonDataDetail.getCommonDataSummaryId()==null){
			return Result.failed(1, "参数不完善!");
		}
			return commonDataHandleService.deleteById(commonDataDetail.getCommonDataSummaryId());

	}
	/**
	 * 这里的名称是查找对应的执行任务用的，所以入参必传，且sql替换中的参数不可以与他重复
	 *
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("dataApiQuery/{name}")
	public Result query(@PathVariable String name, @RequestParam Map<String, String> params) {
		log.info("按照名称执行查询任务,name:" + name);
		Result result = commonDataHandleService.execute(name, params);
		if(result.getCode() != 0){
			CommonDataResult commonDataResult = new CommonDataResult(result.getCode(), result.getErrorMsg());
			commonDataResult.setName(name);
			commonDataResult.setData(Collections.emptyList());
			return commonDataResult;
		}
		return result;
	}


	/**
	 * 通用数据导出
	 * @param name 接口名称
	 * @param exportParam
	 * @param response
	 * @throws Exception
	 */
	//@RequestMapping("/dataApiExport/{name}")
	public void dataApiExport(@PathVariable String name, CommonDataExportParam exportParam, HttpServletResponse response) throws Exception {
		if(CollectionUtils.isEmpty(exportParam.getTitles())){
			return;
		}
		SqlQueryResult sqlQueryResult = new SqlQueryResult();
		CommonDataDetail param = new CommonDataDetail();
		param.setName(name);
		SearchResult<CommonDataDetail> result = commonDataHandleService.query(param);
		if(CollectionUtils.isEmpty(result.getRows())){
			return;
		}
		CommonDataDetail detail = result.getRows().get(0);
		Result<String> sqlContent = FreeMarkerUtil.process(detail.getQueryContent(), exportParam.getParams());
		if(sqlContent.getCode() != 0){
			return;
		}
		List<Object[]> resultList = null;
		try {
			resultList = gpQueryBySqlService.queryBySqlForTitle(sqlContent.getData(), exportParam.getTitles());
		} catch (Exception e) {
			sqlQueryResult.setResCode(-1);
			sqlQueryResult.setResMsg(e.getLocalizedMessage());
		}
		response.setContentType("application/csv;charset=utf-8");
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
	/**
	 * 通用数据导出
	 * @param name 接口名称
	 * @param exportParam
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dataApiExport/{name}")
	public void dataApiExportBetter(@PathVariable String name, CommonDataExportParam exportParam, HttpServletResponse response) throws Exception {
		Result<List<Map<String, Object>>> result = commonDataHandleService.execute(name, exportParam.getParams());
		response.setContentType("application/csv;charset=utf-8");
		response.setHeader("Content-disposition",
				"attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xls");
		int code = result.getCode();
		if(code == 0){
			List<Map<String, Object>> data = result.getData();
			DownLoadPOIUtil.createFixationSheet(response.getOutputStream(), exportParam.getTitles(), data);
		}
	}

	/**
	 * 清除缓存
	 *
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("dataApiCleanCache/{name}")
	public Result cleanCache(@PathVariable String name) {
	return  commonDataHandleService.cleanCache(name);
	}

	/**
	 * 根据条件筛选接口信息
	 *
	 * @param commonDataDetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("dataApiList")
	public SearchResult<CommonDataDetail> list(CommonDataDetail commonDataDetail) {
		log.info("根据条件筛选数据接口信息:{}", JSON.toJSONString(commonDataDetail));
		return commonDataHandleService.query(commonDataDetail);
	}

}
