$(function() {

	$('#query').click(function() {
		query();

	});

	// 查询
	function query() {
		// 拼装SQL
		var query = getQuery();
		$('#tables').empty();
		console.info(query);
		$.post('userProfilequery', {
			query : query
		}, function(data) {
			show(data)
		});
	}
	// 查询
	function show(data) {
	 $('#totalHit').html('符合条件人数:<span style="color:red;">'+data.totalHit+"</span>");
	
		for(var i =0;i< data.aggregations.length;i++){
			var agg=data.aggregations[i];
	 $('#tables').append('<table id="agg_'+agg.tag+'" class="display"></table>');
	 var dataSet=[];
	 for(var key in agg.data){
		 dataSet.push([key,agg.data[key]]);
	 }
	 $('#agg_'+agg.tag).DataTable( {
	        data: dataSet,
	        "autoWidth": false,
	        paging: false,
	        "ordering": false,
	        "scrollY": "300px",
	        "scrollCollapse": true,
	        "searching": false,
	        columns: [
	            { title: agg.name },
	            { title: "人数" }
	        ]
	    } );
	 
		}
	}
	// 拼装SQL
	function getQuery() {
		
		var query={};
		var conditionArr = [];// 条件数组

		var $table = $('#queryTable');

		// 循环遍历表中所有的行，拼SQL条件
		$('tr', $table).each(function() {
			var idVal = $(this).attr('id');// 行ID
			var tag = $('#tag_' + idVal, $(this)).val();// COLUMN列
			var urlVal = $('#url_' + idVal, $(this)).val();// URL
			var typeVal = $('#type_' + idVal, $(this)).val();// 类型
			var ruleVal = $('#rule_' + idVal, $(this)).val();// 获取规则的值
			var value;// 条件值
			var val = [];

			if (ruleVal == 'bt') {
				val.push($('#value_' + idVal, $(this)).val());
				val.push($('#value1_' + idVal, $(this)).val());
			} else {
				val.push($('#value_' + idVal, $(this)).val());
			}

			if (ruleVal != '' && val.length > 0) {
				var temp = {};
				temp.tag = tag;
				temp.rule = ruleVal;
				temp.value1 = val[0];
				if (val.length > 1)
					temp.value2 = val[1];
				conditionArr.push(temp);
			}
		});
		query.conditions=conditionArr;
		
		var nodes = zTreeObj.getCheckedNodes(true);
		var aggArr = [];// 聚合标签数组
		for(var i =0;i<nodes.length;i++){
			if(nodes[i].dataType!=0)
			aggArr.push(nodes[i].tag);
		}
		query.agg=aggArr;
		return JSON.stringify(query);
	}

});
