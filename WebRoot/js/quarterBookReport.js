$(function() {
	var platforms = new Array();
	window.platforms = platforms;

	var catalogs = new Array();
	window.catalogs = catalogs;

	var prepath = '收入报表/';
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
		case 'quarterBookReport':
			return create_quarterBookReport_url();
			break;
		}
	}

	function create_quarterBookReport_url() {
		var param = new Array();
		setCommonLimit(param);

		fillreportname();

		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path, param);
	}
	function fillreportname() {
		var reporttypeArr = {
			1 : '季度预订量报表',
			2 : '季度分城市预订量报表',
			3 : '季度分行业预订量报表'

		};
		reportname += reporttypeArr[$('#reporttype').val()];

	}
	function setCommonLimit(param) {
		var index = 0;
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = $('#from').val();

	}
	$("#from").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel : true,
		maxDate : -1
	}).datepicker('setDate', -1);
});