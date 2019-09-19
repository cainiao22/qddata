$(function() {

	$('#submitBtn').click(function() {
		var isadd = $('#id').val() == '';
		if (!isadd) {
			realSubmit();
		} else if(document.location.pathname.indexOf('useradd')>-1) {

			$.post("./getTotalByUserName", {
				userName : $('#userName').val()
			}, function(data, status) {

				if (data.total > 0) {
					alert('该用户名已经存在!');
				} else {
					realSubmit();

				}
			});

		}else{
		realSubmit();
		}
	});
	function realSubmit() {
		$('#form').submit();
	}

});