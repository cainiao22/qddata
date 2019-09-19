package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.*;
import com.qding.bigdata.ds.model.UserDetailParam;
import com.qding.bigdata.ds.service.GCTUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author yanpf
 * @Date 19-9-2 下午4:50
 * @Description
 **/

@Slf4j
@Service
public class GCTUserDetailServiceImpl implements GCTUserDetailService {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    PCompanyPersonInfoDao pCompanyPersonInfoDao;

    GCTPDingPathMapper gctpDingPathMapper;

    @Resource(name = "esSqlSession")
    public void initESSqlDao(SqlSession sqlSession) {
        this.pCompanyPersonInfoDao = sqlSession.getMapper(PCompanyPersonInfoDao.class);
        this.gctpDingPathMapper = sqlSession.getMapper(GCTPDingPathMapper.class);
    }

    @Override
    public List<Map<String, Object>> queryUserDetails(UserDetailParam param) {
        switch (param.getAction()) {
            case "reg_new":
                param.setRegistered(true);
                param.setRegisterDate(dateFormat.format(new Date()));
            case "active":
                param.setActiveDate(dateFormat.format(new Date()));

        }

        return pCompanyPersonInfoDao.queryPersonDetails(param);
    }

    @Override
    public Map<String, List<Map<String, Object>>> queryUserTrail(String userId, String queryDate) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        Long startTime = null, endTime = null;
        if (!StringUtils.isEmpty(queryDate)) {
            Calendar calendar = Calendar.getInstance();
            Date date = null;
            try {
                date = dateFormat.parse(queryDate);
            } catch (ParseException e) {
                log.error("日期格式转换出错", e);
                return null;
            }
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            startTime = calendar.getTimeInMillis();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            endTime = calendar.getTimeInMillis();
        }
        List<Map<String, Object>> event = gctpDingPathMapper.queryUserTrail(userId, "event", startTime, endTime);
        convertTimestamp(event);
        result.put("event", event);
        List<Map<String, Object>> page = gctpDingPathMapper.queryUserTrail(userId, "page", startTime, endTime);
        convertTimestamp(page);
        result.put("page", page);

        return result;
    }

    private void convertTimestamp(List<Map<String, Object>> mapList) {
        if (!CollectionUtils.isEmpty(mapList)) {
            mapList.forEach(item -> {
                if(item.get("time") != null) {
                    item.put("time", DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINA)
                            .format(new Date(Long.valueOf(item.get("time").toString()))));
                }
            });
        }
    }
}
