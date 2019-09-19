package com.qding.bigdata.ds.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.LpUserService;
import com.qding.bigdata.ds.service.MidLuopanLuruCityDataService;
import com.qding.bigdata.ds.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class LpUserController extends BasicController{

    @Autowired
    private LpUserService lpUserService;
    @Autowired
    private MidLuopanLuruCityDataService midLuopanLuruCityDataService;
    /**
     * 罗盘权限查询全部用户信息
     * @return
     */
    @RequestMapping("/lpUserList")
    @ResponseBody
    public List<LpUser> lpUserList(){
        return  lpUserService.selectAll();
    }

    /**
     * 罗盘用户权限管理
     * @return
     */
    @RequestMapping("/lpUserPermissions")
    public ModelAndView lpUserPermissions() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.罗盘权限管理);
        return modelAndView;
    }
    /**
     * 罗盘物业云指标录入
     * @return
     */
    @RequestMapping("/lpwyyEntering")
    public ModelAndView lpwyyEntering() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.罗盘物业云指标录入);
        return modelAndView;
    }
    @RequestMapping("/midLuopanLuruCityDataList" )
    @ResponseBody
    public  List<MidLuopanLuruCityData> midLuopanLuruCityDataList(MidLuopanLuruCityData param){
        List<MidLuopanLuruCityData> midLuopanLuruCityDataList = midLuopanLuruCityDataService.listNopage(param);
        return midLuopanLuruCityDataList;
    }
    @RequestMapping(value = "/midLuopanLuruCityDataUpdate_old" ,method ={RequestMethod.POST})
    @ResponseBody
    public  Result midLuopanLuruCityDataUpdate_old(MidLuopanLuruCityData param){
       String params="{ \"todo_project_num\":"+ param.getTodoProjectNum()
               +" , \"doing_project_num\":"+ param.getDoingProjectNum()
               +" , \"done_project_num\":"+ param.getDoneProjectNum()
               +" , \"cancel_project_num\":"+ param.getCancelProjectNum()+"}";
        param.setJsonValue(params);
        param.setUpdatetime(DateUtil.formatDateToFullString2(new Date()));
        midLuopanLuruCityDataService.updateByParam(param);
        return Result.success();
    }

    @RequestMapping(value = "/midLuopanLuruCityDataUpdate" ,method ={RequestMethod.POST})
    @ResponseBody
    public  Result midLuopanLuruCityDataBatchUpdate( String  paramList){
        List<MidLuopanLuruCityData> midLuopanLuruCityDataList = JSON.parseArray(paramList, MidLuopanLuruCityData.class);
        List<MidLuopanLuruCityData> list = new ArrayList<MidLuopanLuruCityData>();
        if(midLuopanLuruCityDataList.size()>0){
            for (int i=0; i<midLuopanLuruCityDataList.size(); i++){
                MidLuopanLuruCityData midLuopanLuruCityData = midLuopanLuruCityDataList.get(i);
                String params="{ \"todo_project_num\":"+ midLuopanLuruCityData.getTodoProjectNum()
                        +" , \"doing_project_num\":"+ midLuopanLuruCityData.getDoingProjectNum()
                        +" , \"done_project_num\":"+ midLuopanLuruCityData.getDoneProjectNum()
                        +" , \"cancel_project_num\":"+ midLuopanLuruCityData.getCancelProjectNum()+"}";
                midLuopanLuruCityData.setJsonValue(params);
                midLuopanLuruCityData.setUpdatetime(DateUtil.formatDateToFullString2(new Date()));
                list.add(midLuopanLuruCityData);
            }
            midLuopanLuruCityDataService.batchUpdate(list);
            return Result.success();
        }else {
            return Result.failed( 1,"");
        }
    }
    @RequestMapping("/lpsave")
    @ResponseBody
    public Result save(LpUser lpUser,String v){
        List<RoleAuthorization> roleAuthorizationList= JSONObject.parseArray(v,RoleAuthorization.class);
        lpUser=lpUserService.selectBylpUserId(lpUser.getId());
        LpUserRole lpUserRole=new LpUserRole();
        lpUserRole.setUserId(lpUser.getId());
        List<LpUserRole> lpUserRoleList = lpUserService.selectLpUserRoleByUSERID(lpUserRole);
        if(lpUserRoleList.size()>0){
            lpUserService.updateRoleAuthorization(roleAuthorizationList,lpUserRoleList.get(0));
        }else{
            lpUserService.saveRoleAuthorization(roleAuthorizationList,lpUser);
        }
        return Result.success();
    }

    /**
     * 通用接口 返回权限集合
     * @param userName
     * @param moduleName
     * @return
     */
    @RequestMapping(value="getRegionListByUser")
    @ResponseBody
    public Map<String,Object> getRegionListByUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "moduleName") String moduleName){
        Map<String,Object> result = new HashMap<String, Object>();
        if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(moduleName)){
            result.put("success",false);
            result.put("msg","请求参数不能为空");
        }else{
            result = lpUserService.selectRegionListByUser(userName,moduleName);
        }
        return result;
    }

    /**
     * 依据用户名称和项目名称返回权限id集合
     * @param userName
     * @param moduleName
     * @return
     */
    @RequestMapping(value="getRegionIdListByUser" ,method ={RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> getRegionIdListByUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "moduleName") String moduleName){
        Map<String,Object> result = new HashMap<String, Object>();
        if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(moduleName)){
            result.put("success",false);
            result.put("msg","请求参数不能为空");
        }else{
            HashSet<String> projectSet = lpUserService.selectRegionListByParam(userName,moduleName);
            result.put("success",true);
            result.put("projectSet",projectSet);
        }
        return result;
    }
}
