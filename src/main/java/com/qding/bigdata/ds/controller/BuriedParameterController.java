package com.qding.bigdata.ds.controller;

import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.MaidianParamService;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.ExportExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

@Controller
public class BuriedParameterController extends BasicController {

    @Autowired
    MaidianParamService paramService;

    @PostMapping(value = "paramQuery")
    @ResponseBody
    public PageInfo<DsMaidianParam> paramQuery(DsMaidianParam param, Integer start, Integer length){
        PageInfo<DsMaidianParam> list = paramService.list(param, start/length+1, length);
        return list;
    }
    @PostMapping(value = "paramSaveOrUpate")
    @ResponseBody
    public Result paramSaveOrUpate(DsMaidianParam param){
        Map<String,Object> ret = new HashMap<>();
        try {
            if(null == param.getId()){
                paramService.add(param);
            }else{
                paramService.update(param);
            }
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return Result.failed(501, "参数标识或者参数缩写必须唯一");
            }else{
                return Result.failed(501, e.getMessage());
            }
        }

       return Result.success();
    }

    @RequestMapping("paramExport")
    public void export(DsMaidianParam param, HttpServletResponse response) throws Exception {
        response.setContentType("application/csv;charset=utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xlsx");
        OutputStream os = response.getOutputStream();
        PageInfo<DsMaidianParam> paramPageInfo = paramService.list(param, null, null);
        List<Object[]> resultList = new ArrayList<>();
        for (DsMaidianParam maidianParam : paramPageInfo.getList()) {
            Object[] item = new Object[6];
            item[0] = maidianParam.getId();
            item[1] = maidianParam.getCode();
            item[2] = maidianParam.getSimpleCode();
            item[3] = maidianParam.getName();
            item[4] = maidianParam.getRemark();
            item[5] = maidianParam.getStatus();
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


    @ResponseBody
    @RequestMapping("paramImport")
    public Result paramImport(MultipartFile excel) throws IOException, InvalidFormatException {
        Workbook wookbook =  WorkbookFactory.create(excel.getInputStream());
        Sheet sheet = wookbook.getSheetAt(0);
        List<DsMaidianParam> maidianParamList = new ArrayList<>();
        int totalRowNum = sheet.getLastRowNum();
        for(int i = 1 ; i <= totalRowNum ; i++)
        {
            DsMaidianParam param = new DsMaidianParam();
            //获得第i行对象
            Row row = sheet.getRow(i);
            param.setCode(ExportExcelUtil.getCellValue(row.getCell(0)));
            param.setSimpleCode(ExportExcelUtil.getCellValue(row.getCell(1)));
            param.setName(ExportExcelUtil.getCellValue(row.getCell(2)));
            param.setRemark(ExportExcelUtil.getCellValue(row.getCell(3)));
            param.setStatus(new Double(ExportExcelUtil.getCellValue(row.getCell(4))).intValue());

            maidianParamList.add(param);
        }
        try {
            paramService.batchInsert(maidianParamList);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failed(500, e.getMessage());
        }


        return Result.success();
    }

/*    public List<DsMaidianParam> queryByPageId(String pageId){
        return paramService.queryByPageId(pageId);
    }*/

    @ResponseBody
    @RequestMapping("queryByPageIdOrEventId")
    public Result queryByEventId(String code, String action){
        List<DsMaidianParam> params;
        if("page".equals(action)) {
            params = paramService.queryByPageId(code);
        }else{
            params = paramService.queryByEventId(code);
        }

        return Result.success(params);
    }
}
