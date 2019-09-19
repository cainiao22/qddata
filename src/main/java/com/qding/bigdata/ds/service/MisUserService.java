package com.qding.bigdata.ds.service;
import java.util.List;
import java.util.Map;

public interface MisUserService {

	/**
	 * 查询全部mis账户
	 * @return
	 */
	public List<Map<String,Object>> getMisUserList();

	/**
	 * 将mis账户同步至北斗星
	 * @return
	 */
	public Map<String,Object> addDsUserByMis();

	/**
	 * 定时任务新增或修改用户基本信息
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<String,Object> addOrUpdateDsUserByMisScheduled(String start,String end);

}
