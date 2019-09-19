$(function() {


	$('#submitBtn').click(function() {
		var rolelevels=[];
		$('input[name*=roleLevels_]:checked').each(function(i){
			
			rolelevels.push($(this).val());
		});
		
		
		$('#roleLevelsStr').val(rolelevels.join());
		var isadd = $('#id').val() == '';
		if (!isadd) {
			realSubmit();
		} else {
//TODO
			//需要验证模块标识，是否已经存在
//			$.post("./getTotalByModuleUrl", {
//				username : $('#username').val()
//			}, function(data, status) {
//
//				if (data.total > 0) {
//					alert('该模块URl已经存在!');
//				} else {
//					realSubmit();
//
//				}
//			});
			realSubmit();
		}

	});
	function realSubmit() {
		 $('#form').submit();
	}

});