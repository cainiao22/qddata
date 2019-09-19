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
		$('body,html').animate({
			scrollTop : 900
		}, 2000);
		loadframe();
		// $('#FilterTabs div:eq(0)').hide(500);
		// $('#FilterTabs div:eq(1)').hide(500);

	});
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
		if (reporttype == '1') {
			reportname += '收入报表';
		} else {
			reportname += '消耗量报表';
		}

	}
	function create_bookpackage_url() {

		var param = new Array();
		setCommonLimit(param);

		reportname += '排期包';

		fillreportname();

		var path = encodeURIComponent('收入报表/' + reportname);
		return parsefullurl(path, param);

	}
	function create_stockvrs_url() {

		var param = new Array();
		setCommonLimit(param);

		addParam_vrs(param);

		addOtherDimensionParam(param);

		fillreportname();

		var path = encodeURIComponent(prepath  + reportname);
		return parsefullurl(path, param);

	}
	function create_city_url() {
		var param = new Array();
		setCommonLimit(param);
		addParam_city(param);
		addOtherDimensionParam(param);
		fillreportname();
		var path = encodeURIComponent(prepath  + reportname);
		return parsefullurl(path, param);

	}
	function create_platform_url() {
		var param = new Array();
		setCommonLimit(param);
		addParam_platform(param);
		addOtherDimensionParam(param);
		fillreportname();
		var path = encodeURIComponent(prepath  + reportname);
		return parsefullurl(path, param);

	}
	function create_channel_url() {
		var param = new Array();
		setCommonLimit(param);
		addParam_channel(param);
		addOtherDimensionParam(param);
		fillreportname();
		var path = encodeURIComponent(prepath  + reportname);
		return parsefullurl(path, param);

	}
	function create_catalog_url() {
		var param = new Array();
		setCommonLimit(param);
		addParam_catalog(param);
		addOtherDimensionParam(param);
		fillreportname();
		var path = encodeURIComponent(prepath  + reportname);
		return parsefullurl(path, param);
	}
	function create_stockcatalog_url() {
		var param = new Array();
		setCommonLimit(param);
		addParam_catalog(param);
		addOtherDimensionParam(param);
		fillreportname();
		var path = encodeURIComponent(prepath  + reportname);
		return parsefullurl(path, param);
	}
	function setCommonLimit(param) {
		var index = 0;
		param[++index] = 'bet';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = '2+' + $('#from').val() + '+' + $('#to').val();
		param[++index] = 'cany';
		param[++index] = encodeURIComponent('"排期包维"."执行单ID"');
		param[++index] = projectId;

		param[++index] = 'cany';
		param[++index] = encodeURIComponent('"排期包维"."定向城市ID"');
		param[++index] = $('#city').val();

	}
	function createurl() {
		switch (path) {
		case 'bookpackage':
			return create_bookpackage_url();
			break;
		case 'city':
			return create_city_url();
			break;
		case 'platform':
			return create_platform_url();
			break;
		case 'channel':
			return create_channel_url();
			break;
		case 'catalog':
			return create_catalog_url();
			break;
		case 'stockvrs':
			return create_stockvrs_url();
			break;
		case 'stockcatalog':
			return create_stockcatalog_url();
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
	$('#city_province').length > 0 && $('#city_province').change(function() {
		reloadCity($(this).val());
	});

	$('#hotcity').change(function() {
		if ($(this).attr('checked') == 'checked') {
			// $('#city_state,#city_province,#city_city').attr("disabled",true);

			$('#form_city2,#form_city3').hide(500);
		} else {
			// $('#city_state,#city_province,#city_city').attr("disabled",false);
			$('#form_city2,#form_city3').show(500);
		}
	});

	$('#hotcity').length > 0 && $('#hotcity').removeAttr('checked');
	$('#platform_one').length > 0
			&& platformfunctions($('#platform_one'), $('#platform_two'),
					$('#platform_three'), $('#platform_four'));
	$('#common_platform_one').length > 0
			&& platformfunctions($('#common_platform_one'),
					$('#common_platform_two'), $('#common_platform_three'),
					$('#common_platform_four'));
	function platformfunctions(platform_one, platform_two, platform_three,
			platform_four) {
		if (platforms.length == 0) {
			$.post("./listPlatform", {}, function(data, status) {
				if (platforms.length == 0) {
					$.each(data, function(i, val) {
						platforms.push(val);
					});
				}

				reloadPlatformOne();

			});
		}
		platform_one.change(function() {
			var selectedOneId = $(this).val();
			if (selectedOneId < 0) {
				platform_two.find('option::gt(0)').remove();
				platform_two.find('option:first').attr("selected", "selected")
						.change();
				platform_three.find('option::gt(0)').remove();
				platform_three.find('option:first')
						.attr("selected", "selected").change();
				platform_four.find('option::gt(0)').remove();
				platform_four.find('option:first').attr("selected", "selected")
						.change();
			}
			reloadPlatformTwo();
			reloadPlatformThree();
			reloadPlatformFour();
		});

		function reloadPlatformOne() {
			var platformOneArr = new Array();

			var platform_one_ids = new Array();
			$.each(platforms, function(i, val) {
				if ($.inArray(val.oneID, platform_one_ids) == -1) {
					platform_one_ids.push(val.oneID);
					platformOneArr.push({
						id : val.oneID,
						name : val.oneName
					});
				}
			});
			$.each(platformOneArr, function(i, val) {
				platform_one.append(" <option value='" + val.id + "'>"
						+ val.name + "</option>");
			});

			platform_one.change();
		}
		function reloadPlatformTwo() {
			var platformTwoArr = new Array();

			platform_two.empty();

			var platform_two_ids = new Array();
			$.each(platforms, function(i, val) {
				if ($.inArray(val.twoID, platform_two_ids) == -1
						&& val.oneID == platform_one.val()) {
					platform_two_ids.push(val.twoID);
					platformTwoArr.push({
						id : val.twoID,
						name : val.twoName
					});
				}
			});
			platform_two.append(" <option value='-1'>全部</option>");
			$.each(platformTwoArr, function(i, val) {
				platform_two.append(" <option value='" + val.id + "'>"
						+ val.name + "</option>");
			});

		}

		function reloadPlatformThree() {
			platform_three.empty();
			var platformThreeArr = new Array();
			var platform_three_ids = new Array();
			$.each(platforms, function(i, val) {
				if ($.inArray(val.threeID, platform_three_ids) == -1
						&& val.oneID == platform_one.val()) {
					platform_three_ids.push(val.threeID);
					platformThreeArr.push({
						id : val.threeID,
						name : val.threeName
					});
				}
			});
			platform_three.append(" <option value='-1'>全部</option>");
			$.each(platformThreeArr, function(i, val) {
				platform_three.append(" <option value='" + val.id + "'>"
						+ val.name + "</option>");
			});
		}
		function reloadPlatformFour() {
			var platform_four_ids = new Array();
			platform_four.empty();
			platform_four.append(" <option value='-1'>全部</option>");
			var platformFourArr = new Array();

			$.each(platforms, function(i, val) {
				if ($.inArray(val.fourID, platform_four_ids) == -1
						&& val.oneID == platform_one.val()) {
					platform_four_ids.push(val.fourID);
					platformFourArr.push({
						id : val.fourID,
						name : val.fourName
					});
				}
			});

			$.each(platformFourArr, function(i, val) {
				platform_four.append(" <option value='" + val.id + "'>"
						+ val.name + "</option>");
			});

		}

	}

	$('#channel_one').length > 0 && channelfunctions();
	function channelfunctions() {
		var channels = new Array();
		window.channels = channels;
		$.post("./listChannel", {}, function(data, status) {
			$.each(data, function(i, val) {
				channels.push(val);
			});

			reloadChannelone();
		});
		function reloadChannelone() {
			var channel_one = $('#channel_one');
			var channelOneArr = new Array();

			var channel_one_ids = new Array();
			$.each(channels, function(i, val) {
				if ($.inArray(val.oneID, channel_one_ids) == -1) {
					channel_one_ids.push(val.oneID);
					channelOneArr.push({
						id : val.oneID,
						name : val.oneName
					});
				}
			});
			$.each(channelOneArr, function(i, val) {
				channel_one.append(" <option value='" + val.id + "'>"
						+ val.name + "</option>");
			});
		}
		$('#channel_one').change(
				function() {
					var selectedOneId = $(this).val();
					var channel_two = $('#channel_two');
					if (selectedOneId < 0) {
						channel_two.find('option::gt(0)').remove();
						channel_two.find('option:first').attr("selected",
								"selected");
						return;
					}
					var channelTwoArr = new Array();

					channel_two.empty();

					var channel_two_ids = new Array();
					$.each(channels, function(i, val) {
						if ($.inArray(val.twoID, channel_two_ids) == -1
								&& val.oneID == selectedOneId) {
							channel_two_ids.push(val.twoID);
							channelTwoArr.push({
								id : val.twoID,
								name : val.twoName
							});
						}
					});
					channel_two.append(" <option value='-1'>全部</option>");
					$.each(channelTwoArr, function(i, val) {
						channel_two.append(" <option value='" + val.id + "'>"
								+ val.name + "</option>");
					});
				});
	}

	$('#selbtn').length > 0
			&& $('#selbtn')
					.click(
							function() {
								var city_city = $('#city_city');
								var city_city_sel = $('#city_city_sel');
								var currentProvince = $(
										'#city_province option:selected')
										.text();

								var selectedid = new Array();
								$("#city_city_sel option").each(function() {
									selectedid.push($(this).val());
								});
								$("#city_city option:selected")
										.each(
												function() {
													if ($.inArray(
															$(this).val(),
															selectedid) == -1) {
														city_city_sel
																.append(" <option value='"
																		+ $(
																				this)
																				.val()
																		+ "'>"
																		+ $(
																				this)
																				.text()
																		+ '('
																		+ currentProvince
																		+ ")</option>");
													}
												});
							});
	$('#delbtn').length > 0 && $('#delbtn').click(function() {
		var city_city_sel = $('#city_city_sel');
		$("#city_city_sel option:selected").remove();
	});
	$('#emptybtn').length > 0 && $('#emptybtn').click(function() {
		$('#city_city_sel').empty();
	});
	$('#allbtn').length > 0 && $('#allbtn').click(function() {
		$('#city_city option').attr("selected", "selected");
		$('#selbtn').click();
	});
	$('#city_city').length > 0 && $('#city_city').dblclick(function() {
		$('#selbtn').click();
	});
	$('#city_city_sel').length > 0 && $('#city_city_sel').dblclick(function() {
		$('#delbtn').click();
	});

	$('#catalog_one').length > 0
			&& catalogfunctions($('#catalog_one'), $('#catalog_two'));
	$('#vrs_catalog_one').length > 0
			&& catalogfunctions($('#vrs_catalog_one'), $('#vrs_catalog_two'));
	function catalogfunctions(catalog_one, catalog_two) {

		if (catalogs.length == 0) {
			$.post("./listVrsArea", {}, function(data, status) {
				$.each(data, function(i, val) {
					catalogs.push(val);
				});

				reloadCataloglone();
			});
		}
		function reloadCataloglone() {
			// var catalog_one=$('#catalog_one');
			var catalogOneArr = new Array();

			var catalog_one_ids = new Array();
			$.each(catalogs, function(i, val) {
				if ($.inArray(val.oneID, catalog_one_ids) == -1) {
					catalog_one_ids.push(val.oneID);
					catalogOneArr.push({
						id : val.oneID,
						name : val.oneName
					});
				}
			});
			$.each(catalogOneArr, function(i, val) {
				catalog_one.append(" <option "+(i<4?"style='color:red;font-weight:bold;'":"")+"value='" + val.id + "'>"
						+ val.name + "</option>");
			});
		}
		catalog_one.change(function() {
			var selectedOneId = $(this).val();
			if (selectedOneId < 0) {
				catalog_two.find('option::gt(0)').remove();
				catalog_two.find('option:first').attr("selected", "selected");
				return;
			}
			// var catalog_two=$('#catalog_two');
			var catalogTwoArr = new Array();

			catalog_two.empty();

			var catalog_two_ids = new Array();
			$.each(catalogs, function(i, val) {
				if ($.inArray(val.twoID, catalog_two_ids) == -1
						&& val.oneID == selectedOneId) {
					catalog_two_ids.push(val.twoID);
					catalogTwoArr.push({
						id : val.twoID,
						name : val.twoName
					});
				}
			});
			catalog_two.append(" <option value='-1'>全部</option>");
			$.each(catalogTwoArr, function(i, val) {
				catalog_two.append(" <option value='" + val.id + "'>"
						+ val.name + "</option>");
			});
		});
	}
	function addParam_vrs(param) {
		var index = param.length - 1;
		var selectedtype = $('#vrstype').val();

		var typeid = '"专辑维"."专辑ID"';
		var typename = '"专辑维"."专辑"';
		switch (selectedtype) {
		case '2':
			typeid = '"专辑包维"."专辑包ID"';
			typename = '"专辑包维"."专辑包名称"';
			break;
		case '3':
			typeid = '"广告包维"."广告包ID"';
			typename = '"广告包维"."广告包名称"';
			break;

		default:
			break;
		}
		param[++index] = 'eq';
		param[++index] = encodeURIComponent(typeid);
		param[++index] = $('#vrstypeid').val();
		param[++index] = 'cany';
		param[++index] = encodeURIComponent(typename);
		param[++index] = encodeURIComponent(encodeURIComponent($('#vrstypename')
				.val()));
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
		
		reportname += "专辑";
	}
	function addParam_city(param) {
		var index = param.length - 1;
		if ($('#hotcity').attr('checked') == 'checked') {
			reportname += "热点城市";
		} else {

			var selcityids = new Array();
			$("#city_city_sel option").each(function() {
				selcityids.push($(this).val());
			});

			param[++index] = 'eq';
			param[++index] = encodeURIComponent('"城市维"."城市ID"');
			param[++index] = selcityids.length > 0 ? (selcityids.length + '+' + selcityids
					.join('+'))
					: '';
			reportname += "城市";
		}
	}
	function addParam_channel(param) {
		var index = param.length - 1;
		param[++index] = 'cany';
		param[++index] = encodeURIComponent('"渠道维"."推广码"');
		param[++index] = encodeURIComponent(encodeURIComponent($(
				'#channel_three').val()));
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"渠道维"."一级渠道ID"');
		param[++index] = encodeURIComponent($('#channel_one').val() == '-1' ? ''
				: $('#channel_one').val());
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"渠道维"."大推广渠道ID"');
		param[++index] = encodeURIComponent($('#channel_two').val() == '-1' ? ''
				: $('#channel_two').val());
		reportname += "渠道";
	}
	function addParam_platform(param) {
		var index = param.length - 1;

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
		param[++index] = $('#platform_two').val() == '-1' ? '' : $(
				'#platform_two').val();

		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"平台维"."操作系统编码"');
		param[++index] = $('#platform_three').val() == '-1' ? '' : $(
				'#platform_three').val();

		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"平台维"."应用编码"');
		param[++index] = $('#platform_four').val() == '-1' ? '' : $(
				'#platform_four').val();

		reportname += "平台";
	}
	function addParam_catalog(param) {
		var index = param.length - 1;
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"专辑维"."栏目ID"');
		
		var catalog_one_s=(String)($('#catalog_one').val());
		var catalog_one_arr=catalog_one_s.split(',');
		param[++index] = catalog_one_s=='null'?'':(catalog_one_arr.length+'+'+catalog_one_arr.join('+'));
		
		
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"专辑维"."产地ID"');
		

		var catalog_two_s=(String)($('#catalog_two').val());
		var catalog_two_arr=catalog_two_s.split(',');
		param[++index] = catalog_two_s=='null'?'':(catalog_two_arr.length+'+'+catalog_two_arr.join('+'));
		
		
		reportname += "栏目";
	}

	function addOtherDimensionParam(param) {
		// 判断下钻维度

		var index = param.length - 1;
		var other_dimension_type = $('#other_dimension').val();
		switch (other_dimension_type) {
		case '1':
			addParam_vrs(param);
			break;
		case '2':
			addParam_city(param);
			break;
		case '3':
			addParam_channel(param);
			break;
		case '4':
			addParam_platform(param);
			break;
		case '5':
			addParam_catalog(param);
			break;

		default:
			break;
		}

	}

	function hideCommonPlatform() {
		$('#form_common_platform').hide(500);

	}
	function showCommonPlatform() {
		$('#form_common_platform').show(500);
	}
	function removeCommonPlatform() {
		$('#form_common_platform').remove();
	}
	initTabSelect();
	function initTabSelect() {

		$('#tabSelect li').removeClass('selected');
		var dim = path;
		if (path == 'project') {
			$('#tab_project').addClass('selected');

		} else if (path == 'projectcity') {
			$('#tab_projectcity').addClass('selected');
		}

		$('#tab_project a').first().attr('href', './project');
		$('#tab_projectcity a').first().attr('href', './projectcity');
	}

});