package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.StarVeinSalesInfoService;
import com.qding.bigdata.ds.service.StarVeinSalesWlwLhdnkService;
import com.qding.bigdata.ds.service.StarVeinSalesWlwLhgyService;
import com.qding.bigdata.ds.service.StarVeinSalesWlwWtService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
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
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class SalesChanceController extends BasicController {

	public static Logger logger = LoggerFactory.getLogger(SalesChanceController.class);

	@Autowired
	private StarVeinSalesInfoService starVeinSalesInfoService;

	@Autowired
	private StarVeinSalesWlwWtService starVeinSalesWlwWtService;

	@Autowired
	private StarVeinSalesWlwLhgyService starVeinSalesWlwLhgyService;

	@Autowired
	private StarVeinSalesWlwLhdnkService starVeinSalesWlwLhdnkService;

//	  @RequestMapping("/salesChance")
//	  public ModelAndView saleschance(HttpServletRequest request) {
//	    ModelAndView modelAndView =initModelAndView();
//	    modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.销售机会);
//	    return modelAndView;
//
//	  }

	@RequestMapping("/starveinimport")
	@ResponseBody
	public List StarVeinimport(@RequestParam("file") MultipartFile file, @RequestParam("dim") String dim,
												 HttpServletRequest request, HttpServletResponse response) {

	  	if("wlw-wt-pq".equals(dim) || "wlw-wt-wlw".equals(dim)){
			return StarVeinSalesWlwWtImport(file,dim);
		}else if("wlw-gy".equals(dim)){
			return StarVeinSalesWlwLhgyImport(file,dim);
		}else if("wlw-dnk".equals(dim)){
			return StarVeinSalesWlwLhdnkImport(file,dim);
		}else{
			return StarVeinSalesInfoImport(file,dim);
		}

	}

	@RequestMapping("/starveinselect")
	@ResponseBody
	public List starveinselect(@RequestParam("dim") String dim,
									   HttpServletRequest request, HttpServletResponse response) {

		if("wlw-wt-pq".equals(dim) || "wlw-wt-wlw".equals(dim)){
			StarVeinSalesWlwWt jy = null;
			jy = new StarVeinSalesWlwWt();
			jy.setDim_type(dim);
			return starVeinSalesWlwWtService.list(jy);
		}else if("wlw-gy".equals(dim)){
			StarVeinSalesWlwLhgy jy = null;
			jy = new StarVeinSalesWlwLhgy();
			return starVeinSalesWlwLhgyService.list(jy);
		}else if("wlw-dnk".equals(dim)){
			StarVeinSalesWlwLhdnk jy = null;
			jy = new StarVeinSalesWlwLhdnk();
			return starVeinSalesWlwLhdnkService.list(jy);
		}else{
			StarVeinSalesInfo jy = null;
			jy = new StarVeinSalesInfo();
			jy.setDim_type(dim);
			return  starVeinSalesInfoService.list(jy);
		}

	}

	public List StarVeinSalesInfoImport(MultipartFile file,String dim){
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
			int i = 1;
			StarVeinSalesInfo jy = null;
			try{
				jy = new StarVeinSalesInfo();
				jy.setDim_type(dim);
				starVeinSalesInfoService.delete(jy);
			}catch (Exception e){
				e.printStackTrace();
				return null;
			}
			// 3.循环读取某一行
			for (Row row : sheetAt) {
				System.out.println("读取excel第"+ i +"行");
				i++;
				if (i<=2){
					continue;
				}
				String stringCellValue = null;
				jy = new StarVeinSalesInfo();
				jy.setDim_type(dim);
				jy.setCity(getTextUtil(row,0));
				jy.setPerson(getTextUtil(row,1));
				jy.setClient_name(getTextUtil(row,2));
				jy.setPlan_amount(getTextUtil(row,3));
				jy.setPlan_product(getTextUtil(row,4));
				jy.setProject_stage(getTextUtil(row,5));
				try{
					jy.setPlan_date(getDateUtil(row,6));
				}catch (Exception e){
					jy.setPlan_date("时间格式错误");
					e.printStackTrace();
				}
				jy.setPlan_text(getTextUtil(row,7));
				jy.setRemarks(getTextUtil(row,8));

				try{
					starVeinSalesInfoService.save(jy);
				}catch (Exception e){
					e.printStackTrace();
				}

			}
			jy = new StarVeinSalesInfo();
			jy.setDim_type(dim);
			List<StarVeinSalesInfo> ls  = starVeinSalesInfoService.list(jy);
			return ls;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	return null;
	}

	public List StarVeinSalesWlwWtImport(MultipartFile file,String dim){
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
			int i = 1;
			StarVeinSalesWlwWt jy = null;
			try{
				jy = new StarVeinSalesWlwWt();
				jy.setDim_type(dim);
				starVeinSalesWlwWtService.delete(jy);
			}catch (Exception e){
				e.printStackTrace();
				return null;
			}
			// 3.循环读取某一行
			for (Row row : sheetAt) {
				System.out.println("读取excel第"+ i +"行");
				i++;
				if (i<=2){
					continue;
				}
				String stringCellValue = null;
				jy = new StarVeinSalesWlwWt();
				jy.setDim_type(dim);
				jy.setPerson(getTextUtil(row,0));
				jy.setClient_name(getTextUtil(row,1));
				jy.setPlan_amount(getTextUtil(row,2));
				jy.setMain_product(getTextUtil(row,3));
				jy.setProject_stage(getTextUtil(row,4));
				try{
					jy.setPlan_date(getDateUtil(row,5));
				}catch (Exception e){
					jy.setPlan_date("时间格式错误");
					e.printStackTrace();
				}
				jy.setShipment_info(getTextUtil(row,6));
				jy.setRemarks(getTextUtil(row,7));

				try{
					starVeinSalesWlwWtService.save(jy);
				}catch (Exception e){
					e.printStackTrace();
				}

			}
			jy = new StarVeinSalesWlwWt();
			jy.setDim_type(dim);
			List<StarVeinSalesWlwWt> ls  = starVeinSalesWlwWtService.list(jy);
			return ls;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List StarVeinSalesWlwLhgyImport(MultipartFile file,String dim){
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
			int i = 1;
			StarVeinSalesWlwLhgy jy = null;
			try{
				jy = new StarVeinSalesWlwLhgy();
				starVeinSalesWlwLhgyService.delete(jy);
			}catch (Exception e){
				e.printStackTrace();
				return null;
			}
			// 3.循环读取某一行
			for (Row row : sheetAt) {
				System.out.println("读取excel第"+ i +"行");
				i++;
				if (i<=2){
					continue;
				}
				String stringCellValue = null;
				jy = new StarVeinSalesWlwLhgy();
				jy.setCity(getTextUtil(row,0));
				jy.setProject_name(getTextUtil(row,1));
				jy.setConfig_status(getTextUtil(row,2));
				jy.setPlan_amount(getTextUtil(row,3));
				jy.setMain_product(getTextUtil(row,4));
				jy.setProject_stage(getTextUtil(row,5));
				try{
					jy.setPlan_date(getDateUtil(row,6));
				}catch (Exception e){
					jy.setPlan_date("时间格式错误");
					e.printStackTrace();
				}
				jy.setShipment_info(getTextUtil(row,7));
				jy.setRemarks(getTextUtil(row,8));

				try{
					starVeinSalesWlwLhgyService.save(jy);
				}catch (Exception e){
					e.printStackTrace();
				}

			}
			jy = new StarVeinSalesWlwLhgy();
			List<StarVeinSalesWlwLhgy> ls  = starVeinSalesWlwLhgyService.list(jy);
			return ls;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List StarVeinSalesWlwLhdnkImport(MultipartFile file,String dim){
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
			int i = 1;
			StarVeinSalesWlwLhdnk jy = null;
			try{
				jy = new StarVeinSalesWlwLhdnk();
				starVeinSalesWlwLhdnkService.delete(jy);
			}catch (Exception e){
				e.printStackTrace();
				return null;
			}
			// 3.循环读取某一行
			for (Row row : sheetAt) {
				System.out.println("读取excel第"+ i +"行");
				i++;
				if (i<=2){
					continue;
				}
				String stringCellValue = null;
				jy = new StarVeinSalesWlwLhdnk();
				jy.setQujian(getTextUtil(row,0));
				jy.setPlan_amount(getTextUtil(row,1));
				jy.setMain_product(getTextUtil(row,2));
				jy.setProject_stage(getTextUtil(row,3));
				try{
					jy.setPlan_date(getDateUtil(row,4));
				}catch (Exception e){
					jy.setPlan_date("时间格式错误");
					e.printStackTrace();
				}
				jy.setRemarks(getTextUtil(row,5));

				try{
					starVeinSalesWlwLhdnkService.save(jy);
				}catch (Exception e){
					e.printStackTrace();
				}

			}
			jy = new StarVeinSalesWlwLhdnk();
			List<StarVeinSalesWlwLhdnk> ls  = starVeinSalesWlwLhdnkService.list(jy);
			return ls;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getTextUtil(Row row,int i){
		try {
			if(row.getCell(i)!=null){
				row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
				return row.getCell(i).getStringCellValue()==null?"":
						row.getCell(i).getStringCellValue().toString();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return  "";
	}

	public String getDateUtil(Row row,int i){
		Date date = new Date(String.valueOf(row.getCell(6).getDateCellValue()));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return  simpleDateFormat.format(date);
	}

}
