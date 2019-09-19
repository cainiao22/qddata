$(function() {




	$("#framemain").load(function() {
		$('#loading').hide();
//		$('#framemain').show();
		
		
	});
	function loadframe() {
		if(window.config.portalPages.indexOf('{#username}')>0){
			return;
		}
		window.config.portalPages=window.config.portalPages.replace('{#path}',encodeURIComponent(reportName));
		if(path =='DashBoard'){
			$('#reportName').append('关键指标仪表盘');
			reportName!='高级DashBoard'&&$('#reportName').append('(<span style="color:blue">'+reportName.replace('高级DashBoard','')+'</span>)');
		}else{
			$('#reportName').text(reportName);
		}
		
		
		
//		$('#framemain').hide();
		$('#loading').show();
		$('#framemain').attr("src", window.config.bieehost+window.config.portalPages);
		$('#framemain').attr('height','1220px');
		clearInterval(timer);
	}
	
	var timer =  window.setInterval(loadframe,1000);
});