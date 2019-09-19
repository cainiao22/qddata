package com.qding.bigdata.ds.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.qding.bigdata.ds.model.SalesOpportunities;
import com.qding.bigdata.ds.service.BugetReportService;
import com.qding.bigdata.ds.service.SalesOpportunitiesService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2019/2/18 0018.
 */
@Controller
public class SalesOpportunitiesController {

    //认证类型
    private static String grant_type="password";
    private static String client_id="871b2e1cde6aaadec8f23f31ecd5d0d6";
    private static String client_secret="72370e4b2d815ca19a8b546df5bbaf19";
    private static String redirect_uri="http://10.37.253.9";
    private static String username="dengqionglan@qding.me";
//    private static String username="13810904427";
    private static String password="Aa123456789@Okf1ZmUH";
//    private static String password="xiahui1989";

    private static String POST = "POST";
    private static String GET = "GET";
    private static String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";
    private static String CONTENT_TYPE_JSON = "application/json";

    @Autowired
    private SalesOpportunitiesService salesOpportunitiesService;

//    public static void main(String[] args) throws IOException {
////        saveCrmOpportunity();
//        System.out.println( getAuthorizationUtil());
//    }

    @RequestMapping("/ceshiSalesOpportunitiesAPI")
    @ResponseBody
    public String ceshiSalesOpportunitiesAPI(){
        try{
            saveCrmOpportunity();
            return "成功";
        }catch (Exception e){
            e.printStackTrace();
            return "异常";
        }


    }

        //获取动态令牌
    public static  String getAuthorizationUtil(){
        String APIurl = "https://api.xiaoshouyi.com/oauth2/token.action";
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("grant_type", grant_type);
        parameters.put("client_id", client_id);
        parameters.put("client_secret", client_secret);
        parameters.put("redirect_uri", redirect_uri);
        parameters.put("username", username);
        parameters.put("password", password);
        String result = null;
        try {
            result = httpRequest("POST","",APIurl,parameters,null);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map map = JSON.parseObject(result, Map.class);
        String access_token = (String) map.get("access_token");
        return access_token;
    }

    //拉取销售机会数据存库
    public String saveCrmOpportunity(){
        String token = getAuthorizationUtil();
        String APIurl = "https://api.xiaoshouyi.com/rest/data/v2.0/query/xoql";
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("xoql", "select dimDepart.departName,opportunityName,ownerId.name,accountId.accountName," +
                "money,customItem188__c,closeDate,saleStageId,createdAt,updatedAt,comment   from opportunity ");
        String result = null;
        try {
            result = httpRequest(POST,CONTENT_TYPE_URLENCODED,APIurl,parameters,token);
            System.out.println(result);
            Map map = JSON.parseObject(result, Map.class);
            String code = (String) map.get("code");
            Map ma  =  (Map) map.get("data");
            System.out.println(ma);
            List<Map<String,Object>> lm = (List<Map<String, Object>>) ma.get("records");
            System.out.println(lm.size());
            SalesOpportunities so = null;
            Date d=new Date(System.currentTimeMillis()-1000*60*60*24);
            SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
            String dt=sp.format(d);
            try{
                if(!"200".equals(code)){
                    return "接口code值未返回 200";
                }
                so = new SalesOpportunities();
                so.setDt(dt);
                salesOpportunitiesService.delete(so);
            }catch (Exception e){
                e.printStackTrace();
            }
            for (int i=0;i<lm.size();i++){
                so = new SalesOpportunities();
                so.setDt(dt);
                if(lm.get(i).get("dimDepart.departName")!=null){
                    so.setDimDepart(lm.get(i).get("dimDepart.departName").toString());
                }
                if(lm.get(i).get("opportunityName")!=null){
                    so.setOpportunityName(lm.get(i).get("opportunityName").toString());
                }
                if(lm.get(i).get("ownerId.name")!=null){
                    so.setOwnerId(lm.get(i).get("ownerId.name").toString());
                }
                if(lm.get(i).get("accountId.accountName")!=null){
                    so.setAccountId(lm.get(i).get("accountId.accountName").toString());
                }
                if(lm.get(i).get("money")!=null){
                    so.setMoney(lm.get(i).get("money").toString());
                }
                if(lm.get(i).get("customItem188__c")!=null){
                    try {
                        String ci = lm.get(i).get("customItem188__c").toString();
                        ci = ci.substring(2,ci.length()-2).replace("\"","");
                        so.setCustomItem188__c(ci);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(lm.get(i).get("closeDate")!=null){
                    try {
                        String cd = lm.get(i).get("closeDate").toString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date(Long.valueOf(cd));
                        String closeDate = simpleDateFormat.format(date);
                        so.setCloseDate(closeDate);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(lm.get(i).get("saleStageId")!=null){
                    String a = lm.get(i).get("saleStageId").toString();
                    if ("1868212".equals(a)) {
                        so.setSaleStageId("信息收集");
                    }else if("915958".equals(a)){
                        so.setSaleStageId("初步接触");
                    }else if("915959".equals(a)){
                        so.setSaleStageId("明确意向");
                    }else if("915960".equals(a)){
                        so.setSaleStageId("方案洽谈");
                    }else if("915559".equals(a)){
                        so.setSaleStageId("双方盖章");
                    }else if("757751".equals(a)){
                        so.setSaleStageId("合同签约");
                    }else if("757753".equals(a)){
                        so.setSaleStageId("进入停车场");
                    }
                }
                if(lm.get(i).get("createdAt")!=null){
                    try {
                        String cd = lm.get(i).get("createdAt").toString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date(Long.valueOf(cd));
                        String createdAt = simpleDateFormat.format(date);
                        so.setCreatedAt(createdAt);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(lm.get(i).get("updatedAt")!=null){
                    try {
                        String cd = lm.get(i).get("updatedAt").toString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date(Long.valueOf(cd));
                        String updatedAt = simpleDateFormat.format(date);
                        so.setUpdatedAt(updatedAt);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(lm.get(i).get("comment")!=null){
                    so.setComment(lm.get(i).get("comment").toString());
                }

                try{
                    salesOpportunitiesService.save(so);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            return "成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "异常";
        }

    }

    private static String httpRequest(String method, String contentType, String urlStr, HashMap<String,String> paras,String token)
            throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        if(token!=null){
            con.setRequestProperty("Authorization", token);
        }


        if(paras != null && !paras.isEmpty()){
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(paras));
            out.flush();
            out.close();
        }


        InputStream inStream = con.getInputStream();
        String result = IOUtils.toString(inStream,"utf-8");
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer content = new StringBuffer();
//        while ((inputLine = in.readLine()) != null) {
//            content.append(inputLine);
//        }
//        in.close();
        con.disconnect();
//        return content.toString();
        return result.toString();
    }

    private static class ParameterStringBuilder {
        public static String getParamsString(Map<String, String> params)
                throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        }
    }


//    public static String httpGetRequest(String url){
//        try {
//            return httpRequest(GET, CONTENT_TYPE_URLENCODED, url, null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    public static String httpPostRequest(String url, HashMap<String,String> paras){
//        try {
//            return httpRequest(POST, CONTENT_TYPE_URLENCODED, url, paras);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

}
