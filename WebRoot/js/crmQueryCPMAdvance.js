$(function() {

	var reportname = '';
	window.reportname = reportname;
	var prepath='消耗量报表/';

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
	init();
	function init(){
		switch (path) {
		case 'crmQueryCPMAdvance':
			$('#reportName').text('CRM询量汇总报表');
			break;
		case 'crmQueryCPMLeft':
			$('#reportName').text('CRM询量剩余库存分析报表');
			break;
		default:
			return;
		}
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
			reportname += '报表';
	}
	function createurl() {
		switch (path) {
		case 'crmQueryCPMAdvance':
			return create_crmQueryCPMAdvance_url();
			break;
		case 'crmQueryCPMLeft':
			return create_crmQueryCPMLeft_url();
			break;
		default:
			return;
		}
		
	}
	function create_crmQueryCPMAdvance_url(){
		var param = new Array();
		setCommonLimit(param);	
		var index =param.length-1;
			param[++index] = 'gt';
			param[++index] = encodeURIComponent('"事实表_询量单数据"."CATEGORY_ID"');
			param[++index] = -1*Math.random(); 
			param[++index] = 'gt';
			param[++index] = encodeURIComponent(' "事实表_询量单专辑数据"."ALBUM_ID"');
			param[++index] = -1*Math.random(); 
			
			if($('#others1').attr('checked')){
		reportname +='CRM询量汇总';	
		}
			else{
			
			reportname +='CRM询量专辑';	
		}
		fillreportname();
		var path = encodeURIComponent(prepath  + reportname);
		return parsefullurl(path, param);
	}
	function create_crmQueryCPMLeft_url(){
		var param = new Array();
		setCommonLimit(param);	
		reportname +='CRM询量剩余库存分析';
		fillreportname();
		var path = encodeURIComponent(prepath  + reportname);
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
			
			if($('#adsplatform').length>0){
				param[++index] = 'eq';
				param[++index] = encodeURIComponent('"排期包平台组合维"."ADS_PLAT_ID"');
				var s=(String)($('#adsplatform').val());
				var adsplatformArr=s.split(',');
				param[++index] = s=='null'?'':(adsplatformArr.length+'+'+adsplatformArr.join('+'));
			}
			
			if($('#crmCatalog').length>0){
				if($('input[name=others]').length==0||$('#others1').attr('checked')){
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"询量栏目维"."栏目ID"');
			var s=(String)($('#crmCatalog').val());
			var crmCatalogArr=s.split(',');
			param[++index] = s=='null'?'':(crmCatalogArr.length+'+'+crmCatalogArr.join('+'));
				}
			}
			if($('#crmCity').length>0){
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"城市维"."城市ID"');
			var s=(String)($('#crmCity').val());
			var crmCityArr=s.split(',');
			param[++index] = s=='null'?'':(crmCityArr.length+'+'+crmCityArr.join('+'));
			}
			if($('#crmAlbum').length>0){
				if($('input[name=others]').length==0||$('#others2').attr('checked')){
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"询量专辑维"."专辑缩写"');
			var s=(String)($('#crmAlbum').val());
			var crmAlbumArr=s.split(',');
			param[++index] = s=='null'?'':(crmAlbumArr.length+'+'+crmAlbumArr.join('+'));
				}
			}
			if($('#albumid').length>0){
				if($('input[name=others]').length==0||$('#others2').attr('checked')){
					param[++index] = 'eq';
					param[++index] = encodeURIComponent('"询量专辑维"."专辑ID"');
					param[++index] = $('#albumid').val() ;
				
				}
			}
			if($('#albumname').length>0){
				if($('input[name=others]').length==0||$('#others2').attr('checked')){
					param[++index] = 'cany';
					param[++index] = encodeURIComponent('"询量专辑维"."专辑"');
					param[++index] = encodeURIComponent($('#albumname').val());
				}
			}
	}
$("#crmCatalog[multiple=multiple],#crmCity[multiple=multiple],#crmAlbum[multiple=multiple]").multiselect({
		 minWidth:'auto',
		 height:'300'
	 }).multiselectfilter({
		 label: '筛选:',
	      width: '100', /* override default width set in css file (px). null will inherit */
	      placeholder: '输入关键字',
	      autoReset: false
	 }).multiselect("uncheckAll"); 

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
$("#to").datepicker({
	changeYear : true,
	changeMonth : true,
	dateFormat : "yymmdd",
	showButtonPanel:true,
	showOtherMonths:true,
	numberOfMonths:3,
	showCurrentAtPos:1
}).datepicker('setDate', -1);

$('input[name=others]').click(function(){
	if($(this).attr('id')=='others1'){
		$('#form_album').hide();
		$('#form_catalog').show(500);
	}else{
		$('#form_catalog').hide();
		$('#form_album').show(500);
	}
	
});
$('#others1').click();
$("#albumid").keyup(function(){  
	$(this).val($(this).val().replace(/\D|^0/g,''));
}).bind("paste",function(){  //CTR+V事件处理  
    $(this).val($(this).val().replace(/\D|^0/g,''));   
}).css("ime-mode", "disabled"); //CSS设置输入法不可用  
});