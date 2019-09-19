package com.qding.bigdata.ds.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

public class CommonUtil {
  public static boolean isEmpty(String str) {
    return str == null || str.trim().isEmpty();

  }

  public static String getSymbols(String s, String split, int count) {
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < count; i++) {
      sb.append(s);
      if (i != count - 1) {
        sb.append(split);
      }
    }
    return sb.toString();
  }

  public static String getQuestionMark(int count) {
    return getSymbols("?", ",", count);
  }

  public static String md5(String str) {
    return DigestUtils.md5Hex("2017" + str);
  }

  public static String getLargetKey(Set<String> set) {
    String k = "";
    for (String k1 : set) {
      if (k1.compareToIgnoreCase(k) > 0) {
        k = k1;
      }
    }
    return k;
  }

  public static void main(String[] args) {
    Set<String> set=new HashSet<String>();
    set.add("a");
    set.add("b");
    // System.out.println("s|a".split("\\|").length);
    System.out.println(getLargetKey(set));
  }
}
