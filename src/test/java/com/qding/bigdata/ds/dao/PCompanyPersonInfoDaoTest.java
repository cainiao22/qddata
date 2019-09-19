package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.UserDetailParam;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-9-2 下午6:07
 * @Description
 **/

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})

public class PCompanyPersonInfoDaoTest {

    @Autowired
    PCompanyPersonInfoDao pCompanyPersonInfoDao;
    @Resource(name = "esSqlSession")
    public void initESSqlDao(SqlSession sqlSession) {
        this.pCompanyPersonInfoDao = sqlSession.getMapper(PCompanyPersonInfoDao.class);
    }


    @Test
    public void testGetUserDetail(){
        UserDetailParam param = new UserDetailParam();
        param.setActiveDate("2019-09-02");
        param.setTelphone("17866855712");
        param.setRegistered(true);
        param.setRegisterDate("2019-09-02");
        param.setCompanyId("20181016144335017b2");
        List<Map<String, Object>> maps = pCompanyPersonInfoDao.queryPersonDetails(param);
        System.out.println(maps);
    }
}
