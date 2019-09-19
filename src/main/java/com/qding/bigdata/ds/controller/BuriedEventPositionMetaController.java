package com.qding.bigdata.ds.controller;

import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.model.DsMaidianEventPageRelationship;
import com.qding.bigdata.ds.model.DsMaidianParam;
import com.qding.bigdata.ds.model.Product;
import com.qding.bigdata.ds.service.MaidianEventPageRelationService;
import com.qding.bigdata.ds.service.ProductService;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.ExportExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanpf
 * @date 2019/3/20 17:18
 * @description
 */
@RequestMapping("buriedEventPosition")
@RestController
public class BuriedEventPositionMetaController extends BasicController {

    @Autowired
    MaidianEventPageRelationService relationService;

    @Autowired
    ProductService productService;

    @RequestMapping("query")
    public PageInfo<DsMaidianEventPageRelationship> query(DsMaidianEventPageRelationship param, Integer start, Integer length){
        PageInfo<DsMaidianEventPageRelationship> pageInfo = relationService.list(param, start/length+1, length);
        Map<Long, String> productMap = productService.getAll().stream().collect(Collectors.toMap(item -> item.getId(), item -> item.getName()));
        pageInfo.getList().forEach(item -> item.setProductName(productMap.get(item.getProductId())));
        return pageInfo;
    }

    @RequestMapping("saveOrUpdate")
    public Result saveOrUpdate(DsMaidianEventPageRelationship param){
        if(param.getId() == null){
            relationService.add(param);
        }else{
            relationService.update(param);
        }

        return Result.success();
    }

    @RequestMapping("export")
    public void export(DsMaidianEventPageRelationship param, HttpServletResponse response) throws Exception {
        Map<Long, String> productMap = productService.getAll().stream().collect(Collectors.toMap(item -> item.getId(), item -> item.getName()));
        response.setContentType("application/csv;charset=utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xlsx");
        OutputStream os = response.getOutputStream();
        PageInfo<DsMaidianEventPageRelationship> eventPageInfo = relationService.list(param, null, null);
        eventPageInfo.getList().forEach(item -> item.setProductName(productMap.get(item.getProductId())));
        List<Object[]> resultList = new ArrayList<>();
        for (DsMaidianEventPageRelationship maidianEvent : eventPageInfo.getList()) {
            Object[] item = new Object[11];
            item[0] = maidianEvent.getId();
            item[1] = maidianEvent.getEventCode();
            item[2] = maidianEvent.getEventName();
            item[3] = maidianEvent.getProductName();
            item[4] = maidianEvent.getPageCode();
            item[5] = maidianEvent.getPageName();
            item[6] = maidianEvent.getAreaCode();
            item[7] = maidianEvent.getAreaName();
            item[8] = maidianEvent.getPositionId();
            item[9] = maidianEvent.getPositionName();
            item[10] = maidianEvent.getStatus();
            resultList.add(item);
        }
        OutputStreamWriter writer = new OutputStreamWriter(os,"gbk");
        SXSSFWorkbook workbook = ExportExcelUtil.writeNewExcel("sheet1", resultList);
        workbook.write(os);
        writer.flush();
        os.flush();
        writer.close();
        os.close();
    }

    @RequestMapping("import")
    public Result buriedEventPositionImport(MultipartFile excel) throws IOException, InvalidFormatException {
        Workbook wookbook =  WorkbookFactory.create(excel.getInputStream());
        Sheet sheet = wookbook.getSheetAt(0);
        List<DsMaidianEventPageRelationship> maidianParamList = new ArrayList<>();
        int totalRowNum = sheet.getLastRowNum();
        List<Product> productList = productService.getAll();
        for(int i = 1 ; i <= totalRowNum ; i++)
        {
            DsMaidianEventPageRelationship param = new DsMaidianEventPageRelationship();
            //获得第i行对象
            Row row = sheet.getRow(i);
            param.setEventCode(ExportExcelUtil.getCellValue(row.getCell(0)));
            param.setEventName(ExportExcelUtil.getCellValue(row.getCell(1)));
            for(int j=0; j<productList.size(); j++){
                if(productList.get(j).getName().equals(ExportExcelUtil.getCellValue(row.getCell(2)))){
                    param.setProductId(productList.get(j).getId());
                    break;
                }
            }

            param.setPageCode(ExportExcelUtil.getCellValue(row.getCell(3)));
            param.setPageName(ExportExcelUtil.getCellValue(row.getCell(4)));
            param.setAreaCode(ExportExcelUtil.getCellValue(row.getCell(5)));
            param.setAreaName(ExportExcelUtil.getCellValue(row.getCell(6)));
            param.setPositionId(ExportExcelUtil.getCellValue(row.getCell(7)));
            param.setPositionName(ExportExcelUtil.getCellValue(row.getCell(8)));
            param.setStatus(new Double(ExportExcelUtil.getCellValue(row.getCell(9))).intValue());

            maidianParamList.add(param);
        }
        try {
            relationService.batchInsert(maidianParamList);
        }catch (Exception e){
            e.printStackTrace();
            return  Result.failed(500, e.getMessage());
        }


        return Result.success();
    }

}
