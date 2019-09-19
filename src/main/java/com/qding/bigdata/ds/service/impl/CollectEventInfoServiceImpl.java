package com.qding.bigdata.ds.service.impl;

import com.alibaba.fastjson.JSON;
import com.qding.bigdata.ds.service.CollectEventInfoService;
import com.qding.bigdata.ds.util.HttpClientUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qding.bigdata.ds.common.Constant.DIMENSION_URL;
import static com.qding.bigdata.ds.common.Constant.main;
import static com.qding.bigdata.ds.common.ConstantDimension.*;

@Service
public class CollectEventInfoServiceImpl implements CollectEventInfoService {

    /**
     * 查询事件ID
     * @return
     * event_id
     */
    @Override
    public Map<String, Object> getCollectEventInfo() {
        Map<String,Object> newmap = new HashMap<String, Object>();
        //调用数据接口
        String doGetInfo = HttpClientUtil.doGet(DIMENSION_URL+DIMENSION_INFO);

        //返回数据转换成map格式
        Map map = JSON.parseObject(doGetInfo, Map.class);

        try {
            //获取数据接口返回数据
            List<Map> dataList = (List<Map>)map.get("data");

                //循环返回数据
                for (Map m : dataList){
                    //获取type的值
                    String type = (String)m.get("type");
                    //获取eventIdentity的值
                    String eventIdentity = (String)m.get("eventIdentity");
                    //判断key值组装数据
                    if(newmap.keySet().contains(type)){
                        List<String> listDemo  = (List<String>)newmap.get(type);
                        listDemo.add(eventIdentity);
                    }else{
                        List<String> listDemo  = new ArrayList<String>();
                        listDemo.add(eventIdentity);
                        newmap.put(type,listDemo);
                    }
                }
            String code = map.get("code").toString();
            String errorMsg = map.get("errorMsg").toString();
            newmap.put("errorMsg",errorMsg);
            newmap.put("code",code);
         return newmap;
        }catch (Exception e){
            e.printStackTrace();
            String code = map.get("code").toString();
            String errorMsg = map.get("errorMsg").toString();
            newmap.put("errorMsg",errorMsg);
            newmap.put("code",code);
            return newmap;
        }
    }

    /**
     * 条件查询sourceEvent信息
     * @return
     */
    @Override
    public Map<String, Object> collectEventInfoBySource(Map<String, String> params) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //EventInfoBySource条件查询接口
            String doPostInfo = HttpClientUtil.doPost(DIMENSION_URL+EVNETINFOBYSOURCE, params);
            map = JSON.parseObject(doPostInfo, Map.class); //返回数据转换成map格式
        }catch (Exception e){
            map.put("errInfo",e.getMessage());
        }
        return map;
    }

    /**
     * 条件查询EventInfo信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> collectEventInfoByEvent(Map<String, String> params) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //EventInfoByEvent条件查询接口
            String doPostInfo = HttpClientUtil.doPost(DIMENSION_URL+EVENTINFOBYEVENT, params);
            //返回数据转换成map格式
            map = JSON.parseObject(doPostInfo, Map.class);
        }catch (Exception e){
            map.put("errInfo",e.getMessage());
        }
        return map;
    }

/**
 *
 *
 try {
 List data = (List) eventMap.get("data");

 String code = eventMap.get("code").toString();

 Integer pageSize = (Integer) eventMap.get("pageSize");

 if(null != data || null != pageSize || pageSize < data.size()) {
 //总记录数
 Integer total = (Integer) eventMap.get("total");
 //分多少次处理
 Integer requestCount = total / pageSize;

 for (int i = 0; i <= requestCount; i++) {
 Integer fromIndex = i * pageSize;
 //pageSize,为了防止数组越界,toIndex直接使用totalCount即可
 int toIndex = Math.min(total, (i + 1) * pageSize);
 List<Long> subList = data.subList(fromIndex, toIndex);
 //System.out.println(subList);
 //总数不到一页或者刚好等于一页的时候,只需要处理一次就可以退出for循环了
 if (toIndex == total) {
 break;
 }
 map.put("eventMap", subList);
 }
 map.put("code", code);
 }else{
 map.put("sourceMap",data);
 map.put("code", code);
 map.put("total",eventMap.get("total"));
 }
 map.put("total",eventMap.get("total"));
 }catch (Exception e){
 e.printStackTrace();
 String code = eventMap.get("code").toString();
 String errorMsg = eventMap.get("errorMsg").toString();
 map.put("code", code);
 map.put("errorMsg",errorMsg);
 }
 *
 *
 *
 *
 *     List data = (List) sourceMap.get("data");

 String code = sourceMap.get("code").toString();
 Integer pageSize = (Integer) sourceMap.get("pageSize");
 if(null != data || null != pageSize || pageSize < data.size()) {
 //总记录数
 Integer total = (Integer) sourceMap.get("total");
 //分多少次处理
 Integer requestCount = total / pageSize;

 for (int i = 0; i <= requestCount; i++) {
 Integer fromIndex = i * pageSize;
 //pageSize,为了防止数组越界,toIndex直接使用totalCount即可
 int toIndex = Math.min(total, (i + 1) * pageSize);
 List<Long> subList = data.subList(fromIndex, toIndex);
 //System.out.println(subList);
 //总数不到一页或者刚好等于一页的时候,只需要处理一次就可以退出for循环了
 if (toIndex == total) {
 break;
 }
 map.put("sourceMap",subList);
 }
 map.put("code", code);
 }else{
 map.put("sourceMap",data);
 map.put("code", code);
 map.put("total",sourceMap.get("total"));
 }
 */
}
