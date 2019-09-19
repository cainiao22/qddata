$(function() {
	var platforms=new Array();
	window.platforms=platforms;
	
	var catalogs=new Array();
	window.catalogs=catalogs;
	
	var prepath='消耗量报表/';
	var reportname='';
	window.reportname=reportname;
	
	$("#framemain").load(function() {
		$('#loading').hide();
		$('#framemain').show();
	});
	function loadframe() {
		
		reportname='';
		var url = createurl();
		$('#framemain').hide();
		$('#loading').show();
		$('#framemain').attr("src", url);
	}
	$('#submitBtn').click(function() {
//		$('body,html').animate({
//			scrollTop: 900
//		}, 2000);
		loadframe();
// $('#FilterTabs div:eq(0)').hide(500);
// $('#FilterTabs div:eq(1)').hide(500);
		
		
	});
	function parsefullurl(path,param){
		var url =window.config.bieehost+window.config.commonPages.replace('{#path}',path);

// var adstyle=$('#adstyle');
// var title= $('#from').val() + '' + $('#to').val()+' ' +
// (adstyle.val()==-1?'':adstyle.find("option:selected").text());
// url+='&MYTITLE='+encodeURIComponent(title);
		param[0] = (param.length - 1) / 3;
		for ( var i = 0; i < param.length; i++) {
			url += '&P' + i + '=' + param[i];
		}
		
		url+='&rand='+Math.random();
		return url;
	}
	function createurl() {
		switch (path) {
		case 'consumeAdPosision':
			return create_consumeAdPosision_url();
			break;
		case 'cityCatalogReport':
			return create_cityCatalogReport_url();
			break;
	}
	}
	function fillreportname() {
			
		
	}
	function create_consumeAdPosision_url(){
		var param = new Array();
		setCommonLimit(param);
		
		
		
		reportname+='贴片位置剩余库存报表';
		
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function create_cityCatalogReport_url(){
		var param = new Array();
		setCommonLimit(param);
		
		
		
		reportname+='区域栏目库存消耗报表';
		
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function setCommonLimit(param) {
		var index=0;

	
			param[++index] = 'bet';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = '2+' + $('#from').val() + '+' + $('#to').val();
		
		if($('#form_platform').length>0&&$('#form_platform').is(":visible")){
		
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"平台维"."终端类型"');
			
			if($('#platform_one').val()=='-1'){
				var pArr=new Array();
				$('#platform_one option:gt(0)').each(function(){
					pArr.push(encodeURIComponent(encodeURIComponent($(this).text())));
				});
				param[++index] = pArr.length+'+'+pArr.join('+');
			}else{
			param[++index] = encodeURIComponent(encodeURIComponent($('#platform_one option:selected').text()));
			}
			
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"平台维"."终端编码"');
			param[++index] = $('#platform_two').val()=='-1'?'':$('#platform_two').val();
			
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"平台维"."操作系统编码"');
			param[++index] = $('#platform_three').val()=='-1'?'':$('#platform_three').val();
			
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"平台维"."应用编码"');
			param[++index] = $('#platform_four').val()=='-1'?'':$('#platform_four').val();
			
					
			}
	}
	$("#from").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		onClose : function(selectedDate) {
			$("#to").datepicker("option", "minDate", selectedDate);
		}
	}).datepicker('setDate', -1);
	$("#to").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true
	}).datepicker('setDate', -1);

});