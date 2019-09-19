$(function() {
	var platforms=new Array();
	window.platforms=platforms;
	
	var catalogs=new Array();
	window.catalogs=catalogs;
	
	var prepath='消耗量报表/';
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
	function createurl() {
		switch (path) {
		case 'sportsStock':
			return create_sportsStock_url();
			break;
	}
	}
	function fillreportname() {
			reportname+='体育相关栏目库存报表';
		
	}
	function create_sportsStock_url(){
		var param = new Array();
		setCommonLimit(param);
		
		
		
		fillreportname();
		
		var path = encodeURIComponent(prepath + reportname);
		return parsefullurl(path,param);
	}
	function setCommonLimit(param) {
		var index=0;

	
			param[++index] = 'bet';
		param[++index] = encodeURIComponent('"日期维"."DAY"');
		param[++index] = '2+' + $('#from').val() + '+' + $('#to').val();
		
		
		if($('#duration_select').length>0){
			var durationSel=$('#duration_select').val();
			if(durationSel!='-1'){ 
				param[++index] = 'bet';
				param[++index] = encodeURIComponent('"视频时长维"."视频时长ID"');
				if(durationSel!='-2'){ 
					var strs= durationSel.split(","); 
					param[++index] = '2+' + strs[0] + '+' + strs[1];
				}else{
					if($('#duration_b').val()==''){
						$('#duration_b').val(0);
					}
					if($('#duration_e').val()==''){
						$('#duration_e').val(9999999);
					}
					param[++index] = '2+' + $('#duration_b').val() + '+'+(parseInt($('#duration_e').val())-1);
				}

			
			}
			}
		param[++index] = 'eq';
		param[++index] = encodeURIComponent('"广告形式维"."广告形式代码"');
		var s=(String)($('#adstyle').val());
		var adstyleArr=s.split(',');
		param[++index] = s=='null'?'':(adstyleArr.length+'+'+adstyleArr.join('+'));
		
	}
	$("#from").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		onClose : function(selectedDate) {
			$("#to").datepicker("option", "minDate", selectedDate);
		}
	}).datepicker('setDate', -1);
	$("#to").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true
	}).datepicker('setDate', -1);
	$('#duration_select').length>0&&$('#duration_select').change(function(){
		var durationSel=$(this).val();
				
				if(durationSel=='-2'){
					$('#diyDuration').show(200);
				
				}else{
					$('#diyDuration').hide(200);
				}
			})&&$('#duration_select option:eq(9),#duration_select option:eq(10),#duration_select option:eq(11)').remove();
	$('#diyDuration').qtip({
		content: '请分别输入一个整数，如:从10到20 表示时长大于等于10\'00"且小于等于19\'59"',
		show: 'mouseover',
		hide: 'mouseout',
		style: { 
			color: '#6699CC',
			width:210
		},
		
		position: {
			corner: {
				target: 'bottomLeft',
				tooltip: 'topLeft'
			}
		}
	});
});