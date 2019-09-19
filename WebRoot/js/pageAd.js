$(function() {
	var platforms=new Array();
	window.platforms=platforms;
	
	var catalogs=new Array();
	window.catalogs=catalogs;
	
	var prepath='手搜app报表/';
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
// $('body,html').animate({
// scrollTop: 900
// }, 2000);
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
		case 'pageAd':
			prepath='UV报表/';
			return create_pageAd_url();
		case 'mobileEffect':
			
			prepath='手搜app报表/';
			return create_mobileEffect_url();
			break;
		case 'pkg-pagead':
			prepath='UV报表/';
			return create_pkgpageAd_url();
			break;
		case 'voiceAdSkipReport':
			prepath='喊跳报表/';
			return create_voiceAdSkipReport_url();
			break;
		case 'MobileRequestAndStock':
			prepath='消耗量报表/';
			return create_MobileRequestAndStock_url();
			break;
		case 'mobileRequestRPT':
			prepath='消耗量报表/';
			return create_mobileRequestRPT_url();
			break;
		case 'mobileShortVedioReport':
			prepath='消耗量报表/';
			return create_mobileShortVedioReport_url();
			break;
		case 'newsappmonitor':
			prepath='手搜app报表/';
			return create_newsappmonitor_url();
			break;
		case 'groupMobileReport':
			prepath='手搜app报表/';
			return create_groupMobileReport_url();
			break;
	}
	}
	function create_pageAd_url(){
		var param = new Array();
		setCommonLimit(param);
		
		
		
		reportname+='页面广告位曝光报表';
		
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function create_newsappmonitor_url(){
		var param = new Array();
		setCommonLimit(param);
		reportname+='新闻app库存消耗监控报表';
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function create_groupMobileReport_url(){
		var param = new Array();
		setCommonLimit(param);
		reportname+=$('#reporttype').val();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function create_pkgpageAd_url(){
		var param = new Array();
		setCommonLimit(param);
		
		
		
		reportname+='排期包页面广告位曝光报表';
		
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function create_MobileRequestAndStock_url(){
		var param = new Array();
		setCommonLimit(param);
		
		var s=(String)($('#adsplatform').val());
		var adsplatformArr=s.split(',');
		if(s!='null' && adsplatformArr.length==1){
			reportname+='移动端请求及库存报表_分版本_'+$('#reporttype').val();
			
		}else{
		
		reportname+='移动端请求及库存报表_分平台_'+$('#reporttype').val();
		}
		
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function create_mobileRequestRPT_url(){
		var param = new Array();
		setCommonLimit(param);
		
		
		
		reportname+='移动端请求上报报表';
		
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function create_mobileEffect_url(){
		var param = new Array();
		setCommonLimit(param);

		var index=param.length-1;
		param[++index] = 'le';
		param[++index] = encodeURIComponent('"手搜LINE维"."开始时间"');
		param[++index] = $('#to').val();
		param[++index] = 'ge';
		param[++index] = encodeURIComponent('"手搜LINE维"."结束时间"');
		param[++index] = $('#from').val();
		
		reportname+=$('#reporttype').val();
		
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function create_voiceAdSkipReport_url(){
		var param = new Array();
		setCommonLimit(param);
		reportname+=$('#reporttype').val();;
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function create_mobileShortVedioReport_url(){
		var param = new Array();
		setCommonLimit(param);
		reportname='移动端短视频报表';
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function setCommonLimit(param) {
		var index=0;

	
		if(!$('#to').is(':hidden'))
		{
		param[++index] = 'bet';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = '2+' + $('#from').val() + '+' + $('#to').val();
		}else{
			
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"日期维"."DAY"');
			param[++index] = $('#from').val();
		}
		if($('#adsplatform').length>0 && $('#adsplatform').attr('multiple')!='multiple'){
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"排期包平台组合维"."ADS_PLAT_ID"');
		param[++index] = encodeURIComponent(encodeURIComponent($('#adsplatform').val()=='-1'?'':$('#adsplatform').val()));
		}
		if($('#adsplatform').length>0 && $('#adsplatform').attr('multiple')=='multiple'){
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"排期包平台组合维"."ADS_PLAT_ID"');
			var s=(String)($('#adsplatform').val());
			var adsplatformArr=s.split(',');
			param[++index] = s=='null'?'':(adsplatformArr.length+'+'+adsplatformArr.join('+'));
		}
		if($('#sver').length>0){
			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"事实表_移动端请求与上报数据报表"."APP_VERSION"');
			var s=(String)($('#sver').val());
			var sverArr=s.split(',');
			param[++index] = s=='null'?'':(sverArr.length+'+'+sverArr.join('+'));
		}
	}
	$("#from").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		onClose : function(selectedDate) {
			$("#to").datepicker("option", "minDate", selectedDate);
		}
	}).datepicker('setDate', path=='groupMobileReport'?-2:-1);
	$("#to").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true
	}).datepicker('setDate', path=='groupMobileReport'?-2:-1);
	(path=='mobileRequestRPT' || path=='MobileRequestAndStock')&&($('#adsplatform option:eq(0),#adsplatform option:eq(1)').remove()&&$('#adsplatform').multiselect('refresh'));
	(path=='MobileRequestAndStock')&&initMobileRequestAndStock();
	function initMobileRequestAndStock(){
		
		for(var i=0;i<mobileSverList.length;i++){
			$('#sver').append('<option value="'+mobileSverList[i]['sver']+'">'+mobileSverList[i]['sver']+'</option>');
	}
		
		
		$('#adsplatform').attr('multiple','multiple');
		$('#adsplatform,#sver').multiselect({
		minWidth:'auto',
		height:'300',
		noneSelectedText:'不限'
	}).multiselectfilter({
		label: '筛选:',
		width: '100', /*
						 * override default width set in css file (px). null
						 * will inherit
						 */
		placeholder: '输入关键字',
		autoReset: false
	}).multiselect("uncheckAll"); 
		$('#adsplatform').multiselect({
			close:function (event,ui){
				$('#sver').empty();
				var v=(String)($(this).val());
				var arr=v.split(',');
				if(v!='null' &&  arr.length==1){
					for(var i=0;i<mobileSverList.length;i++){
						if(mobileSverList[i]['platid']==v){
							$('#sver').append('<option value="'+mobileSverList[i]['sver']+'">'+mobileSverList[i]['sver']+'</option>');
						}
					}
					
				}else{
					for(var i=0;i<mobileSverList.length;i++){
							$('#sver').append('<option value="'+mobileSverList[i]['sver']+'">'+mobileSverList[i]['sver']+'</option>');
					}
				}
				$('#sver').multiselect("refresh");
			}
		});
		}
	
	(path=='groupMobileReport')&&$('#reporttype').change(function(){
		var rname=$('#reporttype option:selected').text();
		if(rname.indexOf('周报')>-1){
			$('#from').datepicker('option','beforeShowDay',
				 function (date) {
			   return [date.getDay()!=1?0:1,''];
				 }).datepicker( "refresh" );
			$('#to').parent().hide();
			$('#from').datepicker('setDate',new Date().getDay()*-1-6+'d');
			
		}else{
			$('#to').parent().show();
			$('#from').datepicker('option','beforeShowDay',function (date){return [1,''];});
			
		}
		
	}).change();
});