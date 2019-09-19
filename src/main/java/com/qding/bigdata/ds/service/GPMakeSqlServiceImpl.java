package com.qding.bigdata.ds.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.model.SmartQueryParam;

@Service("gPMakeSqlServiceImpl")
public class GPMakeSqlServiceImpl implements MakeSqlService {

	
	@Override
	public String make(SmartQueryParam smartQueryParam) {
		StringBuffer sb = new StringBuffer("select ");
		makeSelectFiled(smartQueryParam, sb);
		makeMetric(smartQueryParam, sb);
		makeFromTable(smartQueryParam, sb);
		makeFilter(smartQueryParam, sb);
		makeGroupBy(smartQueryParam, sb);
		makeLimit(smartQueryParam, sb);
		return sb.toString();
	}

	private void makeMetric(SmartQueryParam smartQueryParam, StringBuffer sb) {
		String metric = smartQueryParam.getMetric();
		if(StringUtils.isBlank(metric)){
			return;
		}
		String[] arr = metric.split(Constant.EURO);
		for (String str : arr) {
			if(StringUtils.isEmpty(str)){
				continue;
			}
			String[] fieldArr = str.split(":");
			sb.append(fieldArr[0]).append( " as \"")
			.append(StringUtils.isNotEmpty(fieldArr[1]) ? fieldArr[1] : fieldArr[0]).append("\",");

			
			
		}

	}

	private void makeGroupBy(SmartQueryParam smartQueryParam, StringBuffer sb) {

		String groupBy = smartQueryParam.getGroupBy();
		if(StringUtils.isBlank(groupBy)){
			return;
		}
		String[] arr = groupBy.split(Constant.EURO);
		sb.append(" group by ");
		for (String str : arr) {
			if(StringUtils.isEmpty(str)){
				continue;
			}
			String[] fieldArr = str.split(":");
			sb.append(fieldArr[0]).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
	}

	private void makeFilter(SmartQueryParam smartQueryParam, StringBuffer sb) {
		String filter= smartQueryParam.getFilter();
		if(StringUtils.isBlank(filter)){
			return;
		}
		sb.append(" where  ");
		String[] filterArr = filter.split(Constant.EURO);
		for (String str : filterArr) {
			String[] split = str.split("\\#\\$\\#");
			if(split.length!=3){
				continue;
			}
			sb.append(" ").append( split[0]).append(" ");
			int conditional=Integer.parseInt(split[1]);
			
				if(conditional==0||conditional==1){
					sb.append(Constant.CONDITIONAL[conditional]);
					sb.append(" any('{");
					sb.append(split[2].replaceAll("\\^", ","));
					sb.append("}')");
				}
				else if(conditional>=2||conditional<=5){
					sb.append(Constant.CONDITIONAL[conditional]);
					sb.append("'"+split[2]+"'");
				}
				else if(conditional==6){
//					sb.append("between ");
//					sb.append(split[2].replaceAll("#$#", "' and '"));
				}
				
				sb.append(" and");
			}
			
		sb.setLength(sb.length()-4);
	}

	private void makeLimit(SmartQueryParam smartQueryParam, StringBuffer sb) {
		sb.append(" limit " ).append(smartQueryParam.getLimit().intValue());
	}
	private void makeFromTable(SmartQueryParam smartQueryParam, StringBuffer sb) {
		if(sb.charAt(sb.length() - 1)==','){
			
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(" from ").append(smartQueryParam.getFullTableName()).append(" ");
	}

	private void makeSelectFiled(SmartQueryParam smartQueryParam, StringBuffer sb) {
		String groupBy = smartQueryParam.getGroupBy();
		String[] arr = groupBy.split(Constant.EURO);
		for (String str : arr) {
			if(StringUtils.isEmpty(str)){
				continue;
			}
			String[] fieldArr = str.split(":");
			sb.append(fieldArr[0]).append(" as \"")
					.append(StringUtils.isNotEmpty(fieldArr[1]) ? fieldArr[1] : fieldArr[0]).append("\",");

		}

	}

}
