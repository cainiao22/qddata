package com.qding.bigdata.ds.model;

import java.io.Serializable;

/**
 * Created by lzs on 2018/7/4.
 */
public class SystemWebAccess implements Serializable{

   private static final long serialVersionUID = -7833238701908320740L;

   private String date;//用户浏览时间
   private String user;//用户账户id
   private String userName;//用户中文名
   private String source;//用户访问来源
   private int pv;//用户访问页面次数
   private int dayNumber;//登录天数

   public SystemWebAccess() {
   }

   public SystemWebAccess(String date, String user, String userName, String source, int pv, int dayNumber) {
      this.date = date;
      this.user = user;
      this.userName = userName;
      this.source = source;
      this.pv = pv;
      this.dayNumber = dayNumber;
   }

   public static long getSerialVersionUID() {
      return serialVersionUID;
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getUser() {
      return user;
   }

   public void setUser(String user) {
      this.user = user;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getSource() {
      return source;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public int getPv() {
      return pv;
   }

   public void setPv(int pv) {
      this.pv = pv;
   }

   public int getDayNumber() {
      return dayNumber;
   }

   public void setDayNumber(int dayNumber) {
      this.dayNumber = dayNumber;
   }

   @Override
   public String toString() {
      return "SystemWebAccess{" +
              "date='" + date + '\'' +
              ", user='" + user + '\'' +
              ", userName='" + userName + '\'' +
              ", source='" + source + '\'' +
              ", pv=" + pv +
              ", dayNumber=" + dayNumber +
              '}';
   }
}
