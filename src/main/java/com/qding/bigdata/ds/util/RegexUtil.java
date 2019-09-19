package com.qding.bigdata.ds.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.qding.bigdata.ds.common.Result;

public class RegexUtil {

    /** 匹配${param}这种形式的参数 **/
    private static Pattern paramPattern = Pattern.compile("\\$\\{(.*?)\\}");

    public static List<List<String>> getContentLimit(String regex, String string, int limit) {
        Pattern p = Pattern.compile(regex,Pattern.DOTALL);
        Matcher m = p.matcher(string);
        List<List<String>> resultList = new ArrayList<List<String>>();
        int count = 0;
        while (m.find()) {
            if (count >= limit) {
                break;
            }
            List<String> list = new ArrayList<String>();
            for (int i = 1; i <= m.groupCount(); i++) {
                list.add(m.group(i));
            }
            resultList.add(list);
            count++;
        }
        return resultList;
    }

    public static List<List<String>> getAllContent(String regex, String string) {
        return getContentLimit(regex, string, Integer.MAX_VALUE);
    }

    public static List<String> getFirstCotent(String regex, String string) {
        List<List<String>> list = getContentLimit(regex, string, 1);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return new ArrayList<String>();
        }

    }

    /**
     * 获取内容中的动态参数
     * @param content
     * @return
     */
    public static List<String> getDynamicParams(String content){
        Matcher matcher = paramPattern.matcher(content);
        List<String> result = new ArrayList<String>();
        while (matcher.find()){
            String group = matcher.group(1);
            if(!result.contains(group)){
                result.add(group);
            }
        }
        return result;
    }


    /**
     * 替换所有动态的参数
     *
     * @param params
     * @param content
     * @param addQuotation 是否需要添加单引号
     * @return
     */
    public static Result<String> replaceDynamicParams(Map<String, String> params, String content, boolean addQuotation ) {
        List<String> dynamicParams = RegexUtil.getDynamicParams(content);
        StringBuffer sb = new StringBuffer();
        for (String paramName : dynamicParams) {
            String param = params.get(paramName);
            if (StringUtils.isEmpty(param)) {
                sb.append(paramName).append(",");
            } else if(addQuotation){
                content = content.replace(String.format("${%s}", paramName), String.format("'%s'", param));
            } else {
                content = content.replace(String.format("${%s}", paramName), String.format("%s", param));
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
            return Result.failed(3, "缺少必要参数:[" + sb.toString() + "]");
        } else {
            return Result.success(content);
        }
    }

    public static void main(String[] args) {
//        String p=">曝光日期</span><span id=\"newPmVal_1\"><a href='/cell_phone_index/subcate57_list_s5359_1.html'>2014年</a>";
//        System.out.println(RegexUtil.getFirstCotent("曝光日期</span>(.*?)(\\d{4})年", p));
//        String p="<p title=\"4英寸\"><span>主屏尺寸：</span><a href='/cell_phone_index/subcate57_list_p13272_1.html'>47英寸</a></p>";
//        System.out.println(RegexUtil.getFirstCotent("主屏尺寸：</span(.*?)(\\d*\\.*\\d*)英寸", p));
        String p="<li class='cate'><a href='/cell_phone_index/subcate57_list_1.html' target='_blank'>手机</a> - <a href='/cell_phone_index/subcate57_98_list_1.html' target='_blank'>三星</a></li><li title=''>4G网络：移动TD-LTE  </li><li title='移动3G（TD-SCDMA），联通2G/移动2G（GSM） '>3G网络：移动3G（TD-SCDMA），联 </li><li title=''>网络类型：单卡  </li><li title=''>主屏尺寸：4.3英寸  800x480像素 </li><li title=''>CPU频率：1.2GHz  四核 </li><li title=''>后置摄像：500万像素  </li><li title=''>操作系统：Android OS  <a href=\"/384/383994/param.shtml\" target=\"_blank\">更多参数>></a></li>";
        System.out.println(RegexUtil
                .getFirstCotent("手机</a> -(.*?)subcate(.*?)list_1.html' target='_blank'>(.*?)</a>", p)
                .get(2).trim());
    }
}
