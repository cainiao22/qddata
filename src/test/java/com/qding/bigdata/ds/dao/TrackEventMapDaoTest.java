package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DsMaidianPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yanpf
 * @date 2018/7/18 10:53
 * @description
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})
public class TrackEventMapDaoTest {


    @Autowired
    DsMaidianPageMapper maidianPageMapper;

    @Autowired
    TrackEventMapDao trackEventMapDao;

    @Test
    public void getNameByEventId() throws Exception {
        trackEventMapDao.getNameByEventId("aaa");
        System.out.println();
    }

    @Test
    public void testQueryAll(){
        List<DsMaidianPage> maidianPageList = maidianPageMapper.queryAll();
        maidianPageList.stream().forEach(item -> System.out.println(item.getCode()));
    }

}