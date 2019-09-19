$(function() {

	$('#submitBtn').click(function() {
		if($('#title').val().replace(/(^\s*)|(\s*$)/g, "").length<1){
			$('#tip').text('标题怎能不填?!');
			return fasle;
		}else{
			$('#tip').text('');
		}
		realSubmit();

	});
	function realSubmit() {
		$('#form').submit();
	}

});