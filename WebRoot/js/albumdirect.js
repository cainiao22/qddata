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
		// $('body,html').animate({
		// scrollTop: 900
		// }, 2000);
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
		case 'albumdirect':
			return create_albumdirect_url();
			break;
		}
	}

	function create_albumdirect_url() {
		var param = new Array();
		setCommonLimit(param);

		fillreportname();

		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path, param);
	}
	function fillreportname() {
		if( $('#byday').attr("checked")=='checked'){
			prepath=prepath.replace('消耗量报表/', '消耗量报表(日报)/');
		}else{
			prepath=prepath.replace('消耗量报表(日报)/', '消耗量报表/');
			
		}
		
		reportname += '专辑定投散投报表';

	}
	function setCommonLimit(param) {
		var index = 0;
		param[++index] = 'bet';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = '2+' + $('#from').val() + '+' + $('#to').val();
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"专辑定向维"."专辑名称"');
		param[++index] =encodeURIComponent(encodeURIComponent($('#album').val()));

	}
	$("#from").datepicker({
		changeYear : true,
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		showOtherMonths:true,
		onClose : function(selectedDate) {
			$("#to").datepicker("option", "minDate", selectedDate);
		}
	}).datepicker('setDate', -1);
	$("#to").datepicker({
		changeYear : true,
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		showOtherMonths:true
	}).datepicker('setDate', -1);
});