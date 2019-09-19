package com.qding.bigdata.ds.controller;


import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.model.DsMaidianPage;
import com.qding.bigdata.ds.model.DsMaidianParam;
import com.qding.bigdata.ds.model.DsMaidianSkip;
import com.qding.bigdata.ds.model.Product;
import com.qding.bigdata.ds.service.MaidianSkipService;
import com.qding.bigdata.ds.service.ProductService;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.ExportExcelUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

@RequestMapping("/buriedSkip")
@RestController
public class BuriedSkipMetaListController extends BasicController {

    @Autowired
    MaidianSkipService skipService;

    @Autowired
    ProductService productService;

    @RequestMapping("query")
    public PageInfo<DsMaidianSkip> query(DsMaidianSkip param, Integer start, Integer length){
        return skipService.list(param, start/length+1, length);
    }

    @RequestMapping("saveOrUpdate")
    public Result saveOrUpdate(DsMaidianSkip param){
        if(param.getId() == null){
            try {
                skipService.add(param);
            } catch (Exception e) {
                return Result.failed(500, e.getMessage());
            }
        }else{
            try {
                skipService.update(param);
            } catch (Exception e) {
                return Result.failed(500, e.getMessage());
            }
        }

        return Result.success();
    }

    @RequestMapping("export")
    public void export(DsMaidianSkip param, HttpServletResponse response) throws Exception {
        Map<Long, String> productMap = productService.getAll().stream().collect(Collectors.toMap(item -> item.getId(), item -> item.getName()));
        response.setContentType("application/csv;charset=utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xlsx");
        OutputStream os = response.getOutputStream();
        PageInfo<DsMaidianSkip> pagePageInfo = skipService.list(param, null, null);
        List<Object[]> resultList = new ArrayList<>();
        for (DsMaidianSkip dsMaidianPage : pagePageInfo.getList()) {
            Object[] item = new Object[7];
            item[0] = dsMaidianPage.getId();
            item[1] = dsMaidianPage.getSkNo();
            item[2] = dsMaidianPage.getAction();
            item[3] = dsMaidianPage.getPageCode();
            item[4] = dsMaidianPage.getPageName();
            item[5] = productMap.get(dsMaidianPage.getProductId());
            item[6] = dsMaidianPage.getStatus();
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
    public Result buriedPageImport(MultipartFile excel) throws IOException, InvalidFormatException {
        Workbook wookbook =  WorkbookFactory.create(excel.getInputStream());
        Sheet sheet = wookbook.getSheetAt(0);
        List<DsMaidianSkip> maidianSkipList = new ArrayList<>();
        int totalRowNum = sheet.getLastRowNum();
        List<Product> productList = productService.getAll();
        for(int i = 1 ; i <= totalRowNum ; i++)
        {
            DsMaidianSkip maidianSkip = new DsMaidianSkip();

            Row row = sheet.getRow(i);
            maidianSkip.setSkNo(new Double(ExportExcelUtil.getCellValue(row.getCell(0))).intValue());
            maidianSkip.setAction(ExportExcelUtil.getCellValue(row.getCell(1)));
            maidianSkip.setPageCode(ExportExcelUtil.getCellValue(row.getCell(2)));
            maidianSkip.setPageName(ExportExcelUtil.getCellValue(row.getCell(3)));

            for(int j=0; j<productList.size(); j++){
                if(productList.get(j).getName().equals(ExportExcelUtil.getCellValue(row.getCell(4)))){
                    maidianSkip.setProductId(productList.get(j).getId());
                    break;
                }
            }
            maidianSkip.setStatus(new Double(ExportExcelUtil.getCellValue(row.getCell(5))).intValue());

            maidianSkipList.add(maidianSkip);
        }

        try {
            skipService.batchInsert(maidianSkipList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed(500, e.getMessage());
        }

        return Result.success();
    }

}
