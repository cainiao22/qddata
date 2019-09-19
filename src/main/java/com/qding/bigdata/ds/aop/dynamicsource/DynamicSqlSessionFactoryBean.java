package com.qding.bigdata.ds.aop.dynamicsource;

import org.apache.ibatis.session.*;

import java.sql.Connection;
import java.util.Map;

/**
 * @author yanpf
 * @date 2019/5/14 11:13
 * @description
 */
public class DynamicSqlSessionFactoryBean implements SqlSessionFactory {

    private Map<String, SqlSessionFactory> map;


    private SqlSessionFactory defaultTarget;

    public Map<String, SqlSessionFactory> getMap(){
        return map;
    }

    public void setMap(Map<String, SqlSessionFactory> map) {
        this.map = map;
    }

    public SqlSessionFactory getDefaultTarget() {
        return defaultTarget;
    }

    public void setDefaultTarget(SqlSessionFactory defaultTarget) {
        this.defaultTarget = defaultTarget;
    }

    private SqlSessionFactory getTarget(){
        SqlSessionFactory sessionFactory = map.get(DataSourceContextHolder.getDataSource());
        if(sessionFactory == null){
            return defaultTarget;
        }

        return sessionFactory;
    }
    @Override
    public SqlSession openSession() {
        return getTarget().openSession();
    }

    @Override
    public SqlSession openSession(boolean autoCommit) {
        return getTarget().openSession(autoCommit);
    }

    @Override
    public SqlSession openSession(Connection connection) {
        return getTarget().openSession(connection);
    }

    @Override
    public SqlSession openSession(TransactionIsolationLevel level) {
        return getTarget().openSession(level);
    }

    @Override
    public SqlSession openSession(ExecutorType execType) {
        return getTarget().openSession(execType);
    }

    @Override
    public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
        return getTarget().openSession(execType, autoCommit);
    }

    @Override
    public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
        return getTarget().openSession(execType, level);
    }

    @Override
    public SqlSession openSession(ExecutorType execType, Connection connection) {
        return getTarget().openSession(execType, connection);
    }

    @Override
    public Configuration getConfiguration() {
        return getTarget().getConfiguration();
    }
}
