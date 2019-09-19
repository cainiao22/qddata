package com.qding.bigdata.ds.controller;

import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.BusinessService;
import com.qding.bigdata.ds.service.MaidianPageService;
import com.qding.bigdata.ds.service.ProductService;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.ExportExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanpf
 * @date 2019/3/20 17:18
 * @description
 */
@Slf4j
@RestController
@RequestMapping("buriedPage")
public class BuriedPageTrackMetaListController extends BasicController {

    @Autowired
    MaidianPageService maidianPageService;

    @Autowired
    ProductService productService;

    @Autowired
    BusinessService businessService;

    @RequestMapping("findByCode")
    public Result findByCode(String code){
        DsMaidianPage page = maidianPageService.findByCode(code);
        return Result.success(page);
    }

    @RequestMapping("query")
    public PageInfo<DsMaidianPage> query(DsMaidianPage param, Integer start, Integer length){
        return maidianPageService.list(param, start/length+1, length);
    }

    @RequestMapping("saveOrUpdate")
    public Result saveOrUpdate(DsMaidianPage param){
        if(param.getId() == null){
            maidianPageService.add(param);
        }else{
            maidianPageService.update(param);
        }

        return Result.success();
    }

    @RequestMapping("export")
    public void export(DsMaidianPage param, HttpServletResponse response) throws Exception {
        Map<Long, String> businessMap = businessService.getAll().stream().collect(Collectors.toMap(item -> item.getId(), item -> item.getName()));
        Map<Long, String> productMap = productService.getAll().stream().collect(Collectors.toMap(item -> item.getId(), item -> item.getName()));
        response.setContentType("application/csv;charset=utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xlsx");
        OutputStream os = response.getOutputStream();
        PageInfo<DsMaidianPage> pagePageInfo = maidianPageService.list(param, null, null);
        List<Object[]> resultList = new ArrayList<>();
        for (DsMaidianPage dsMaidianPage : pagePageInfo.getList()) {
            Object[] item = new Object[7];
            item[0] = dsMaidianPage.getId();
            item[1] = dsMaidianPage.getCode();
            item[2] = dsMaidianPage.getName();
            item[3] = dsMaidianPage.getParamList().stream().map(a -> a.getName()).collect(Collectors.joining(","));
            item[4] = businessMap.get(dsMaidianPage.getBusinessId());
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
        List<DsMaidianPage> maidianPageList = new ArrayList<>();
        int totalRowNum = sheet.getLastRowNum();
        List<Business> businessList = businessService.getAll();
        List<Product> productList = productService.getAll();
        for(int i = 1 ; i <= totalRowNum ; i++)
        {
            DsMaidianPage maidianPage = new DsMaidianPage();
            maidianPage.setParamList(new ArrayList<>());
            //获得第i行对象
            Row row = sheet.getRow(i);
            maidianPage.setCode(ExportExcelUtil.getCellValue(row.getCell(0)));
            maidianPage.setName(ExportExcelUtil.getCellValue(row.getCell(1)));
            String paramStr = ExportExcelUtil.getCellValue(row.getCell(2));
            if(!StringUtils.isEmpty(paramStr)){
                String[] paramList = paramStr.split(",");
                DsMaidianParam param = new DsMaidianParam();
                for(int j=0; j<paramList.length; j++){
                    param.setCode(paramList[j]);
                    maidianPage.getParamList().add(param);
                }
            }
            for(int j=0; j<businessList.size(); j++){
                if(businessList.get(j).getName().equals(ExportExcelUtil.getCellValue(row.getCell(3)))){
                    maidianPage.setBusinessId(businessList.get(j).getId());
                    break;
                }
            }

            for(int j=0; j<productList.size(); j++){
                if(productList.get(j).getName().equals(ExportExcelUtil.getCellValue(row.getCell(4)))){
                    maidianPage.setProductId(productList.get(j).getId());
                    break;
                }
            }
            maidianPage.setStatus(new Double(ExportExcelUtil.getCellValue(row.getCell(5))).intValue());
            maidianPageList.add(maidianPage);
        }

        try {
            maidianPageService.batchInsert(maidianPageList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed(500, e.getMessage());
        }

        return Result.success();
    }

    @RequestMapping("getPageInfoByBusinessType")
    public Result<List<Map>> getPageInfoByBusinessType(Long businessType){
        List<DsMaidianPage> maidianPages = maidianPageService.getPageInfoByProductId(businessType);
        if(maidianPages != null){
            return Result.success(maidianPages.stream().map(a -> {
                Map<String, String> map = new HashMap<>();
                map.put("pageId", a.getCode());
                map.put("pageName", a.getName());
                return map;
            }).collect(Collectors.toList()));
        }

        return Result.success();
    }
}
