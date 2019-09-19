package com.qding.bigdata.ds.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class HttpUtil {
	
    public static Logger logger = LoggerFactory.getLogger(HttpUtil.class);


    public static String get(String url) throws ParseException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(request);
        String content= EntityUtils.toString(response.getEntity());
        response.close();
        httpclient.close();
        return content;

    }

    public static String post(String url, Map<String, String> params) throws Exception {
        logger.debug("HttpUtil,request url:" + url + " and params:" + params.toString());
        long start = System.currentTimeMillis();

    	CloseableHttpClient httpclient = HttpClients.createDefault();

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();

        for (String key : params.keySet()) {
            formparams.add(new BasicNameValuePair(key, params.get(key)));
        }
        HttpPost request = new HttpPost(url);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Charset.defaultCharset());
        request.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        String responStr = EntityUtils.toString(response.getEntity());
        if (statusCode != 200) {
        	request.abort();
            logger.error("HttpUtil,error status code:" + statusCode + "|request url:" + url + "|params:" + params.toString());
            throw new RuntimeException("HttpUtil,error status code :" + statusCode+",response："+responStr);
        }
        
		if (entity != null) {
            long end = System.currentTimeMillis();
            logger.info("HttpUtil,request url:" + url + "|params:" + params.toString() + "|time:" + (end - start) + "ms");
            logger.debug("HttpUtil,response result :" + responStr);
        }
        return responStr;

    }

    public static String post(String url, String paramString) throws ParseException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost request = new HttpPost(url);
        StringEntity entity = new StringEntity(paramString, Charset.defaultCharset());
        entity.setContentType("application/json");
        request.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(request);
        return EntityUtils.toString(response.getEntity());

    }
}
