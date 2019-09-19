	
$(function(){
	

$('input[name=queryType]').click(function(){
		var queryType=$(this).val();
		var city_province=$('#city_province').parent();
		var city_type=$('#city_type').parent();
		var project_input=$('#project-input').parent();
		var project_input_query=$('#project-input_query').parent();
		var cityname_input=$('#cityname-input').parent();
		var cityname_input_query=$('#cityname-input_query').parent();
		switch (queryType) {
		case "1":
			city_province.slideDown();
			city_type.hide();
			project_input.hide();
			project_input_query.hide();
			$('#city_province option').first().attr('selected',true);
			cityname_input.hide();
			cityname_input_query.hide();
			break;
		case "2":
			city_province.hide();
			city_type.slideDown();
			project_input.hide();
			project_input_query.hide();
			$('#city_type option').first().attr('selected',true);
			cityname_input.hide();
			cityname_input_query.hide();
			break;
		case "3":
			city_province.hide();
			city_type.hide();
			project_input.slideDown();
			project_input_query.slideDown();
			cityname_input.hide();
			cityname_input_query.hide();
			break;
		case "4":
			city_province.hide();
			city_type.hide();
			project_input.hide();
			project_input_query.hide();
			cityname_input.slideDown();
			cityname_input_query.slideDown();
			
			break;

		default:
			break;
		}
		
		
	}).first().click();
	
	$('#city_type').change(function(){
		var citytype=$(this).val();
		if(citytype=='-1')
			return;
		
		reloadHotcity(citytype,'');
//		
	});
	$('#project-input_query').click(function(){
		var proNumber=$('#project-input').val();
	
		if(proNumber!=''){
		reloadHotcity(proNumber,'');
		}
		else{
			$('#project-input').focus();
		}
//		
	});
	$('#cityname-input_query').click(function(){
		var cityname=$('#cityname-input').val();
		if(cityname!=''){
			reloadHotcity('',cityname);
		}
		else{
			$('#cityname-input').focus();
		}
//		
	});
	
	function reloadHotcity(group,name) {
		var city = $('#city_city');
		city.empty();
		$.post("./listCity", {
			group : group,
			name:name
		}, function(data, status) {
			
			for ( var i in data) {
				city.append(" <option value='" + data[i].id + "'>"
						+ data[i].name + "</option>");
			}
		});
	}
	
	$('#city_state').length > 0 && loadCountry($('#city_state'));
	function loadCountry(obj) {

		$.post("./listCountry", {}, function(data, status) {
			for ( var i in data) {
				obj.append(" <option value='" + data[i].id + "'>"
						+ data[i].name + "</option>");
			}
			obj.multiselect("refresh");
		});
		
	}
		$('#city_state').on("multiselectclick", function(event, ui) {
			var selID=($(this).multiselect('getChecked')).first().val();
			if($(this).multiselect('getChecked').length==1 && (selID=='CN' || selID=='10000001')){
			$('#form_city2,#form_city3,#form_uvcity2,#form_uvcity3').show();
			$('input[name=queryType]').parent().show();
			$('#splitByProvince').parent().show();
			$('#splitByCity').parent().show();
		}else{
			
			$('#city_province option:eq(0)').attr('selected','selected');
			$('#city_city,#city_city_sel').empty();
			$('#form_city2,#form_city3,#form_uvcity2,#form_uvcity3').hide();
			$('#splitByProvince').parent().hide();
			$('#splitByCity').parent().hide();
			$('input[name=queryType]').parent().hide();
			$('#splitByProvince').removeAttr('checked');
			$('#splitByCity').removeAttr('checked');
		}
		
		
	});
	
	
});