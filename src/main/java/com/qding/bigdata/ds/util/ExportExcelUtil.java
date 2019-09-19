package com.qding.bigdata.ds.util;


import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.*;

import com.qding.bigdata.ds.model.ExportParam;
import com.qding.bigdata.ds.model.ExportTitle;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * @author yanpf
 */
public class ExportExcelUtil {

    public static SXSSFWorkbook getWorkbook(ExportParam param, String sheetName, List datas) throws Exception {
        List<Object[]> dataList = new ArrayList<Object[]>();
        int size = param.getTitles().size();
        List<Map.Entry> entryList = new ArrayList<Map.Entry>();
        for(Map.Entry e : param.getTitles().entrySet()){
            entryList.add(e);
        }
        Collections.sort(entryList, new Comparator<Map.Entry>() {
            @Override
            public int compare(Map.Entry o1, Map.Entry o2) {
                return ((ExportTitle)o1.getValue()).compareTo((ExportTitle)o2.getValue());
            }
        });
        Object[] obj = new Object[size];
        int i = 0;
        for (Map.Entry<String, ExportTitle> entry : entryList) {
            obj[i ++] = entry.getValue().getName();
        }
        dataList.add(obj);
        for (Object data : datas) {
            i = 0;
            obj = new Object[entryList.size()];
            for (Map.Entry<String, ExportTitle> entry : entryList) {
                try {
                    Field field =getDeclaredField(entry.getKey(), data.getClass());
                    field.setAccessible(true);
                    Object value = field.get(data);
                    obj[i ++] = value;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            dataList.add(obj);
        }
        return ExportExcelUtil.writeNewExcel(sheetName, dataList);
    }

	public static  SXSSFWorkbook writeNewExcel( String sheetName, List<Object[]> dataList) throws Exception {

		// 声明一个工作薄
        SXSSFWorkbook wb = new SXSSFWorkbook();
		// 给单子名称一个长度
		// 生成一个样式
        CellStyle style = wb.createCellStyle();
		// 创建第一行（也可以称为表头）
		Row row =null;
		// 样式字体居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		int lineNum=0;
		Sheet sheet = wb.createSheet(sheetName);
		for(int i=0;i<dataList.size();i++){
			row = sheet.createRow(lineNum );
			for(int j=0;j<dataList.get(i).length;j++){
				row.createCell(j).setCellValue(String.valueOf(dataList.get(i)[j]));
			}
		
			lineNum++;

		}

		return wb;
	}
	public static  void writeNewCsv( OutputStreamWriter writer,List<Object[]> dataList) throws Exception {
		if(dataList.isEmpty()){
			return ;
		}
		int colLenth=dataList.get(0).length;
		for(int i=0;i<dataList.size();i++){
			for(int j=0;j<colLenth;j++){
				  writer.append(String.valueOf(dataList.get(i)[j]));
				  writer.append(",");
			}
			if(i<dataList.size()-1){
				  writer.append("\n");
			}
		}
		
	}

	private static Field getDeclaredField(String name, Class clazz){
        Stack<Class> stack = new Stack<Class>();
        Map<String, Field> fieldMap = new HashMap<String, Field>();
        Class tmp = clazz;
        while (tmp != null){
            stack.push(tmp);
            tmp = tmp.getSuperclass();
        }
        while(!stack.isEmpty()){
            tmp = stack.pop();
            Field[] declaredFields = tmp.getDeclaredFields();
            for (Field field : declaredFields) {
                fieldMap.put(field.getName(), field);
            }
        }

        return fieldMap.get(name);
    }


    public static String getCellValue(Cell cell){
	    if(cell == null){
	        return null;
        }
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
        }

        return cell.getStringCellValue();
    }

}