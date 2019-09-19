$(function() {
	var platforms=new Array();
	window.platforms=platforms;
	var path = document.location.pathname.split("/");
	path = path[path.length - 1];
	window.path = path;
	$('.recordable').each(function(){
			$(this).removeClass('open');
			$(this).addClass('close');
			$(this).next().hide();
		
	  });
	var t=$('.aside .nav li  a[href='+path+']').length;
	$('.aside .nav li  a').each(function() {
		if ((t==0 && path.indexOf($(this).attr('href')) >-1) || (t>0 && path==$(this).attr('href'))) {
			$(this).parent().addClass('selected');
			var li=$(this).parent().parent().prev();
			li.removeClass('close');
			li.addClass('open');
			li.next().show();
		}

	});
	$('.recordable').click(function() {
		if ($(this).hasClass('open')) {
			$(this).removeClass('open');
			$(this).addClass('close');
			$(this).next().hide(100);
		} else if ($(this).hasClass('close')) {
			
			$('.recordable').each(function(index){
				if ($(this).hasClass('open')) {
					$(this).removeClass('open');
					$(this).addClass('close');
					$(this).next().hide();
				}
			  });
			
			$(this).removeClass('close');
			$(this).addClass('open');
			$(this).next().show(100);
		}
	});


	
	function getCurrentUserInfo() {
		$.post("./getCurrentUserInfo", {}, function(data, status) {

			$('#currentUserInfo').length>0&&$('#currentUserInfo').text(decodeURI(data.realname));
			
		});
		
	
	}
	
	getCurrentUserInfo();

	$('#syncAuthority').length > 0 && $('#syncAuthority').click(function() {
		var textspan = $(this).children().first();
		if (textspan.text().indexOf("进行中") > -1) {
			return;
		}
		textspan.text('权限同步(进行中...)');
		$(this).attr('disable', true);
		$.post("./syncAuthority", {}, function(data, status) {

			if (data.result == 1) {
				textspan.text('权限同步(成功！)');
			} else {
				textspan.text('权限同步(失败！)');
			}
			setTimeout("$('#syncAuthority span').text('权限同步')", 2000);
			setTimeout("$('#syncAuthority').removeAttr('disable')", 2000);
		});

	});

	$('#tabSelect').length > 0 && initTabSelect();
	function initTabSelect() {
		if($("#tabSelect li").length==1){
			return;
		}
		$.post("./listAllowedModule", {}, function(data, status) {
			$("#tabSelect li").each(function() {
				var h = $(this).find('a').first().attr('href').replace('./', '');
				if ($.inArray(h, data) < 0) {
					$(this).remove();
				}
			});
			$("#tabSelect").show();
		});
	}
	function getSysInfo() {
		$.post("./sitenotice", {}, function(data, status) {

			if(data.notice!=null&&data.notice!=''&&data.notice.charAt(0)!='#'){
//				$('#notice').text(data.notice).show(1000);
				$('#notice').text(data.notice).slideDown(500);
			}
			if(data.onduty!=null && data.onduty!='' &&data.onduty.charAt(0)!='#'){
				$('#onduty').text(data.onduty).show();
			}
		});
	}
//	getSysInfo();
	
	if($('#platform_one').length>0||$('#common_platform_one').length>0){
			$.post("./listPlatform", {
			}, function(data, status) {
				$.each(data, function(i,val){
					platforms.push(val);
				});
				
				$('#platform_one').length>0&& platformfunctions($('#platform_one'),$('#platform_two'),$('#platform_three'),$('#platform_four'));
				$('#common_platform_one').length>0&& platformfunctions($('#common_platform_one'),$('#common_platform_two'),$('#common_platform_three'),$('#common_platform_four'));
					
			});
	}

	
	function platformfunctions(platform_one,platform_two,platform_three,platform_four){
		
		platform_one.change(function(){
			var selectedOneId=$(this).val();
			if(selectedOneId<0){
				platform_two.find('option::gt(0)').remove();
				platform_two.find('option:first').attr("selected","selected").change();
				platform_three.find('option::gt(0)').remove();
				platform_three.find('option:first').attr("selected","selected").change();
				platform_four.find('option::gt(0)').remove();
				platform_four.find('option:first').attr("selected","selected").change();
			}
			reloadPlatformTwo();
			reloadPlatformThree();
			reloadPlatformFour();
			});
		reloadPlatformOne();
		function reloadPlatformOne(){
			var platformOneArr= new Array();
			
			var platform_one_ids=new Array();
			$.each(platforms, function(i,val){
				if($.inArray(val.oneID,platform_one_ids)==-1){
				platform_one_ids.push(val.oneID);
				platformOneArr.push( {id:val.oneID,name:val.oneName});
				}
			  });   
			//platform_one.find('option').first().remove();
			$.each(platformOneArr, function(i,val){
			platform_one.append(" <option value='" + val.id + "'>"
					+ val.name + "</option>");
			  });  
			
			platform_one.change();
		}
		function reloadPlatformTwo(){
			var platformTwoArr= new Array();
			
			platform_two.empty();
			
			var platform_two_ids=new Array();
			$.each(platforms, function(i,val){
				if($.inArray(val.twoID,platform_two_ids)==-1  && val.oneID== platform_one.val()){
					platform_two_ids.push(val.twoID);
					platformTwoArr.push( {id:val.twoID,name:val.twoName});
				}
			  }); 
			platform_two.append(" <option value='-1'>全部</option>");		
			$.each(platformTwoArr, function(i,val){
				platform_two.append(" <option value='" + val.id + "'>"
						+ val.name + "</option>");
				  });  
			
			
		}
		
		
		function reloadPlatformThree(){
			platform_three.empty();
			var platformThreeArr= new Array();
			var platform_three_ids=new Array();
			$.each(platforms, function(i,val){
				if($.inArray(val.threeID,platform_three_ids)==-1 && val.oneID== platform_one.val()){
					platform_three_ids.push(val.threeID);
					platformThreeArr.push( {id:val.threeID,name:val.threeName});
				}
			}); 
			platform_three.append(" <option value='-1'>全部</option>");		
			$.each(platformThreeArr, function(i,val){
				platform_three.append(" <option value='" + val.id + "'>"
						+ val.name + "</option>");
			});  
		}
		function reloadPlatformFour(){
			var platform_four_ids=new Array();
			platform_four.empty();
			platform_four.append(" <option value='-1'>全部</option>");
			var platformFourArr= new Array();
			
			
			$.each(platforms, function(i,val){
				if($.inArray(val.fourID,platform_four_ids)==-1 && val.oneID== platform_one.val()){
					platform_four_ids.push(val.fourID);
					platformFourArr.push( {id:val.fourID,name:val.fourName});
				}
			}); 
			
			$.each(platformFourArr, function(i,val){
				platform_four.append(" <option value='" + val.id + "'>"
						+ val.name + "</option>");
			});  
			
			
		}
	
	}
  $('select[multiple=multiple][multipleOri!=true]').multiselect({  enableCaseInsensitiveFiltering: true});
/*
	$("#sourceTables[multiple=multiple]").length>0&& $("#sourceTables[multiple=multiple]").multiselect({
		 minWidth:'300',
		 height:'300'
	 }).multiselectfilter({
		 label: '筛选:',
	      width: '150',  override default width set in css file (px). null will inherit 
	      placeholder: '输入关键字',
	      autoReset: false
	 })
	$("#playStyle[multiple=multiple]").length>0&& $("#playStyle[multiple=multiple]").multiselect({
		 minWidth:'auto',
		 height:'300'
	 }).multiselectfilter({
		 label: '筛选:',
	      width: '100',  override default width set in css file (px). null will inherit 
	      placeholder: '输入关键字',
	      autoReset: false
	 }).multiselect("uncheckAll"); 
	$("#adTime[multiple=multiple]").length>0&& $("#adTime[multiple=multiple]").multiselect({
		minWidth:'auto',
		height:'300'
	}).multiselectfilter({
		label: '筛选:',
		width: '100',  override default width set in css file (px). null will inherit 
		placeholder: '输入关键字',
		autoReset: false
	}).multiselect("uncheckAll"); 
	$("#vtType[multiple=multiple]").length>0&& $("#vtType[multiple=multiple]").multiselect({
		minWidth:'auto',
		height:'300'
	}).multiselectfilter({
		label: '筛选:',
		width: '100',  override default width set in css file (px). null will inherit 
		placeholder: '输入关键字',
		autoReset: false
	}).multiselect("uncheckAll"); 
	$("#catalog_one[multiple=multiple]").length>0&& $("#catalog_one[multiple=multiple]").multiselect({
		minWidth:'auto',
		height:'300'
	}).multiselectfilter({
		label: '筛选:',
		width: '100',  override default width set in css file (px). null will inherit 
		placeholder: '输入关键字',
		autoReset: false
	}).multiselect("uncheckAll"); 
	$("#vrs_catalog_one[multiple=multiple]").length>0&& $("#vrs_catalog_one[multiple=multiple]").multiselect({
		minWidth:'auto',
		height:'300'
	}).multiselectfilter({
		label: '筛选:',
		width: '100',  override default width set in css file (px). null will inherit 
		placeholder: '输入关键字',
		autoReset: false
	}).multiselect("uncheckAll"); 
	$("#catalog_two[multiple=multiple]").length>0&& $("#catalog_two[multiple=multiple]").multiselect({
		minWidth:'auto',
		height:'300'
	}).multiselectfilter({
		label: '筛选:',
		width: '100',  override default width set in css file (px). null will inherit 
		placeholder: '输入关键字',
		autoReset: false
	}).multiselect("uncheckAll"); 
	$("#vrs_catalog_two[multiple=multiple]").length>0&& $("#vrs_catalog_two[multiple=multiple]").multiselect({
		minWidth:'auto',
		height:'300'
	}).multiselectfilter({
		label: '筛选:',
		width: '100',  override default width set in css file (px). null will inherit 
		placeholder: '输入关键字',
		autoReset: false
	}).multiselect("uncheckAll"); 
	$("#pkgtype[multiple=multiple]").length>0&& $("#pkgtype[multiple=multiple]").multiselect({
		minWidth:'auto',
		height:'300',
		noneSelectedText:'不限'
	}).multiselectfilter({
		label: '筛选:',
		width: '100',  override default width set in css file (px). null will inherit 
		placeholder: '输入关键字',
		autoReset: false
	}).multiselect("uncheckAll"); 
	$("#city_state[multiple=multiple]").length>0&& $("#city_state[multiple=multiple]").multiselect({
		minWidth:'auto',
		height:'300'
	}).multiselectfilter({
		label: '筛选:',
		width: '100',  override default width set in css file (px). null will inherit 
		placeholder: '输入关键字',
		autoReset: false
	}).multiselect("uncheckAll"); 
	$("#position_detail[multiple=multiple]").length>0&& $("#position_detail[multiple=multiple]").multiselect({
		minWidth:'auto',
		height:'300'
	}).multiselectfilter({
		label: '筛选:',
		width: '100',  override default width set in css file (px). null will inherit 
		placeholder: '输入关键字',
		autoReset: false
	}).multiselect("uncheckAll"); 
	$("#adsplatform[multiple=multiple]").length>0&& $("#adsplatform[multiple=multiple]").multiselect({
		minWidth:'auto',
		height:'300'
	}).multiselectfilter({
		label: '筛选:',
		width: '100',  override default width set in css file (px). null will inherit 
		placeholder: '输入关键字',
		autoReset: false
	}).multiselect("uncheckAll"); */
	
	$('#report_introBtn').click(function(){
		if($(this).text()=='打开报表说明'){
			 $("#report_intro_content").slideDown('fast');
			 $(this).text('关闭报表说明');
		}else{
			$("#report_intro_content").slideUp('fast');
			 $(this).text('打开报表说明');
		}
		
	});
	
	$('#report_intro_editBtn').length>0 && $('#report_intro_link').attr('href','./reportIntroAdd?path='+path);
	$('#report_introBtn').length>0 &&$.post("./getReportIntro", {path:path}, function(data){
		$('#report_intro_content').html(data.content!=null?data.content:'暂无！');
	});
	$('#fullscreen').click(function(){
		if($(this).text()=='全屏'){
			$('.aside').hide(500);
			$('.header-wrapper').hide(500);
			$('body').css('background','url("")');
			$('.main').css('margin-left','30px');
		
			
			
			$(this).text('退出全屏');
		}else{
			$('.aside').show(500);
			$('.header-wrapper').show(500);
			$('body').css('background','url("img/aside_bg.png") repeat-y scroll 0 0 rgba(0, 0, 0, 0)');
			$('.main').css('margin-left','160px');
			$(this).text('全屏');
			
		}
	});
});

function gourl(url) {
	window.location.href = url;
}
function deleteItem(url) {
	if (confirm("是否确认删除?")) {
		window.location.href = url;
	}
}