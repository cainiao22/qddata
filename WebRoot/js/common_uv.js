$(function() {
	var catalogs=new Array();
	window.catalogs=catalogs;
	var prepath='UV报表/';
	var reportname='';
	window.reportname=reportname;
	
	
	function init_other_dimension(){
		var init_other_dimension_arr=new Array();
		switch (path) {
		case 'uvvrs':
			init_other_dimension_arr.push({value:2,name:'地域'});
			init_other_dimension_arr.push({value:4,name:'平台'});
			break;
		case 'uvcity':
			init_other_dimension_arr.push({value:1,name:'专辑'});
			init_other_dimension_arr.push({value:5,name:'栏目'});
			init_other_dimension_arr.push({value:4,name:'平台'});

			break;
		case 'uvplatform':
			removeCommonPlatform();
			init_other_dimension_arr.push({value:2,name:'地域'});
			break;
		case 'uvcatalog':
			init_other_dimension_arr.push({value:2,name:'地域'});
			init_other_dimension_arr.push({value:4,name:'平台'});
			break;

		default:
			break;
		}
		for ( var i in init_other_dimension_arr) {
			$('#other_dimension').append(" <option value=" + init_other_dimension_arr[i]['value'] + ">"
					+  init_other_dimension_arr[i]['name']+"</option>");
			
		}
		
	}
	init_other_dimension();
	$('#other_dimension').change(function(){
		var v=$(this).val();
		
		v=='4'?hideCommonPlatform():showCommonPlatform();
		var other_dimension_id;
		switch (v) {
		case '1':
			other_dimension_id='vrs';
			break;
		case '2':
			other_dimension_id='uvcity';
			break;
		case '4':
			other_dimension_id='platform';
			break;
		case '5':
			other_dimension_id='catalog';
			break;

		default:
			other_dimension_id=-1;
		}
		
		$('div[id*=form_][id!=form_common][id!=form_common_platform]').each(function() {
			if($(this).attr('id').indexOf(other_dimension_id)<0 && $(this).attr('id').indexOf(path.replace('uv',''))<0){
				$(this).hide();
			}else{
				
				$(this).show(500);
				}
		});
	}).change();
	

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
//			scrollTop: 900
//		}, 2000);
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
	function fillreportname() {
		if($('#reporttype').val()=="1"){
			reportname+='UV日报';
			
		}else{
			
			reportname+='UV月报';
		}
		
	}
	function create_vrs_url() {

		
		var param = new Array();
		setCommonLimit(param);
		
		addParam_vrs(param);
		
		addOtherDimensionParam(param);
		
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);

	}
	function create_stockvrs_url() {
		
		
		var param = new Array();
		setCommonLimit(param);
		
		addParam_vrs(param);
		
		addOtherDimensionParam(param);
	
		fillreportname();
		
		
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
		
	}
	function create_city_url() {
		var param = new Array();
		setCommonLimit(param);
		addParam_city(param);
		addOtherDimensionParam(param);
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);


	}
	function create_platform_url() {
		var param = new Array();
		setCommonLimit(param);
		addParam_platform(param);
		addOtherDimensionParam(param);
		fillreportname();
			var path = encodeURIComponent(prepath + reportname);
			return parsefullurl(path,param);

		
	}
	function create_channel_url() {
		var param = new Array();
		setCommonLimit(param);
		addParam_channel(param);
		addOtherDimensionParam(param);
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);

		
	}
	function create_catalog_url(){
		var param = new Array();
		setCommonLimit(param);
		addParam_catalog(param);
		addOtherDimensionParam(param);
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function create_stockcatalog_url(){
		var param = new Array();
		setCommonLimit(param);
		addParam_catalog(param);
		addOtherDimensionParam(param);
		fillreportname();
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function setCommonLimit(param) {
		var index=0;
		if($('#reporttype').val()=="1"){
		param[++index] = 'bet';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = '2+' + $('#from').val() + '+' + $('#to').val();
		}else{
			param[++index] = 'bet';
			param[++index] = encodeURIComponent('"日期维"."MONTH"');
			
			if($('#from_month').val()=='-1'){
				$('#from_month').find('option:eq(1)').attr("selected","selected");
			}
			if($('#to_month').val()=='-1'){
				$('#to_month').find('option:eq(12)').attr("selected","selected");
			}
			param[++index] = '2+' + $('#year').val()+($('#from_month').val().length>1?$('#from_month').val():'0'
				+$('#from_month').val() )+ '+' + $('#year').val()+ ($('#to_month').val().length>1?$('#to_month').val():'0'+$('#to_month').val());
		}
		if($('#adstyle').length>0){
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"广告形式维"."广告形式代码"');
		var s=(String)($('#adstyle').val());
		var adstyleArr=s.split(',');
		param[++index] = s=='null'?'':(adstyleArr.length+'+'+adstyleArr.join('+'));
		}
		
		if($('#form_common_platform').is(":visible")){
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
		
		
	}
	function createurl() {
		switch (path) {
		case 'uvvrs':
			return create_vrs_url();
			break;
		case 'uvcity':
			return create_city_url();
			break;
		case 'uvplatform':
			return create_platform_url();
			break;
		case 'uvcatalog':
			return create_catalog_url();
			break;
		default:
			return create_default_url();
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
	$("#to").datepicker({
		changeYear : true,
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		showOtherMonths:true,
		numberOfMonths:3,
		showCurrentAtPos:1
	}).datepicker('setDate', -1);
	


	$('#channel_one').length>0&& channelfunctions();
	function channelfunctions(){
		var channels=new Array();
		window.channels=channels;
		$.post("./listChannel", {
		}, function(data, status) {
			$.each(data, function(i,val){
				channels.push(val);
			});
			
				reloadChannelone();
		});
		function reloadChannelone(){
			var channel_one=$('#channel_one');
			var channelOneArr= new Array();
			
			var channel_one_ids=new Array();
			$.each(channels, function(i,val){
				if($.inArray(val.oneID,channel_one_ids)==-1){
					channel_one_ids.push(val.oneID);
					channelOneArr.push( {id:val.oneID,name:val.oneName});
				}
			  });   
			$.each(channelOneArr, function(i,val){
				channel_one.append(" <option value='" + val.id + "'>"
					+ val.name + "</option>");
			  }); 
		}
		$('#channel_one').change(function(){
			var selectedOneId=$(this).val();
			var channel_two=$('#channel_two');
			if(selectedOneId<0){
				channel_two.find('option::gt(0)').remove();
				channel_two.find('option:first').attr("selected","selected");
				return;
			}
			var channelTwoArr= new Array();
			
			channel_two.empty();
			
			var channel_two_ids=new Array();
			$.each(channels, function(i,val){
				if($.inArray(val.twoID,channel_two_ids)==-1 &&val.oneID==selectedOneId ){
					channel_two_ids.push(val.twoID);
					channelTwoArr.push( {id:val.twoID,name:val.twoName});
				}
			  }); 
			channel_two.append(" <option value='-1'>全部</option>");
			$.each(channelTwoArr, function(i,val){
				channel_two.append(" <option value='" + val.id + "'>"
						+ val.name + "</option>");
				  });  
		});
	}
	
	$('#selbtn').length>0&& $('#selbtn').click(function(){
		var city_city=$('#city_city');
		var city_city_sel=$('#city_city_sel');
		var currentProvince=$('#city_province option:selected').text();
		
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
		var oparr=['eq','ge','le','gt','lt','neq'];
		param[++index] = oparr[$('#idlimit').val()-1];
		param[++index] = encodeURIComponent(typeid);
		
		var s=$('#vrstypeid').val();
		s=s.replace(/\s+/g, ',').replace('，',',');
		var idlimitvArr=s.split(',');
		param[++index] = s==''?'':(idlimitvArr.length+'+'+idlimitvArr.join('+'));
		
		
		
		 oparr=['eq','cany','neq','bwith','ewith','neq'];;
		param[++index] =oparr[$('#vrsnamelimit').val()-1];
		param[++index] = encodeURIComponent(typename);
		s=$('#vrstypename').val();
		s=s.replace(/\s+/g, ',').replace('，',',');
		 s=encodeURIComponent(encodeURIComponent(s));
		var vrsnamevArr=s.split('%252C');
		param[++index] = s==''?'':(vrsnamevArr.length+'+'+vrsnamevArr.join('+'));

		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"专辑维"."栏目ID"');
		var catalog_one_s=(String)($('#vrs_catalog_one').val());
		var catalog_one_arr=catalog_one_s.split(',');
		param[++index] = catalog_one_s=='null'?'':(catalog_one_arr.length+'+'+catalog_one_arr.join('+'));
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"专辑维"."产地ID"');
		var vrs_catalog_two_s=(String)($('#vrs_catalog_two').val());
		var vrs_catalog_two_arr=vrs_catalog_two_s.split(',');
		param[++index] = vrs_catalog_two_s=='null'?'':(vrs_catalog_two_arr.length+'+'+vrs_catalog_two_arr.join('+'));
		reportname+="专辑";
	}
	function addParam_city(param){
		var index =param.length-1;
		var city_state=$('#city_state');
		var city_province=$('#city_province');
		var city_city=$('#city_city_sel');
		if($('#splitByProvince').attr("checked")=='checked'){
			reportname+="省份";
		}else if ($('#splitByCity').attr("checked")=='checked'){
			reportname+="城市";
		}else{
			if(city_province.val()!='-1'){
				if(city_city.children().length>0){
					 reportname+="城市";
						var selcityids=new Array();
						 $("#city_city_sel option").each(function() {
							 selcityids.push($(this).val());
					        });
							param[++index] = 'eq';
							param[++index] = encodeURIComponent('"城市维"."城市ID"');
							param[++index] =selcityids.length>0?( selcityids.length+'+'+selcityids.join('+')):'';
				}else{
					reportname+="省份";
					param[++index] = 'eq';
					param[++index] = encodeURIComponent('"省份维"."省份ID"');
					param[++index] =city_province.val()=='-1'?'':city_province.val();
				}
				
				
			
			}else {
				reportname+="国家";
				param[++index] = 'eq';
				param[++index] = encodeURIComponent('"国家维"."国家ID"');
				var city_states=(String)(city_state.val());
				var city_statearr=city_states.split(',');
				param[++index] = city_states=='null'?'':(city_statearr.length+'+'+city_statearr.join('+'));
			}
		}
			
	
		
		}
	function addParam_channel(param){
		var index =param.length-1;
		param[++index] = 'cany';
		param[++index] = encodeURIComponent('"渠道维"."推广码"');
		param[++index] = encodeURIComponent(encodeURIComponent($(
		'#channel_three').val()));
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"渠道维"."一级渠道ID"');
		param[++index] = encodeURIComponent($('#channel_one').val()=='-1'?'':$('#channel_one').val());
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"渠道维"."大推广渠道ID"');
		param[++index] = encodeURIComponent($('#channel_two').val()=='-1'?'':$('#channel_two').val());
		reportname+="渠道";
	}
	function addParam_platform(param){
		var index =param.length-1;
		
		
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
		param[++index] = $('#platform_two').val()=='-1'?'':$('#platform_two').val();
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"平台维"."操作系统编码"');
		param[++index] = $('#platform_three').val()=='-1'?'':$('#platform_three').val();
		
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"平台维"."应用编码"');
		param[++index] = $('#platform_four').val()=='-1'?'':$('#platform_four').val();
		
		
		
		
		
		
		
		
		
		
		reportname+="平台";
	}
	function addParam_catalog(param){
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
		reportname+="栏目";
	}
	
	function addOtherDimensionParam(param){
		// 判断下钻维度
		
		var index=param.length-1;
		var other_dimension_type=$('#other_dimension').val();
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
	
	function hideCommonPlatform(){
		$('#form_common_platform').hide(500);
		
	}
	function showCommonPlatform(){
		$('#form_common_platform').show(500);
	}
	function removeCommonPlatform(){
		$('#form_common_platform').remove();
	}
	initTabSelect();
	function initTabSelect(){
		

		$('#tabSelect li').removeClass('selected');
		var dim=path;
		var keys=['all','uv','stock','simplestock','vv','keytarget','du','adsenseRatio','mobileStock','newMobileStock','hourdata','click','specialAD','logicalStock','pkgSpecialAD','pkgKey','posad','dukeytarget','dingsantou'];
		for(var i=1;i<keys.length;i++){
			var k=keys[i];
			if(path.indexOf(k)==0){
				$('#tab_'+k).addClass('selected');
				dim=path.replace(k,'');
			}
		}
		if(dim==path){
			$('#tab_all').addClass('selected');
		}
		for(var i=0;i<keys.length;i++){
			var k=keys[i];
		$('#tab_'+k+' a').first().attr('href','./'+k.replace('all', '')+dim);
		}
	}
	
	


		$('#reporttype').change(function(){
			if($(this).val()=="2"){
				$('#form_common label:eq(2),#form_common label:eq(3)').hide();
				$('#form_common label:gt(3)').show();
			}else{
				$('#form_common label:gt(3)').hide();
				$('#form_common label:eq(2),#form_common label:eq(3)').show();
			}
		
	}).find('option:first').attr("selected","selected").change();
		
		initmonth();
		function initmonth(){
			
			for(var i=1;i<=12;i++){
				$('#from_month,#to_month').append('<option value="'+i+'">'+i+'</option>');
			}
			
		}
		
		$('#from_month').change(function(){
			$('#to_month option:gt(0)').remove();
			var from_month=$(this).val();
			for(var i=from_month;i<=12;i++){
				if(i<1){continue;}
				$('#to_month').append('<option value="'+i+'">'+i+'</option>');
			}
		});
		
		$('#vrsidlimitbtn').length>0&&$('#vrsidlimitbtn').click(function(){
			
			var idlimit=$('#idlimit').val();
			var idlimit_v=$('#idlimit_v').val();
			var str='';
			if(idlimit_v!=''){
				str+='ID';
				
				
			switch (idlimit) {
			case '1':
				str+='='+idlimit_v;
				break;
			case '2':
				str+='>='+idlimit_v;
				break;
			case '3':
				str+='<='+idlimit_v;
				break;
			case '4':
				str+='>'+idlimit_v;
				break;
			case '5':
				str+='<'+idlimit_v;
				break;
			case '6':
				str+='!='+idlimit_v;
				break;

			default:
				break;
			}
			
		}
			
			
			$('#vrstypeid').val(str);
			
		});
		$('#vrsnamelimitbtn').length>0&&$('#vrsnamelimitbtn').click(function(){
			
			var vrsnamelimit=$('#vrsnamelimit').val();
			var vrsname_v=$('#vrsname_v').val();
			var str='';
			if(vrsname_v!=''){
				str+='名称';
				
				
				switch (vrsnamelimit) {
				case '1':
					str+='='+vrsname_v;
					break;
				case '2':
					str+='包含:'+vrsname_v;
					break;
				case '3':
					str+='不包含:'+vrsname_v;
					break;
				case '4':
					str+='开头是:'+vrsname_v;
					break;
				case '5':
					str+='结尾是:'+vrsname_v;
					break;
				case '6':
					str+='不等于:'+vrsname_v;
					break;
					
				default:
					break;
				}
				
			}
			$('#vrstypename').val(str);
			
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
		$('#city_province').length>0 &&$('#city_province').change(function() {
			reloadCity($(this).val());
		});
});