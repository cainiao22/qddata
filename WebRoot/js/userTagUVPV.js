$(function() {

	$('#selbtn').length > 0
			&& $('#selbtn')
					.click(
							function() {
								var city_city = $('#city_city');
								var city_city_sel = $('#city_city_sel');
								// var currentProvince=$('#city_province
								// option:selected').text();

								var selectedid = new Array();
								$("#city_city_sel option").each(function() {
									selectedid.push($(this).val());
								});
								$("#city_city option:selected")
										.each(
												function() {
													if ($.inArray(
															$(this).val(),
															selectedid) == -1) {
														city_city_sel
																.append(" <option value='"
																		+ $(
																				this)
																				.val()
																		+ "'>"
																		+ $(
																				this)
																				.text()
																		+ "</option>");
													}
												});
							});
	$('#delbtn').length > 0 && $('#delbtn').click(function() {
		var city_city_sel = $('#city_city_sel');
		$("#city_city_sel option:selected").remove();
	});
	$('#emptybtn').length > 0 && $('#emptybtn').click(function() {
		$('#city_city_sel').empty();
	});
	$('#allbtn').length > 0 && $('#allbtn').click(function() {
		$('#city_city option').attr("selected", "selected");
		$('#selbtn').click();
	});
	$('#city_city').length > 0 && $('#city_city').dblclick(function() {
		$('#selbtn').click();
	});
	$('#city_city_sel').length > 0 && $('#city_city_sel').dblclick(function() {
		$('#delbtn').click();
	});
	var allcitys=new Array();
	$('#city_city option').each(function() {
		allcitys.push({id:$(this).val(),name:$(this).text()});
	});
	var matchcitys=new Array();
	var notmatchcitys=new Array();
	$('#query').keyup(function() {
		var key = $(this).val();
		if (key.length < 1) {
			return;
		}
		matchcitys.length=0;
		notmatchcitys.length=0;
		$('#city_city option').attr('selected', false);
		for(var i =0;i<allcitys.length;i++){
			if (allcitys[i].name.indexOf(key) > -1) {
				matchcitys.push({id:allcitys[i].id,name:allcitys[i].name});
			}else{
				notmatchcitys.push({id:allcitys[i].id,name:allcitys[i].name});
			}
		}
		$('#city_city').empty();
		for(var i =0;i<matchcitys.length;i++){
			$('#city_city').append('<option style="color:red;" value="'+matchcitys[i].id+'">'+matchcitys[i].name+'</option>');
		}
		for(var i =0;i<notmatchcitys.length;i++){
			$('#city_city').append('<option value="'+notmatchcitys[i].id+'">'+notmatchcitys[i].name+'</option>');
		}
		

	});
	$('#submitBtn').click(
			function() {
				var selcityids = [];
				[];
				$("#city_city_sel option").each(function() {
					selcityids.push($(this).val());
				});
				var str1 = selcityids.join();
				var tag1 = $("#tag1").val().split("\n").join();
				var tag2 = $("#tag2").val().split("\n").join();
				var tag3='';
				if(!$.trim($('#adpositionAuto'))=='')
				 tag3 = $( "#adpositionVal" ).val();
				var posttag = str1 + '|' + tag1 + '|' + tag2 + '|' + tag3;
				var url = "./userTagUVPV_getData?tagStr=" + posttag + '&serverType='+$('#serverType').val()+'&rand='
						+ Math.random();
				$('#framemain').hide();
				$('#loading').show();
				$('#framemain').attr("src", url);
			});

	$("#framemain").load(function() {
		$('#loading').hide();
		$('#framemain').show();
	});
	 $( "#adpositionAuto" ).autocomplete({
         source: function( request, response ) {
             $.ajax({
                 url: "./listAdposition",
                 dataType: "json",
                 contentType: "application/x-www-form-urlencoded; charset=utf-8", //中文乱码
                 type: "post", 
                 data:{
                     name: request.term
                 },
                 
                 success: function( data ) {
                     response( $.map( data, function( item ) {
                         return {
                         	label:"("+item.id+")"+item.name,
                         	value:"("+item.id+")"+item.name,
                         	id:item.id
                         };
                     }));
                 }
             });
         },
         minLength: 1,
         
         //选中的值
         select: function( event, ui ) {
             $("#adpositionVal").val(ui.item.id); 
         },
         open: function() {
             $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
           },
           close: function() {
             $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
         }
     });
});
