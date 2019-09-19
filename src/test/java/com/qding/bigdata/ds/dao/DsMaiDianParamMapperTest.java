package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DsMaidianParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @Author yanpf
 * @Date 19-8-29 下午2:45
 * @Description TODO
 **/
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})

public class DsMaiDianParamMapperTest {

    @Autowired
    DsMaidianParamMapper maidianParamMapper;

    @Test
    public void testMaidianParamMapper(){
        String pageCode = "Live_PGCListPage";
        List<DsMaidianParam> dsMaidianParams = maidianParamMapper.queryByPageCode(pageCode);
        System.out.println(dsMaidianParams);
        List<DsMaidianParam> dsMaidianParams1 = maidianParamMapper.queryByEventCode("event_SelectRole_ConfirmClick");
        System.out.println(dsMaidianParams1);
    }
}
