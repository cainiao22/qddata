$(function() {

	$("#framemain").load(function() {
		$('#loading').hide();
		$('#framemain').show();
	});

	$('#submitBtn').click(function() {
		$('#framemain').hide();
		$('#loading').show();
		$('#form').submit();
		
		
	});

	$(".date").datepicker({
		changeYear : true,
		changeMonth : true,
		dateFormat : "yymmdd",
		showButtonPanel:true,
		showOtherMonths:true
		
	}).datepicker('setDate', -1);
});