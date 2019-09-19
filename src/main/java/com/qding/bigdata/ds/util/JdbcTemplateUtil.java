package com.qding.bigdata.ds.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.qding.bigdata.ds.Exception.JDBCConnExcetion;
import com.qding.bigdata.ds.model.SchemeInfo;

/**
 * Created by QDHL on 2017/8/10.
 */
public class JdbcTemplateUtil {
    private static Logger logger = Logger.getLogger(JdbcTemplateUtil.class);
    private  static Map<String, JdbcTemplate> map = new HashMap<String, JdbcTemplate>();


    public static JdbcTemplate getJdbcTemplate(SchemeInfo info) throws JDBCConnExcetion {
        String dbName=info.getDbName();
        if (!map.containsKey(dbName)) {
            synchronized (JdbcTemplateUtil.class) {
                if (!map.containsKey(dbName)) {
                    JdbcTemplate jt = new JdbcTemplate(DataSourceUtil.getDataSource(info));;
                    map.put(dbName, jt);
                }
            }
        }
       return  map.get(dbName);
    }
    /*private static String buidQuerySchemeSql(String tableName, String dbName) {
        return "select COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT from information_schema.COLUMNS  where TABLE_NAME='" + tableName + "' and table_schema = '" + dbName + "'";
    }*/
}
