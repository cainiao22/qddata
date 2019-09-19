$(function() {
	
	var catalogs=new Array();
	window.catalogs=catalogs;
	var reportname='';
	window.reportname=reportname;
	
	var prepath='收入报表/';


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
//			scrollTop: $("#submitBtn").offset().top
//		}, 1000);
		loadframe();
//		$('#FilterTabs div:eq(0)').hide(500);
//		$('#FilterTabs div:eq(1)').hide(500);
		
		
	});

	function parsefullurl(path,param){
		var url =window.config.bieehost+window.config.commonPages.replace('{#path}',path);

//		var title= $('#from').val() + '' + $('#to').val()+' ' + (adstyle.val()==-1?'':adstyle.find("option:selected").text());
//		url+='&MYTITLE='+encodeURIComponent(title);
		if(param.length>0){
		param[0] = (param.length - 1) / 3;
		}
		for ( var i = 0; i < param.length; i++) {
			url += '&P' + i + '=' + param[i];
		}
		
		url+='&rand='+Math.random();
		return url;
	}
	function fillreportname() {
		if(path=='project'){
			
		var reporttype=$('#viewtype').val();
		
		if(reporttype=='1'){
			reportname+='收入';
		}
			else if(reporttype=='2'){
				reportname+='消耗量';
				}
		
		}
		reportname+='报表';
	}
	function create_project_url() {

		
		var param = new Array();
		setCommonLimit(param);
		
		reportname+='执行单';
		
		fillreportname();
		
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);

	}
	
	function create_projectdirection_url() {
		var param = new Array();
		setCommonLimit(param);
		addParam_city(param);
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);


	}
	function create_projectGap2_url() {
		var param = new Array();
		setCommonLimit2(param);
		var index=param.length-1;
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"排期包与库存平台组合维"."ADS_PLAT_ID"');
		
		var s=(String)($('#adsplatform').val());
		var adsplatformArr=s.split(',');
		param[++index] = s=='null'?'':(adsplatformArr.length+'+'+adsplatformArr.join('+'));
		addParam_city2(param);
		reportname='执行单频次gap报表';
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
		
		
	}
	function create_projectHour_url() {
		var param = new Array();
		setCommonLimit2(param);
		var index=param.length-1;
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"排期包与库存平台组合维"."ADS_PLAT_ID"');
		
		var s=(String)($('#adsplatform').val());
		var adsplatformArr=s.split(',');
		param[++index] = s=='null'?'':(adsplatformArr.length+'+'+adsplatformArr.join('+'));
		if($('#fromhour').val().length>0 && $('#tohour').val()){
		param[++index] = 'bet';
		param[++index] = encodeURIComponent('"事实表_IES_执行单小时数据"."I_HOUR" ');
		param[++index] = '2+' + $('#fromhour').val() + '+' + $('#tohour').val();
		}
		addParam_city2(param);
		reportname='执行单小时数据报表';
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
		
		
	}
	function create_projectPkgDetailData_url() {
		var param = new Array();
		setCommonLimit2(param);
		addParam_city2(param);
		var index =param.length-1;
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"栏目维"."栏目ID"');
		var catalog_one_s=(String)($('#catalog_one').val());
		var catalog_one_arr=catalog_one_s.split(',');
		param[++index] = catalog_one_s=='null'?'':(catalog_one_arr.length+'+'+catalog_one_arr.join('+'));
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"产地维"."产地ID"');
		
		var catalog_two_s=(String)($('#catalog_two').val());
		var catalog_two_arr=catalog_two_s.split(',');
		param[++index] = catalog_two_s=='null'?'':(catalog_two_arr.length+'+'+catalog_two_arr.join('+'));
		
		switch ($('#reporttype').val()) {
		case '1':
			reportname='排期包栏目产地消耗收入日报';
			break;
		case '2':
			reportname='排期包栏目产地消耗收入周报';
			break;
		case '3':
			reportname='排期包栏目产地消耗收入月报';
			break;
		default:
			break;
		}
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function create_arrivalstat_url() {
		var bypkg=$('#bypkg').is(':checked');
		var param = new Array();
		setCommonLimit2(param);
		var index =param.length-1;
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"曝光位置维"."V_ID"');
		param[++index] = encodeURIComponent($('#vp').val());
		
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"事实表_业务到达率统计'+(bypkg?'_排期包':'')+'"."平台类型"');
		param[++index] = encodeURIComponent($('#plattype').val());
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"事实表_业务到达率统计'+(bypkg?'_排期包':'')+'"."页面类型"');
		param[++index] = encodeURIComponent($('#pagetype').val());
		
		
		if(bypkg){
			
			param[++index] = 'cany';
			param[++index] = encodeURIComponent('"排期包维"."执行单编号"');
			param[++index] = encodeURIComponent($('#projectnumber').val());
		}
		
		reportname='业务到达率统计_'+(bypkg?'排期包':'执行单');
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
		
		
	}
	function create_summarystat_url() {
		var bypkg=$('#bypkg').is(':checked');
		var param = new Array();
		setCommonLimit2(param);
		var index =param.length-1;
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"曝光位置维"."V_ID"');
		param[++index] = encodeURIComponent($('#vp').val());
		
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"事实表_汇总数据统计'+(bypkg?'_排期包':'')+'"."平台类型"');
		param[++index] = encodeURIComponent($('#plattype').val());
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"事实表_汇总数据统计'+(bypkg?'_排期包':'')+'"."页面类型"');
		param[++index] = encodeURIComponent($('#pagetype').val());
		
		
		if(bypkg){
			
			param[++index] = 'cany';
			param[++index] = encodeURIComponent('"排期包维"."执行单编号"');
			param[++index] = encodeURIComponent($('#projectnumber').val());
		}
		
		reportname='汇总数据统计_'+(bypkg?'排期包':'执行单');
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
		
		
	}
	function create_hourdataproject_url() {
		var param = new Array();
		setCommonLimit2(param);
		reportname+='小时分排期包';
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
		
		
	}
	function create_mobileStockproject_url() {
		var param = new Array();
		setCommonLimit2(param);
		reportname+='执行单机型消耗';
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
		
		
	}
	
	function create_clickproject_url() {
		var param = new Array();
		setCommonLimit2(param);
		
		reportname='执行单报表';

		if( $('#byday').attr("checked")=='checked'){
			prepath='点击数(日报)/';
		}else{
			prepath='点击数/';
			
		}
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
		
		
	}
	function create_pkgrestrict_url() {
		var param = new Array();
		setCommonLimit2(param);
		
		reportname='排期包定向栏目报表';
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
		
		
	}
	function create_packageDetailData_url() {
		var param = new Array();
		setCommonLimit2(param);
		addParam_city2(param);
		reportname=$('#reporttype').val();

		if(reportname=='排期包预订汇总报表' && $('#city_city_sel option').length>0){
			reportname='排期包城市预订汇总报表';
		}
		if( $('#byday').attr("checked")=='checked'){
			prepath='收入报表(日报)/';
		}else{
			prepath='收入报表/';
			
		}
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
		
		
	}
		function create_projectGap_url() {
		var param = new Array();
		reportname+='执行单GAP';
		$('#from').val()!=$('#to').val()&&(reportname+='1');
		addParam_projectGap(param);
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
		
		
	}
		function create_cpmMakeUp_url() {
			var param = new Array();
			reportname+='排期包及时补量';
			addParam_projectGap(param);
			fillreportname();
			var path = encodeURIComponent(prepath + reportname);
			return parsefullurl(path,param);
			
			
		}
		function create_projectFinishStatus_url(){
			var param = new Array();
			addParam_projectFinishStatus(param);
			var index=param.length-1;
			if($('#projectnumber').length>0){
				param[++index] = 'cany';
				param[++index] = encodeURIComponent('"执行单维"."执行单编号"');
				param[++index] = encodeURIComponent($('#projectnumber').val());
				}
				if($('#projectid').length>0){
				param[++index] = 'eq';
				param[++index] = encodeURIComponent('"执行单维"."执行单ID"');
				param[++index] = $('#projectid').val();
				}
			
			reportname+='执行单完成情况总览';
			fillreportname();
			
			var path = encodeURIComponent(prepath + reportname);
			return parsefullurl(path,param);
		}
		function create_areaFlightReport_url(){
			var param = new Array();
			addParam_areaFlightReport(param);
			
			
			reportname+='地域投放量总览';
			fillreportname();
			
			var path = encodeURIComponent(prepath + reportname);
			return parsefullurl(path,param);
		}
		function addParam_projectFinishStatus(param){
			var index =0;
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"日期维"."DAY"');
			param[++index] = $('#from').val();
			param[++index] = 'le';
			param[++index] = encodeURIComponent('"执行单维"."执行单开始日期"');
			param[++index] = $('#from').val();
			param[++index] = 'ge';
			param[++index] = encodeURIComponent('"执行单维"."执行单结束日期"  大于等于');
			param[++index] = $('#from').val();
		}
		function addParam_areaFlightReport(param){
			var index =0;
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"日期维"."DAY"');
			param[++index] = $('#from').val();
		}
	function create_salesmonitor_url() {
		var param = new Array();
		 var salesmonitor_type=$('input:radio[name="salesmonitor_type"]:checked').val();
		 if(salesmonitor_type=='1'){
			 reportname='01大行业收入报表';
		 }else if (salesmonitor_type=='2'){
			 reportname='02大行业消耗量报表';
		 }
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
		
		
	}
	function setCommonLimit(param) {
		var index=0;
		param[++index] = 'bet';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = '2+' + $('#from').val() + '+' + $('#to').val();
		if(path=='project'){
		param[++index] = 'cany';
		param[++index] = encodeURIComponent('"执行单维"."执行单编号"');
		param[++index] = encodeURIComponent($('#projectnumber').val());
	
		param[++index] = 'cany';
		param[++index] = encodeURIComponent('"合同维"."合同编号"');
		param[++index] = encodeURIComponent($('#contractnumber').val()); 
		}else if(path=='projectdirection'){
			
		param[++index] = 'cany';
		param[++index] = encodeURIComponent('"排期包维"."执行单编号"');
		param[++index] = encodeURIComponent($('#projectnumber').val());
		}
		
		
	}
	function setCommonLimit2(param) {
		var index=0;
		if($('#to').length>0){
		param[++index] = 'bet';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = '2+' + $('#from').val() + '+' + $('#to').val();
		}else{
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"日期维"."DAY"');
			param[++index] = $('#from').val();
		}
		
		if($('#adstyle').length>0){
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"广告形式维"."广告形式代码"');
		var s=(String)($('#adstyle').val());
		var adstyleArr=s.split(',');
		param[++index] = s=='null'?'':(adstyleArr.length+'+'+adstyleArr.join('+'));
		}
		if($('#pkgtype').length>0){
			param[++index] = 'eq';
			
			param[++index] = encodeURIComponent(path.indexOf('vrs')>0?'"事实表_排期包专辑消耗收入"."排期包类型"':'"排期包类型维"."排期包类型"');
			var s=(String)($('#pkgtype').val());
			var pkgtypeArr=s.split(',');
			param[++index] = s=='null'?'':(pkgtypeArr.length+'+'+pkgtypeArr.join('+'));
		}
		if($('#scope').length>0){
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"范围维"."范围ID"');
		param[++index] = $('#scope').val()==-1?'':$('#scope').val(); 
		
		}
		if($('#duration_select').length>0){
		var durationSel=$('#duration_select').val();
		if(durationSel!='-1'){ 
			param[++index] = 'bet';
			param[++index] = encodeURIComponent('"视频时长维"."视频时长ID"');
			if(durationSel!='-2'){ 
				var strs= durationSel.split(","); 
				param[++index] = '2+' + strs[0] + '+' + strs[1];
			}else{
				if($('#duration_b').val()==''){
					$('#duration_b').val(0);
				}
				if($('#duration_e').val()==''){
					$('#duration_e').val(9999999);
				}
				param[++index] = '2+' + $('#duration_b').val() + '+'+(parseInt($('#duration_e').val())-1);
			}

		
		}
		}
		if($('#form_common_platform').length>0&&$('#form_common_platform').is(":visible")){
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"平台维"."终端类型"');
		
		if($('#common_platform_one').val()=='-1'){
			var pArr=new Array();
			$('#common_platform_one option:gt(0)').each(function(){
				pArr.push(encodeURIComponent(encodeURIComponent($(this).text())));
			});
			param[++index] = pArr.length+'+'+pArr.join('+');
		}else{
		param[++index] = encodeURIComponent(encodeURIComponent($('#common_platform_one option:selected').text()));
		}
		
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"平台维"."终端编码"');
		param[++index] = $('#common_platform_two').val()=='-1'?'':$('#common_platform_two').val();
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"平台维"."操作系统编码"');
		param[++index] = $('#common_platform_three').val()=='-1'?'':$('#common_platform_three').val();
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"平台维"."应用编码"');
		param[++index] = $('#common_platform_four').val()=='-1'?'':$('#common_platform_four').val();
				
		}
		if($('#from_hour').length>0 && $('#to_hour').length>0){
			param[++index] = 'bet';
			param[++index] = encodeURIComponent('"小时维"."HOUR_ID"');
			param[++index] = '2+' + ($('#from_hour').val()=='-1'?1:$('#from_hour').val()) + '+'+($('#to_hour').val()=='-1'?24:$('#to_hour').val());
		}
		if($('#projectnumber').length>0){
		param[++index] = 'cany';
		param[++index] = encodeURIComponent('"执行单维"."执行单编号"');
		param[++index] = encodeURIComponent($('#projectnumber').val());
		}
		if($('#projectid').length>0){
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"执行单维"."执行单ID"');
		param[++index] = $('#projectid').val();
		}
		if($('#pkgid').length>0){
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"排期包维"."排期包ID"');
			param[++index] = $('#pkgid').val();
		}
		if($('#adsplatform').length>0){
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"排期包平台组合维"."ADS_PLAT_ID"');
			var s=(String)($('#adsplatform').val());
			var adsplatformArr=s.split(',');
			param[++index] = s=='null'?'':(adsplatformArr.length+'+'+adsplatformArr.join('+'));
		}
	}
	function createurl() {
		switch (path) {
		case 'project':
			return create_project_url();
			break;
		case 'projectdirection':
			return create_projectdirection_url();
			break;
		case 'projectHour':
			return create_projectHour_url();
			break;
		case 'projectGap2':
			return create_projectGap2_url();
			break;
		case 'salesmonitor':
			return create_salesmonitor_url();
			break;
		case 'projectGap':
			return create_projectGap_url();
			break;
		case 'cpmMakeUp':
			return create_cpmMakeUp_url();
			break;
		case 'projectFinishStatus':
			return create_projectFinishStatus_url();
			break;
		case 'areaFlightReport':
			return create_areaFlightReport_url();
			break;
		case 'hourdataproject':
			return create_hourdataproject_url();
			break;
		case 'packageDetailData':
			return create_packageDetailData_url();
			break;
		case 'clickproject':
			return create_clickproject_url();
			break;
		case 'pkgrestrict':
			return create_pkgrestrict_url();
			break;
		case 'mobileStockproject':
			return create_mobileStockproject_url();
			break;
		case 'projectPkgDetailData':
			return create_projectPkgDetailData_url();
			break;
		case 'arrivalstat':
			return create_arrivalstat_url();
			break;
		case 'summarystat':
			return create_summarystat_url();
			break;
		default:
			break;
		}
	}
	$("#from").datepicker({
		changeYear : true,
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		showOtherMonths:true,
		numberOfMonths:3,
		showCurrentAtPos:1,
		onClose : function(selectedDate) {
			$("#to").datepicker("option", "minDate", selectedDate);
		}
	}).datepicker('setDate', -1);
	$("#date").datepicker({
		changeYear : true,
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		showOtherMonths:true,
		numberOfMonths:3,
		showCurrentAtPos:1
	}).datepicker('setDate', -1);
	$("#to").datepicker({
		changeYear : true,
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		showOtherMonths:true,
		numberOfMonths:3,
		showCurrentAtPos:1
	}).datepicker('setDate', -1);


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
		city.empty();
		$.post("./listCity", {
			pid : pid
		}, function(data, status) {
			
			for ( var i in data) {
				city.append(" <option value='" + data[i].id + "'>"
						+ data[i].name + "</option>");
			}
		});
	}
	function reloadHotcity(group) {
		var city = $('#city_city');
		city.empty();
		$.post("./listCity", {
			group : group
		}, function(data, status) {
			
			for ( var i in data) {
				city.append(" <option value='" + data[i].id + "'>"
						+ data[i].name + "</option>");
			}
		});
	}
	$('#city_province').length>0 &&$('#city_province').change(function() {
		reloadCity($(this).val());
	});
	
	$('#hot_city').length>0 &&$('#hot_city').change(function() {
		if($(this).val()!='-1'){
		reloadHotcity($(this).val());
		}
	});
	

	$('#selbtn').length>0&& $('#selbtn').click(function(){
		var city_city=$('#city_city');
		var city_city_sel=$('#city_city_sel');
		
		
		var currentGroup='';
		var citytype=$("#citytype").val();
		switch (citytype) {
		case '1':
			currentGroup=$('#hot_city option:selected').text();
			break;
		case '2':
			currentGroup=$('#province option:selected').text();
			break;
			
		case '3':
			currentGroup='直辖市';
			break;
			
		}
		
		var selectedid=new Array();
		$("#city_city_sel option").each(function() {
			selectedid.push($(this).val());
		});
		   $("#city_city option:selected").each(function() {
			   if($.inArray($(this).val(),selectedid)==-1){
				   city_city_sel.append(" <option value='" + $(this).val() + "'>"
							+ $(this).text() +"</option>");
			   }
	        });
	});
	$('#delbtn').length>0&& $('#delbtn').click(function(){
		var city_city_sel=$('#city_city_sel');
		$("#city_city_sel option:selected").remove();
	});
	$('#emptybtn').length>0&& $('#emptybtn').click(function(){
	$('#city_city_sel').empty();
	});
	$('#allbtn').length>0&& $('#allbtn').click(function(){
		$('#city_city option').attr("selected","selected");
		$('#selbtn').click();
	});
	$('#city_city').length>0 && $('#city_city').dblclick(function(){
		$('#selbtn').click();
	});
	$('#city_city_sel').length>0 && $('#city_city_sel').dblclick(function(){
		$('#delbtn').click();
	});
	
	$('#catalog_one').length>0&& catalogfunctions($('#catalog_one'),$('#catalog_two'));
	$('#vrs_catalog_one').length>0&& catalogfunctions($('#vrs_catalog_one'),$('#vrs_catalog_two'));

	function catalogfunctions(catalog_one,catalog_two){
		
		if(catalogs.length==0){
		$.post("./listVrsArea", {
		}, function(data, status) {
			$.each(data, function(i,val){
				catalogs.push(val);
			});
			
				reloadCataloglone();
		});
		}
		function reloadCataloglone(){
// var catalog_one=$('#catalog_one');
			var catalogOneArr= new Array();
			
			var catalog_one_ids=new Array();
			$.each(catalogs, function(i,val){
				if($.inArray(val.oneID,catalog_one_ids)==-1){
					catalog_one_ids.push(val.oneID);
					catalogOneArr.push( {id:val.oneID,name:val.oneName});
				}
			  });   
			$.each(catalogOneArr, function(i,val){
				catalog_one.append(" <option "+(i<4?"style='color:red;font-weight:bold;'":"")+"value='" + val.id + "'>"
						+ val.name + "</option>");
			  }); 
			catalog_one.multiselect("refresh");
		}
		catalog_one.on("multiselectclick", function(event, ui) {
			if($(this).multiselect('getChecked').length>1){
				catalog_two.empty();
				catalog_two.multiselect("refresh");
				return true;
			}
			var selectedOneId=($(this).multiselect('getChecked')).first().val();
// var catalog_two=$('#catalog_two');
			var catalogTwoArr= new Array();
			
			catalog_two.empty();
			
			var catalog_two_ids=new Array();
			$.each(catalogs, function(i,val){
				if($.inArray(val.twoID,catalog_two_ids)==-1 &&val.oneID==selectedOneId ){
					catalog_two_ids.push(val.twoID);
					catalogTwoArr.push( {id:val.twoID,name:val.twoName});
				}
			  }); 
			$.each(catalogTwoArr, function(i,val){
				catalog_two.append(" <option value='" + val.id + "'>"
						+ val.name + "</option>");
				  });  
			catalog_two.multiselect("refresh");
		});
	}
	function addParam_vrs(param){
		var index =param.length-1;
	var selectedtype=$('#vrstype').val();
		
		var typeid='"专辑维"."专辑ID"';
		var typename='"专辑维"."专辑"';
		switch (selectedtype) {
		case '2':
			 typeid='"专辑包维"."专辑包ID"';
			 typename='"专辑包维"."专辑包名称"';
			break;
		case '3':
			 typeid='"广告包维"."广告包ID"';
			 typename='"广告包维"."广告包名称"';
			break;

		default:
			break;
		}
		param[++index] = 'eq';
		param[++index] = encodeURIComponent(typeid);
		param[++index] = $('#vrstypeid').val();
		param[++index] = 'cany';
		param[++index] = encodeURIComponent(typename);
		param[++index] = encodeURIComponent(encodeURIComponent($(
				'#vrstypename').val()));
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"专辑维"."栏目ID"');
		
		var catalog_one_s=(String)($('#vrs_catalog_one').val());
		var catalog_one_arr=catalog_one_s.split(',');
		param[++index] = catalog_one_s=='null'?'':(catalog_one_arr.length+'+'+catalog_one_arr.join('+'));
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"专辑维"."产地ID"');
		var catalog_two_s=(String)($('#vrs_catalog_two').val());
		var catalog_two_arr=catalog_two_s.split(',');
		param[++index] = catalog_two_s=='null'?'':(catalog_two_arr.length+'+'+catalog_two_arr.join('+'));
		reportname+="专辑";
	}
	function addParam_city(param){
		var index =param.length-1;
				
			
		var selcityids=new Array();
		 $("#city_city_sel option").each(function() {
			 selcityids.push($(this).val());
	        });
		 
			param[++index] = 'cany';
			param[++index] = encodeURIComponent('"排期包区域定向维"."C_CODE"');
			param[++index] =selcityids.length>0?( selcityids.length+'+'+selcityids.join('+')):'';
		 reportname+="执行单执行城市完成情况";
		 $('#from').val()!=$('#to').val()&&(reportname+='1');
	}
	function addParam_city2(param){
		var index =param.length-1;
		
		
		var selcityids=new Array();
		$("#city_city_sel option").each(function() {
			selcityids.push($(this).val());
		});
		
		param[++index] = 'cany';
		param[++index] = encodeURIComponent('"城市维"."城市ID"');
		param[++index] =selcityids.length>0?( selcityids.length+'+'+selcityids.join('+')):'';
	}
	function addParam_projectGap(param){
		var index =0;

		param[++index] = 'bet';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = '2+' + $('#from').val() + '+' + $('#to').val();
		
		param[++index] = 'cany';
		param[++index] = encodeURIComponent('"执行单维"."执行单编号"');
		param[++index] = encodeURIComponent($('#projectnumber').val());
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"执行单维"."执行单ID"');
		param[++index] = $('#projectid').val();
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"执行单维"."BIID"');
		param[++index] = $('#bi').val()!='-1'?$('#bi').val():'';
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"执行单维"."媒介" ');
		param[++index] = $('#media').val()!='-1'?encodeURIComponent(encodeURIComponent($('#media option:selected').text())):'';
	}
	initTabSelect();
	function initTabSelect(){
		
		$('#tabSelect li').removeClass('selected');
		$("#tabSelect li").each(function(){
		   $(this).children().first().attr('href','./'+$(this).attr('id').replace('tab_',''));
		  });
		$('#tab_'+path).addClass('selected');
	}
	function initCommonLimit(){
		if(path=='projectdirection'){
			$('#contractnumber').parent().hide();
			$('#viewtype').parent().hide();
			$('div[id*=form_city]').show();
		}
		else if(path=='projectGap2'){
			$('div[id*=form_city]').show();
		}
		else if(path=='salesmonitor'){
			$('#salesmonitor_type1').removeAttr('checked');
			$('#salesmonitor_type2').removeAttr('checked');
//			loadframe();
		}
		else if(path=='projectPkgDetailData'){
			$('div[id*=form_city]').show();
			$('#city_state').parent().hide();
		}
		else if(path=='packageDetailData'){
			$('div[id*=form_city]').show();
			$('#city_state').parent().hide();
		}
	}
	initCommonLimit();
		$('#citytype').change(function(){
			var citytype=$(this).val();
			var hot_city=$('#hot_city');
			var province=$('#province');
			hot_city.children().first().attr('selected','selected');
			province.children().first().attr('selected','selected');
			switch (citytype) {
			case '1':
				province.parent().hide();
				hot_city.parent().show();
				
				break;
			case '2':
				hot_city.parent().hide();
				province.parent().show();
				
				break;
			case '3':
				hot_city.parent().hide();
				province.parent().hide();
				var city_city=$('#city_city');
				city_city.empty();
				city_city.append(" <option value='110000'>北京市</option>");
				city_city.append(" <option value='120000'>天津市</option>");
				city_city.append(" <option value='310000'>上海市</option>");
				city_city.append(" <option value='500000'>重庆市</option>");
				break;
			default:
				break;
			}
		}).change();
		
		$('#salesmonitor_type1,#salesmonitor_type2').click(function (){
			loadframe();
		});
		
});