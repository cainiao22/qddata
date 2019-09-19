$(function() {
	var platforms = new Array();
	window.platforms = platforms;

	var catalogs = new Array();
	window.catalogs = catalogs;

	var prepath = 'UV报表/';
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
		case 'adeffect':
			return create_adeffect_url();
			break;
		}
	}

	function create_adeffect_url() {
		var param = new Array();
		setCommonLimit(param);

		fillreportname();

		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path, param);
	}
	function fillreportname() {
		var reporttypeArr = {
			1 : '广告推广效果报表',
			2 : '广告推广交集UV报表'

		};
		reportname += reporttypeArr[$('#reporttype').val()];

	}
	function setCommonLimit(param) {
		var index = 0;
		param[++index] = 'bet';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = '2+' + $('#from').val() + '+' + $('#to').val();

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
	$("#to").datepicker({
		changeYear : true,
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		showOtherMonths:true,
		numberOfMonths:3,
		showCurrentAtPos:1
	}).datepicker('setDate', -1);
});