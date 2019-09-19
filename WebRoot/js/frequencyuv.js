$(function() {
	var platforms=new Array();
	window.platforms=platforms;
	
	var catalogs=new Array();
	window.catalogs=catalogs;
	
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
		case 'frequencyuv':
			return create_frequencyuv_url();
			break;
	}
	}
	function fillreportname() {
		var limitnumberbegin=$("#limitnumberbegin").val();
		var limitnumberend=$("#limitnumberend").val();
		if(limitnumberbegin!='' || limitnumberend!=''){
			reportname+='频次控频UV报表';
			
		}else{
			reportname+='频次UV报表';
		}
		
	}
	function create_frequencyuv_url(){
		var param = new Array();
		setCommonLimit(param);
		
		addParam_region(param);
		
		addParam_platform(param);
		
		fillreportname();
		
		var path = encodeURIComponent('频次UV报表/' + reportname);
		return parsefullurl(path,param);
	}
	function setCommonLimit(param) {
		var index=0;
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = $('#from').val();
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"时间类型维"."DATA_TYPE_ID"');
		param[++index] = $('#reporttype').val();
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"广告形式维"."广告形式代码"');
		param[++index] = $('#adstyle').val();
		
	}
	function addParam_platform(param){
		var index =param.length-1;
		
		
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
	function addParam_limitnumber(type,limitnumberbegin,limitnumberend,param){
		if(limitnumberbegin=='' && limitnumberend==''){
			return;
		}
		if(isNaN(limitnumberbegin)|| limitnumberbegin<1){
			$('#limitnumberbegin').val(1);
		}
		if(isNaN(limitnumberend) || limitnumberend>100){
			$('#limitnumberend').val(100);
		}
		var index =param.length-1;
		if(limitnumberbegin!=''){
			if(limitnumberend!=''){
				param[++index] = 'bet';
				param[++index] = encodeURIComponent('"事实表_频次'+type+'UV"."频次"');
				param[++index] ='2+' + limitnumberbegin + '+' + limitnumberend;
			}
			else{
				param[++index] = 'ge';
				param[++index] = encodeURIComponent('"事实表_频次'+type+'UV"."频次"');
				param[++index] =limitnumberbegin;
			}
		}else{
			param[++index] = 'le';
			param[++index] = encodeURIComponent('"事实表_频次'+type+'UV"."频次"');
			param[++index] =limitnumberend;
		}
		
	}
	function addParam_region(param){
		var index =param.length-1;
		var city_state=$('#city_state');
		var city_province=$('#city_province');
		var city_city=$('#city_city');
		var limitnumberbegin=$("#limitnumberbegin").val();
		var limitnumberend=$("#limitnumberend").val();
		
			if(city_city.val()!='-1'){
					 reportname+="城市";
							param[++index] = 'eq';
							param[++index] = encodeURIComponent('"城市维"."城市ID"');
							param[++index] =city_city.val();
							addParam_limitnumber('城市',limitnumberbegin,limitnumberend,param);
				}else if (city_province.val()!=-1){
					reportname+="省份";
					param[++index] = 'eq';
					param[++index] = encodeURIComponent('"省份维"."省份ID"');
					param[++index] =city_province.val()=='-1'?'':city_province.val();
					addParam_limitnumber('省份',limitnumberbegin,limitnumberend,param);
				}
				
			else {
				reportname+="国家";
				param[++index] = 'eq';
				param[++index] = encodeURIComponent('"国家维"."国家ID"');
				param[++index] =city_state.val()=='-1'?'':city_state.val();
				
				addParam_limitnumber('国家',limitnumberbegin,limitnumberend,param);
			}
				
			
	
		
		}
	$('#city_state').length > 0 && loadCountry($('#city_state'));
	function loadCountry(obj) {

		$.post("./listCountry", {}, function(data, status) {
			for ( var i in data) {
				obj.append(" <option value='" + data[i].code + "'>"
						+ data[i].name + "</option>");
			}
			$('#city_state option:eq(0)').attr('selected','selected');
		});
	}
	$('#city_state').change(function(){
		if($(this).val()=='CN'||$(this).val()=='10000001'){
			$('#city_province,#city_city').attr("disabled",false); 
		}
		else{
			$('#city_province option:eq(0)').attr('selected','selected');
			$('#city_city option:eq(0)').attr('selected','selected');
			$('#city_province,#city_city').attr("disabled",true); 
		}
	});
	$('#city_province').length > 0 && loadProvince($('#city_province'));
	function loadProvince(obj) {
		
		$.post("./listProvince", {}, function(data, status) {
			for ( var i in data) {
				obj.append(" <option value='" + data[i].id + "'>"
						+ data[i].name + "</option>");
			}
		});
	}
	function reloadCity(pid) {
		var city = $('#city_city');
		city.find('option:gt(0)').remove();
		$.post("./listCity", {
			pid : pid
		}, function(data, status) {
			
			for ( var i in data) {
				city.append(" <option value='" + data[i].id + "'>"
						+ data[i].name + "</option>");
			}
			if(data.length==1){
				city.find('option:eq(1)').attr("selected","selected");
			}
		});
	}
	$('#city_province').length>0 &&$('#city_province').change(function() {
		reloadCity($(this).val());
	});
	var reporttype=$('#reporttype').val();
	$('#reporttype').change(function(){
		 reporttype=$('#reporttype').val();
	});
	var lastDays=-1;
	
		var b=new Date();
		var c=b.getDay();
		lastDays= c*-1;
	$("#from").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		beforeShowDay:  function(date){var b=date.getDay();return [b!=0?0:1,''];}, 
		maxDate:new Date()
	}).datepicker('setDate', lastDays<0?lastDays:new Date());
	$('#limitnumberLabel').qtip({
		content: '1.只允许1-100间整数.\<br>2.两处均留空，展示全部.',
		show: 'mouseover',
		hide: 'mouseout',
		style: { 
			color: '#6699CC',
			width:210
		},
		
		position: {
			corner: {
				target: 'bottomLeft',
				tooltip: 'topLeft'
			}
		}
	});
	
});