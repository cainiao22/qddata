$(function() {
	var platforms=new Array();
	window.platforms=platforms;
	
	
	$("#framemain").load(function() {
		$('#loading').hide();
		$('#framemain').show();
	});
	function loadframe() {
		if(path== 'stockInTheory'){
			if(!check_stockInTheory()){
				return false;
			}
			
			}
		
		
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
		
	});
	function parsefullurl(path,param){
		var url ='./stockInTheoryResult?';
		
		for ( var i = 0; i < param.length; i++) {
			url +='&'+ param[i]+'=' + param[++i];
		}
		
		url+='&rand='+Math.random();
		return url;
	}
	function createurl() {
		switch (path) {
		case 'stockInTheory':
			return create_stockInTheory_url();
			break;
	}
	}
	function create_stockInTheory_url(){
		var param = new Array();
		setCommonLimit(param);
	
		return parsefullurl(path,param);
	}
	function check_stockInTheory(){
		var duRegExp1= /^[0-9]+-[0-9]+$/;
		var duRegExp2= /^[0-9]+\+$/;
		var adRegExp= /^[0-9]+$/;
		var l=$('input[id*=du_]').length;
		for(var i=1;i<=l;i++){
			var du=$('#du_'+i).val();
			var ad=$('#ad_'+i).val();
			if(du==''){
				continue;
			}
			if(duRegExp1.test(du)|| duRegExp2.test(du)){
				if(!adRegExp.test(ad)){
					alert('对应库存输入有误:'+ad);
					return false;
				}
	
}else{
	alert('时长划分输入有误:'+du);
	return false;
	}
			
		}
		return true;
	}
	function setCommonLimit(param) {
		var index=-1;
		param[++index] ='beginDate';
		param[++index] =$('#from').val();
		param[++index] ='endDate';
		param[++index] =$('#to').val();
		param[++index] ='platform.oneID';
		param[++index] =$('#platform_one').val();
		param[++index] ='platform.twoID';
		param[++index] =$('#platform_two').val();
		param[++index] ='platform.threeID';
		param[++index] =$('#platform_three').val();
		param[++index] ='platform.fourID';
		param[++index] =$('#platform_four').val();
		var l=$('input[id*=du_]').length;
		var duArr=[];
		var adArr=[];
		for(var i=1;i<=l;i++){
			var du=$('#du_'+i).val();
			var ad=$('#ad_'+i).val();
			if(du==''){
				continue;
			}
	duArr.push(du);
	adArr.push(ad);
}
		param[++index] ='durationStr';
		param[++index] =encodeURIComponent(duArr.join(','));
		param[++index] ='adStr';
		param[++index] =adArr.join(',');
	}

	
	$("#from").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		maxDate:0,
		onClose : function(selectedDate) {
			$("#to").datepicker("option", "minDate", selectedDate);
		}
	}).datepicker('setDate', -1);
	$("#to").datepicker({
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		maxDate:0
	}).datepicker('setDate', -1);
	$('input[id*=du_]').qtip({
		content: '格式举例:0-3 表示为0\'0"-2\'59";10+代表10分钟以上',
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
	$('input[id*=ad_]').qtip({
		content: '请输入对应时长的理论库存数',
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
	
	function init(){
		var duArr=['0-1','1-2','2-4','4-10','10+'];
		var adArr=[0,1,2,3,4];
		for(var i=0;i<duArr.length;i++){
			$('#du_'+(i+1)).val(duArr[i]);
			$('#ad_'+(i+1)).val(adArr[i]);
		}
	}
	init();
});