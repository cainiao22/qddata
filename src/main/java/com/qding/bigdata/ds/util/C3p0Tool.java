package com.qding.bigdata.ds.util;

import java.util.HashMap;
import java.util.Map;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.qding.bigdata.ds.Exception.JDBCConnExcetion;
import com.qding.bigdata.ds.model.SchemeInfo;

/**
 * Created by QDHL on 2017/8/10.
 */
public class C3p0Tool {
    private static String classDriver = "com.mysql.jdbc.Driver";
    static Map<String, ComboPooledDataSource> map = new HashMap<String, ComboPooledDataSource>();

    public static ComboPooledDataSource buildConnetionPool(SchemeInfo info) throws JDBCConnExcetion {
        //获取连接池对象
        ComboPooledDataSource cp = new ComboPooledDataSource();
        String url = "jdbc:mysql://" + info.getIp() + "/" + info.getDbName();
        try {
            //设置连接参数
            cp.setJdbcUrl(url);
            cp.setUser(info.getUser());
            cp.setPassword(info.getPassword());
            cp.setDriverClass(classDriver);
            //设置连接池的参数
            cp.setInitialPoolSize(1);//初始数量
            cp.setMaxPoolSize(8);//最大数量
            cp.setCheckoutTimeout(3000);//最大等待时间
            cp.getConnection().hashCode();
        } catch (Exception e) {
            cp.close();
            throw new JDBCConnExcetion("链接数据库异常请检查传入参数:" + url);
        }
        return cp;
    }

    public static ComboPooledDataSource getComboPooledDataSource(SchemeInfo info) throws JDBCConnExcetion {
        String dbName = info.getDbName();
        if (!map.containsKey(dbName)) {
            synchronized (C3p0Tool.class) {
                if (!map.containsKey(dbName)) {
                    ComboPooledDataSource cp = buildConnetionPool(info);
                    map.put(dbName, cp);
                }
            }
        }
        return map.get(dbName);


    }
  /* public static void close(Connection conn) {
        try {
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

   }
*/


}









