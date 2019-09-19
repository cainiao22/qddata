package com.qding.bigdata.ds.enums;

public enum SideBarMenu {

	驾驶舱("驾驶舱","","dataCenter","",false,false),
        /*大屏数据("大屏数据", "数据中心","mainDashBoard","",true,false),
        SQL查询("SQL查询", "数据中心","queryBySql","",true,false),
        自助分析("自助分析", "数据中心","smartQuery","",true,false),
        高管驾驶舱HIVE("高管驾驶舱HIVE", "驾驶舱HIVE","zhihuishiByHive","",true,true)*/

        高管驾驶舱("高管驾驶舱", "驾驶舱","zhihuishiByHive","",true,true),
        	星脉("星脉", "驾驶舱","assets/pages/businessAnalysis/index.html","",true,true),
        旅游驾驶舱("旅游驾驶舱", "驾驶舱","travelDashboard","",true,true),
        智器航行舱("智器航行舱", "驾驶舱","intelligentVessel","",true,true),
	      预算目标录入 ("预算目标录入", "驾驶舱","budgetkeyboarder","",true,false),
	      预算目标录入新 ("预算目标录入新", "驾驶舱","budgetkeyboarder_new","",true,false),
		BI大屏("BI大屏", "驾驶舱", "assets/pages/bi_screen/index.html", "", true, true),

	报表中心("报表中心", "","reportCenter","",false,false),
        社区日报("社区日报", "报表中心","communityReport","",true,false),
        流量分析("流量分析", "报表中心","collectEventInfo","",true,false),
        /*项目活跃统计("项目活跃统计", "报表中心","","",true),*/
        人次统计("人次统计", "报表中心","logonPersonStatistics","",true, false),
        人次统计旧版("人次统计旧版", "报表中心","logonPersonStatistics_old","",true, false),
        物业费数据仪表盘("物业费数据仪表盘", "报表中心","propertyCostsDashboard","",true, true),


	收益月报("收益月报", "","earningsReport","",false, false),
        月报图片上传("月报图片上传", "收益月报","uploadUtil","",true, false),

	用户画像("用户画像","","userProfileGroup","",false,false),
        画像分析("画像分析", "用户画像","userProfile","",true,false),
        标签管理("标签管理", "用户画像","listTag","",true,false),
        群组管理("群组管理", "用户画像","userGroupIndex","",true,false),

    用户画像新("用户画像新","","userPortrait","",false,false),
        数据概览("数据概览", "用户画像新","dataSummary","",true,false),
        画像分析新("画像分析", "用户画像新","userProfileAnalysis","",true,false),
        群组管理新("群组管理", "用户画像新","groupManagement","",true,false),
        标签管理新("标签管理", "用户画像新","tagManagement","",true,false),

	系统管理("系统管理","","SysManage_group","",false,false),
        欢迎页面("欢迎页面","系统管理","index","",true,false),
        用户管理("用户管理","系统管理","userlist","",true,false),
        角色管理("角色管理","系统管理","rolelist","",true,false),
        模块管理("模块管理","系统管理","modulelist","",true,false),

	罗盘系统("罗盘系统", "","projectManagement","",false,false),
		罗盘数据列表("罗盘数据列表", "罗盘系统","midluopanrengongdatalist","",true,false),
	    罗盘权限管理("罗盘权限管理", "罗盘系统","lpUserPermissions","",true,false),
	    成功罗盘("成功罗盘", "罗盘系统","assets/pages/compass/index.html","",true,false),
	    罗盘物业云指标录入("罗盘物业云指标录入", "罗盘系统","lpwyyEntering","",true,false),

    数据盔甲("数据盔甲", "","dataArmor","",false,false),
	    基础档案("基础档案", "数据盔甲","basicfiles","",true,true),
	    审批列表("审批列表", "数据盔甲","approvalList","",true,true),
        物业公司("物业公司", "数据盔甲","property","",true,true),
	    社区信息("社区信息", "数据盔甲","communitylist","",true,true),

	数据分析("数据分析", "","shujufenxi","",false,false),
		观测台("观测台", "数据分析","guancetai","",false,true),
		新版观测台("新版观测台", "数据分析","/pages/guancetai-v2/index.html","",false,true),

	数据维护("数据维护", "","dataMaintenance","",false,false),
		客户服务 ("客户服务", "数据维护","customer","",true,false),
		销售任务 ("销售任务", "数据维护","salestask","",true,false),
		页面维表 ("页面维表", "数据维护","pageTrackMetaList","",true,false),
		skip映射表 ("skip映射表", "数据维护","skipMetaList","",true,false),
		事件维表 ("事件维表", "数据维护","eventTrackMetaList","",true,false),
		事件位置维表 ("事件位置维表", "数据维护","eventPositionMetaList","",true,false),
		参数表 ("参数表", "数据维护","parameterDIM","",true,false),

	Azkaban("Azkaban", "","azkaban","",false,false),
		Azkaban任务("Azkaban任务", "Azkaban","azkabanIndex","",true,false);


	private String name;
	private String parentName;
	private String path;
	private String icon;
	private boolean isLink;
	private boolean target;

	SideBarMenu(String name, String parentName,String path,String icon,boolean isLink,boolean target) {
		this.name = name;
		this.parentName = parentName;
		this.icon=icon;
		this.path=path;
		this.isLink=isLink;
		this.target=target;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isLink() {
		return isLink;
	}

	public void setLink(boolean isLink) {
		this.isLink = isLink;
	}

	public boolean getTarget() {
		return target;
	}

	public void setTarget(boolean target) {
		this.target = target;
	}
}
