package com.qding.bigdata.ds.controller;

import com.alibaba.fastjson.JSON;
import com.qding.bigdata.ds.model.RibaoActivityDetail;
import com.qding.bigdata.ds.model.RibaoActivityInfo;
import com.qding.bigdata.ds.util.BeanMapper;
import com.qding.qdh.bigdata.ufo.model.ActvityDivision;
import com.qding.qdh.bigdata.ufo.model.IOTDeviceActivityResponse;
import com.qding.qdh.bigdata.ufo.service.IUfoIotDeviceDailyActivityService;
import com.qding.qdh.framework.model.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yanpf
 * @date 2019/2/15 11:40
 * @description
 */

@Controller
public class RibaoDataController {

    private static final String[] periodTypes = new String[]{"day", "month", "year", "total"};
    private static final PeriodType[] lastperiodTypes = new PeriodType[]{new PeriodType("day", Calendar.DATE), new PeriodType("month", Calendar.MONTH)};

    @Autowired
    IUfoIotDeviceDailyActivityService service;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping("getRibaoActivityData")
    public Map<String, RibaoActivityInfo> getRibaoActivityData(Date pointDate) throws Exception{
        Map<String, RibaoActivityInfo> map = new HashMap<>();
        for (String periodType : periodTypes) {
            ServiceResult<String> result = service.getActivityData(pointDate, periodType);
            IOTDeviceActivityResponse data = (IOTDeviceActivityResponse) result.getData();
            logger.info("IOT日报入参-->pointDate：" + JSON.toJSONString(pointDate) + ", periodType:" + JSON.toJSONString(periodType) + ", 结果:" + JSON.toJSONString(result));
            List<ActvityDivision> businessDataList = data.getBusinessDataList();
            List<ActvityDivision> cityDataList = data.getCityDataList();
            Comparator comparator = Comparator.comparing(ActvityDivision::getName);
            Collections.sort(businessDataList, comparator);
            Collections.sort(cityDataList, comparator);

            RibaoActivityInfo ribaoActivityInfo = new RibaoActivityInfo();
            ribaoActivityInfo.setBusinessDataList(BeanMapper.mapList(businessDataList, RibaoActivityDetail.class));
            ribaoActivityInfo.setCityDataList(BeanMapper.mapList(cityDataList, RibaoActivityDetail.class));
            ribaoActivityInfo.setCounts(data.getCounts());

            map.put(periodType, ribaoActivityInfo);
        }

        for (PeriodType periodType : lastperiodTypes) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(pointDate);
            calendar.add(periodType.getCalanderType(), - 1);
            ServiceResult<String> result = service.getActivityData(calendar.getTime(), periodType.getName());
            IOTDeviceActivityResponse data = (IOTDeviceActivityResponse) result.getData();
            List<ActvityDivision> businessDataList = data.getBusinessDataList();
            List<ActvityDivision> cityDataList = data.getCityDataList();
            Comparator comparator = Comparator.comparing(ActvityDivision::getName);
            Collections.sort(businessDataList, comparator);
            Collections.sort(cityDataList, comparator);
            RibaoActivityInfo ribaoActivityInfo = map.get(periodType.getName());
            if(null != ribaoActivityInfo){
                getIncreasement(ribaoActivityInfo.getBusinessDataList(), businessDataList);
                getIncreasement(ribaoActivityInfo.getCityDataList(), cityDataList);
            }
            if(data.getCounts() != 0 && null!=ribaoActivityInfo) {
                ribaoActivityInfo.setIncreasement((ribaoActivityInfo.getCounts() - data.getCounts()) * 10000 / data.getCounts() / 100.0);
            }else if(null!=ribaoActivityInfo){
                ribaoActivityInfo.setIncreasement(100.00);
            }
        }

        return map;
    }

    private void getIncreasement(List<RibaoActivityDetail> main, List<ActvityDivision> itemList){
        int i=0, j=0;
        while(i<main.size() && j < itemList.size()){
            RibaoActivityDetail detail = main.get(i);
            ActvityDivision actvityDivision = itemList.get(j);
            if(detail.getName().equals(actvityDivision.getName())){
                detail.setIncreasement((detail.getCounts() - actvityDivision.getCounts()) * 10000 / actvityDivision.getCounts()/100.0);
                i ++;
                j ++;
            }else if(detail.getName().compareTo(actvityDivision.getName()) < 0){
                detail.setIncreasement(100.00);
                i ++;
            }else{
                j ++;
            }
        }

        while(i < main.size()){
            RibaoActivityDetail detail = main.get(i);
            detail.setIncreasement(100.00);
            i ++;
        }
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(df, true); // true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }
}

class PeriodType {
    String name;
    int calanderType;

    public PeriodType(String name, int calanderType) {
        this.name = name;
        this.calanderType = calanderType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalanderType() {
        return calanderType;
    }

    public void setCalanderType(int calanderType) {
        this.calanderType = calanderType;
    }
}
