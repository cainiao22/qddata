package com.qding.bigdata.ds.common;

import java.util.HashMap;
import java.util.Map;

import com.qding.bigdata.ds.enums.DbUrlEnum;
import com.qding.bigdata.ds.util.PropertiesUtil;

public class Constant {

	public static final String BOSS_LOGIN_URL;
	public static final String BOSS_LOGIN_URL_KEY = "boss.login.url";
	public static final Map<String,String> dbUrlMap = new HashMap<String, String>();

	static {
		BOSS_LOGIN_URL = PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, Constant.BOSS_LOGIN_URL_KEY,
				"https://qd.qdingnet.com/managerbg/home/loginByMobile");
		for (DbUrlEnum  dbUrlEnum :DbUrlEnum.values()) {
			dbUrlMap.put(dbUrlEnum.getDbType(),dbUrlEnum.getDbUrl());
		}

	}

	public static final String DIMENSION_URL = "http://yushanfang.bigdata.qdingnet.com";

	public static final String BOSS_LOGIN_ACCOUNT = "account";
	public static final String BOSS_LOGIN_PASSWORD = "password";
	public static final String BOSS_LOGIN_ORGTYPEFLAG = "bs";
	public static final String BOSS_LOGIN_ORGTYPEFLAG_KEY = "orgTypeFlag";
	public static final String BOSS_LOGIN_CALLBACK = "callback";
	public static final String BOSS_LOGIN_SUCCESS_CODE = "200";

	public static final String USERNAME = "username";
	public static final String REALNAME = "realname";
	public static final String PASSWORD = "password";
	public static final String SALTKEY = "saltkey";
	public static final String ONLINECOUNT = "onlineCount";
	public static final String ONLINEUSER = "onlineUser";
	public static final String LOGINPATH = "./login";
	public static final Map<String, String> MODULEMAP = new HashMap<String, String>();
	public static final String EMPTY = "";
	public static final String MIDLINE = "-";
	public static final String CONFIGFILE = "config.properties";
	public static final String ZOOKEEPERHOST = "zookeeper.host";
	public static final String ZOOKEEPERPORT = "zookeeper.port";
	public static final String DSREDISHOST = "ds.redis.host";
	public static final String DSREDISPORT = "ds.redis.port";
	public static final String SHELL_SCRIPT_COMMAND = "syn.data.script.command";
	public final static String USERID = "userId";

	public static final String[] META_TABLE_TYPES = { "STG", "ODS", "MID", "DWP", "DIM", "OUT", "QDING_BI", "TEST",
			"DA", "REALTIME"};
	public static final String[] META_FIELD_TYPES = { "TEXT", "INTEGER", "DOUBLE" };
	public static final String[] MEASURE_STATUS = { "1", "0" };
	public static final String[] ETL_JOB_TYPES = { "SQL", "Java(开发中)", "Kettle(开发中)" };

	// ------------ httpclient config begin------------------//

	// 每个主机的最大并行链接数,为每个区设置最大的并发连接数,默认每个路由基础上的连接不超过10个
	public static final int DEFAULT_MAX_PER_ROUTE = Integer
			.valueOf(PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "httpclient.default_max_per_route", "10"));
	// 客户端总并行链接最大数。默认值总连接数不能超过200
	public static final int MAX_TOTAL = Integer
			.valueOf(PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "httpclient.max_total", "200"));
	// http连接超时时间。默认值设置为30秒
	public static final int CONNECT_TIMEOUT = Integer
			.valueOf(PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "httpclient.connect_timeout", "1000000"));
	// socket连接超时时间。默认值设置为15秒
	public static final int SOCKET_TIMEOUT = Integer
			.valueOf(PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "httpclient.socket_timeout", "105000"));

	// http连接超时时间。默认值设置为30秒
	public static final int CONNECT_TIMEOUT_INNER = Integer.valueOf(
			PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "httpclient.connect_timeout_inner", "1000000"));
	// socket连接超时时间。默认值设置为15秒
	public static final int SOCKET_TIMEOUT_INNER = Integer.valueOf(
			PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "httpclient.socket_timeout_inner", "105000"));

	// http请求重试次数
	public static final int HTTP_RETRY_NUM = Integer
			.valueOf(PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "httpclient.retry_num", "3"));

	// http请求失败后等待重试时间
	public static final int HTTP_SLEEP_TIME = Integer
			.valueOf(PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "httpclient.sleep_time", "3000"));

	// ------------ httpclient config end------------------//

	public static final String SIDEBAR_MENU = "sidebar_menu";

	/** jboss登录相关 **/
	public static final String SESSION_ID = PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE,
			"jboss.user.sessionId", null);

	public static final String JBOSS_DOMAIN = PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "jboss.domain",
			null);

	/****** jboss end ******/

	/**** shell command ***/
	public static final String SHELL_COMMAND = PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE,
			SHELL_SCRIPT_COMMAND, null);

	/****shell end ***/

	public static final String  EURO="€";
	public static final String[] CONDITIONAL = { "=","!=",">",">=","<","<=" };

	public static final String DRUID_QUERY_URL = PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "druid.query.url", null);
	public static final String ES_QUERY_URL = PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "es.query.url", null);

	public static final String COMMON_DATA_TIME_USED_DRUID = PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "druid.timeused.insert.url", null);

	public static final String CALCULATEL_TOTAL = "calTotal";
	public static final String PAGE_SIZE = "pageSize";
	public static final String CURRENT_PAGE = "currentPage";


	public static void main(String args[]){

		System.err.println(dbUrlMap);
	}
}
