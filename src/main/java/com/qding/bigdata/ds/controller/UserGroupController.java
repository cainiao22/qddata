package com.qding.bigdata.ds.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.DIMProject;
import com.qding.bigdata.ds.model.PortraitDictionaries;
import com.qding.bigdata.ds.model.PortraitTag;
import com.qding.bigdata.ds.model.PortraitUserGroup;
import com.qding.bigdata.ds.service.PortraitDictionariesService;
import com.qding.bigdata.ds.service.PortraitTagService;
import com.qding.bigdata.ds.service.UserProfileV2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.service.UserGroupService;
import com.qding.bigdata.ds.service.impl.UserProfileService;
import com.qding.bigdata.ds.util.CommonUtil;
import com.qding.bigdata.ds.util.CookieUtil;

@Controller
public class UserGroupController extends BasicController {

    public static Logger logger = LoggerFactory.getLogger(UserProfileService.class);

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserProfileV2Service userProfileService;

    @Autowired
    private PortraitTagService PortraittagService;

    @Autowired
    private PortraitDictionariesService portraitDictionariesService;

    static ExecutorService pool = Executors.newFixedThreadPool(10);


    private List<PortraitTag> userGroupTagSelect(PortraitTag tagParam) {
        List<PortraitTag> treeList = PortraittagService.listAll(tagParam);
        try {
            for (int i = 0; i < treeList.size(); i++) {
                if (3 == treeList.get(i).getDataType()) {
                    PortraitTag portraitTag = treeList.get(i);
                    if ("city".equals(portraitTag.getTag())) {
                        List<DIMProject> ldm = portraitDictionariesService.getDimProjectCity();
                        portraitDictionariesService.addDinctionaries(ldm, portraitTag);
                    } else if ("community".equals(portraitTag.getTag())) {
                        List<DIMProject> ldm = portraitDictionariesService.getDimProjectCommunity();
                        portraitDictionariesService.addDinctionaries(ldm, portraitTag);
                    } else if ("property".equals(portraitTag.getTag())) {
                        List<DIMProject> ldm = portraitDictionariesService.getDimProjectProperty();
                        portraitDictionariesService.addDinctionaries(ldm, portraitTag);
                    } else {
                        PortraitDictionaries pd = new PortraitDictionaries();
                        pd.setSource(treeList.get(i).getTag());
                        List<PortraitDictionaries> pdList = portraitDictionariesService.listAll(pd);
                        if (pdList.size() > 0) {
                            treeList.get(i).setLpd(pdList);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return treeList;
    }

    /**
     * 获取用户群组列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getUserGroups")
    @ResponseBody
    public Object getUserGroups(HttpServletRequest request) {
        long beginTime = System.currentTimeMillis();
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        //从Cookie获取用户名（对应boss后台账号）
        String userId = CookieUtil.getCookieValue(request, Constant.USERNAME);
        PortraitUserGroup userGroupParam = new PortraitUserGroup();
        userGroupParam.setCreateUser(userId);
        List<PortraitUserGroup> userGroups = userGroupService.getUserGroups(userGroupParam);
        List<Object> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userGroups)) {
            for (PortraitUserGroup userGroup : userGroups) {
                Map<String, String> userGroupMap = new HashMap<String, String>();
                userGroupMap.put("groupId", userGroup.getId());
                userGroupMap.put("groupName", userGroup.getName());
                userGroupMap.put("modModel", userGroup.getModModel() + "");
                list.add(userGroupMap);
            }
        }
        map.put("reqid", UUID.randomUUID().toString().replace("-", ""));
        map.put("num", list.size());
        map.put("results", list);
        long endTime = System.currentTimeMillis();
        map.put("usetime", (endTime - beginTime));
        return map;
    }

    /**
     * 根据群组ID获取分群条件
     *
     * @param userGroupId
     * @return
     */
    @RequestMapping("/getConditionsById")
    @ResponseBody
    public Object getConditionsById(String userGroupId) {
        long beginTime = System.currentTimeMillis();
        List<PortraitTag> portraitTags = this.userGroupTagSelect(new PortraitTag());
        Map<String, PortraitTag> portraitTagMap = portraitTags.stream().collect(Collectors.toMap(PortraitTag::getTag, a -> a, (k1, k2) -> k1));
        Map<String, Object> map = userGroupService.getConditionsById(userGroupId);
        Object results = map.get("results");
        if(results instanceof JSONObject) {
            JSONObject result = (JSONObject) results;
            JSONArray conditions = result.getJSONArray("conditions");
            for (int i = 0; i < conditions.size(); i++) {
                JSONObject condition = conditions.getJSONObject(i);
                String tag = condition.getString("tag");
                String value1 = condition.getString("value1");
                String value2 = condition.getString("value2");
                String rule = condition.getString("rule");
                PortraitTag portraitTag = portraitTagMap.get(tag);
                if (portraitTag != null && portraitTag.getLpd() != null) {
                    if ("in".equals(rule)) {
                        StringBuffer sb = new StringBuffer();
                        String[] value1Arr = value1.split(",");
                        for (PortraitDictionaries dictionaries : portraitTag.getLpd()) {
                            for (String item : value1Arr) {
                                if (dictionaries.getExample_id().equals(item)) {
                                    sb.append(dictionaries.getExample_name()).append(",");
                                }
                            }
                        }
                        if (sb.length() != 0) {
                            sb.setLength(sb.length() - 1);
                            value1 = sb.toString();
                            condition.put("value1", value1);
                        }
                    } else {
                        for (PortraitDictionaries dictionaries : portraitTag.getLpd()) {
                            if (dictionaries.getExample_id().equals(value1)) {
                                value1 = dictionaries.getExample_name();
                            }
                            if (dictionaries.getExample_id().equals(value2)) {
                                value2 = dictionaries.getExample_name();
                            }
                        }

                        condition.put("value1", value1);
                        if (value2 != null) {
                            condition.put("value2", value2);
                        }
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        map.put("usetime", (endTime - beginTime));
        return map;
    }

    @RequestMapping("saveUserGroup")
    @ResponseBody
    public Object saveUserGroup(PortraitUserGroup userGroup, HttpServletRequest request) {
        long beginTime = System.currentTimeMillis();
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        String msg = "";
        String results = "success";
        //usesrId为空，则从Cookie获取用户名（对应boss后台账号）
        userGroup.setCreateUser(CookieUtil.getCookieValue(request, Constant.USERNAME));
        userGroup.setUpdateUser(CookieUtil.getCookieValue(request, Constant.USERNAME));

        try {
            //检查状态，为空则默认置为1
            if (userGroup.getStatus() == null) {
                userGroup.setStatus(0);
            }
            if (CommonUtil.isEmpty(userGroup.getId())) {
                userGroup.setId(UUID.randomUUID().toString());
                userGroupService.save(userGroup);
                msg = "save success!";
            } else {
                userGroupService.update(userGroup);
                msg = "update success!";
            }

            //用户群组ID存在，则生成用户列表
            PortraitUserGroup saveedUserGroup = userGroupService.getOne(userGroup);
            if (null != saveedUserGroup) {
                pool.submit(new ExportUserRunner(userGroup.getId(), userProfileService));
            }

        } catch (Exception e) {
            msg = "save or update faild";
            results = "faild";
        }
        map.put("reqid", UUID.randomUUID().toString().replace("-", ""));
        map.put("results", results);
        map.put("msg", msg);
        long endTime = System.currentTimeMillis();
        map.put("usetime", (endTime - beginTime));
        return map;
    }

    @ResponseBody
    @RequestMapping("listUserGroup")
    public SearchResult<PortraitUserGroup> listUserGroup(PortraitUserGroup userGroup) {
        List<PortraitUserGroup> userGroupList = userGroupService.list(userGroup);
        int count = userGroupService.count(userGroup);
        SearchResult<PortraitUserGroup> result = new SearchResult<>();
        result.setRows(userGroupList);
        result.setCurrentPage(userGroup.getPage());
        result.setPageCount(userGroup.getPageCount());
        result.setTotal(count);

        return result;
    }

    @RequestMapping("/userGroupIndex")
    public ModelAndView userGroupIndex() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.群组管理);
        return modelAndView;

    }

    @RequestMapping("/usergroup")
    public ModelAndView index() {
        ModelAndView modelAndView = initModelAndView();
        return modelAndView;

    }

    @RequestMapping("addUserGroup")
    public ModelAndView addUserGroup() {
        ModelAndView modelAndView = initModelAndView();
        return modelAndView;
    }

    @RequestMapping("manageUserGroup")
    public ModelAndView manageUserGroup(PortraitUserGroup userGroupParam) {
        PortraitUserGroup userGroup = userGroupService.getOne(userGroupParam);
        ModelAndView modelAndView = initModelAndView("addUserGroup");
        modelAndView.addObject("userGroup", userGroup);
        return modelAndView;
    }

    @RequestMapping("deleteUserGroup")
    public String deleteUserGroup(PortraitUserGroup userGroup) {
        userGroupService.delete(userGroup);
        return "redirect:./userGroupIndex";
    }


    /**
     * 导出用户列表线程
     */
    static class ExportUserRunner implements Runnable {

        private String userGroupId;
        private UserProfileV2Service userProfileService;

        public ExportUserRunner(String userGroupId, UserProfileV2Service userProfileService) {
            this.userGroupId = userGroupId;
            this.userProfileService = userProfileService;
        }

        @Override
        public void run() {
            try {
                logger.info("{},正在导出用户群组【{}】列表", Thread.currentThread().getName(), userGroupId);
                long start = System.currentTimeMillis();
                userProfileService.exportUsers(userGroupId);
                long end = System.currentTimeMillis();
                logger.info("{},已成功导出用户群组【{}】列表，耗时：{}", new String[]{Thread.currentThread().getName(), userGroupId, String.valueOf(end - start)});
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
