$(function() {
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

	$('#jobType').change(function(){
		$('#form_common').nextAll().hide();
		var jobType=$('#jobType').val();
		$('.jobType'+jobType).show(300);
		
		
	});
	$('#submitBtn').click(function() {
		var jobType=$('#jobType').val();
		var fromDate=$('#from').val();
		var toDate=$('#to').val();
		var projectNumber=$('#projectNumber').val();
		var freqLimit=$('#freqLimit').val();
		var jobName=$('#jobName').val();
		var vp=$('#vp').val();
		if(jobType=='-1'){
			alert('请选择指标！');
			return false;
		}
		else if(jobType=='1'){
			if(projectNumber.length<1){
				alert('请输入执行单号！');
				return false;
			}
		}
		$.post("hivejobSubmit", {
			jobType : jobType,
			fromDate : fromDate,
			toDate : toDate,
			projectNumber : projectNumber,
			freqLimit : freqLimit,
			jobName : jobName
		}, function(data) {
			if(data.valid==false){
				alert(data.errorList);
			}else{
				alert("提交成功！");
			window.location.href="hivejobList";
			}
		});
	});

	   
	 
	 function refresh(){
		 $ids=$('td[id*=id_]');
		 $ids.each(function(index){ 
			 var id=$(this).text();
			 if($('#view_'+id).text().indexOf('查看')<0){
			 refreshById(id);
			 }
		 });
	 }
	 function refreshById(id){
			$.post("hivejobGetJson", {
				id : id
			}, function(data) {
				var obj=data[0];
				$('#progress_'+id).text(obj.progress+"%");
				$('#div_progress_'+id).css('width',obj.progress+"%");
				$('#du_'+id).text(obj.du);
				if(obj.hasDone==true){
					
					$('#view_'+id).html('<a href="hivejobView?id='+id+'" target="_blank">查看</a>');
				}
				else{
					$('#view_'+id).text('计算中...');
				}
				
			});
		 
		 
	 }
	 setInterval(refresh, 5000);
});