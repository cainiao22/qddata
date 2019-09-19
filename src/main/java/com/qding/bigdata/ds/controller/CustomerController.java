package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.BugetReport;
import com.qding.bigdata.ds.model.CsmRisks;
import com.qding.bigdata.ds.model.PropertyLevel;
import com.qding.bigdata.ds.service.CsmRisksService;
import com.qding.bigdata.ds.service.PropertyLevelService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrator on 2019/3/5 0005.
 */
@Controller
public class CustomerController extends BasicController{

    public static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CsmRisksService csmRisksService;

    @Autowired
    private PropertyLevelService propertyLevelService;

    //客户分级查询
    @RequestMapping("/propertylevelselect")
    @ResponseBody
    public List<PropertyLevel> propertylevelselect(HttpServletRequest request,HttpServletResponse response) {

        try{
            PropertyLevel propertyLevel = new PropertyLevel();
            return propertyLevelService.list(propertyLevel);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //客户风险预警查询
    @RequestMapping("/csmRisksselect")
    @ResponseBody
    public List<CsmRisks> csmRisksselect(HttpServletRequest request,HttpServletResponse response) {

        try{
            CsmRisks csmRisks = new CsmRisks();
            return csmRisksService.list(csmRisks);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //客户等级导入
    @RequestMapping("/propertylevelimport")
    @ResponseBody
    public List<PropertyLevel> propertylevelimport(@RequestParam("file") MultipartFile file,
                                                    HttpServletRequest request, HttpServletResponse response) {
        try {
            InputStream inputStream = file.getInputStream();
            boolean isE2007 = false;    //判断是否是excel2007格式
            if(file.getName().endsWith("xlsx")){
                isE2007 = true;
            }
            Workbook wb = null;
            if(isE2007){
                wb = new XSSFWorkbook();
            }else{
                wb = new HSSFWorkbook();
            }
            // 1.创建workbook对象，读取整个文档
            wb = WorkbookFactory.create(inputStream);
            Sheet sheetAt = wb.getSheetAt(0);
            System.out.println("总行数为："+sheetAt.getLastRowNum());
//            for (Row row : sheetAt) {
//                if (row.getPhysicalNumberOfCells()!=4){
//                    return null;
//                }
//            }
            int i = 1;
            PropertyLevel jy = null;
            try{
                jy = new PropertyLevel();
                propertyLevelService.delete(jy);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            // 3.循环读取某一行
            for (Row row : sheetAt) {
                System.out.println("读取excel第"+ i +"行");
//                System.out.println("本行列数为："+row.getPhysicalNumberOfCells());
                i++;
                if (i<=2){
                    continue;
                }
                jy = new PropertyLevel();
                jy.setCity(getTextUtil(row,0));
                jy.setProperty_name(getTextUtil(row,1));
                jy.setLevel(getTextUtil(row,2));
                jy.setRemarks(getTextUtil(row,3));

                try{
                    propertyLevelService.save(jy);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            jy = new PropertyLevel();
            List<PropertyLevel> ls  = propertyLevelService.list(jy);
            return ls;
        }catch (Exception e){
            e.printStackTrace();
        }

        return  null;
    }


    //客户风险导入
    @RequestMapping("/csmRisksimport")
    @ResponseBody
    public List<CsmRisks> csmRisksimport(@RequestParam("file") MultipartFile file,
                                                    HttpServletRequest request, HttpServletResponse response) {
        try {
            InputStream inputStream = file.getInputStream();
            boolean isE2007 = false;    //判断是否是excel2007格式
            if(file.getName().endsWith("xlsx")){
                isE2007 = true;
            }
            Workbook wb = null;
            if(isE2007){
                wb = new XSSFWorkbook();
            }else{
                wb = new HSSFWorkbook();
            }
            // 1.创建workbook对象，读取整个文档
            wb = WorkbookFactory.create(inputStream);
            Sheet sheetAt = wb.getSheetAt(0);
            System.out.println("总行数为："+sheetAt.getLastRowNum());
//            for (Row row : sheetAt) {
//                if (row.getPhysicalNumberOfCells()!=12){
//                    return null;
//                }
//            }
            int i = 1;
            CsmRisks jy = null;
            try{
                jy = new CsmRisks();
                csmRisksService.delete(jy);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            // 3.循环读取某一行
            for (Row row : sheetAt) {
                System.out.println("读取excel第"+ i +"行");
//                System.out.println("本行列数为："+row.getPhysicalNumberOfCells());
                i++;
                if (i<=2){
                    continue;
                }
                jy = new CsmRisks();
                jy.setClient_level(getTextUtil(row,0));
                jy.setClient_name(getTextUtil(row,1));
                jy.setSite(getTextUtil(row,2));
                jy.setCity(getTextUtil(row,3));
                jy.setManager(getTextUtil(row,4));
                jy.setRisk_description(getTextUtil(row,5));
                try{

                if (row.getCell(6).getCellType() == 0){
                    Calendar calendar = new GregorianCalendar(1900,0,-1);
                    Date d = calendar.getTime();
                    Date dd = DateUtils.addDays(d,Integer.valueOf(getTextUtil(row,6)));
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String s=df.format(dd);
                    jy.setPropose_time(s);
                }else {
                    jy.setPropose_time(getTextUtil(row,6));
                }

                }catch (Exception e){
                    e.printStackTrace();
                }

                jy.setProgramme(getTextUtil(row,7));

                try{
                    if (row.getCell(8).getCellType() == 0){
                        Calendar calendar = new GregorianCalendar(1900,0,-1);
                        Date d = calendar.getTime();
                        Date dd = DateUtils.addDays(d,Integer.valueOf(getTextUtil(row,8)));
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String s=df.format(dd);
                        jy.setEnd_time(s);
                    }else {
                        jy.setEnd_time(getTextUtil(row,8));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                jy.setCsm_status(getTextUtil(row,9));
                jy.setAdviser(getTextUtil(row,10));
                jy.setRemarks(getTextUtil(row,11));
                try{
                    csmRisksService.save(jy);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            jy = new CsmRisks();
            List<CsmRisks> ls  = csmRisksService.list(jy);
            return ls;
        }catch (Exception e){
            e.printStackTrace();
        }

        return  null;
    }


    @RequestMapping("/customer")
    public ModelAndView customer(PropertyLevel propertyLevel) {
        ModelAndView modelAndView =initModelAndView();
        modelAndView.addObject("data",propertyLevelService.list(propertyLevel));
        modelAndView.setViewName("customer");
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.客户服务);
        return modelAndView;
    }

    public String getTextUtil(Row row, int i){
        if(row.getCell(i)!=null){
            row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
            if(row.getCell(i).getStringCellValue().toString().trim().equals("")){
                return  "";
            }
            return row.getCell(i).getStringCellValue()==null?"":
                    row.getCell(i).getStringCellValue().toString();
        }
        return  "";
    }

    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int len;
        byte[] data = new byte[100000];
        while ((len = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, len);
        }

        buffer.flush();
        return buffer.toByteArray();
    }

}
