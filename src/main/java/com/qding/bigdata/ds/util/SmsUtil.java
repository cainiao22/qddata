package com.qding.bigdata.ds.util;

import java.io.IOException;

import org.apache.http.ParseException;

public class SmsUtil {

    private static String SMSHOST = "";

    public static void sendSms(String phonenos, String content) {

        try {
            String[] phonenosArr = phonenos.split(",");
            for (String phoneno : phonenosArr) {
                HttpUtil.get(SMSHOST.replace("#number", phoneno).replace("#desc", content));

            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendSms("15110234502,15110234502", "测试2");
    }
}
