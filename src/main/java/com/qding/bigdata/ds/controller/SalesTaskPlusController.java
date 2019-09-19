package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.StarVeinSalesTargetImport;
import com.qding.bigdata.ds.service.StarVeinSalesTargetService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Administrator on 2019/3/12 0012.
 */
@Controller
public class SalesTaskPlusController extends BasicController{


    public static Logger logger = LoggerFactory.getLogger(SalesTaskPlusController.class);

    @Autowired
    private StarVeinSalesTargetService starVeinSalesTargetService;

    @RequestMapping("/salestaskexport")
    @ResponseBody
    public void salestaskexportExcel(@RequestParam("dt") String dt,HttpServletRequest request, HttpServletResponse response) {

        String classpath  = this.getClass().getResource("/").getPath().replaceFirst("/", "");
        String webappRoot = classpath.replaceAll("WEB-INF/classes/", "");
        System.out.println(webappRoot+"document/salestaskexport.xlsx");
        File newFile = new File("/"+webappRoot+"document/salestaskexport.xlsx");

        // 新文件写入数据，并下载*****************************************************
        InputStream is = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
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
                XSSFRow row = sheet.getRow(2);
                if (row == null) {
                    row = sheet.createRow(2);
                }
                XSSFCell cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }

                StarVeinSalesTargetImport jy = null;
                jy = new StarVeinSalesTargetImport();
                jy.setYear(dt);
                List<StarVeinSalesTargetImport> ls = starVeinSalesTargetService.list(jy);

                for (int m = 0; m < ls.size(); m++) {
                    jy = new StarVeinSalesTargetImport();
                    jy = ls.get(m);
                    row = sheet.createRow((int) m + 1);
                    createCell(row,0,cell,jy.getCity()==null?"":jy.getCity());
                    createCell(row,1,cell,jy.getPersion()==null?"":jy.getPersion());
                    createCell(row,2,cell,jy.getJan()==null?"0":jy.getJan());
                    createCell(row,3,cell,jy.getFeb()==null?"0":jy.getFeb());
                    createCell(row,4,cell,jy.getMar()==null?"0":jy.getMar());
                    createCell(row,5,cell,jy.getApril()==null?"0":jy.getApril());
                    createCell(row,6,cell,jy.getMay()==null?"0":jy.getMay());
                    createCell(row,7,cell,jy.getJune()==null?"0":jy.getJune());
                    createCell(row,8,cell,jy.getJule()==null?"0":jy.getJule());
                    createCell(row,9,cell,jy.getAug()==null?"0":jy.getAug());
                    createCell(row,10,cell,jy.getSept()==null?"0":jy.getSept());
                    createCell(row,11,cell,jy.getOct()==null?"0":jy.getOct());
                    createCell(row,12,cell,jy.getNov()==null?"0":jy.getNov());
                    createCell(row,13,cell,jy.getDec()==null?"0":jy.getDec());
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
                        "销售目标-" + dt + ".xlsx",
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




    //新城市计划导入
    @RequestMapping("/salestaskimport")
    @ResponseBody
    public List<StarVeinSalesTargetImport> salestaskimport(@RequestParam("file") MultipartFile file, @RequestParam("dt") String dt,
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
//                System.out.println("总行数为："+sheetAt.getLastRowNum());
//                if (row.getPhysicalNumberOfCells()!=14){
//                    return null;
//                }
//            }
            int i = 1;
            StarVeinSalesTargetImport jy = null;
            try{
                if (dt!=null&&!"".equals(dt)){
                    jy = new StarVeinSalesTargetImport();
                    jy.setYear(dt);
                    starVeinSalesTargetService.delete(jy);
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
                if (i<=2){
                    continue;
                }
                String stringCellValue = null;
                jy = new StarVeinSalesTargetImport();
                jy.setSortno(i);
                jy.setYear(dt.substring(0,4));

                String city = getTextUtil(row,0);
                if(city.indexOf("北京")!=-1){
                    jy.setCity("北京");
                }else if(city.indexOf("上海")!=-1){
                    jy.setCity("上海");
                }else if(city.indexOf("广州")!=-1){
                    jy.setCity("广州");
                }else if(city.indexOf("成都")!=-1){
                    jy.setCity("成都");
                }else if(city.indexOf("重庆")!=-1){
                    jy.setCity("重庆");
                }else if(city.indexOf("杭州")!=-1){
                    jy.setCity("杭州");
                }else if(city.indexOf("西安")!=-1){
                    jy.setCity("西安");
                }else{
                    jy.setCity(city);
                }

//                jy.setCity(getTextUtil(row,0));
                jy.setPersion(getTextUtil(row,1));

                jy.setJan(getTextUtil(row,2));
                jy.setFeb(getTextUtil(row,3));
                jy.setMar(getTextUtil(row,4));
                jy.setApril(getTextUtil(row,5));
                jy.setMay(getTextUtil(row,6));
                jy.setJune(getTextUtil(row,7));
                jy.setJule(getTextUtil(row,8));
                jy.setAug(getTextUtil(row,9));
                jy.setSept(getTextUtil(row,10));
                jy.setOct(getTextUtil(row,11));
                jy.setNov(getTextUtil(row,12));
                jy.setDec(getTextUtil(row,13));

                try{
                    starVeinSalesTargetService.save(jy);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            jy = new StarVeinSalesTargetImport();
            jy.setYear(dt);
            List<StarVeinSalesTargetImport> ls  = starVeinSalesTargetService.list(jy);
            return ls;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    //城市计划查询
    @RequestMapping("/salestaskselectbydt")
    @ResponseBody
    public List<StarVeinSalesTargetImport> salestaskselectbydt(@RequestParam("dt") String dt, HttpServletRequest request,
                                                              HttpServletResponse response) {

        try{
            StarVeinSalesTargetImport jy = null;
            jy = new StarVeinSalesTargetImport();
            jy.setYear(dt);
            return starVeinSalesTargetService.list(jy);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/salestask")
    public ModelAndView customer(StarVeinSalesTargetImport starVeinSalesTargetImport) {
        ModelAndView modelAndView =initModelAndView();
//        modelAndView.addObject("data",starVeinSalesTargetService.list(starVeinSalesTargetImport));
        modelAndView.setViewName("salestask");
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.销售任务);
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

    public void createCell(XSSFRow row, int i, XSSFCell cell, String value){
        cell = row.createCell(i);
        cell.setCellValue(value);
    }

}
