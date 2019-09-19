package com.qding.bigdata.ds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.MetaField;
import com.qding.bigdata.ds.model.MetaTable;
import com.qding.bigdata.ds.service.MetaFieldService;
import com.qding.bigdata.ds.service.MetaTableService;
import com.qding.bigdata.ds.util.CommonUtil;
import com.qding.bigdata.ds.util.PageUtil;

@Controller
public class MetaFieldController extends BasicController {

	@Autowired
	private MetaFieldService metaFieldService;
	@Autowired
	private MetaTableService metaTableService;

	@RequestMapping("/metaFieldSave")
	public String metaTableSave(MetaField metaFieldParam) {
		if(metaFieldParam.getSourceTables()==null){
			metaFieldParam.setSourceTables("");
		}
		if (CommonUtil.isEmpty(metaFieldParam.getId())) {
			metaFieldService.save(metaFieldParam);
		} else {
			metaFieldService.update(metaFieldParam);
		}
		return "redirect:./metaFieldList?tableId=" + metaFieldParam.getTableId();
	}


	@RequestMapping("/metaFieldDelete")
	public String metaTabledelete(MetaField metaFieldParam) {
		metaFieldService.delete(metaFieldParam);
		return "redirect:./metaFieldList?tableId=" + metaFieldParam.getTableId();
	}

	@RequestMapping("/syncFieldMeta")
	@ResponseBody
	public void syncFieldMeta(MetaTable metaTable) {
		metaTableService.syncFieldMeta(metaTable);
	}
	@RequestMapping("/updateFiled")
	@ResponseBody
	public Object updateFiled(@RequestParam("fieldId")String fieldId,@RequestParam("key")String key,
			@RequestParam("value")String value) {
		return metaTableService.updateFiled(fieldId,key,value);
	}

}
