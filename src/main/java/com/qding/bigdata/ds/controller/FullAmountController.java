package com.qding.bigdata.ds.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.enums.LPEnum;
import com.qding.bigdata.ds.model.HttpDataParam;
import com.qding.bigdata.ds.util.HttpClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by syj on 2018/6/21.
 */
@Controller
public class FullAmountController extends BasicController{
    @RequestMapping("/getRegionList")
    @ResponseBody
    public JSONObject   getRegionList() throws Exception {
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

        if(null != result){
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
                    map.put(LPEnum.REGION_ID.getValue(),ts.get(i).get(LPEnum.REGION_ID.getValue()).toString());
                    map.put(LPEnum.REGION_NAME.getValue(),ts.get(i).get(LPEnum.REGION_NAME.getValue()).toString());
                    map.put(LPEnum.LEVEL.getValue(),ts.get(i).get(LPEnum.LEVEL.getValue()).toString());
                }
                if(level.equals("2")){
                    Map areaMap=new TreeMap();
                    areaMap.put(LPEnum.REGION_ID.getValue(),ts.get(i).get(LPEnum.REGION_ID.getValue()).toString());
                    areaMap.put(LPEnum.REGION_NAME.getValue(),ts.get(i).get(LPEnum.REGION_NAME.getValue()).toString());
                    areaMap.put(LPEnum.LEVEL.getValue(),ts.get(i).get(LPEnum.LEVEL.getValue()).toString());
                    areaMap.put(LPEnum.PID.getValue(),ts.get(i).get(LPEnum.PID.getValue()).toString());
                    areaList.add(areaMap);
                }
                if(level.equals("3")){
                    Map cityMap=new TreeMap();
                    cityMap.put(LPEnum.REGION_ID.getValue(),ts.get(i).get(LPEnum.REGION_ID.getValue()).toString());
                    cityMap.put(LPEnum.REGION_NAME.getValue(),ts.get(i).get(LPEnum.REGION_NAME.getValue()).toString());
                    cityMap.put(LPEnum.LEVEL.getValue(),ts.get(i).get(LPEnum.LEVEL.getValue()).toString());
                    cityMap.put(LPEnum.PID.getValue(),ts.get(i).get(LPEnum.PID.getValue()).toString());
                    cityList.add(cityMap);
                }
                if(level.equals("4")){
                    Map projectMap=new TreeMap();
                    projectMap.put(LPEnum.REGION_ID.getValue(),ts.get(i).get(LPEnum.REGION_ID.getValue()).toString());
                    projectMap.put(LPEnum.REGION_NAME.getValue(),ts.get(i).get(LPEnum.REGION_NAME.getValue()).toString());
                    projectMap.put(LPEnum.LEVEL.getValue(),ts.get(i).get(LPEnum.LEVEL.getValue()).toString());
                    projectMap.put(LPEnum.PID.getValue(),ts.get(i).get(LPEnum.PID.getValue()).toString());
                    projectList.add(projectMap);
                }
            }
            for (int a=0;a<areaList.size();a++){
                List<Map> cityList2=new ArrayList<Map>();//用来放区域的region_id和城市的pid比较之后的城市
                for (int c=0;c<cityList.size();c++){
                    List<Map> projectList2=new ArrayList<Map>();//用来放城市的region_id和社区的pid比较之后的社区
                    for (int d=0;d<projectList.size();d++){//进行城市region_id和社区pid的比较
                        //if(cityList.get(c).get("id").toString().equals(projectList.get(d).get(LPEnum.PID.getValue()))){
                        if(cityList.get(c).get(LPEnum.REGION_ID.getValue()).toString().equals(projectList.get(d).get(LPEnum.PID.getValue()))){
                            projectList2.add(projectList.get(d));
                            cityList.get(c).put(LPEnum.projectList.getValue(),projectList2);
                            //cityList.get(c).put("children",projectList2);
                        }
                    }
                    if(areaList.get(a).get(LPEnum.REGION_ID.getValue()).toString().equals(cityList.get(c).get(LPEnum.PID.getValue()))){
                        cityList2.add(cityList.get(c));
                        areaList.get(a).put(LPEnum.cityList.getValue(),cityList2);
                        //areaList.get(a).put("children",cityList2);
                    }
                }
            }
            map.put(LPEnum.areaList.getValue(),areaList);
            //map.put("children",areaList);
            String s = JSON.toJSONString(map);
            System.out.println(s);
            JSONObject jsonObject1 = JSON.parseObject(s);
            return jsonObject1;
        }
        return null;
    }
}
