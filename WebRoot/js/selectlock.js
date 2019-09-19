$(function() {

	$(
			'#common_platform_one,#common_platform_two,#platform_one,#platform_two,#adstyle')
			.change(
					function() {

						var common_platform_oneObj = $('#common_platform_one');
						var common_platform_twoObj = $('#common_platform_two');
						var common_platform_threeObj = $('#common_platform_three');
						var platform_oneObj = $('#platform_one');
						var platform_twoObj = $('#platform_two');
						var platform_threeObj = $('#platform_three');
						var adstyleObj = $('#adstyle');
						// var durationObj = $('#duration');
						var scopeObj = $('#scope');

						var common_platform_one = common_platform_oneObj.val();
						var common_platform_two = common_platform_twoObj.val();
						var platform_one = platform_oneObj.val();
						var platform_two = platform_twoObj.val();
						var adstyle = adstyleObj.val();

						selectunlock([ scopeObj, common_platform_twoObj,
								common_platform_threeObj, platform_twoObj,
								platform_threeObj ]);
						// lockrule([adstyle != 'oad' && adstyle != '-1' ],
						// [ durationObj ]);
						lockrule([ common_platform_one == '1',
								common_platform_two == '11' ],
								[ common_platform_threeObj ]);
						// lockrule([ common_platform_one == '3'],
						// [ common_platform_twoObj,common_platform_threeObj
						// ,scopeObj]);
						// lockrule([ common_platform_one == '2'],
						// [ scopeObj]);
						lockrule([ platform_one == '1', platform_two == '11' ],
								[ platform_threeObj ]);
						// lockrule([platform_one == '3'],
						// [platform_twoObj,platform_threeObj ,scopeObj]);
						// lockrule([platform_one == '2'],
						// [ scopeObj]);
					});

	function lockrule(ifarr, lockobjarr) {
		var check = true;
		for ( var i = 0; i < ifarr.length; i++) {
			check = check && ifarr[i];
		}

		check && selectlock(lockobjarr);

	}

	function selectlock(lockobjarr) {
		for ( var i = 0; i < lockobjarr.length; i++) {
			lockobjarr[i].children('option').first().attr('selected',
					'selected');
			lockobjarr[i].attr("disabled", true);
		}
	}
	function selectunlock(lockobjarr) {
		for ( var i = 0; i < lockobjarr.length; i++) {
			lockobjarr[i].removeAttr("disabled");
		}
	}

	$('#duration_select').length > 0 && initDurationSelect();
	function initDurationSelect() {
		var du = $('#duration_select');
		var durationArr = new Array();
		window.durationArr = durationArr;
		du.find('option').each(function() {
			durationArr.push({
				v : $(this).val(),
				n : $(this).text()
			});
		});
	}

	// 处理不同平台控制时长范围下拉框

	$('#common_platform_one,#platform_one').change(
			function() {
				var durationArr = window.durationArr;
				var platform = $(this).val();
				var du = $('#duration_select');
				if (du.length == 0) {
					return;
				}
				du.removeAttr("disabled");
				du.empty();
				switch (platform) {
				case "-1":

					for ( var i = 0; i < durationArr.length; i++) {
						du.append('<option value=' + durationArr[i]['v'] + '>'
								+ durationArr[i]['n'] + '</option>');
					}
					du.find('option:first').attr("selected", "selected");
					du.attr("disabled", true);
					break;
				case "1":
					for ( var i = 0; i < durationArr.length; i++) {
						if (i < 9 || i > 11) {
							du.append('<option value=' + durationArr[i]['v']
									+ '>' + durationArr[i]['n'] + '</option>');
						}
					}
					break;
				case "2":
					for ( var i = 0; i < durationArr.length; i++) {
						if (i == 0 || i > 8) {
							du.append('<option value=' + durationArr[i]['v']
									+ '>' + durationArr[i]['n'] + '</option>');
						}

					}
					break;
				case "3":
					for ( var i = 0; i < durationArr.length; i++) {
						if (i == 0 || i > 8) {
							du.append('<option value=' + durationArr[i]['v']
									+ '>' + durationArr[i]['n'] + '</option>');
						}

					}
					break;

				default:
					for ( var i = 0; i < durationArr.length; i++) {
						du.append('<option value=' + durationArr[i]['v'] + '>'
								+ durationArr[i]['n'] + '</option>');
					}
					du.find('option:first').attr("selected", "selected");
					du.attr("disabled", true);
					break;
				}
			});

	$('#from_hour').length > 0 && $('#to_hour').length > 0 && initHourSelect();
	function initHourSelect() {

		for ( var i = 0; i < 24; i++) {
			$('#from_hour,#to_hour').append(
					'<option value="' + (i + 1) + '">' + i + '</option>');
		}
		$('#from_hour').change(
				function() {
					$('#to_hour option:gt(0)').remove();
					var from_hour = $(this).val();
					for ( var i = from_hour - 1; i < 24; i++) {
						if (i < 0) {
							continue;
						}
						$('#to_hour').append(
								'<option value="' + (i + 1) + '">' + i
										+ '</option>');
					}
				});
	}

	// path.indexOf('uv') < 0 && lockSelect_State();
	// function lockSelect_State() {
	// $('#city_state').parent().hide();
	// }

	path.indexOf('vvplatform') > -1 && lock_platformVV();
	function lock_platformVV() {
		// $('#adstyle').parent().remove();
		$('#scope').parent().remove();
	}
	path.indexOf('vvplatform') > -1 && lock_adstyle('oad');
	function lock_adstyle(v) {
		$('#adstyle').empty();
		$('#adstyle').append('<optgroup label="框内">').append(
				'<option value="oad">前贴片</option>');
		$("#adstyle[multiple=multiple]").multiselect({
			minWidth : 'auto',
			height : '20'
		}).multiselect("refresh");

	}
	path.indexOf('hourdata') > -1 && lockhourdata();
	function lockhourdata() {
		$('#scope').parent().remove();
	}
	path.indexOf('click') == 0 && lockclick();
	function lockclick() {
		$('#duration_select,#scope').parent().remove();
	}

	path.indexOf('logicalStockplatform') == 0
			&& $('#adstyle').parent().remove()
			&& $('#adsplatform option:first').remove();
	$('#splitByProvince').length > 0
			&& $('#splitByProvince').change(
					function() {
						if ($(this).attr('checked') == 'checked') {
							$(this).parent().prev().find(
									'#city_state option:first').attr(
									"selected", "selected");
							$(this).parent().prev().find('#city_state')
									.change();
							$('#splitByCity').removeAttr('checked');
						}
					});
	$('#splitByCity').length > 0
			&& $('#splitByCity').change(
					function() {
						if ($(this).attr('checked') == 'checked') {
							$(this).parent().prev().find(
									'#city_state option:first').attr(
									"selected", "selected");
							$(this).parent().prev().find('#city_state')
									.change();
							$('#splitByProvince').removeAttr('checked');
						}
					});

	(path == 'worldcupAdvanced') && ($('#form_vrs').remove())
			&& ($('#vrstype').parent().prev().removeAttr('style'));
	path.indexOf('packageDetailData') > -1
			&& $('#reporttype').change(function() {
				var type = $(this).val();
				if (type == '排期包消耗明细报表') {
					$('#pkgtype option').removeAttr('disabled');
				} else {
					$('#pkgtype option:gt(2)').attr('disabled', 'disabled');
				}
				$("#pkgtype[multiple=multiple]").multiselect('refresh');

			}).change();
	(path == 'posadcatalog') && ($('#form_common2').remove());

	if (path == 'projectPkgDetailData') {
		$('#reporttype').change(function() {
			var lastday = -1;
			var reporttype = $('#reporttype').val();
			if (reporttype == '2') {
				var b = new Date();
				var c = b.getDay();
				lastday = c * -1;
			} else if (reporttype == '3') {
				var b = new Date();
				var c = b.getDate();
				lastday = c * -1 + 1;
			}

			$("#from").datepicker("destroy");
			$("#from").datepicker({
				changeYear : true,
				changeMonth : true,
				dateFormat : "yymmdd",
				beforeShowDay : function(date) {
					if (reporttype == '1') {
						return [ 1, '', '' ];
					} else if (reporttype == '2') {
						var b = date.getDay();
						return b != 0 ? [ 0, '', '' ] : [ 1, '', '选择本周' ];
					}
					if (reporttype == '3') {
						var b = date.getDate();
						return b != 1 ? [ 0, '', '' ] : [ 1, '', '选择本月' ];
					}
				},
				showButtonPanel : true
			}).datepicker('setDate', lastday);

		}).change();
	}

	(path == 'wanmeifengbao') && ($('div[id*=form_city]').show())
			&& ($('#city_state').parent().hide());
	(path == 'adfunnelreport')
			&& (($('#adsplatform option[value=0],#adsplatform option[value=5]').remove()))&&$("#adsplatform[multiple=multiple]").multiselect("refresh");
	(path == 'clickplatform') && $('#reporttype').change(function() {
		var rt = $('#reporttype').val();
		if (rt == '平台报表') {
$('#form_city1,#form_city2,#form_city3,#form_catalog,#form_project').hide();
		} else if (rt == '平台城市报表') {
			$('#form_catalog,#form_project').hide();
			$('#form_city1,#form_city2,#form_city3').show();

		} else if (rt == '平台栏目报表') {
			$('#form_city1,#form_city2,#form_city3,#form_project').hide();
			$('#form_catalog').show();
		} else if (rt == '平台执行单报表') {
			$('#form_city1,#form_city2,#form_city3,#form_catalog').hide();
			$('#form_project').show();

		}
	}).change();
	
	(path == '56reportV2'||path=='userpoireport'|| path=='mobilestockbycity'|| path=='pkgBookAndConsumeSummary'|| path=='pkgBookAndConsumeDetail')&& ($('div[id*=form_city]').show())
	&& ($('input[name=queryType][value=3]').parent().hide());
	(path == 'wapAndNewsapp')&& ($('#adsplatform option[value=0],#adsplatform option[value=5]').remove())
	&& ($('#clienttype option[value=0],#clienttype option[value=3],#clienttype option[value=4]').remove());
});