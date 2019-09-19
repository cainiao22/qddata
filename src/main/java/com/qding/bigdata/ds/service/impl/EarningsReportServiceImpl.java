package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.dao.EarningsReportDao;
import com.qding.bigdata.ds.model.EarningsReport;
import com.qding.bigdata.ds.service.EarningsReportService;
import com.qding.bigdata.ds.util.CookieUtil;
import com.qding.bigdata.ds.util.QiniuUtils;
import com.qding.bigdata.ds.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EarningsReportServiceImpl implements EarningsReportService{

    @Value("${upload.imagePath}")
    private String uploadImagePath;


    @Value("${upload.imageUrl}")
    private String imageUrl;

    @Autowired
    private EarningsReportDao earningsReportDao;

    /**
     * 查询
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> queryImgInfo(Map<String, String> map) {
        map.put("currentPage", String.valueOf((Integer.parseInt(map.get("currentPage")) -1 ) * Integer.parseInt(map.get("pageSize"))));
        Map<String, Object> remap = new HashMap<String,Object>();
        try {
            int total = earningsReportDao.queryCount(map);
            List<EarningsReport> list = earningsReportDao.queryImgInfo(map);
            if(null != list && list.size() > 0){
                remap.put("success",true);
                remap.put("data",list);
                remap.put("total",total);
            }
        }catch (Exception e){
            e.printStackTrace();
            remap.put("success",false);
            remap.put("errInfo",e.getMessage());
        }
        return remap;
    }

    /**
     * 删除单个数据
     * @param id
     * @return
     */
    @Override
    public void deleteImgInfoById(String id) {
         earningsReportDao.deleteImgInfoById(id);
    }

    /**
     * 上传图片
     * @return
     */
    @Override
    public Map<String, Object> upImgInfo(HttpServletRequest request, MultipartFile file, Integer width, Integer height, Double imgSize) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();

                try {
                    //判断file不能为空
                    if(null != file){
                        BufferedImage image = ImageIO.read(file.getInputStream());
                        if (image != null) {//如果image=null 表示上传的不是图片格式
                            if(null != width){
                                if(image.getWidth() > width){
                                    map.put("success", false);
                                    map.put("msg", "图片宽度不符合规定!");
                                    return map;
                                }
                            }
                            if(null != height){
                                if(image.getHeight() > height){
                                    map.put("success", false);
                                    map.put("msg", "图片高度不符合规定!");
                                    return map;
                                }
                            }
                            if(null != imgSize){
                                Double c = imgSize * 1024 * 1204;
                                if (file.getSize() > c) {
                                    map.put("success", false);
                                    map.put("msg", "上传图片大小超出限制！");
                                    return map;
                                }
                            }else{
                                if (file.getSize() > 15* 1024 * 1024) {
                                    map.put("success", false);
                                    map.put("msg", "上传图片大小不能超过15M！");
                                    return map;
                                }
                            }
                            //判断类型是否符合
                            String nameHz = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                            if (!isPhoto(nameHz)){
                                map.put("success", false);
                                map.put("msg", "请选择格式为jpeg/jpg/pjpeg/png/x-png/gif的图片!");
                                return map;
                            }
                        }
                            //将文件传入工具类上传文件,返回的是成功上传文件的路径
                            //String imgPath = QiniuUtils.uploadImg2QiNiu(file);
                            File fileLocal = new File( this.uploadImagePath + "/" + UUIDUtil.createId() + "_" + file.getOriginalFilename());
                            if(!fileLocal.getParentFile().exists()){
                                fileLocal.getParentFile().mkdirs();
                            }
                            fileLocal.createNewFile();
                            file.transferTo(fileLocal);
                            //String imgPath = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                            map.put("success",true);
                            map.put("imgPath",imageUrl + "/" + fileLocal.getName());
                    }else{
                        map.put("success",false);
                        map.put("msg","调用失败!文件为空!");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    map.put("success",false);
                    map.put("msg","系统异常");
                    map.put("errMsg",e.getMessage());
                }
        return map;
    }

    /**
     * 新增信息
     * @param earningsReport
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> insertEarningsReportInfo(EarningsReport earningsReport, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String username = CookieUtil.getCookieValue(request, Constant.USERNAME);
            if(null != username){
                earningsReport.setUpname(username);
            }else{
                earningsReport.setUpname("null");
            }
            earningsReport.setUpdateTime(new Date());

            if(!"".equals(earningsReport.getId())){
               int count = earningsReportDao.updateEarningsReportInfoById(earningsReport);
               if(count > 0){
                   map.put("success",true);
               }
            }else{
                earningsReport.setId(UUIDUtil.createId());
                int count = earningsReportDao.insertEarningsReportInfo(earningsReport);
                if(count > 0){
                    map.put("success",true);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg","系统异常!");
            map.put("errMsg",e.getMessage());
            map.put("success",false);
        }
        return map;
    }

    /**
     * 查询月报图片信息
     * @return
     */
    @Override
    public Map<String, Object> queryMonthlyImgInfo(String date, Integer type) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String url = earningsReportDao.queryMonthlyImgInfo(date,type);
            map.put("success",true);
            map.put("url",url);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("errMsg",e.getMessage());
        }
        return map;
    }

    /**
     * 查询回显信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> queryThisRowInfoById(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            EarningsReport earningsReport = earningsReportDao.queryThisRowInfoById(id);
            if(null != earningsReport){
                map.put("earningsReport",earningsReport);
                map.put("success",true);
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("errMsg",e.getMessage());
        }
        return map;
    }

    /**
     * 判断是否是图片 请选择格式为jpeg/jpg/pjpeg/png/x-png/gif的图片!
     * @param photoName
     * @return
     */
    private boolean isPhoto(String photoName){
        String imgExt = "jpg|jpeg|png|bmp|gif|pjpeg|x-png|GIF|JPG|PNG|JPEG|BMP|PJPEG|X-PNG";
        if(imgExt .indexOf(photoName) >= 0){
            return true;
        }
        return false;
    }
}
