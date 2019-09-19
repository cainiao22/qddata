package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.PortraitDictionaries;
import com.qding.bigdata.ds.model.PortraitTag;
import com.qding.bigdata.ds.model.PortraitUserGroup;
import com.qding.bigdata.ds.service.*;
import com.qding.bigdata.ds.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class PortraitUserGroupController extends BasicController {

	public static Logger logger = LoggerFactory.getLogger(PortraitUserGroupController.class);

	@Autowired
	private PortraitUserGroupService userGroupService;

	@Autowired
	private UserProfileV2Service userProfileService;

	@Autowired
	private PortraitTagService PortraittagService;

	@Autowired
	private PortraitDictionariesService portraitDictionariesService;

	static ExecutorService pool = Executors.newFixedThreadPool(10);

	/**
	 * 获取用户群组列表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("getPortraitUserGroupsNew")
	@ResponseBody
	public Object getUserGroupsNew(HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		//从Cookie获取用户名ID
		String username = CookieUtil.getCookieValue(request, Constant.USERNAME);
		PortraitUserGroup userGroupParam = new PortraitUserGroup();
		userGroupParam.setCreateUser(username);
		userGroupParam.setIsShare(1);
		List<PortraitUserGroup> userGroups = null;
		if(username != null){
			userGroups = userGroupService.getPortraitUserGroups(userGroupParam);
		}
		//公有
		PortraitUserGroup userGroupParampublic = new PortraitUserGroup();
		userGroupParampublic.setIsShare(0);
		List<PortraitUserGroup> userGroupspublic = userGroupService.getPortraitUserGroups(userGroupParampublic);
		map.put("public",userGroupspublic);
		map.put("private",userGroups);
		return map;
	}

	/**
	 * 根据群组ID获取分群条件
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("getPortraitConditionsById")
	@ResponseBody
	public Object getConditionsById(HttpServletRequest request) {
		long beginTime = System.currentTimeMillis();
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String userGroupId = request.getParameter("userGroupId");
		map = userGroupService.getConditionsById(userGroupId);
		long endTime = System.currentTimeMillis();
		map.put("usetime", (endTime - beginTime));
		return map;
	}

	@RequestMapping("copyPortraitUserGroup")
	@ResponseBody
	public Object copyUserGroup(PortraitUserGroup userGroupId,HttpServletRequest request) {
		PortraitUserGroup userGroup = userGroupService.getOne(userGroupId);

		long beginTime = System.currentTimeMillis();
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String msg="";
		String results = "success";
		//usesrId为空，则从Cookie获取用户ID（对应boss后台账号）
		String username = CookieUtil.getCookieValue(request, Constant.USERNAME);
		userGroup.setCreateUser(username);
		userGroup.setUpdateUser(username);


		userGroup.setName(userGroup.getName()+"copy");
		try {
			//检查状态，为空则默认置为1
			if(userGroup.getStatus()==null){
				userGroup.setStatus(0);
			}

				userGroup.setId(UUID.randomUUID().toString());
				userGroupService.save(userGroup);
				msg = "copy success!";


//			//用户群组ID存在，则生成用户列表
//			PortraitUserGroup saveedUserGroup = userGroupService.getOne(userGroup);
//			if(null!=saveedUserGroup){
//				pool.submit(new ExportUserRunner(userGroup.getId(),userProfileService));
//			}

		} catch (Exception e) {
			msg = "copy faild";
			results = "faild";
		}
		map.put("reqid",UUID.randomUUID().toString().replace("-", ""));
		map.put("results", results);
		map.put("msg", msg);
		map.put("userGroup",userGroup);
		long endTime = System.currentTimeMillis();
		map.put("usetime", (endTime - beginTime));
		return map;
	}

	@ResponseBody
	@RequestMapping("listPortraitUserGroup")
	public SearchResult<PortraitUserGroup> listUserGroup(PortraitUserGroup userGroup) {
		List<PortraitUserGroup> userGroupList = userGroupService.list(userGroup);
		int count = userGroupService.count(userGroup);
		SearchResult<PortraitUserGroup> result = new SearchResult<PortraitUserGroup>();
		result.setRows(userGroupList);
		result.setCurrentPage(userGroup.getPage());
		result.setPageCount(userGroup.getPageCount());
		result.setTotal(count);

		return result;
	}

	@RequestMapping("/userPortraitGroupIndex")
    @ResponseBody
	public ModelAndView userGroupIndex(HttpServletRequest request) {
		ModelAndView modelAndView = initModelAndView();
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.群组管理);
		return modelAndView;

	}

	@RequestMapping("/portraitusergroup")
    @ResponseBody
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView modelAndView = initModelAndView();
		return modelAndView;

	}

	@RequestMapping("addOrUpdatePortraitUserGroup")
    @ResponseBody
	public Object addUserGroup(PortraitUserGroup userGroupParam,HttpServletRequest request) {
        Map<String,Object> map = new LinkedHashMap<String,Object>();
        try {
            if(userGroupParam != null && !StringUtils.isEmpty(userGroupParam.getId())){
                //更新
                userGroupParam.setUpdateUser(CookieUtil.getCookieValue(request, Constant.USERNAME));
                userGroupParam.setUpdateTime(new Date());
                userGroupService.update(userGroupParam);
            }else{
                //新增
                userGroupParam.setId(UUID.randomUUID().toString());
                userGroupParam.setCreateUser(CookieUtil.getCookieValue(request, Constant.USERNAME));
                userGroupParam.setCreateTime(new Date());
                userGroupService.save(userGroupParam);
            }
            pool.submit(new ExportUserRunner(userGroupParam.getId(), userProfileService));
            map.put("results", "success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
	}

	@RequestMapping("managePortraitUserGroup")
    @ResponseBody
	public Object manageUserGroup(PortraitUserGroup userGroupParam) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		try {
			PortraitUserGroup userGroup = userGroupService.getOne(userGroupParam);
			map.put("results", "success");
			map.put("userGroup", userGroup);

		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}

		return map;
	}

	public List<PortraitTag> getPortraitTag(){

		PortraitTag tagParam = new PortraitTag();
		tagParam.setStatus(0);
		List<PortraitTag> treeList = PortraittagService.listAll(tagParam);
		List<PortraitDictionaries> pdList = null;
		PortraitDictionaries pd = null;
		try {
			for (int i = 0; i < treeList.size(); i++) {
				if ( 3 == treeList.get(i).getDataType()){
					pdList = new ArrayList<PortraitDictionaries>();
					pd = new PortraitDictionaries();
					pd.setSource(treeList.get(i).getTag());
					pdList = portraitDictionariesService.listAll(pd);
					PortraitDictionaries pdj = null;
//                    for (int j = 0; j < pdList.size(); j++) {
//                        pdj = new PortraitDictionaries();
//                        pdj.setId(pdList.get(j).getId());
//                        pdj.setExample_id(pdList.get(j).getExample_id());
//                        pdj.setExample_name(pdList.get(j).getExample_name());
//                        pdj.setSource(pdList.get(j).getSource());
//                        pdj.setSortno(pdList.get(j).getSortno());
//                        treeList.get(i).getLpd().add(pdj);
//                    }
					if (pdList.size()>0) {
						treeList.get(i).setLpd(pdList);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return  treeList;
	}

	@RequestMapping("deletePortraitUserGroup")
    @ResponseBody
	public String deleteUserGroup(PortraitUserGroup userGroup) {
		userGroupService.delete(userGroup);
		return "redirect:./userGroupIndex";
	}


	@RequestMapping("deletePortraitUserGroupnew")
	@ResponseBody
	public String deleteUserGroupNew(PortraitUserGroup userGroup) {
		try {
			userGroupService.delete(userGroup);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "faild";
		}


	}

	/**
	 * 导出用户列表线程
	 */
	static class ExportUserRunner  implements Runnable{

		private String userGroupId;
		private UserProfileV2Service userProfileService;
		public ExportUserRunner(String userGroupId,UserProfileV2Service userProfileService){
             this.userGroupId = userGroupId;
             this.userProfileService = userProfileService;
		}

		@Override
		public void run() {
			try {
				logger.info(Thread.currentThread().getName() +",正在导出用户群组【"+userGroupId+"】列表");
				long start = System.currentTimeMillis();
				userProfileService.exportUsers(userGroupId);
				long end = System.currentTimeMillis();
				logger.info(Thread.currentThread().getName() +",已成功导出用户群组【"+userGroupId+"】列表，耗时："+(end-start));
			}catch(Exception e){
				e.printStackTrace();
			}

		}
	}
}
