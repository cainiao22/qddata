import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.dao.DsMaidianEventMapper;
import com.qding.bigdata.ds.dao.DsMaidianPageMapper;
import com.qding.bigdata.ds.dao.DsMaidianSkipMapper;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.MaidianEventService;
import com.qding.bigdata.ds.service.MaidianPageService;
import com.qding.bigdata.ds.service.MaidianParamService;
import com.qding.bigdata.ds.service.SystemWebAccessService;
import com.qding.bigdata.ds.util.CommonUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QDHL on 2018/7/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})
public class TestService {
    @Autowired
    SystemWebAccessService systemWebAccessService;
    @Test
    public void test1(){
        CountData data=new CountData();
        data.setStartTime("20180704");
        data.setEndTime("20180705");
        data.setSource("jiashicang");
        List<SystemWebAccess> systemWebAccesses = systemWebAccessService.listBySource(data);
        for (SystemWebAccess ss:systemWebAccesses) {
            System.out.println(ss.toString()+"----------------------------------");
        }
    }

    @Autowired
    MaidianPageService maidianPageService;

    @Autowired
    MaidianEventService maidianEventService;

    @Autowired
    DsMaidianEventMapper maidianEventMapper;

    @Autowired
    DsMaidianSkipMapper maidianSkipMapper;

    @Test
    public void testPageHelper(){
        DsMaidianEvent param = new DsMaidianEvent();
        param.setId(2L);
        final PageInfo<DsMaidianEvent> list = maidianEventService.list(param, 1, 10);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void testAdd(){
        DsMaidianEvent param = new DsMaidianEvent();
        param.setCode("code");
        param.setName("name");
        param.setParamList(new ArrayList<>());
        param.setProductId(1L);
        param.setStatus(1);
        maidianEventMapper.insert(param);
        System.out.println(param.getId());
    }

    @Test
    public void testGetMaidianSKip(){
        final String s = CommonUtil.md5("");
        String a = "1a935255c9b7d97f134532c4b94edeeb";
        System.out.println("s=" + s + ", a="  + a);
        System.out.println(s.equals(a));
    }


    @Autowired
    MaidianParamService maidianParamService;
    @Test
    public void testupload() throws IOException {
        FileInputStream excel = new FileInputStream(new File("C:\\Users\\QDHL\\Desktop\\参数.xls"));
        Workbook wookbook = new HSSFWorkbook(excel);
        Sheet sheet = wookbook.getSheetAt(0);
        List<DsMaidianParam> maidianParamList = new ArrayList<>();
        int totalRowNum = sheet.getLastRowNum();
        for(int i = 1 ; i <= totalRowNum ; i++)
        {
            DsMaidianParam param = new DsMaidianParam();
            //获得第i行对象
            Row row = sheet.getRow(i);
            param.setCode(row.getCell(0).getStringCellValue());
            param.setSimpleCode(row.getCell(1).getStringCellValue());
            param.setName(row.getCell(2).getStringCellValue());
            param.setRemark(row.getCell(3).getStringCellValue());
            param.setStatus(new Double(row.getCell(4).getNumericCellValue()).intValue());
            maidianParamList.add(param);
        }

        maidianParamService.batchInsert(maidianParamList);
    }
}
