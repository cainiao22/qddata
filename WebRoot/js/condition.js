
//树节点双击事件,拼HTML
function addCondition(opts){
	//拼HTML
	var idLists = [];
	var $table = $('#queryTable');
	$('tr',$table).each(function(){
		idLists.push($(this).attr('id'));
	});
	if(idLists.join(',').indexOf(opts.id) === -1){
		var _html = [];
		_html.push('<tr id="'+opts.id+'"><td class="closeQuery" title="'+opts.name+'"></td><td><label class="conditionLabel">');
		_html.push(	opts.name);
		_html.push(':</label>	<input type="hidden" id="tag_'+opts.id+'" value="'+opts.tag+'" /></td><td>');
		_html.push('	<select id="rule_'+opts.id+'" class="w80"></select></td><td>');
		_html.push('	<input type="hidden" id="type_'+opts.id+'" value="'+opts.type+'"/>');
		_html.push('	<input type="text" id="value_'+opts.id+'"  style="height: 20px;line-height: 20px;vertical-align: middle;border: 1px solid #999999;"/>');
		_html.push('</tr>');

		var $showText = $(_html.join(''));
		if($.browser.msie){
			$table.append(_html.join(''));
		}else{
			$table.append($showText);
		}
		
		//获得节点信息
		var reuleIds;
		var reuleNames;
		if(opts.tag.indexOf("interesting_") >= 0){
			 reuleIds = ['eq','like'];
			 reuleNames = ['精准','模糊'];
		}else{
			 reuleIds = ['eq','gt','lt','ge','le','bt'];
			 reuleNames = ['等于','大于','小于','大于等于','小于等于','介于'];
		}
		
		//存储jSON
		var ruleArr = [];
		for(var i =0;i<reuleIds.length;i++){
			ruleArr.push('{id:"'+reuleIds[i]+'"','name:"'+reuleNames[i]+'"}');
		}
		var data = '['+ruleArr.join(",")+']';
		$('#rule_'+opts.id).combox({
			data:eval(data),
			width:80,
			onSelect:function(_ruleVal){
				//如果选择的是介于
				if(_ruleVal=='bt'){
					$('#value_'+opts.id).after('&nbsp;<input type="text"  id="value1_'+opts.id+'"  style="height: 20px;line-height: 20px;vertical-align: middle;border: 1px solid #999999;"/>');
					
					$('#value_'+opts.id).addClass('w110');
					$('#value1_'+opts.id).addClass('w110');
					
					if($('#type_'+opts.id).val() == 'date'){
						$('#value_'+opts.id).addClass('Wdate').bind('click',function(){
							new WdatePicker({
								 dateFmt:'yyyy-MM-dd',
								 maxDate:$('#value1_'+opts.id).val()
							});
						});
						
						$('#value1_'+opts.id).addClass('Wdate').bind('click',function(){
							new WdatePicker({
								 dateFmt:'yyyy-MM-dd',
								 minDate:$('#value_'+opts.id).val()
							});
						});
					}
				
				}else{//如果选择的不是介于	
					
					//宽度改为初始化宽度
					$('#value_'+opts.id).removeClass('w110');
					
					
					if($('#value1_'+opts.id).length != 0){
						$('#value1_'+opts.id).remove();
					}
					
					//如果是日期类型,设置日期类型的事件以及样式
					if($('#type_'+opts.id).val() == 'date'){
						
						$('#type_'+opts.id).nextAll().filter("input[id^='value']").addClass('Wdate').bind('click',function(){
							new WdatePicker({
								 dateFmt:'yyyy-MM-dd'
							});
						});
						
					}
					
				}
			}
		});
		
		
		
		if($('#type_'+opts.id).val()){
				//如果是日期类型
				if($('#type_'+opts.id).val() == 'date'){
					$('#type_'+opts.id).nextAll().filter("input[id^='value']").addClass('Wdate').bind('click',function(){
						new WdatePicker({
							 dateFmt:'yyyyMMdd'
						});
					});
				}
				//如果是【select.0-是,1-否】类型
				else if($('#type_'+opts.id).val() == 'iselect'){
					var $td = $('#value_'+opts.id).closest('td');
					$('#value_'+opts.id).remove();
					var _selectHtml = '<select id="value_'+opts.id+'" class="w180"></select>';
					$td.append(_selectHtml);
					var datas = $('#dataSource_'+opts.id).val().split(',');
					//存储jSON
					var dataArr = [];
					for(var i =0;i<datas.length;i++){
						dataArr.push('{value:"'+datas[i].split('-')[0]+'"','text:"'+datas[i].split('-')[1]+'"}');
					}
					var data = '['+dataArr.join(",")+']';
					$('#value_'+opts.id).combox({
						data:eval(data),
						valueField:'value',
						textField:'text',
						onSelect:function(id){
							$('#value_'+opts.id).find('option[value="'+id+'"]').attr('selected','selected');
						}
						
					});
				}
				//如果是外部引用类型
				else if($('#type_'+opts.id).val() == 'oselect'){
					var $td = $('#value_'+opts.id).closest('td');
					$('#value_'+opts.id).remove();
					var _selectHtml = '<select id="value_'+opts.id+'"><option value="">————请选择————</option></select>';
					$td.append(_selectHtml);
					//如果url有值,说明是参照
					if(opts.dataurl){
						$('#value_'+opts.id).combox({
							url:ctx+'/'+opts.dataurl,
							idField:'id',
							textField:opts.objColumn,
							onSelect:function(id){
								$('#value_'+opts.id).find('option[value="'+id+'"]').attr('selected','selected');
							}
						});
					}
				}
				//如果是字典引用
				else if($('#type_'+opts.id).val() == 'dict'){
					var $td = $('#value_'+opts.id).closest('td');
					$('#value_'+opts.id).remove();
					var _selectHtml = '<select id="value_'+opts.id+'"><option value="">————请选择————</option></select>';
					$td.append(_selectHtml);
					//如果url有值,说明是参照
					if(opts.dataurl){
						$('#value_'+opts.id).combox({
							url:ctx+'/sys/dict/listData?type='+opts.dataurl,
							idField:'id',
							textField:opts.objColumn,
							onSelect:function(id){
								$('#value_'+opts.id).find('option[value="'+id+'"]').attr('selected','selected');
							}
						});
					}
				}
		}
		
		bindEvent();
	}
}

//树节点双击事件-关键词,拼HTML
/*function addKWCondition(opts){
	//拼HTML
	var idLists = [];
	var $table = $('#queryTable');
	$('tr',$table).each(function(){
		idLists.push($(this).attr('id'));
	});
	if(idLists.join(',').indexOf(opts.id) === -1){
		var _html = [];
		_html.push('<tr id="'+opts.id+'"><td class="closeQuery" title="'+opts.name+'"></td><td><label class="conditionLabel">');
		_html.push(	opts.name);
		_html.push(':</label>	<input type="hidden" id="tag_'+opts.id+'" value="'+opts.tag+'" /></td><td>');
		_html.push('	<select id="rule_'+opts.id+'" class="w80"></select></td><td>');
		_html.push('	<input type="hidden" id="type_'+opts.id+'" value="'+opts.type+'"/>');
		_html.push('	<input type="text" id="value_'+opts.id+'"  style="height: 20px;line-height: 20px;vertical-align: middle;border: 1px solid #999999;"/>');
		_html.push('</tr>');

		var $showText = $(_html.join(''));
		if($.browser.msie){
			$table.append(_html.join(''));
		}else{
			$table.append($showText);
		}
		//获得节点信息
		var reuleIds = ['eq','like'];
		var reuleNames = ['精准','模糊'];
		//存储jSON
		var ruleArr = [];
		for(var i =0;i<reuleIds.length;i++){
			ruleArr.push('{id:"'+reuleIds[i]+'"','name:"'+reuleNames[i]+'"}');
		}
		var data = '['+ruleArr.join(",")+']';
		$('#rule_'+opts.id).combox({
			data:eval(data),
			width:80
		});
		
		bindEvent();
	}
}*/

//查询条件初始化事件
function bindEvent(){
	$('.closeQuery').bind('click',function(){
		$(this).closest('tr').remove();
	});

	$('.closeQuery').hover(
		function(){
			$(this).addClass('closeQuery_over');
		},
		function(){
			$(this).removeClass('closeQuery_over');
		}
	);
}