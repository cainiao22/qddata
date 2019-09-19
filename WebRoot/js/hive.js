$(function(){
	$('#queryBtn').click(function(){
		$('td').css('color','');
		$('td a').css('color','');
		var k=$('#query').val();
		
		if($('#'+k).length==1){
			$('#'+k+' td').css('color','red');
			$('#'+k+' td a').css('color','red');
			window.location.hash=k;
		}
	});
	
	$('#returnBtn').click(function(){
		window.location.href="./hive-listtables";
		
	});
	
	
});