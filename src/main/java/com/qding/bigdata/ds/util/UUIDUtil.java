package com.qding.bigdata.ds.util;

import java.util.UUID;

import com.qding.bigdata.ds.common.Constant;

public class UUIDUtil {

  public static String createId() {
    return UUID.randomUUID().toString().replace(Constant.MIDLINE, Constant.EMPTY);
  }

  public static void main(String[] args) {
    System.out.println(UUID.randomUUID().toString());
  }
}
