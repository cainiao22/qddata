$(function() {

	function init(itemsel, itemall, selbtn, allbtn, delbtn, emptybtn) {
		selbtn.length > 0
				&& selbtn.click(function() {

					var selectedid = new Array();
					itemsel.children().each(function() {
						selectedid.push($(this).val());
					});
					itemall.children(':selected')
							.each(
									function() {
										if ($
												.inArray($(this).val(),
														selectedid) == -1) {
											itemsel.append(" <option value='"
													+ $(this).val() + "'>"
													+ $(this).text()
													+ "</option>");
										}
										$(this).remove();
									});
				});
		delbtn.length > 0
				&& delbtn.click(function() {
					itemsel.children(':selected').each(
							function() {
								itemall.append(" <option value='"
										+ $(this).val() + "'>" + $(this).text()
										+ "</option>");
								$(this).remove();
							});
				});
		emptybtn.length > 0 && emptybtn.click(function() {
			itemsel.children().attr("selected", "selected");
			delbtn.click();

		});
		allbtn.length > 0 && allbtn.click(function() {
			itemall.children().attr("selected", "selected");
			selbtn.click();
		});
		itemall.length > 0 && itemall.dblclick(function() {
			selbtn.click();
		});
		itemsel.length > 0 && itemsel.dblclick(function() {
			delbtn.click();
		});

		var selectedid = new Array();
		itemsel.children().each(function() {
			selectedid.push($(this).val());
		});

		itemall.children().each(function() {
			if ($.inArray($(this).val(), selectedid) > -1) {
				$(this).remove();
			}
		});
	}
	$('#itemsel').length > 0
			&& init($('#itemsel'), $('#itemall'), $('#selbtn'), $('#allbtn'),
					$('#delbtn'), $('#emptybtn'));
	$('#itemsel2').length > 0
			&& init($('#itemsel2'), $('#itemall2'), $('#selbtn2'),
					$('#allbtn2'), $('#delbtn2'), $('#emptybtn2'));

	$('#submitBtn').click(function() {
		var flag=checkForm();
		if(!flag) return false;
		var isadd = $('#id').val() == '';
		if (!isadd) {
			realSubmit();
		}else{
			$('#form').submit();
		}

	});
	function checkForm() {
		var re = /^[0-9]+[0-9]*]*$/;
		if (!re.test($('#level').val()))
		{
		alert('层级编码只可以是0或者正整数！');
		$('#level').focus();
		return false;
		} 
		return true;
		
	}
		function realSubmit() {
		
		var selectedid2=new Array();
		$("#itemsel2 option").each(function() {
			selectedid2.push($(this).val());
		});
		 $('#users').length>0&&$('#users').val(selectedid2.join(","));

		 var treeObj = $.fn.zTree.getZTreeObj("tree");
		 var nodes = treeObj.getCheckedNodes(true);
		 var selectedModuleId=new Array();
		  for(var i in nodes){
			  selectedModuleId.push(nodes[i]['id']);
	        }  
		 $('#modules').length>0&&$('#modules').val(selectedModuleId.join(","));
		$('#form').submit();
	}

		$('#query').keyup(function(){
			var key=$(this).val();
			if(key.length<1){
				return;
			}
			$('#itemall2 option').attr('selected',false);
			$('#itemall2 option').each(function(){
				if($(this).text().indexOf(key)>-1){
					$(this).attr('selected',true);
				}
			});
			
		});
});