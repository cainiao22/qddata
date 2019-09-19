$(function() {

	function sessionlog_loadnode() {
		function getFont(treeId, node) {
			return node.font ? node.font : {};
		}
		var color=['red','green'];
		var zNodes = [];
		var setting = {
			data : {
				simpleData : {
					enable : true
				}
			},
			view: {
				fontCss: getFont,
				nameIsHTML: true
			}
		};
		$.post("./sessionlog_loadnode", {
			userID:userID
		}, function(data, status) {
			$('#loading').hide();
			var fieldsNoPermissionInMap=data.fieldsNoPermissionInMap;
			
			var columnsAllList=data.columnsAllList;
			for ( var i in columnsAllList) {
				var column=columnsAllList[i];
			zNodes.push({
				id : column.id,
				pId :'-1',
				name : column.columnName+'(ID:'+column.columnID+')',
				open : false,
				url : './sessionlog_userpermission_column?userID='+userID+'&columnID='+column.columnID,
				target : "_self",
				font:{'font-weight':'bold','color':color[column.permission]}
			});
			
			var sessionFieldList=column.sessionFieldList;
			for ( var j in sessionFieldList) {
				var field=sessionFieldList[j];
				var filter=fieldsNoPermissionInMap[column.columnID+'_'+field.fieldID];
				var isPermission=(filter==undefined?1:0);
			zNodes.push({
				id : field.id,
				pId :column.id,
				name : field.fieldName+'(ID:'+field.fieldID+')'+(isPermission==0?'(FILTER:'+filter+')':''),
				open : false,
				url : column.permission==1?('./sessionlog_userpermission_field?userID='+userID+'&columnID='+column.columnID+'&fieldID='+field.fieldID):"javascript:void(0);",
				target : "_self",
				font:{'font-weight':'bold','color':(column.permission==1?color[isPermission]:"red")}
			});
			}
			}

			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});

	}
	$('#loading').show();
	sessionlog_loadnode();
});