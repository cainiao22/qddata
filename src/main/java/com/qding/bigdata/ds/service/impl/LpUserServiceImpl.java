package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.*;
import com.qding.bigdata.ds.enums.LPEnum;
import com.qding.bigdata.ds.model.LpRole;
import com.qding.bigdata.ds.model.LpUser;
import com.qding.bigdata.ds.model.LpUserRole;
import com.qding.bigdata.ds.model.RoleAuthorization;
import com.qding.bigdata.ds.service.LpUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LpUserServiceImpl implements LpUserService{
    @Autowired
    private LpUserDao lpUserDao;
    @Autowired
    private LpModuleDao lpModuleDao;

    @Autowired
    private LpRoleDao lpRoleDao;

    @Autowired
    private RoleAuthorizationDao roleAuthorizationDao;
    @Autowired
    private LpUserRoleDao lpUserRoleDao;

    @Override
    public List<LpUser> selectAll() {
        return lpUserDao.selectAll();
    }

    @Override
    public  List<LpUserRole>  selectLpUserRoleByUSERID(LpUserRole param) {
        List<LpUserRole> lpUserRoleList = lpUserRoleDao.select(param);
        return lpUserRoleList;
    }

    @Override
    public void updateRoleAuthorization(List<RoleAuthorization> roleAuthorizationList, LpUserRole lpUserRole) {
        roleAuthorizationDao.delete(lpUserRole.getRoleId());
        List<RoleAuthorization> roleAuthorizationList2=new ArrayList<RoleAuthorization>();
        for (RoleAuthorization roleAuthorization : roleAuthorizationList){
            roleAuthorization.setRoleId(lpUserRole.getRoleId());
            roleAuthorizationList2.add(roleAuthorization);
        }
        roleAuthorizationDao.addList(roleAuthorizationList2);
    }

    @Override
    public void saveRoleAuthorization(List<RoleAuthorization> roleAuthorizationList, LpUser lpUser) {
        LpUserRole  lpUserRole=new LpUserRole();
        LpRole lpRole=new LpRole();
        lpRole.setModuleName(lpUser.getModuleName());
        lpRole.setRoleName(lpUser.getUserName());
        lpRoleDao.insert(lpRole);
        List<LpRole> lpRoleList = lpRoleDao.select(lpRole);
        if(lpRoleList.size()>0){
            lpUserRole.setUserId(lpUser.getId());
            lpUserRole.setUserName(lpUser.getUserName());
            lpUserRole.setRoleId(lpRoleList.get(0).getId());
            lpUserRoleDao.insert(lpUserRole);
        }
        List<LpUserRole> lpUserRoleList = lpUserRoleDao.select(lpUserRole);
        List<RoleAuthorization> roleAuthorizationList2=new ArrayList<RoleAuthorization>();
        if(lpUserRoleList.size()>0){
            for (RoleAuthorization roleAuthorization : roleAuthorizationList){
                roleAuthorization.setRoleId(lpUserRoleList.get(0).getRoleId());
                roleAuthorizationList2.add(roleAuthorization);
            }
        }
        roleAuthorizationDao.addList(roleAuthorizationList2);
    }

    @Override
    public LpUser selectBylpUserId(Long id) {
        LpUser lpUser = lpUserDao.selectByPrimaryKey(id);
        return lpUser;
    }

    @Override
    public Map<String, Object> selectRegionListByUser(String userName, String moduleName) {
        Map<String, Object>  result = new LinkedHashMap<String,Object>();
        result.put("success",false);
        if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(moduleName)){
            result.put("msg","请求参数有误");
            return result;
        }
        //如果项目名称不存在
        if(null==lpModuleDao.selectModuleByName(moduleName)){
            result.put("msg","项目名称不存在!!");
            return result;
        }
        //然后依据用户名 查询出用户信息
        LpUser queryUser = new LpUser();
        queryUser.setUserName(userName);
        queryUser = lpUserDao.selectLpUserByUserName(queryUser);
        if(null==queryUser){
            result.put("msg","用户不存在!!");
            return result;
        }
        LpUserRole lpUserRole = new LpUserRole();
        lpUserRole.setUserId(queryUser.getId());
        //查询出所有的用户关联角色
        List<LpUserRole> lpUserRoles = lpUserRoleDao.select(lpUserRole);
        if(!lpUserRoles.isEmpty()&&lpUserRoles.size()>0){
            //声明三个集合 分别存储 areaList cityList projectList
            HashSet<Map<String,Object>> areaList = new HashSet<Map<String, Object>>();
            HashSet<Map<String,Object>> cityList = new HashSet<Map<String, Object>>();
            List<Map<String,Object>> projectList = new ArrayList<Map<String,Object>>();
            result.put("isAllRegion",false);
            //遍历所有角色
            for (LpUserRole lp:lpUserRoles) {
                RoleAuthorization roleAuthorization = new RoleAuthorization();
                roleAuthorization.setRoleId(lp.getRoleId());
                ///List<RoleAuthorization> roleAuthorizations  = roleAuthorizationDao.list(roleAuthorization);
                //拼装数据,将信息返回
                if(roleAuthorizationDao.count(roleAuthorization)>0){
                    roleAuthorization.setRegionLevel(1);
                    if(roleAuthorizationDao.count(roleAuthorization)>0){
                        result.put("isAllRegion",true);
                        result.put(LPEnum.areaList.getValue(),areaList);
                        result.put(LPEnum.cityList.getValue(),cityList);
                        result.put(LPEnum.projectList.getValue(),projectList);
                        result.put("success",true);
                        return result;
                    }else{
                        Map<String,Object> map = null;
                        //查询区域集合
                        roleAuthorization.setRegionLevel(2);
                        List<RoleAuthorization> areas = roleAuthorizationDao.list(roleAuthorization);
                        if(areas.size()>0){
                            for (RoleAuthorization area:areas) {
                                map = new HashMap<String,Object>();
                                map.put(LPEnum.REGION_ID.getValue(),area.getRegionId());
                                map.put(LPEnum.REGION_NAME.getValue(),area.getRegionName());
                                map.put("open",true);
                                if(areaList.contains(map)){
                                    continue;
                                }else{
                                    areaList.add(map);
                                }
                            }
                        }
                        //查询城市集合
                        roleAuthorization.setRegionLevel(3);
                        List<RoleAuthorization> citys = roleAuthorizationDao.list(roleAuthorization);
                        if(citys.size()>0){
                            for (RoleAuthorization city:citys) {
                                map = new HashMap<String,Object>();
                                map.put(LPEnum.REGION_ID.getValue(),city.getRegionId());
                                map.put(LPEnum.REGION_NAME.getValue(),city.getRegionName());
                                map.put(LPEnum.PID.getValue(),city.getParentId());
                                if(cityList.contains(map)){
                                    continue;
                                }else{
                                    cityList.add(map);
                                }
                            }
                        }
                        //查询社区结合
                        roleAuthorization.setRegionLevel(4);
                        List<RoleAuthorization> projects = roleAuthorizationDao.list(roleAuthorization);
                        if(projects.size()>0){
                            for (RoleAuthorization project:projects) {
                                map = new HashMap<String,Object>();
                                map.put(LPEnum.REGION_ID.getValue(),project.getRegionId());
                                map.put(LPEnum.REGION_NAME.getValue(),project.getRegionName());
                               if(projectList.contains(map)){
                                   continue;
                                }else{
                                    projectList.add(map);
                                }
                            }
                        }
                        //result.put("success",true);
                        //return result;
                    }
                }else{
                    /*result.put("success",false);
                    result.put("msg","该用户还没有分配权限");
                    return result;*/
                }
                result.put(LPEnum.areaList.getValue(),areaList);
                result.put(LPEnum.cityList.getValue(),cityList);
                result.put(LPEnum.projectList.getValue(),projectList);
                result.put("success",true);
            }
        }else{
            result.put("msg","该用户还没有角色关联！");
        }
        return result;
    }

    @Override
    public HashSet<String> selectRegionListByParam(String userName, String moduleName) {
        HashSet<String> result = new HashSet<String>();
        LpUser queryUser = new LpUser();
        queryUser.setUserName(userName);
        queryUser = lpUserDao.selectLpUserByUserName(queryUser);
        LpUserRole lpUserRole = new LpUserRole();
        lpUserRole.setUserId(queryUser.getId());
        //查询出所有的用户关联角色
        List<LpUserRole> lpUserRoles = lpUserRoleDao.select(lpUserRole);
        for (LpUserRole lp:lpUserRoles) {
            RoleAuthorization roleAuthorization = new RoleAuthorization();
            roleAuthorization.setRoleId(lp.getRoleId());
            List<RoleAuthorization> list = roleAuthorizationDao.list(roleAuthorization);
            for (RoleAuthorization ra:list
                 ) {
                if(!result.contains(ra.getRegionId())){
                    result.add(ra.getRegionId());
                }
            }
        }
        return result;
    }
}
