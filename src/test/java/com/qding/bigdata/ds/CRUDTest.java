package com.qding.bigdata.ds;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.dao.LpRoleDao;
import com.qding.bigdata.ds.dao.LpUserRoleDao;
import com.qding.bigdata.ds.dao.RoleAuthorizationDao;
import com.qding.bigdata.ds.enums.LPEnum;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.util.HttpClientUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by syj on 2018/6/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})
public class CRUDTest {
    @Autowired
    private LpRoleDao lpRoleDao;
    @Autowired
    private LpUserRoleDao lpUserRoleDao;
    @Autowired
    private RoleAuthorizationDao roleAuthorizationDao;
    @Test
    public void  ss() throws Exception {
        HttpDataParam httpDataParam=new HttpDataParam();
        httpDataParam.setUrl("http://yushanfang.bigdata.qdingnet.com/dataApiQuery/getluopanRegionInfoList");
        httpDataParam.setPost(true);
        httpDataParam.setParams(new HashMap<String, String>());
        Object result = null;
        if(httpDataParam.getPost() != null && httpDataParam.getPost()) {
            result = HttpClientUtil.doPost(httpDataParam.getUrl(), httpDataParam.getParams());
        }else {
            result = HttpClientUtil.doGet(httpDataParam.getUrl(), httpDataParam.getParams());
        }
        JSONObject jsonObject = JSON.parseObject(result.toString());
        String data = jsonObject.getString("data");
        Map map=new TreeMap();
        List<Map> areaList=new ArrayList<Map>();
        List<Map> cityList=new ArrayList<Map>();
        List<Map> projectList=new ArrayList<Map>();
        List<Map> ts = (List) JSONArray.parseArray(data,Map.class);
        for (int i=0;i<ts.size();i++){
            String level = ts.get(i).get("level").toString();
            if(level.equals("1")){
                map.put("region_id",ts.get(i).get("region_id").toString());
                map.put("region_name",ts.get(i).get("region_name").toString());
                map.put("level",ts.get(i).get("level").toString());
            }
            if(level.equals("2")){
                Map areaMap=new TreeMap();
                areaMap.put("region_id",ts.get(i).get("region_id").toString());
                areaMap.put("region_name",ts.get(i).get("region_name").toString());
                areaMap.put("level",ts.get(i).get("level").toString());
                areaList.add(areaMap);
            }
            if(level.equals("3")){
                Map cityMap=new TreeMap();
                cityMap.put("region_id",ts.get(i).get("region_id").toString());
                cityMap.put("region_name",ts.get(i).get("region_name").toString());
                cityMap.put("level",ts.get(i).get("level").toString());
                cityMap.put("pid",ts.get(i).get("pid").toString());
                cityList.add(cityMap);
            }
            if(level.equals("4")){
                Map projectMap=new TreeMap();
                projectMap.put("region_id",ts.get(i).get("region_id").toString());
                projectMap.put("region_name",ts.get(i).get("region_name").toString());
                projectMap.put("level",ts.get(i).get("level").toString());
                projectMap.put("pid",ts.get(i).get("pid").toString());
                projectList.add(projectMap);
            }
        }
        for (int a=0;a<areaList.size();a++){
            List<Map> cityList2=new ArrayList<Map>();//用来放区域的region_id和城市的pid比较之后的城市
            for (int c=0;c<cityList.size();c++){
                List<Map> projectList2=new ArrayList<Map>();//用来放城市的region_id和社区的pid比较之后的社区
                for (int d=0;d<projectList.size();d++){//进行城市region_id和社区pid的比较
                    if(cityList.get(c).get("region_id").toString().equals(projectList.get(d).get("pid"))){
                        projectList2.add(projectList.get(d));
                        cityList.get(c).put("projectList",projectList2);
                    }
                }
            }
            for (int b=0;b<areaList.size();b++){//进行区域的region_id和城市的pid比较
                if(areaList.get(a).get("region_id").toString().equals(cityList.get(b).get("pid"))){
                    cityList2.add(cityList.get(b));
                    areaList.get(a).put("cityList",cityList2);
                }
            }
        }
        //map.put("areaList",areaList);
        map.put(LPEnum.areaList.getValue(),areaList);
        String s = JSON.toJSONString(map);
        System.out.println(s);
       // System.out.print(result.toString());
    }
    @Test
    public void ll(){
        LpRole lpRole=new LpRole();
        lpRole.setModuleName("123");
        lpRole.setRoleName("1234567");
        lpRoleDao.insert(lpRole);
    }
    @Test
    public void saveRoleAuthorization() {
        List<RoleAuthorization> roleAuthorizationList=new ArrayList<RoleAuthorization>();
        RoleAuthorization authorization1=new RoleAuthorization();
        authorization1.setParentId("14");
        authorization1.setRegionId("121");
        authorization1.setRegionLevel(4);
        authorization1.setRegionName("杭州滟澜山");
        roleAuthorizationList.add(authorization1);
        RoleAuthorization authorization2=new RoleAuthorization();
        authorization2.setParentId("14");
        authorization2.setRegionId("1810");
        authorization2.setRegionLevel(4);
        authorization2.setRegionName("杭州计划财务部");
        roleAuthorizationList.add(authorization2);
        RoleAuthorization authorization3=new RoleAuthorization();
        authorization3.setParentId("14");
        authorization3.setRegionId("1941");
        authorization3.setRegionLevel(4);
        authorization3.setRegionName("杭州名景台");
        roleAuthorizationList.add(authorization3);
        LpUser lpUser=new LpUser();
        lpUser.setId((long)4);
        lpUser.setModuleName("罗盘");
        lpUser.setRealName("测试");
        lpUser.setUserName("test");
        LpUserRole lpUserRole=new LpUserRole();
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
}
