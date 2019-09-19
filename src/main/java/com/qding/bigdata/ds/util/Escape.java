package com.qding.bigdata.ds.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringEscapeUtils;

public class Escape {
    private final static String[] hex = {
        "00","01","02","03","04","05","06","07","08","09","0A","0B","0C","0D","0E","0F",
        "10","11","12","13","14","15","16","17","18","19","1A","1B","1C","1D","1E","1F",
        "20","21","22","23","24","25","26","27","28","29","2A","2B","2C","2D","2E","2F",
        "30","31","32","33","34","35","36","37","38","39","3A","3B","3C","3D","3E","3F",
        "40","41","42","43","44","45","46","47","48","49","4A","4B","4C","4D","4E","4F",
        "50","51","52","53","54","55","56","57","58","59","5A","5B","5C","5D","5E","5F",
        "60","61","62","63","64","65","66","67","68","69","6A","6B","6C","6D","6E","6F",
        "70","71","72","73","74","75","76","77","78","79","7A","7B","7C","7D","7E","7F",
        "80","81","82","83","84","85","86","87","88","89","8A","8B","8C","8D","8E","8F",
        "90","91","92","93","94","95","96","97","98","99","9A","9B","9C","9D","9E","9F",
        "A0","A1","A2","A3","A4","A5","A6","A7","A8","A9","AA","AB","AC","AD","AE","AF",
        "B0","B1","B2","B3","B4","B5","B6","B7","B8","B9","BA","BB","BC","BD","BE","BF",
        "C0","C1","C2","C3","C4","C5","C6","C7","C8","C9","CA","CB","CC","CD","CE","CF",
        "D0","D1","D2","D3","D4","D5","D6","D7","D8","D9","DA","DB","DC","DD","DE","DF",
        "E0","E1","E2","E3","E4","E5","E6","E7","E8","E9","EA","EB","EC","ED","EE","EF",
        "F0","F1","F2","F3","F4","F5","F6","F7","F8","F9","FA","FB","FC","FD","FE","FF"
    };
    private final static byte[] val = {
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F
    };
    public static String escape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int ch = s.charAt(i);
            if (ch == ' ') {                        // space : map to '+' 
                sbuf.append('+');
            } else if ('A' <= ch && ch <= 'Z') {    // 'A'..'Z' : as it was
                sbuf.append((char)ch);
            } else if ('a' <= ch && ch <= 'z') {    // 'a'..'z' : as it was
                sbuf.append((char)ch);
            } else if ('0' <= ch && ch <= '9') {    // '0'..'9' : as it was
                sbuf.append((char)ch);
            } else if (ch == '-' || ch == '_'       // unreserved : as it was
                || ch == '.' || ch == '!'
                || ch == '~' || ch == '*'
                || ch == '\'' || ch == '(' || ch == ')') {
                sbuf.append((char)ch);
            } else if (ch <= 0x007F) {              // other ASCII : map to %XX
                sbuf.append('%');
                sbuf.append(hex[ch]);
            } else {                                // unicode : map to %uXXXX
                sbuf.append('%');
                sbuf.append('u');
                sbuf.append(hex[(ch >>> 8)]);
                sbuf.append(hex[(0x00FF & ch)]);
            }
        }
        return sbuf.toString();
    }
    public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int len = s.length();
        while (i < len) {
            int ch = s.charAt(i);
            if (ch == '+') {                        // + : map to ' ' 
                sbuf.append(' ');
            } else if ('A' <= ch && ch <= 'Z') {    // 'A'..'Z' : as it was
                sbuf.append((char)ch);
            } else if ('a' <= ch && ch <= 'z') {    // 'a'..'z' : as it was
                sbuf.append((char)ch);
            } else if ('0' <= ch && ch <= '9') {    // '0'..'9' : as it was
                sbuf.append((char)ch);
            } else if (ch == '-' || ch == '_'       // unreserved : as it was
                || ch == '.' || ch == '!'
                || ch == '~' || ch == '*'
                || ch == '\'' || ch == '(' || ch == ')') {
                sbuf.append((char)ch);
            } else if (ch == '%') {
                int cint = 0;
                if ('u' != s.charAt(i+1)) {         // %XX : map to ascii(XX)
                    cint = (cint << 4) | val[s.charAt(i+1)];
                    cint = (cint << 4) | val[s.charAt(i+2)];
                    i+=2;
                } else {                            // %uXXXX : map to unicode(XXXX)
                    cint = (cint << 4) | val[s.charAt(i+2)];
                    cint = (cint << 4) | val[s.charAt(i+3)];
                    cint = (cint << 4) | val[s.charAt(i+4)];
                    cint = (cint << 4) | val[s.charAt(i+5)];
                    i+=5;
                }
                sbuf.append((char)cint);
            }else{
              sbuf.append((char)ch);
            }
            i++;
        }
        return sbuf.toString();
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
      System.out.println(StringEscapeUtils.unescapeJavaScript("tl=%u5C1A%u54C1%u7F51_%u9AD8%u7AEF%u65F6%u5C1A%u8D2D%u7269%u5E73%u53F0_%u5B98%u7F51%u6388%u6743%7C%u6D77%u91CF%u65B0%u54C1%7C%u5168%u573A2%u6298%u8D77&cs=UTF-8&dm"));
    
    System.out.println("**************");
    Map<String, Object> tmap = new TreeMap<String, Object>();
    Map<String, Object> map2 = new TreeMap<String, Object>();
    for (String str : EncodeUtil.urlDecode("quark_id%3Dquark_2.0.0%26os%3DWin32%26app%3DNetscape%26ver%3D5.0%2520(Windows)%26ag%3DMozilla%252F5.0%2520(Windows%2520NT%25206.1%253B%2520WOW64%253B%2520rv%253A50.0)%2520Gecko%252F20100101%2520Firefox%252F50.0%26ec%3Dtrue%26ej%3Dfalse%26fv%3D24.0%2520r0%26lg%3Dzh-cn%26bit%3D24%26px%3D24%26ht%3D1080%26wt%3D1920%26aht%3D1040%26awt%3D1920%26uvid%3D4084327612922029775%26uv_new%3D0%26uid%3D%26url%3Dfile%253A%2F%2F%2FC%253A%2FUsers%2Fwangjunping%2FDesktop%2F%2525E5%25259F%25258B%2525E7%252582%2525B9%2525E6%2525B5%25258B%2525E8%2525AF%252595%2Fdemo.html%26tl%3D%25u5C1A%25u54C1%25u7F51_%25u9AD8%25u7AEF%25u65F6%25u5C1A%25u8D2D%25u7269%25u5E73%25u53F0_%25u5B98%25u7F51%25u6388%25u6743%257C%25u6D77%25u91CF%25u65B0%25u54C1%257C%25u5168%25u573A2%25u6298%25u8D77%26cs%3DUTF-8%26dm%3D%26isapp%3D%26imei%3D%26bt%3Dclick%26tm%3D1484107507466%26et%3Dclick%26cm%3D0%26ttn%3DBUTTON%26tid%3D%26ttp%3Dsubmit%26tsr%3Dundefined%26thf%3Dundefined%26ptx%3D51%26pty%3D19%26ctag%3D%7B%22id%22%3A%22N10%22%2C%22cid%22%3A%22%E5%93%81%E7%B1%BBid%22%7D"
      ).split("&")) {
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