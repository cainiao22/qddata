package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.MidLuopanRengongDataDao;
import com.qding.bigdata.ds.model.MidLuopanRengongData;
import com.qding.bigdata.ds.service.MidLuopanRengongDataService;
import com.qding.bigdata.ds.util.CommonUtil;
import com.qding.bigdata.ds.util.CookieUtil;
import com.qding.bigdata.ds.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MidLuopanRengongDataServiceImpl extends BaseServiceImpl<MidLuopanRengongData> implements MidLuopanRengongDataService {

    private MidLuopanRengongDataDao midLuopanRengongDataDao;
    @Override
    public BaseDao<MidLuopanRengongData> getDao() {
        return midLuopanRengongDataDao;
    }

    @Resource(name = "sqlSessionExecutor")
    public void setMidLuopanRengongDataDao(SqlSession sqlSession) {
        this.midLuopanRengongDataDao = sqlSession.getMapper(MidLuopanRengongDataDao.class);
    }

    protected Log log = LogFactory.getLog(this.getClass());

    @Override
    public List<MidLuopanRengongData> listNopage(MidLuopanRengongData param) {
        if (StringUtils.isBlank(param.getSortAndDesc())) {
            param.setSortAndDesc("create_time desc");
        }
        return midLuopanRengongDataDao.listNoPage(param);
    }

    //校验数据是否存在
    private boolean checkMidLuopanRengongData(MidLuopanRengongData midLuopanRengongData){
        MidLuopanRengongData temp = new MidLuopanRengongData();
        temp.setDateValue(midLuopanRengongData.getDateValue());
        temp.setKey(midLuopanRengongData.getKey());
        temp.setRegionId(midLuopanRengongData.getRegionId());
        List<MidLuopanRengongData> lists = midLuopanRengongDataDao.list(temp);
        //如果依据城市id 时间 加类型 查不到值则允许添加或者修改
        if(lists.isEmpty()||lists.size()==0){
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Integer> addOrUpdate(List<MidLuopanRengongData> midluopanrengongdatas,String username) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("add",0);
        map.put("update",0);
        if(null==username){
            username ="admin";
        }
        MidLuopanRengongData tmp = null;
        //在这儿应该把用户设置进增加或修改操作人里边
        for (MidLuopanRengongData midluopanrengongdata:midluopanrengongdatas) {
            if(StringUtils.isEmpty(midluopanrengongdata.getId())){
                //如果不存在,执行插入操作
                if(checkMidLuopanRengongData(midluopanrengongdata)){
                    midluopanrengongdata.setId(UUIDUtil.createId());
                    midluopanrengongdata.setCreateTime(new Date());
                    midluopanrengongdata.setUpdateTime(new Date());
                    midluopanrengongdata.setCreateUser(username);
                    midluopanrengongdata.setUpdateUser(username);
                    midLuopanRengongDataDao.insertSelective(midluopanrengongdata);
                    map.put("add",map.get("add")+1);
                }else{
                    midluopanrengongdata.setUpdateTime(new Date());
                    midluopanrengongdata.setUpdateUser(username);
                    midLuopanRengongDataDao.updateByParam(midluopanrengongdata);
                    map.put("update",map.get("update")+1);
                }
            }else{
                tmp = midLuopanRengongDataDao.getById(midluopanrengongdata);
                if(!midluopanrengongdata.getValue().equals(tmp.getValue())){
                    midluopanrengongdata.setUpdateTime(new Date());
                    midluopanrengongdata.setUpdateUser(username);
                    midLuopanRengongDataDao.updateById(midluopanrengongdata);
                    map.put("update",map.get("update")+1);
                }
                midluopanrengongdata.setRegionName(tmp.getRegionName());
                midluopanrengongdata.setKey(tmp.getKey());
                midluopanrengongdata.setDateValue(tmp.getDateValue());
                if(null==midluopanrengongdata.getUpdateTime()){
                    midluopanrengongdata.setUpdateTime(tmp.getUpdateTime());
                }
            }
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> selectProjectByQ(Map<String, Object> param) {
        return midLuopanRengongDataDao.selectProjectByQ(param);
    }

    @Override
    public Map<String,Object> selectOneProjectUUid(String projectId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        return midLuopanRengongDataDao.selectOneProjectUUid(map);
    }

    @Override
    public Map<String, Object> addOrUpdatewuyeyun(MidLuopanRengongData midluopanrengongdata, String username) {
        Map<String, Object>  result = new HashMap<String, Object>();
        //一共只有三个,可以直接遍历呀
        if(null==midluopanrengongdata.getRegionId() || null==midluopanrengongdata.getProjectId() || null==midluopanrengongdata.getDateValue() || null ==midluopanrengongdata.getMlrds() || 3!=midluopanrengongdata.getMlrds().size()){
            //依据这三个来查询,如果能查询
            result.put("error","请求参数有误,请不要随意传数据");
            return result;
        }
        if(null==username){
            username ="admin";
        }
        //在这儿进行数据遍历,将数据批量添加等
        List<MidLuopanRengongData> mlrds = midluopanrengongdata.getMlrds();
        for (MidLuopanRengongData mlrd:mlrds) {
            mlrd.setRegionId(midluopanrengongdata.getRegionId());
            mlrd.setRegionName(midluopanrengongdata.getRegionName());
            mlrd.setDateValue(midluopanrengongdata.getDateValue());
            mlrd.setProjectId(midluopanrengongdata.getProjectId());
            mlrd.setProjectName(midluopanrengongdata.getProjectName());
            mlrd.setPropertyinfoName(midluopanrengongdata.getPropertyinfoName());
            if(0==midLuopanRengongDataDao.checkCount(mlrd)){
                mlrd.setId(UUIDUtil.createId());
                mlrd.setCreateTime(new Date());
                mlrd.setUpdateTime(new Date());
                mlrd.setCreateUser(username);
                mlrd.setUpdateUser(username);
                midLuopanRengongDataDao.insertSelective(mlrd);
            }else{
                mlrd.setUpdateTime(new Date());
                mlrd.setUpdateUser(username);
                midLuopanRengongDataDao.updateToWuyeyunByParam(mlrd);
            }
        }
        result.put("success","操作成功");
        return result;
    }

    @Override
    public MidLuopanRengongData selectOne(MidLuopanRengongData midluopanrengongdata) {
        Map<String, Object> map = midLuopanRengongDataDao.getProjectById(midluopanrengongdata.getProjectId());
        String key = midluopanrengongdata.getKey();
        //查询时候不需要key
        midluopanrengongdata.setKey(null);
        midluopanrengongdata.setProjectId(map.get("project_id").toString());
        midluopanrengongdata.setProjectName(map.get("project_name").toString());
        midluopanrengongdata.setRegionId(map.get("region_id").toString());
        midluopanrengongdata.setRegionName(null==map.get("region_name")?null:map.get("region_name").toString());
        midluopanrengongdata.setPropertyinfoName(null==map.get("propertyinfo_name")?null:map.get("propertyinfo_name").toString());
        List<MidLuopanRengongData> list =  midLuopanRengongDataDao.listNoPage(midluopanrengongdata);
        midluopanrengongdata.setKey(key);
        if(null==list || 3!=list.size()){

            return midluopanrengongdata;
        }else{
            List<MidLuopanRengongData> mlrds = new LinkedList<MidLuopanRengongData>();
            int a =0;
            int b = 0;
            int c = 0;
            for(int i=0;i<3;i++){
                if("物业云实施状态".equals(list.get(i).getKey())){
                    a = i;
                }else if("员工潜在使用系统的人员数量".equals(list.get(i).getKey())){
                    b =i;
                }else if("员工注册数量".equals(list.get(i).getKey())){
                    c = i;
                }
            }
            mlrds.add(list.get(a));
            mlrds.add(list.get(b));
            mlrds.add(list.get(c));
            midluopanrengongdata.setMlrds(mlrds);
        }
        return midluopanrengongdata;
    }

    @Override
    public List<MidLuopanRengongData> listRenwuMlrds(MidLuopanRengongData param) {
        List<MidLuopanRengongData> lists;
        if (StringUtils.isBlank(param.getSortAndDesc())) {
            param.setSortAndDesc("create_time asc");
        }
        lists = midLuopanRengongDataDao.listNoPage(param);
        if(null==lists || lists.size()<8){
            lists = new LinkedList<MidLuopanRengongData>();
            MidLuopanRengongData mlrdtemp = new MidLuopanRengongData();
            mlrdtemp.setRegionName("北京市");
            mlrdtemp.setRegionId("1");
            mlrdtemp.setKey(param.getKey());
            mlrdtemp.setDateValue(param.getDateValue());
            lists.add(mlrdtemp);
            MidLuopanRengongData mlrdtemp2 = new MidLuopanRengongData();
            mlrdtemp2.setRegionName("重庆市");
            mlrdtemp2.setRegionId("3");
            mlrdtemp2.setKey(param.getKey());
            mlrdtemp2.setDateValue(param.getDateValue());
            lists.add(mlrdtemp2);
            MidLuopanRengongData mlrdtemp3 = new MidLuopanRengongData();
            mlrdtemp3.setRegionName("成都市");
            mlrdtemp3.setRegionId("5");
            mlrdtemp3.setKey(param.getKey());
            mlrdtemp3.setDateValue(param.getDateValue());
            lists.add(mlrdtemp3);
            MidLuopanRengongData mlrdtemp4 = new MidLuopanRengongData();
            mlrdtemp4.setRegionName("上海市");
            mlrdtemp4.setRegionId("9");
            mlrdtemp4.setKey(param.getKey());
            mlrdtemp4.setDateValue(param.getDateValue());
            lists.add(mlrdtemp4);
            MidLuopanRengongData mlrdtemp5 = new MidLuopanRengongData();
            mlrdtemp5.setRegionName("西安市");
            mlrdtemp5.setRegionId("11");
            mlrdtemp5.setKey(param.getKey());
            mlrdtemp5.setDateValue(param.getDateValue());
            lists.add(mlrdtemp5);
            MidLuopanRengongData mlrdtemp6 = new MidLuopanRengongData();
            mlrdtemp6.setRegionName("杭州市");
            mlrdtemp6.setRegionId("14");
            mlrdtemp6.setKey(param.getKey());
            mlrdtemp6.setDateValue(param.getDateValue());
            lists.add(mlrdtemp6);
            MidLuopanRengongData mlrdtemp7 = new MidLuopanRengongData();
            mlrdtemp7.setRegionName("广州市");
            mlrdtemp7.setRegionId("31");
            mlrdtemp7.setKey(param.getKey());
            mlrdtemp7.setDateValue(param.getDateValue());
            lists.add(mlrdtemp7);
            MidLuopanRengongData mlrdtemp8 = new MidLuopanRengongData();
            mlrdtemp8.setRegionName("其它");
            mlrdtemp8.setRegionId("qita");
            mlrdtemp8.setKey(param.getKey());
            mlrdtemp8.setDateValue(param.getDateValue());
            lists.add(mlrdtemp8);
        }
        return lists;
    }
}
