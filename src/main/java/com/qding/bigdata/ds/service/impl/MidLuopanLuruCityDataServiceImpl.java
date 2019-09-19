package com.qding.bigdata.ds.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.MidLuopanLuruCityDataDao;
import com.qding.bigdata.ds.model.MidLuopanLuruCityData;
import com.qding.bigdata.ds.service.MidLuopanLuruCityDataService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MidLuopanLuruCityDataServiceImpl extends BaseServiceImpl<MidLuopanLuruCityData> implements MidLuopanLuruCityDataService {

    private MidLuopanLuruCityDataDao midLuopanLuruCityDataDao;
    @Override
    public BaseDao<MidLuopanLuruCityData> getDao() {
        return midLuopanLuruCityDataDao;
    }

    @Resource(name = "sqlSessionExecutor")
    public void setMidLuopanLuruCityDataDao(SqlSession sqlSession) {
        this.midLuopanLuruCityDataDao = sqlSession.getMapper(MidLuopanLuruCityDataDao.class);
    }

    protected Log log = LogFactory.getLog(this.getClass());

    @Override
    public List<MidLuopanLuruCityData> listNopage(MidLuopanLuruCityData param) {
        if (StringUtils.isBlank(param.getSortAndDesc())) {
            param.setSortAndDesc("region_id asc");
        }
        List<MidLuopanLuruCityData> midLuopanLuruCityDataList = midLuopanLuruCityDataDao.listNopage(param);
        List<MidLuopanLuruCityData> list =new ArrayList<MidLuopanLuruCityData>();
        if(midLuopanLuruCityDataList.size()>0){
            for (int i=0; i<midLuopanLuruCityDataList.size(); i++){
                if(!"-1".equals(midLuopanLuruCityDataList.get(i).getRegionId())){
                MidLuopanLuruCityData midLuopanLuruCityData = midLuopanLuruCityDataList.get(i);
                String jsonValue = midLuopanLuruCityData.getJsonValue();
                JSONObject jsonObject=JSONObject.parseObject(jsonValue);
                String cancel_project_num = jsonObject.getString("cancel_project_num");
                String todo_project_num = jsonObject.getString("todo_project_num");
                String done_project_num = jsonObject.getString("done_project_num");
                String doing_project_num = jsonObject.getString("doing_project_num");
                midLuopanLuruCityData.setTodoProjectNum(todo_project_num);
                midLuopanLuruCityData.setDoingProjectNum(doing_project_num);
                midLuopanLuruCityData.setDoneProjectNum(done_project_num);
                midLuopanLuruCityData.setCancelProjectNum(cancel_project_num);
                list.add(midLuopanLuruCityData);
                }
            }
        }
        return  list;
    }
    @Override
    public int updateByParam(MidLuopanLuruCityData param){

        return midLuopanLuruCityDataDao.updateByParam(param);
    }
    @Override
    public int batchUpdate(List<MidLuopanLuruCityData>  param){

        return midLuopanLuruCityDataDao.batchUpdate(param);
    }

}
