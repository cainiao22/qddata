package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.BugetReport;
import com.qding.bigdata.ds.model.JiashicangYusuanluru;
import com.qding.bigdata.ds.model.StarVeinCityPlan;
import com.qding.bigdata.ds.service.BugetReportService;
import com.qding.bigdata.ds.service.JiashicangYusuanluruService;
import com.qding.bigdata.ds.service.StarVeinCityPlanService;
import com.qding.framework.common.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class BudgetKeyboarderController extends BasicController{

    public static Logger logger = LoggerFactory.getLogger(BudgetKeyboarderController.class);

    @Autowired
    private BugetReportService bugetReportService;

    @Autowired
    private JiashicangYusuanluruService jiashicangYusuanluruService;

    @Autowired
    private StarVeinCityPlanService starVeinCityPlanService;


    /**
     * 生成excel并下载
     */
    @RequestMapping("/jiashicangyusuanexport")
    @ResponseBody
    public void exportExcel(@RequestParam("dt") String dt,HttpServletRequest request, HttpServletResponse response) {

        String classpath  = this.getClass().getResource("/").getPath().replaceFirst("/", "");
        String webappRoot = classpath.replaceAll("WEB-INF/classes/", "");
        System.out.println(webappRoot+"document/yusuanlurumodel.xlsx");
        File newFile = new File("/"+webappRoot+"document/yusuanlurumodel.xlsx");

        // 新文件写入数据，并下载*****************************************************
        InputStream is = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        try {
            is = new FileInputStream(newFile);// 将excel文件转为输入流
            workbook = new XSSFWorkbook(is);// 创建个workbook，
            // 获取第一个sheet
            sheet = workbook.getSheetAt(0);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (sheet != null) {
            try {
                // 写数据
                FileOutputStream fos = new FileOutputStream(newFile);
                XSSFRow row = sheet.getRow(3);
                if (row == null) {
                    row = sheet.createRow(3);
                }
                XSSFCell cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }

                JiashicangYusuanluru jy = null;
                jy = new JiashicangYusuanluru();
                jy.setDt(dt);
                List<JiashicangYusuanluru> ls = jiashicangYusuanluruService.list(jy);

                for (int m = 0; m < ls.size(); m++) {
                    jy = new JiashicangYusuanluru();
                    jy = ls.get(m);
                    row = sheet.createRow((int) m + 3);
                    createCell(row,0,cell,jy.getYewuxian_name()==null?"":jy.getYewuxian_name());
                    createCell(row,1,cell,jy.getYewuxian_id()==null?"":jy.getYewuxian_id());
                    createCell(row,2,cell,jy.getDim_name()==null?"":jy.getDim_name());
                    createCell(row,3,cell,jy.getDim_type()==null?"":jy.getDim_type());
                    createCell(row,4,cell,jy.getPerson_name()==null?"":jy.getPerson_name());
                    createCell(row,5,cell,jy.getAll_shouru()==null?"0":jy.getAll_shouru());
                    createCell(row,6,cell,jy.getAll_lirun()==null?"0":jy.getAll_lirun());
                    createCell(row,7,cell,jy.getAll_chuhuo()==null?"0":jy.getAll_chuhuo());
                    createCell(row,8,cell,jy.getAll_huikuan()==null?"0":jy.getAll_huikuan());
                    createCell(row,9,cell,jy.getWeek1_shouru()==null?"0":jy.getWeek1_shouru());
                    createCell(row,10,cell,jy.getWeek1_lirun()==null?"0":jy.getWeek1_lirun());
                    createCell(row,11,cell,jy.getWeek1_chuhuo()==null?"0":jy.getWeek1_chuhuo());
                    createCell(row,12,cell,jy.getWeek1_huikuan()==null?"0":jy.getWeek1_huikuan());
                    createCell(row,13,cell,jy.getWeek2_shouru()==null?"0":jy.getWeek2_shouru());
                    createCell(row,14,cell,jy.getWeek2_lirun()==null?"0":jy.getWeek2_lirun());
                    createCell(row,15,cell,jy.getWeek2_chuhuo()==null?"0":jy.getWeek2_chuhuo());
                    createCell(row,16,cell,jy.getWeek2_huikuan()==null?"0":jy.getWeek2_huikuan());
                    createCell(row,17,cell,jy.getWeek3_shouru()==null?"0":jy.getWeek3_shouru());
                    createCell(row,18,cell,jy.getWeek3_lirun()==null?"0":jy.getWeek3_lirun());
                    createCell(row,19,cell,jy.getWeek3_chuhuo()==null?"0":jy.getWeek3_chuhuo());
                    createCell(row,20,cell,jy.getWeek3_huikuan()==null?"0":jy.getWeek3_huikuan());
                    createCell(row,21,cell,jy.getWeek4_shouru()==null?"0":jy.getWeek4_shouru());
                    createCell(row,22,cell,jy.getWeek4_lirun()==null?"0":jy.getWeek4_lirun());
                    createCell(row,23,cell,jy.getWeek4_chuhuo()==null?"0":jy.getWeek4_chuhuo());
                    createCell(row,24,cell,jy.getWeek4_huikuan()==null?"0":jy.getWeek4_huikuan());
                    createCell(row,25,cell,jy.getWeek5_shouru()==null?"0":jy.getWeek5_shouru());
                    createCell(row,26,cell,jy.getWeek5_lirun()==null?"0":jy.getWeek5_lirun());
                    createCell(row,27,cell,jy.getWeek5_chuhuo()==null?"0":jy.getWeek5_chuhuo());
                    createCell(row,28,cell,jy.getWeek5_huikuan()==null?"0":jy.getWeek5_huikuan());
                    createCell(row,29,cell,jy.getWeek6_shouru()==null?"0":jy.getWeek6_shouru());
                    createCell(row,30,cell,jy.getWeek6_lirun()==null?"0":jy.getWeek6_lirun());
                    createCell(row,31,cell,jy.getWeek6_chuhuo()==null?"0":jy.getWeek6_chuhuo());
                    createCell(row,32,cell,jy.getWeek6_huikuan()==null?"0":jy.getWeek6_huikuan());

                }
                workbook.write(fos);
                fos.flush();
                fos.close();
                // 下载
                InputStream fis = new BufferedInputStream(new FileInputStream(
                        newFile));

                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                OutputStream toClient = new BufferedOutputStream(
                        response.getOutputStream());
                response.setContentType("application/x-msdownload");
                String newName = URLEncoder.encode(
                        "预算录入-" + dt + ".xlsx",
                        "UTF-8");
                response.addHeader("Content-Disposition",
                        "attachment;filename=\"" + newName + "\"");
                response.addHeader("Content-Length", "" + newFile.length());
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != is) {
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 生成excel并下载
     */
    @RequestMapping("/cityplanexport")
    @ResponseBody
    public void cityplanexportExcel(@RequestParam("dt") String dt,HttpServletRequest request, HttpServletResponse response) {

        String classpath  = this.getClass().getResource("/").getPath().replaceFirst("/", "");
        String webappRoot = classpath.replaceAll("WEB-INF/classes/", "");
        System.out.println(webappRoot+"document/cityplanmodel.xlsx");
        File newFile = new File("/"+webappRoot+"document/cityplanmodel.xlsx");

        // 新文件写入数据，并下载*****************************************************
        InputStream is = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        InputStream fis = null;
        FileInputStream fileInputStream = null;
        int a = 0;
        try {
            is = new FileInputStream(newFile);// 将excel文件转为输入流
            workbook = new XSSFWorkbook(is);// 创建个workbook，
            sheet = workbook.getSheetAt(a);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sheet != null) {
            try {
                // 写数据
                FileOutputStream fos = new FileOutputStream(newFile);
                XSSFRow row = sheet.getRow(3);
                if (row == null) {
                    row = sheet.createRow(3);
                }
                XSSFCell cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }

                StarVeinCityPlan jy = null;
                jy = new StarVeinCityPlan();
                jy.setDt(dt);
                List<StarVeinCityPlan> ls = starVeinCityPlanService.list(jy);

                for (int m = 0; m < ls.size(); m++) {
                    jy = new StarVeinCityPlan();
                    jy = ls.get(m);
                    row = sheet.createRow((int) m + 2);
                    createCell(row,0,cell,jy.getRegion_name()==null?"":jy.getRegion_name());
                    createCell(row,1,cell,jy.getRegion_id()==null?"":jy.getRegion_id());
                    createCell(row,2,cell,jy.getYewuxian_name()==null?"":jy.getYewuxian_name());
                    createCell(row,3,cell,jy.getYewuxian_id()==null?"":jy.getYewuxian_id());
                    createCell(row,4,cell,jy.getAll_shouru()==null?"":jy.getAll_shouru());
                    createCell(row,5,cell,jy.getAll_lirun()==null?"0":jy.getAll_lirun());
                    createCell(row,6,cell,jy.getAll_chuhuo()==null?"0":jy.getAll_chuhuo());
                    createCell(row,7,cell,jy.getAll_huikuan()==null?"0":jy.getAll_huikuan());
                    createCell(row,8,cell,jy.getAll_gsv()==null?"0":jy.getAll_gsv());
                }
                workbook.write(fos);
                fos.flush();
                fos.close();
                // 下载
                fileInputStream = new FileInputStream(newFile);
                fis = new BufferedInputStream(fileInputStream);

                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                OutputStream toClient = new BufferedOutputStream(
                        response.getOutputStream());
                response.setContentType("application/x-msdownload");
                String newName = URLEncoder.encode(
                        "城市计划-" + dt + ".xlsx",
                        "UTF-8");
                response.addHeader("Content-Disposition",
                        "attachment;filename=\"" + newName + "\"");
                response.addHeader("Content-Length", "" + newFile.length());
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != is ) {
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (null != fis) {
                        fis.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (null != fileInputStream) {
                        fileInputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void createCell(XSSFRow row,int i,XSSFCell cell,String value){
        cell = row.createCell(i);
        cell.setCellValue(value);
    }


    //新新预算目标录入导入
    @RequestMapping("/jiashicangyusuanimport")
    @ResponseBody
    public List<JiashicangYusuanluru> export(@RequestParam("file") MultipartFile file, @RequestParam("dt") String dt,
                       HttpServletRequest request, HttpServletResponse response) {
        try {
            InputStream inputStreamFile = file.getInputStream();
            byte[] bytes = getBytes(inputStreamFile);
            InputStream inputStream = new ByteArrayInputStream(bytes);
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
            for (Row row : sheetAt) {
                if (row.getPhysicalNumberOfCells()!=33){
                       return null;
                }
            }
            int i = 1;
            JiashicangYusuanluru jy = null;
            try{
                if (dt!=null&&!"".equals(dt)){
                    jy = new JiashicangYusuanluru();
                    jy.setDt(dt);
                    jiashicangYusuanluruService.delete(jy);
                }
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            // 3.循环读取某一行
            for (Row row : sheetAt) {
                System.out.println("读取excel第"+ i +"行");
                i++;
                if (i<=4){
                    continue;
                }
                jy.setSortno(i);
                jy.setYear(dt.substring(0,4));
                jy.setDt(dt);
                jy.setYewuxian_name(row.getCell(0).getStringCellValue()==null?"":
                        row.getCell(0).getStringCellValue());// 第一列数据
                jy.setYewuxian_id(row.getCell(1).getStringCellValue()==null?"":
                        row.getCell(1).getStringCellValue());
                jy.setDim_name(row.getCell(2).getStringCellValue()==null?"":
                        row.getCell(2).getStringCellValue());
                jy.setDim_type(row.getCell(3).getStringCellValue()==null?"":
                        row.getCell(3).getStringCellValue());
                jy.setPerson_name(row.getCell(4).getStringCellValue()==null?"":
                        row.getCell(4).getStringCellValue());

                jy.setAll_shouru(getTextUtil(row,5));
                jy.setAll_lirun(getTextUtil(row,6));
                jy.setAll_chuhuo(getTextUtil(row,7));
                jy.setAll_huikuan(getTextUtil(row,8));

                jy.setWeek1_shouru(getTextUtil(row,9));
                jy.setWeek1_lirun(getTextUtil(row,10));
                jy.setWeek1_chuhuo(getTextUtil(row,11));
                jy.setWeek1_huikuan(getTextUtil(row,12));

                jy.setWeek2_shouru(getTextUtil(row,13));
                jy.setWeek2_lirun(getTextUtil(row,14));
                jy.setWeek2_chuhuo(getTextUtil(row,15));
                jy.setWeek2_huikuan(getTextUtil(row,16));

                jy.setWeek3_shouru(getTextUtil(row,17));
                jy.setWeek3_lirun(getTextUtil(row,18));
                jy.setWeek3_chuhuo(getTextUtil(row,19));
                jy.setWeek3_huikuan(getTextUtil(row,20));

                jy.setWeek4_shouru(getTextUtil(row,21));
                jy.setWeek4_lirun(getTextUtil(row,22));
                jy.setWeek4_chuhuo(getTextUtil(row,23));
                jy.setWeek4_huikuan(getTextUtil(row,24));

                jy.setWeek5_shouru(getTextUtil(row,25));
                jy.setWeek5_lirun(getTextUtil(row,26));
                jy.setWeek5_chuhuo(getTextUtil(row,27));
                jy.setWeek5_huikuan(getTextUtil(row,28));

                jy.setWeek6_shouru(getTextUtil(row,29));
                jy.setWeek6_lirun(getTextUtil(row,30));
                jy.setWeek6_chuhuo(getTextUtil(row,31));
                jy.setWeek6_huikuan(getTextUtil(row,32));

                try{
                    jiashicangYusuanluruService.save(jy);
                }catch (Exception e){
                    e.printStackTrace();
                }

                }
            jy = new JiashicangYusuanluru();
            jy.setDt(dt);
            List<JiashicangYusuanluru> ls  = jiashicangYusuanluruService.list(jy);
            return ls;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public String getTextUtil(Row row,int i){
        if(row.getCell(i)!=null){
            row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
            if(row.getCell(i).getStringCellValue().toString().trim().equals("")){
                return  "0";
            }
            return row.getCell(i).getStringCellValue()==null?"0":
                    String.format("%.2f ",Double.valueOf(row.getCell(i).getStringCellValue().toString()));
        }
        return  "0";
    }

    //新新预算目标录入查询
    @RequestMapping("/yusuanluruselectbydt")
    @ResponseBody
    public List<JiashicangYusuanluru> yusuanluruselectbydt(@RequestParam("dt") String dt, HttpServletRequest request,
                                             HttpServletResponse response) {

        try{
            JiashicangYusuanluru jy = null;
            jy = new JiashicangYusuanluru();
            jy.setDt(dt);
            return jiashicangYusuanluruService.list(jy);
        }catch (Exception e){
            e.printStackTrace();
        }
    return null;
    }

    //城市计划查询
    @RequestMapping("/cityplanselectbydt")
    @ResponseBody
    public List<StarVeinCityPlan> cityplanselectbydt(@RequestParam("dt") String dt, HttpServletRequest request,
                                             HttpServletResponse response) {

        try{
            StarVeinCityPlan jy = null;
            jy = new StarVeinCityPlan();
            jy.setDt(dt);
            return starVeinCityPlanService.list(jy);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //新城市计划导入
    @RequestMapping("/cityplanimport")
    @ResponseBody
    public List<StarVeinCityPlan> cityplanimport(@RequestParam("file") MultipartFile file, @RequestParam("dt") String dt,
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
            for (Row row : sheetAt) {
                if (row.getPhysicalNumberOfCells()!=9){
                    return null;
                }
            }
            int i = 1;
            StarVeinCityPlan jy = null;
            try{
                if (dt!=null&&!"".equals(dt)){
                jy = new StarVeinCityPlan();
                jy.setDt(dt);
                starVeinCityPlanService.delete(jy);
                }
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            // 3.循环读取某一行
            for (Row row : sheetAt) {
                System.out.println("读取excel第"+ i +"行");
//                System.out.println("本行列数为："+row.getPhysicalNumberOfCells());
                i++;
                if (i<=3){
                    continue;
                }
                String stringCellValue = null;
                jy = new StarVeinCityPlan();
                jy.setSortno(i);
                jy.setYear(dt.substring(0,4));
                jy.setDt(dt);
                jy.setRegion_name(row.getCell(0).getStringCellValue()==null?"":
                        row.getCell(0).getStringCellValue());
                jy.setRegion_id(row.getCell(1).getStringCellValue()==null?"":
                        row.getCell(1).getStringCellValue());
                jy.setYewuxian_name(row.getCell(2).getStringCellValue()==null?"":
                        row.getCell(2).getStringCellValue());
                jy.setYewuxian_id(row.getCell(3).getStringCellValue()==null?"":
                        row.getCell(3).getStringCellValue());
                jy.setAll_shouru(getTextUtil(row,4));
                jy.setAll_lirun(getTextUtil(row,5));
                jy.setAll_chuhuo(getTextUtil(row,6));
                jy.setAll_huikuan(getTextUtil(row,7));
                jy.setAll_gsv(getTextUtil(row,8));

                try{
                    starVeinCityPlanService.save(jy);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            jy = new StarVeinCityPlan();
            jy.setDt(dt);
            List<StarVeinCityPlan> ls  = starVeinCityPlanService.list(jy);
            return ls;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
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

    //初始页面查询
    @RequestMapping("/budgetkeyboarder")
    public ModelAndView budgetkeyboarder(BugetReport bugetReport) {
        ModelAndView modelAndView =initModelAndView();
        modelAndView.addObject("data",bugetReportService.list(bugetReport));
        modelAndView.setViewName("budgetkeyboarder");
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.预算目标录入);
        return modelAndView;
    }

    //预算目标录入新
    @RequestMapping("/budgetkeyboarder_new")
    public ModelAndView budgetkeyboarderNew(BugetReport bugetReport) {
        ModelAndView modelAndView =initModelAndView();
        modelAndView.addObject("data",bugetReportService.list(bugetReport));
        modelAndView.setViewName("budgetkeyboarder_new");
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.预算目标录入新);
        return modelAndView;
    }

    //查询页面
    @RequestMapping(value = "/budgetkeyboarderlist",method = RequestMethod.GET)
    @ResponseBody
    public Result budgetkeyboarderlist(BugetReport bugetReport) {
        try {
            //判断查询是否是合计
            if(StringUtils.isNotBlank(bugetReport.getSecond_format_total())){
                String[] temp = bugetReport.getSecond_format_total().split(",");
                List<String> second_format_list = new ArrayList<String>();
                for (String second_format:temp) {
                    second_format_list.add(second_format);
                }
                bugetReport.setSecond_format_totalList(second_format_list);
                return Result.success(bugetReportService.getTotal(bugetReport));
            }else {
                return Result.success(bugetReportService.list(bugetReport));
            }
        }catch (Exception ex){
            logger.info("预算目标录入错误-原因:"+ex.getMessage());
            return Result.failed(1,"查询异常请联系管理员!:"+ex.getMessage());
        }
    }

    //保存&更新
    @RequestMapping(value = "/budgetkeyboarderSave",method = RequestMethod.POST)
    @ResponseBody
    public Result budgetkeyboarderUpdate(String bugetReportJson) {
        try {
            if(StringUtils.isBlank(bugetReportJson)){
                return Result.failed(1,"保存或更新不能为空,请联系管理员!");
            }
            List<BugetReport> bugetReports = JsonUtil.JsonStr2JavaList(bugetReportJson, BugetReport.class);
            BugetReport bugetReport = new BugetReport();
            if(bugetReports != null && bugetReports.size()>0){
                bugetReport = bugetReports.get(0);
            }
            if(bugetReports != null && bugetReports.size()>0) {
                for (int i = 0; i < bugetReports.size(); i++) {
                    if (StringUtils.isNotBlank(bugetReports.get(i).getId())) {
                        logger.info("========预算目标录入更新开始-" + "id:" + bugetReports.get(i).getId());
                        bugetReports.get(i).setUpdate_dt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        bugetReportService.update(bugetReports.get(i));
                        logger.info("========预算目标录入更新结束-" + "id:" + bugetReports.get(i).getId());
                    } else {
                        logger.info("========预算目标录入新增开始-" + "id:" + bugetReports.get(i).getId());
                        Integer week = bugetReports.get(i).getWeek();
                        if (week != 0) {
                            bugetReports.get(i).setGsv_month(BigDecimal.ZERO);
                            bugetReports.get(i).setMaoli_month(BigDecimal.ZERO);
                            bugetReports.get(i).setShouru_month(BigDecimal.ZERO);
                        }
                        bugetReports.get(i).setData_dt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        bugetReportService.save(bugetReports.get(i));
                    }
                }
            }
            return Result.success(bugetReportService.list(bugetReport));
        }catch (Exception ex){
            logger.info("预算目标录入保存或更新错误-原因:"+ex.getMessage());
            return Result.failed(1,"保存或更新异常请联系管理员!:"+ex.getMessage());
        }
    }
}
