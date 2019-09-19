$(function() {
	var platforms = new Array();
	window.platforms = platforms;

	var catalogs = new Array();
	window.catalogs = catalogs;

	var prepath = '消耗量报表/';
	var reportname = '';
	window.reportname = reportname;

	$("#framemain").load(function() {
		$('#loading').hide();
		$('#framemain').show();
	});
	function loadframe() {

		reportname = '';
		var url = createurl();
		$('#framemain').hide();
		$('#loading').show();
		$('#framemain').attr("src", url);
	}
	$('#submitBtn').click(function() {
		loadframe();
	});
	function parsefullurl(path, param) {
		var url = window.config.bieehost
				+ window.config.commonPages.replace('{#path}', path);
		param[0] = (param.length - 1) / 3;
		for ( var i = 0; i < param.length; i++) {
			url += '&P' + i + '=' + param[i];
		}
		url += '&rand=' + Math.random();
		return url;
	}
	function createurl() {
		switch (path) {
		case 'worldcupVrsCity':
			return create_worldcupVrsCity_url();
			break;
		case 'worldcupVrs':
			return create_worldcupVrs_url();
			break;
		case 'worldcupProject':
			return create_worldcupProject_url();
			break;
		case 'worldcupOAD':
			return create_worldcupOAD_url();
			break;
		case 'worldcupCity':
			return create_worldcupCity_url();
			break;
		case 'worldcupAdvanced':
			return create_worldcupAdvanced_url();
			break;
		}
	}
	function fillreportname() {
		if( $('#byday').attr("checked")=='checked'){
			prepath=prepath.replace('消耗量报表/', '消耗量报表(日报)/');
		}else{
			prepath=prepath.replace('消耗量报表(日报)/', '消耗量报表/');
			
		}
		reportname += '报表';

	}
	function create_worldcupVrsCity_url() {
		var param = new Array();
		setCommonLimit(param);
		reportname='世界杯专辑城市';
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path, param);
	}
	function create_worldcupVrs_url() {
		var param = new Array();
		setCommonLimit(param);
		reportname='世界杯专辑';
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path, param);
	}
	function create_worldcupProject_url() {
		var param = new Array();
		setCommonLimit(param);
		reportname='世界杯执行单';
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path, param);
	}

	function create_worldcupOAD_url() {
		var param = new Array();
		setCommonLimit(param);
		reportname='世界杯前贴片日报';
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path, param);
	}
	function create_worldcupCity_url() {
		var param = new Array();
		setCommonLimit(param);
		reportname='世界杯城市';
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path, param);
	}
	function create_worldcupAdvanced_url() {
		var param = new Array();
		setCommonLimit(param);
		addParam_vrs(param);
		addParam_appid(param);
		reportname='世界杯综合查询';
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path, param);
	}
		function setCommonLimit(param) {
			var index=0;
			param[++index] = 'bet';
			param[++index] = encodeURIComponent('"日期维"."DAY"');
			param[++index] = '2+' + $('#from').val() + '+' + $('#to').val();
			
			if($('#adstyle').length>0){
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"广告形式维"."广告形式代码"');
			var s=(String)($('#adstyle').val());
			var adstyleArr=s.split(',');
			param[++index] = s=='null'?'':(adstyleArr.length+'+'+adstyleArr.join('+'));
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
			if($('#adsplatform').length>0){
				param[++index] = 'eq';
				param[++index] = encodeURIComponent('"排期包平台组合维"."ADS_PLAT_ID"');
				var s=(String)($('#adsplatform').val());
				var adsplatformArr=s.split(',');
				param[++index] = s=='null'?'':(adsplatformArr.length+'+'+adsplatformArr.join('+'));
			}
			
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
			var oparr=['eq','ge','le','gt','lt','neq'];
			param[++index] = oparr[$('#idlimit').val()-1];
			param[++index] = encodeURIComponent(typeid);
			
			var s=$('#vrstypeid').val();
			s=s.replace(/\s+/g, ',').replace(/，/g,',');
			var idlimitvArr=s.split(',');
			param[++index] = s==''?'':(idlimitvArr.length+'+'+idlimitvArr.join('+'));
			
			
			
			 oparr=['eq','cany','neq','bwith','ewith','neq'];;
			param[++index] =oparr[$('#vrsnamelimit').val()-1];
			param[++index] = encodeURIComponent(typename);
			s=$('#vrstypename').val();
			s=s.replace(/\s+/g, ',').replace(/，/g,',');
			 s=encodeURIComponent(encodeURIComponent(s));
			var vrsnamevArr=s.split('%252C');
			param[++index] = s==''?'':(vrsnamevArr.length+'+'+vrsnamevArr.join('+'));
			
		}
		function addParam_appid(param){
			var index =param.length-1;
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"应用类型维"."APP_ID"');
			param[++index] = encodeURIComponent($('#appid').val()=='-1'?'':$('#appid').val());
		}
	$("#from").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel : true,
		onClose : function(selectedDate) {
			$("#to").datepicker("option", "minDate", selectedDate);
		}
	}).datepicker('setDate', -1);
	$("#to").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel : true
	}).datepicker('setDate', -1);

	$('#duration_select').length>0&&$('#duration_select').change(function(){
var durationSel=$(this).val();
		
		if(durationSel=='-2'){
			$('#diyDuration').show(200);
		
		}else{
			$('#diyDuration').hide(200);
		}
	});

	
	$('#vrstypeid,#vrstypename').qtip({
		   content: '(多)表示可以输入多个参数，如输入多个，请用逗号或者空格隔开',
		   show: 'mouseover',
		   hide: 'mouseout',
		   style: { 
			   color: '#6699CC',
			   width:190
		   },
		  
		   position: {
			      corner: {
			         target: 'bottomLeft',
			         tooltip: 'topLeft'
			      }
		   }
		});
	$('#diyDuration').qtip({
		content: '请分别输入一个整数，如:从10到20 表示时长大于等于10\'00"且小于等于19\'59"',
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