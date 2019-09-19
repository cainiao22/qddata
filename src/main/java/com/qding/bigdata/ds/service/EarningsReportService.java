package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.EarningsReport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface EarningsReportService {
    Map<String,Object> queryImgInfo(Map<String, String> params);

    void deleteImgInfoById(String id);

    Map<String,Object> upImgInfo(HttpServletRequest request, MultipartFile file, Integer width, Integer height, Double imgSize) throws IOException;

    Map<String,Object> insertEarningsReportInfo(EarningsReport earningsReport, HttpServletRequest request);

    Map<String,Object> queryMonthlyImgInfo(String date, Integer type);

    Map<String,Object> queryThisRowInfoById(String id);
}
