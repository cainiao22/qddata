package com.qding.bigdata.ds.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

public class EncodeUtil {

  public static String urlDecode(String url) {
    try {
      return URLDecoder.decode(url, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return "";
    }
  }

  public static String urlDecode2Times(String url) {
    return urlDecode(urlDecode(url));
  }

  public static void main(String[] args) {
    String param =
        "quark_id%253Dquark_2.0.0%2526os%253DWin32%2526app%253DNetscape%2526ver%253D5.0%252520(Windows)%2526ag%253DMozilla%25252F5.0%252520(Windows%252520NT%2525206.1%25253B%252520WOW64%25253B%252520rv%25253A50.0)%252520Gecko%25252F20100101%252520Firefox%25252F50.0%2526ec%253Dtrue%2526ej%253Dfalse%2526fv%253D24.0%252520r0%2526lg%253Dzh-cn%2526bit%253D24%2526px%253D24%2526ht%253D1080%2526wt%253D1920%2526aht%253D1040%2526awt%253D1920%2526uvid%253D4084327612922029775%2526uv_new%253D0%2526uid%253D%2526url%253Dfile%25253A%252F%252F%252FC%25253A%252FUsers%252Fwangjunping%252FDesktop%252F%252525E5%2525259F%2525258B%252525E7%25252582%252525B9%252525E6%252525B5%2525258B%252525E8%252525AF%25252595%252Fdemo.html%2526tl%253D%2525u5C1A%2525u54C1%2525u7F51_%2525u9AD8%2525u7AEF%2525u65F6%2525u5C1A%2525u8D2D%2525u7269%2525u5E73%2525u53F0_%2525u5B98%2525u7F51%2525u6388%2525u6743%25257C%2525u6D77%2525u91CF%2525u65B0%2525u54C1%25257C%2525u5168%2525u573A2%2525u6298%2525u8D77%2526cs%253DUTF-8%2526dm%253D%2526isapp%253D%2526imei%253D%2526bt%253Dvisit%2526tm%253D1484127619187%2526ref%253D%2526bd%253D";

    // String param =((String) map.get("param")).replaceAll("%u5", "%");
    if (param.startsWith("quark")) {

      param = EncodeUtil.urlDecode2Times(param);
    }
    Map<String, Object> tmap = new TreeMap<String, Object>();
    Map<String, Object> map2 = new TreeMap<String, Object>();
    for (String str : param.split("&")) {
      String[] split = str.split("=");
      if (split.length > 1) {
        try {


          tmap.put(split[0], URLDecoder.decode(split[1], "utf-8"));
        } catch (Exception e) {
          tmap.put(split[0], Escape.unescape(split[1]));
          System.out.println(split[0]);
        }
      }

    }
  }
}
