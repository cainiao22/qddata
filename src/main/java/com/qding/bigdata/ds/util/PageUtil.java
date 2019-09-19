package com.qding.bigdata.ds.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.qding.bigdata.ds.model.BaseModel;


public class PageUtil {

    /**
     * 获取总页数
     * @param total
     * @param pageCount
     * @return
     */
    public static int getTotalCount(int total,int pageCount){
        return (total  +  pageCount  - 1) / pageCount;
    }

    /**
     * 获取当前页码
     * @param offset
     * @param pageCount
     * @return
     */
    public static int getCurrentPage(int offset,int pageCount){
       return  (offset/pageCount)+1;
    }

    public static String parserDateStr(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(date);
    }

    public static String getPageLimitAndOrderBy(String sql, BaseModel model) {
        String orderBy=model.getSortAndDesc();
        int page = model.getPage() > 0 ? model.getPage() : 1;

        StringBuffer sb = new StringBuffer();
        sb.append(sql);
        if (!orderBy.trim().isEmpty()) {
            sb.append("  order By ").append(orderBy);
        }

        sb.append("  limit  ");
        sb.append((page - 1) * model.getPageCount());

        sb.append(" ,  ");
        sb.append( model.getPageCount());

        return sb.toString();
    }

    public  static void  main(String[] args)
    {

       int currentPage =  PageUtil.getCurrentPage(20,10);
       System.err.println(currentPage);

       for(int i=0;i<215;i+=20){
           currentPage =  PageUtil.getCurrentPage(i,20);
           System.err.println("offset==="+i+",,,,,currentPage====="+currentPage);
       }





    }
}
