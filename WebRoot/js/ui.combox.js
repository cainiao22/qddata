(function($){

	$.fn.combox = function(options){

		var opts = $.extend({},$.fn.defaults,options);

		return this.each(function(){

			$.data(this,'combox',{options:opts});

			initData(this);

			bindEvent(this);

		});

	}
       
	function initData(target){
		var opts = $.data(target,'combox').options;

		if(opts.url){
			var url = opts.url;
			$.ajax({
				url : url,
				data:'',
				dataType:'json',
				success:function(data){
					filter(target,data);
				}
			});

		}else{
			if($.isArray(opts.data)){
                filter(target,opts.data);
            }
		}
	};

	function filter(target,array){

		var opts = $.data(target,'combox').options;

		var len = array.length;

		$(target).empty();

		var _opHtml = [];

		for( var i = 0 ; i < len ; i++){

			_opHtml.push('<option value="');
			_opHtml.push(array[i][opts.valueField]);
			_opHtml.push('">');
			_opHtml.push(array[i][opts.textField]);
			_opHtml.push('</option>');

		}

		var _result = _opHtml.join('');
		$(target).append(_result);

	};


	function bindEvent(target){
		var opts = $.data(target,'combox').options;
		$(target).bind('change',function(){
			var _val = $(target).val();
			opts.onSelect.call(opts,_val);
		});
	}


	

	$.fn.defaults = {
		url : '',
		data : '',
		textField : 'name',
		valueField : 'id',
		onSelect:$.noop
	};
})(jQuery)