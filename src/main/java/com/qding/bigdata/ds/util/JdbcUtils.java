package com.qding.bigdata.ds.util;



        import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qding.bigdata.ds.Exception.JDBCConnExcetion;
import com.qding.bigdata.ds.Exception.JDBCExeceteExcetion;
import com.qding.bigdata.ds.model.SchemeInfo;


public final class JdbcUtils {
    protected Log log = LogFactory.getLog(this.getClass());
    public static final String name = "com.mysql.jdbc.Driver";
    public Connection conn = null;
    public PreparedStatement pst = null;
    public ResultSet rs = null;
    public Connection onnecttionDB(SchemeInfo info) throws JDBCConnExcetion {
        String url="jdbc:mysql://"+info.getIp()+"/"+info.getDbName();
        try {
            url="jdbc:mysql://"+info.getIp()+"/"+info.getDbName();
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, info.getUser(), info.getPassword());//获取连接
        } catch (Exception e) {
           log.error("链接数据路异常",e);
            throw new JDBCConnExcetion("链接数据库异常请检查传入参数:"+url);
        }
        return conn;
    }

    public ResultSet executeSql(String sql) throws JDBCExeceteExcetion  {
        try {
            rs = execute(sql, conn, rs);
        }catch (SQLException e) {
            log.error("执行sql异常",e);
            throw new JDBCExeceteExcetion("执行sql异常 :"+sql);
        }
        return rs;
    }

    public static  ResultSet executeSql(String sql,Connection conn) throws JDBCExeceteExcetion  {
        ResultSet rs = null;
        try {
            rs = execute(sql, conn, rs);
        }catch (SQLException e) {
            throw new JDBCExeceteExcetion("执行sql异常 :"+sql);
        }
        return rs;
    }

    public static ResultSet execute(String sql, Connection conn, ResultSet rs) throws SQLException, JDBCExeceteExcetion {
        if(conn!=null){
            rs = conn.prepareStatement(sql).executeQuery();
            if(!rs.first()){
                throw new JDBCExeceteExcetion("传入参数错误表不存在");
            }else{
                rs.previous();
            }
        }
        return rs;
    }

    public  void close() {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null)
                    pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
