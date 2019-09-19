package com.qding.bigdata.ds.controller;

import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.BusinessService;
import com.qding.bigdata.ds.service.MaidianEventService;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanpf
 * @date 2019/3/20 17:18
 * @description
 */
@RequestMapping("/buriedEvent")
@RestController
public class BuriedEvenTrackMetaController extends BasicController {

    @Autowired
    MaidianEventService maidianEventService;

    @Autowired
    ProductService productService;

    @Autowired
    BusinessService businessService;

    @RequestMapping("query")
    public PageInfo<DsMaidianEvent> query(DsMaidianEvent param, Integer start, Integer length){
        return maidianEventService.list(param, start/length+1, length);
    }

    @RequestMapping("findByCode")
    public Result findByCode(String code){
        DsMaidianEvent event = maidianEventService.findByCode(code);
        return Result.success(event);
    }

    @RequestMapping("saveOrUpdate")
    public Result saveOrUpdate(DsMaidianEvent param){
        if(param.getId() == null){
            maidianEventService.add(param);
        }else{
            maidianEventService.update(param);
        }

        return Result.success();
    }

    @RequestMapping("export")
    public void export(DsMaidianEvent param, HttpServletResponse response) throws Exception {
        Map<Long, String> businessMap = businessService.getAll().stream().collect(Collectors.toMap(Business::getId, Business::getName));
        Map<Long, String> productMap = productService.getAll().stream().collect(Collectors.toMap(Product::getId, Product::getName));
        response.setContentType("application/csv;charset=utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xlsx");
        OutputStream os = response.getOutputStream();
        PageInfo<DsMaidianEvent> eventPageInfo = maidianEventService.list(param, null, null);
        List<Object[]> resultList = new ArrayList<>();
        for (DsMaidianEvent maidianEvent : eventPageInfo.getList()) {
            Object[] item = new Object[7];
            item[0] = maidianEvent.getId();
            item[1] = maidianEvent.getCode();
            item[2] = maidianEvent.getName();
            item[3] = maidianEvent.getParamList().stream().map(DsMaidianParam::getName).collect(Collectors.joining(","));
            item[4] = businessMap.get(maidianEvent.getBusinessId());
            item[5] = productMap.get(maidianEvent.getProductId());
            item[6] = maidianEvent.getStatus();
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
    public Result eventImport(MultipartFile excel) throws IOException, InvalidFormatException {
        Workbook wookbook =  WorkbookFactory.create(excel.getInputStream());
        Sheet sheet = wookbook.getSheetAt(0);
        List<DsMaidianEvent> maidianEventList = new ArrayList<>();
        int totalRowNum = sheet.getLastRowNum();
        List<Business> businessList = businessService.getAll();
        List<Product> productList = productService.getAll();
        for(int i=1; i<totalRowNum; i++){
            DsMaidianEvent maidianEvent = new DsMaidianEvent();
            maidianEvent.setParamList(new ArrayList<>());
            Row row = sheet.getRow(i);
            maidianEvent.setCode(ExportExcelUtil.getCellValue(row.getCell(0)));
            maidianEvent.setName(ExportExcelUtil.getCellValue(row.getCell(1)));
            String paramStr = ExportExcelUtil.getCellValue(row.getCell(2));
            if(!StringUtils.isEmpty(paramStr)){
                String[] paramList = paramStr.split(",");
                DsMaidianParam param = new DsMaidianParam();
                for(int j=0; j<paramList.length; j++){
                    param.setCode(paramList[j]);
                    maidianEvent.getParamList().add(param);
                }


            }
            for(int j=0; j<businessList.size(); j++){
                if(businessList.get(j).getName().equals(ExportExcelUtil.getCellValue(row.getCell(3)))){
                    maidianEvent.setBusinessId(businessList.get(j).getId());
                    break;
                }
            }

            for(int j=0; j<productList.size(); j++){
                if(productList.get(j).getName().equals(ExportExcelUtil.getCellValue(row.getCell(4)))){
                    maidianEvent.setProductId(productList.get(j).getId());
                    break;
                }
            }
            maidianEvent.setStatus(new Double(ExportExcelUtil.getCellValue(row.getCell(5))).intValue());

            maidianEventList.add(maidianEvent);
        }
        try {
            maidianEventService.batchInsert(maidianEventList);
        }catch (Exception e){
            log.error("{}", e);
            return Result.failed(500, e.getMessage());
        }


        return Result.success();
    }


    @RequestMapping("getEventInfoByBusinessType")
    public Result<Map> getEventInfoByBusinessType(Long businessType){
        List<DsMaidianEvent> maidianEventList  = maidianEventService.getEventInfoByProductId(businessType);
        if(maidianEventList != null){
            return Result.success(maidianEventList.stream().map(a -> {
                Map<String, String> map = new HashMap<>();
                map.put("eventId", a.getCode());
                map.put("eventName", a.getName());
                return map;
            }).collect(Collectors.toList()));
        }

        return Result.success();
    }
}
