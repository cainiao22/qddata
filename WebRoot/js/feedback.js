function b(){
	h = $(window).height();
	t = $(document).scrollTop();
	if(t >( h-500)){
		$('#gotop').fadeIn('slow');
	}else{
		$('#gotop').fadeOut('slow');
	}
}
$(document).ready(function(e) {	
	
	$('#jianyi').length>0&& $('#jianyi').attr('href','./topicAdd?path='+path);
	b();
	$('#gotop').click(function(){
		$(document).scrollTop(0);	
	});
});

$(window).scroll(function(e){
	b();		
});
