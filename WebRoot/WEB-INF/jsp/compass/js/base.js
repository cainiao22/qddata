/**
 * 二、三级页面公用类
 *
 */
//html拼接
function pianquCommonFun(text, key, value, huanbi) {
  var pianquAreaStr = "";
  pianquAreaStr += '<div class="business text-info">';
  pianquAreaStr += '<span class="value text-left">' + text + '</span>';
  pianquAreaStr += '<span class="value flex-layout target_val">';
  if (value) {
    if (key == "pduan_atc_lv" || key == "gg_shequ_zhanbi" || key == "jf_shequ_rate" || key == "bind_task_rate" || key == "bind_time_rate") {
      pianquAreaStr += '<span>' + value + '%</span>';
    } else {
      pianquAreaStr += '<span>' + value + '</span>';
    }
  } else {
    pianquAreaStr += '<span>-</span>';
  }

  if (Number(huanbi) > 0) {
    pianquAreaStr += '<span class="text-right text-red">' + huanbi + '%<span class="raiseIcon"></span></span>';
  } else if (Number(huanbi) < 0) {
    pianquAreaStr += '<span class="text-right text-green">' + huanbi + '%<span class="lowerIcon"></span></span>';
  } else if (Number(huanbi) == 0) {
    if (value > 0) {
      pianquAreaStr += '<span class="text-right">' + huanbi + '%</span>';
    } else {
      pianquAreaStr += '<span class="text-right">-</span>';
    }
  } else {
    pianquAreaStr += '<span class="text-right">' + huanbi + '</span>';
  }
  pianquAreaStr += '</span>';
  pianquAreaStr += '</div>';

  return pianquAreaStr;
}

/**
 * 根据指标id 展示文字
 * key 指标id
 */
function getValueByKey(key) {
  var kid = key;
  var text = "";
  if (kid == "qy_qiye_num") {
    text = "签约企业数量";
  }
  if (kid == "qy_guidang_shequ") {
    text = "合同归档社区数量";
  }
  if (kid == "wyy_todo_project_num") {
    text = "待实施项目";
  }
  if (kid == "wyy_doing_project_num") {
    text = "实施中项目";
  }
  if (kid == "wyy_done_project_num") {
    text = "已上线完成项目";
  }
  if (kid == "wyy_cancel_project_num") {
    text = "取消或暂停项";
  }
  if (kid == "pduan_bind_mem") {
    text = "P端绑定员工数";
  }
  if (kid == "pduan_act_mem") {
    text = "P端活跃员工数";
  }
  if (kid == "pduan_atc_lv") {
    text = "P端活跃员工占比";
  }
  if (kid == "gg_shequ_num") {
    text = "发公告社区数量";
  }
  if (kid == "gg_shequ_zhanbi") {
    text = "发公告社区占比";
  }
  if (kid == "gg_shequ_avg") {
    text = "社区发公告平均数量";
  }
  if (kid == "mj_shequ_num") {
    text = "安装门禁社区数";
  }
  if (kid == "mj_total_num") {
    text = "门禁安装总量";
  }
  if (kid == "mj_nopass_num") {
    text = "30天无人通行门禁数量";
  }
  if (kid == "mj_use_rate") {
    text = "门禁使用率";
  }
  if (kid == "bs_total_num") {
    text = "报事总量";
  }
  if (kid == "bs_yezhu_num") {
    text = "业主报事量";
  }
  if (kid == "bs_wuye_num") {
    text = "物业报事量";
  }
  if (kid == "jf_shequ_rate") {
    text = "开通缴费功能的项目占比";
  }
  if (kid == "jf_online_times") {
    text = "在线缴费次数";
  }
  if (kid == "jf_online_jine") {
    text = "在线缴费金额";
  }
  if (kid == "bind_task_hushu") {
    text = "任务绑定户数";
  }
  if (kid == "bind_new_inc") {
    text = "新增注册人数";
  }
  if (kid == "bind_act_num") {
    text = "活跃人数";
  }
  if (kid == "bind_new_hushu") {
    text = "新增绑定户数";
  }
  if (kid == "bind_task_rate") {
    text = "绑定任务完成占比";
  }
  if (kid == "bind_time_rate") {
    text = "时间进度比";
  }
  return text;
}

//检查维度
function checkTopic(topic_type) {
  if (topic_type == "qianyue") {
    $("#busTitle").text("签约情况");
  }
  if (topic_type == "wuyeyun") {
    $("#busTitle").text("物业云");
  }
  if (topic_type == "pduan") {
    $("#busTitle").text("P端使用情况");
  }
  if (topic_type == "gonggao") {
    $("#busTitle").text("公告");
  }
  if (topic_type == "menjin") {
    $("#busTitle").text("	门禁");
  }
  if (topic_type == "baoshi") {
    $("#busTitle").text("报事");
  }
  if (topic_type == "jiaofei") {
    $("#busTitle").text("缴费");
  }
  if (topic_type == "bind") {
    $("#busTitle").text("绑定及活跃情况");
  }
}

/**
 * 根据区域id展示对应文字
 * @param cityId
 */
function getCityName(cityId) {
  var cid = cityId;
  var cityName = "";
  if (cid == GAreaType.ALL) {
    cityName = "全国";
  }
  if (cid == GAreaType.HUABEI) {
    cityName = "华北片区";
  }
  if (cid == GAreaType.DONGNAN) {
    cityName = "东南片区";
  }
  if (cid == GAreaType.XINAN) {
    cityName = "西南片区";
  }
  if (cid == GAreaType.OTHER) {
    cityName = "其它";
  }
  if(cid == GAreaType.BJ){
    cityName = "北京市";
  }
  if(cid == GAreaType.CQ){
    cityName = "重庆市";
  }
  if(cid == GAreaType.CD){
    cityName = "成都市";
  }
  if(cid == GAreaType.SH){
    cityName = "上海市";
  }
  if(cid == GAreaType.XA){
    cityName = "西安市";
  }
  if(cid == GAreaType.HZ){
    cityName = "杭州市";
  }
  if(cid == GAreaType.GZ){
    cityName = "广州市";
  }
  return cityName;
}
