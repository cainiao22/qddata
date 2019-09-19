package com.qding.bigdata.ds.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.VO.UserPortraitDetailVO;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.ExportParam;
import com.qding.bigdata.ds.model.MostCommonDataExportParam;
import com.qding.bigdata.ds.model.PortraitUserGroup;
import com.qding.bigdata.ds.service.UserGroupService;
import com.qding.bigdata.ds.service.UserProfileV2Service;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.ExportExcelUtil;
import com.qding.bigdata.ds.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserProfileV2Controller extends BasicController {
	@Autowired
	private UserProfileV2Service userProfileV2Service;
	@Autowired
	private UserGroupService userGroupService;

	@RequestMapping("/userProfileAnalysisV2")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView modelAndView = initModelAndView();
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.画像分析新);
		return modelAndView;
	}

    @RequestMapping("/userProfileDetails")
    public ModelAndView profileDetailPage() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.画像分析新);
        return modelAndView;
    }

	@RequestMapping("/userProfileV2query")
	@ResponseBody
	public Object userProfileV2query(@RequestParam("query") String query) {
		return userProfileV2Service.query(JSONObject.parseObject(query));
	}
	
	/**
	 * 导出用户列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportUsersV2")
	@ResponseBody
	public Object exportUsers(HttpServletRequest request) {
		String userGroupId = request.getParameter("userGroupId");
		Object exportUsers = null;
		try {
			exportUsers = userProfileV2Service.exportUsers(userGroupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exportUsers;
	}
	
	/**
	 * 查询用户列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUserListFileByIdV2")
	@ResponseBody
	public Object getUsersByGroupId(HttpServletRequest request) {
		
		long beginTime = System.currentTimeMillis();
		Map<String,Object> rsMap = new HashMap<String, Object>();
		String fileName = "";
		if(StringUtils.isNotBlank(request.getParameter("userGroupId"))){
			PortraitUserGroup userGroupParam = new PortraitUserGroup();
			userGroupParam.setId(request.getParameter("userGroupId"));
			PortraitUserGroup userGroup = userGroupService.getOne(userGroupParam);
			if (null != userGroup) {
				fileName =  PropertiesUtil.getPropertiesByKey(
						Constant.CONFIGFILE, "export.user.pre.url")+"/"+request.getParameter("userGroupId")+"/"+"export_user_"+request.getParameter("userGroupId")+".txt";
			}else{
				fileName = "文件不存在";
			}
		}
		rsMap.put("fileName",fileName);
		long endTime = System.currentTimeMillis();
		rsMap.put("usetime", (endTime - beginTime));
		return rsMap;
	}

	/**
	 *获取所有用户
	 * @return
	 */
	@RequestMapping("/queryAllUserOverview")
	@ResponseBody
	public Object queryAllUserOverview(){
        JSONArray conditionsArr = null;
		return userProfileV2Service.queryAllUserOverview(conditionsArr);
	}

    /**
     *获取C端用户
     * @return
     */
    @RequestMapping("/queryCUserOverview")
    @ResponseBody
    public Object queryCUserOverview(){
        JSONArray conditionsArr = new JSONArray();
        JSONObject o = new JSONObject();
        o.put("tag","member_type");
        o.put("rule","in");
        o.put("value1","member_type_c,member_type_pc");
        conditionsArr.add(o);
        return userProfileV2Service.queryCUserOverview(conditionsArr);
    }

    /**
     *获取P端用户
     * @return
     */
    @RequestMapping("/queryPUserOverview")
    @ResponseBody
    public Object queryPUserOverview(){
        JSONArray conditionsArr = new JSONArray();
        JSONObject o = new JSONObject();
        o.put("tag","member_type");
        o.put("rule","in");
        o.put("value1","member_type_p,member_type_pc");
        conditionsArr.add(o);
        return userProfileV2Service.queryPUserOverview(conditionsArr);
    }

	/**
	 *画像分析
	 * @return
	 */
	@RequestMapping("/queryPortraitAnalysis")
	@ResponseBody
	public Object queryPortraitAnalysis(@RequestParam("query") String query){
		JSONObject jsonObject = JSONObject.parseObject(query);
		JSONArray conditionsArr = (JSONArray) jsonObject.get("conditions");
		String typeVal = (String) jsonObject.get("typeVal");
		return userProfileV2Service.getValueByConditional(conditionsArr,typeVal);
	}
	@RequestMapping("/getUserDetailsByPagination")
	@ResponseBody
	public Map<String,Object> getUserDetailsByPagination(@RequestParam("query") String query){
		JSONObject jsonObject = JSONObject.parseObject(query);
		JSONArray conditionsArr = (JSONArray) jsonObject.get("conditions");
		String wildcardParam = (String) jsonObject.get("wildcardParam");
		String from = String.valueOf(jsonObject.get("from")) ;
		String size = String.valueOf(jsonObject.get("size"));
		return  userProfileV2Service.getUserDetails(conditionsArr,wildcardParam,Integer.parseInt(from),Integer.parseInt(size));
    }

	/**
	 * 查询模糊匹配名称或者SKUID
	 * @return
	 */
	@RequestMapping("/searchSKU")
	@ResponseBody
	public Map<Object,Object> searchSKU(String searchKey, Boolean isMatch){
		int count =  userProfileV2Service.searchSKUByWareNameOrIDCount(searchKey, isMatch);
		Map<Object, Object> objectMap = userProfileV2Service.checkMaxNum(count);
		if(null == objectMap){
			return userProfileV2Service.searchSKUByWareNameOrID(searchKey, isMatch);
		}else{
			return objectMap;
		}
	}
	/**
	 * 查询模糊匹配名称或者classifID
	 * @return
	 */
	@RequestMapping("/searchClassif")
	@ResponseBody
	public Map<Object,Object> searchClassif(String searchKey, Boolean isMatch){
		int count =  userProfileV2Service.searchClassifByNameOrIDCount(searchKey, isMatch);
		Map<Object, Object> objectMap = userProfileV2Service.checkMaxNum(count);
		if(null == objectMap){
			return userProfileV2Service.searchClassifByNameOrID(searchKey, isMatch);
		}else{
			return objectMap;
		}
	}
	/**
	 * 查询模糊匹配名称或者categoryID
	 * @return
	 */
	@RequestMapping("/searchCategory")
	@ResponseBody
	public Map<Object,Object> searchCategory(String searchKey, Boolean isMatch){
		int count =  userProfileV2Service.searchCategoryByNameOrIDCount(searchKey, isMatch);
		Map<Object, Object> objectMap = userProfileV2Service.checkMaxNum(count);
		if(null == objectMap){
			return userProfileV2Service.searchCategoryByNameOrID(searchKey, isMatch);
		}else{
			return objectMap;
		}
	}

	@PostMapping("exportUserDatail")
	public void exportUserDatail(MostCommonDataExportParam param, HttpServletResponse response) throws Exception {
		String query = param.getParams().get("query");
		JSONObject queryObject = null;
		if(StringUtils.isNotBlank(query)){
			queryObject = JSONObject.parseObject(query);
		}
		List<UserPortraitDetailVO> userPortraitDetailVOList = userProfileV2Service.getUserInfo(queryObject);
		ExportParam exportParam = new ExportParam();
		exportParam.setTitles(param.getTitles());
		if(userPortraitDetailVOList!=null && userPortraitDetailVOList.size()>0){
			SXSSFWorkbook workbook = ExportExcelUtil.getWorkbook(exportParam, "用户画像用户ID", userPortraitDetailVOList);
			response.setContentType("application/xls;charset=utf-8");
			response.setHeader("Content-disposition",
					"attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xls");
			workbook.write(response.getOutputStream());
		}
	}
}
