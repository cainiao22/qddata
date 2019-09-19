package com.qding.bigdata.ds.util;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.qding.bigdata.ds.Exception.JDBCConnExcetion;
import com.qding.bigdata.ds.model.SchemeInfo;

/**
 * Describe: 请补充类描述
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/11/11.
 */
public class DataSourceUtil {
    private static Logger logger = Logger.getLogger(DataSourceUtil.class);
    private static DataSource dataSource;
    public static  DataSource getDataSource(SchemeInfo info) throws JDBCConnExcetion {

        dataSource =C3p0Tool.getComboPooledDataSource(info);

        return dataSource;
    }

}
