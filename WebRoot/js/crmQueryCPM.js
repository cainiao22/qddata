$(function() {

	var reportname = '';
	window.reportname = reportname;
	var prepath='消耗量报表/';

	$("#framemain").load(function() {
		$('#loading').hide();
		$('#framemain').show();
	});

	function loadframe() {
		
		
//		if(window.config.portalPages.indexOf('{#username}')>0){
//			return;
//		}
		reportname = '';
		var url = createurl();
		$('#framemain').hide();
		$('#loading').show();
		$('#framemain').attr("src", url);
		clearInterval(timer);
	}
	
	function parsefullurl(path, param) {
		var url =window.config.bieehost+window.config.commonPages.replace('{#path}',path).replace(/NQuser=(.*?)&/g,"NQuser=crmPublicUser&").replace(/NQpassword=(.*?)&/g,"NQpassword=dd155946ff2b8f1ad74db07a7819dfcf&");
		param[0] = (param.length - 1) / 3;
		for ( var i = 0; i < param.length; i++) {
			url += '&P' + i + '=' + param[i];
		}
		url += '&rand=' + Math.random();
		return url;
	}
	function fillreportname() {
			reportname += 'CRM询量报表';
	}
	function createurl() {
		var param = new Array();
		var index=param.length;
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"事实表_询量单数据"."FORECAST_ID"');
			param[++index] = forecastID; 
			param[++index] = 'gt';
			param[++index] = encodeURIComponent('"事实表_询量单数据"."已下单"');
			param[++index] = -1*Math.random(); 
			
		fillreportname();
		var path = encodeURIComponent(prepath  + reportname);
		return parsefullurl(path, param);
	}
	if(forecastID!=undefined && forecastID!=''){
	var timer =  window.setInterval(loadframe,2000);}
	else{
		$('#error').show();
	}
});