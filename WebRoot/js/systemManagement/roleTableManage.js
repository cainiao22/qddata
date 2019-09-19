$(function() {
	$('a[id*=role_]').click(function() {
		var roleId = $(this).attr('id').replace('role_', '');

		var statusText = $(this).text();
		if (statusText == '展开') {
			$('#roleLevelTab_' + roleId).slideDown();
			$(this).text('折叠');
		} else {
			$('#roleLevelTab_' + roleId).slideUp();
			$(this).text('展开');
		}

	});
	$('a[id*=NoSelect_]').click(function() {
		var roleId = $(this).attr('id').replace('NoSelect_', '');
		$('input[name=roleLevels_' + roleId + ']').removeAttr('checked');

	});

	$("#form").submit(
			function(e) {
				var isadd = $('#id').val() == '';
				if (isadd) {
					return true;
				}
				var selectedRoleIds = new Array();
				$("input[name*=roleLevels]").each(function() {
					if ($(this).is(':checked')) {
						selectedRoleIds.push($(this).val());

					}
				});

				$('#rolesStr').length > 0
						&& $('#rolesStr').val(selectedRoleIds.join(","));
			});
});