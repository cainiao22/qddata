package com.qding.bigdata.ds.util.elasticsearch;

import com.qding.bigdata.ds.util.PropertiesUtil;
import org.apache.commons.dbcp.BasicDataSource;
import org.elasticsearch.xpack.sql.jdbc.EsDataSource;


import javax.annotation.Resource;
import java.sql.*;
import java.util.Properties;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/13 下午5:32
 */
public class EsJDBCInstance {
    @Resource(name = "esDataSource")
    static BasicDataSource esDataSource;

    private final static String CONFIG_FILE="config.properties";
    private static Connection connection = null;
    static{
        EsDataSource dataSource = new EsDataSource();
//        String address = "jdbc:es://" + PropertiesUtil.getPropertiesByKey(CONFIG_FILE, "es.cluster.hosts")+":9200";
        String address = "jdbc:es://10.50.8.140:9200";
//        String address = "jdbc:es://10.50.8.140:9200,10.50.8.141:9200,10.50.8.247:9200,10.50.8.248:9200,10.50.8.137:9200";
//            String address = "jdbc:es://" + PropertiesUtil.getPropertiesByKey(CONFIG_FILE, "es.cluster.hosts")+":"+PropertiesUtil.getPropertiesByKey(CONFIG_FILE, "es.cluster.port");
        dataSource.setUrl(address);
        Properties connectionProperties = new Properties();
        dataSource.setProperties(connectionProperties);
         try {
             connection = dataSource.getConnection();
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }

    public static ResultSet execute(String sql) throws SQLException {
        Statement statement = esDataSource.getConnection().createStatement();
        ResultSet results = statement.executeQuery(sql);
        return results;
    }

    public static ResultSet execute2(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(sql);
        return results;
    }
//    @Test
    public void testExecute() throws Exception {
        long s = System.currentTimeMillis();
        final ResultSet execute2 = EsJDBCInstance.execute2("select * from  big_data_test  where  pi like '%Open%' and rpi like '%Open%' limit 300");
//        final ResultSet execute = EsJDBCInstance.execute2("SELECT hour,count(distinct ui) FROM \"gct_base.2019-08-14\" WHERE 1=1 and business_type=1 and month=\'08\' and day=\'14\' and ding_reg_new=\'1\'   group by hour");
        long count = System.currentTimeMillis() -s ;
        System.out.println(count);
    }
}
