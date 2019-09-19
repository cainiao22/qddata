$(function() {

	var reportname = '';
	window.reportname = reportname;
	var prepath='收入报表/';

	$("#framemain").load(function() {
		$('#loading').hide();
		$('#framemain').show();
	});

	function loadframe() {
		if(window.config.portalPages.indexOf('{#username}')>0){
			return;
		}
		reportname = '';
		var url = createurl();
		$('#framemain').hide();
		$('#loading').show();
		$('#framemain').attr("src", url);
		clearInterval(timer);
	}
	$('#submitBtn').click(function() {
		$('body,html').animate({
			scrollTop : 900
		}, 2000);
		loadframe();
		// $('#FilterTabs div:eq(0)').hide(500);
		// $('#FilterTabs div:eq(1)').hide(500);

	});
	var timer =  window.setInterval(loadframe,1000);
	//$('#submitBtn').click();
	function parsefullurl(path, param) {
		var url =window.config.bieehost+window.config.commonPages.replace('{#path}',path);


		// var title= $('#from').val() + '' + $('#to').val()+' ' +
		// (adstyle.val()==-1?'':adstyle.find("option:selected").text());
		// url+='&MYTITLE='+encodeURIComponent(title);
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
	function create_pkgAreaFlightReport_url() {

		var param = new Array();
		setCommonLimit(param);

		if(type==''||type=='1'){
		reportname += '排期包地域投放明细';
		}else if(type==''||type=='2'){
			reportname += '排期包散投地域投放明细';
		}

		fillreportname();

		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path, param);

	}
	
	function setCommonLimit(param) {
		var index = 0;
		param[++index] = 'bet';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = '2+' + $('#from').val() + '+' + $('#to').val();
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('case when ( "城市维"."城市编码" is null ) then \'00000000\'  when "城市维"."热点城市编码" in(\'A\',\'B\',\'K\') then "城市维"."城市编码" else \'99999999\' end');
		param[++index] = area;

		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"排期包与库存平台组合维"."平台名称"');
		param[++index] = plat;

	}
	function createurl() {
		switch (path) {
		case 'pkgAreaFlightReport':
			return create_pkgAreaFlightReport_url();
			break;
		default:
			break;
		}
	}
	$("#from").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel : true,
		onClose : function(selectedDate) {
			$("#to").datepicker("option", "minDate", selectedDate);
		}
	});
	$("#to").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel : true
	});

	

});