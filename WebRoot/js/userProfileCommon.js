/**
 * 用户画像公共js
 */
(function(){
	userTagSelect();
	userGroup();
})();

var typeOne="",typeTwo="",typeThree="",typefour="",typefive="";

//判断rule规则选中  文本
function typeOneSelect(val) {
	if(val=="eq"){
		typeOne="<option>请选择</option><option value='eq' selected>等于</option><option value='neq'>不等于</option>";
	}else if(val=="neq"){
		typeOne="<option>请选择</option><option value='eq'>等于</option><option value='neq' selected>不等于</option>";
	}else{
		typeOne="<option>请选择</option><option value='eq'>等于</option><option value='neq'>不等于</option>";
	}
}
//json
function typeTwoSelect(val) {
	if(val=="wildcard"){
		typeTwo="<option>请选择</option><option value='wildcard' selected>包含</option><option value='exWildcard'>不包含</option>";
	}else if(val=="exWildcard"){
		typeTwo="<option>请选择</option><option value='wildcard'>包含</option><option value='exWildcard' selected>不包含</option>";
	}else{
		typeTwo="<option>请选择</option><option value='wildcard'>包含</option><option value='exWildcard'>不包含</option>";
	}
}
//列表
function typeThreeSelect(val) {
	if(val=="in"){
		typeThree="<option>请选择</option><option value='in' selected>包含</option><option value='ex'>不包含</option><option value='eq'>等于</option><option value='neq'>不等于</option>";
	}else if(val=="ex"){
		typeThree="<option>请选择</option><option value='in'>包含</option><option value='ex' selected>不包含</option><option value='eq'>等于</option><option value='neq'>不等于</option>";
	}else if(val=="eq"){
		typeThree="<option>请选择</option><option value='in'>包含</option><option value='ex'>不包含</option><option value='eq' selected>等于</option><option value='neq'>不等于</option>";
	}else if(val=="neq"){
		typeThree="<option>请选择</option><option value='in'>包含</option><option value='ex'>不包含</option><option value='eq'>等于</option><option value='neq' selected>不等于</option>";
	}else{
		typeThree="<option>请选择</option><option value='in'>包含</option><option value='ex'>不包含</option><option value='eq'>等于</option><option value='neq'>不等于</option>";
	}
}
//日期
function typeFourSelect(val) {
	if(val=="gt"){
		typefour="<option>请选择</option><option value='gt' selected>大于</option><option value='lt'>小于</option><option value='eq'>等于</option><option value='bt'>介于</option>";
	}else if(val=="lt"){
		typefour="<option>请选择</option><option value='gt'>大于</option><option value='lt' selected>小于</option><option value='eq'>等于</option><option value='bt'>介于</option>";
	}else if(val=="eq"){
		typefour="<option>请选择</option><option value='gt'>大于</option><option value='lt'>小于</option><option value='eq' selected>等于</option><option value='bt'>介于</option>";
	}else if(val=='bt'){
		typefour="<option>请选择</option><option value='gt'>大于</option><option value='lt'>小于</option><option value='eq'>等于</option><option value='bt' selected>介于</option>";
	}else{
		typefour="<option>请选择</option><option value='gt'>大于</option><option value='lt'>小于</option><option value='eq'>等于</option><option value='bt'>介于</option>";
	}
}
//数值
function typeFiveSelect(val) {
	if(val=="eq"){
		typefive="<option>请选择</option><option value='eq' selected>等于</option><option value='neq'>不等于</option><option value='gt'>大于</option><option value='lt'>小于</option><option value='bt'>介于</option>";
	}else if(val=="neq"){
		typefive="<option>请选择</option><option value='eq'>等于</option><option value='neq' selected>不等于</option><option value='gt'>大于</option><option value='lt'>小于</option><option value='bt'>介于</option>";
	}else if(val=="gt") {
		typefive = "<option>请选择</option><option value='eq'>等于</option><option value='neq'>不等于</option><option value='gt' selected>大于</option><option value='lt'>小于</option><option value='bt'>介于</option>";
	}else if(val=="lt") {
		typefive = "<option>请选择</option><option value='eq'>等于</option><option value='neq'>不等于</option><option value='gt'>大于</option><option value='lt' selected>小于</option><option value='bt'>介于</option>";
	}else if(val=="bt") {
		typefive = "<option>请选择</option><option value='eq'>等于</option><option value='neq'>不等于</option><option value='gt'>大于</option><option value='lt'>小于</option><option value='bt' selected>介于</option>";
	}else{
		typefive="<option>请选择</option><option value='eq'>等于</option><option value='neq'>不等于</option><option value='gt'>大于</option><option value='lt'>小于</option><option value='bt'>介于</option>";
	}
}

/**
 * 循环得到每个标签的legend、series
 * @param obj 接口数据
 * @param arr 标签数组
 * @param legendData 空数组
 * @param seriesData 空数组
 * @param type 图表区分
 */
function getData(obj,arr,legendData,seriesData,type) {
	if(arr && arr.length > 0){
		var v = 0;
		for(var i=0;i<arr.length;i++){
			var id = arr[i].example_id;
			var name = arr[i].example_name;
			if(id != -1){
				if(type=="city"){
					if(name=="北京市" || name=="重庆市" || name=="成都市" || name=="上海市" || name=="西安市" || name=="杭州市" || name=="广州市"){
						legendData.push(name);
						seriesData.push({value:obj[id], name:name});
					}else{
						if(obj[id]){
							v += obj[id];
						}
					}
				}else{
					if(obj[id]) {
						legendData.push(name);
						seriesData.push({value: obj[id], name: name});
					}
				}
			}
		}
	}
	if(type=="city") {
		legendData.push("其它");
		seriesData.push({value: v, name: "其它"});
	}
}

//数组的最大值
function getMaxValue(arr){
	var max = Math.max.apply(null,arr);
	return max;
}

//用户标签查询
function userTagSelect() {
	var tagTimeout = $.ajax({
		url: "./userGroupTagSelect",
		type:"get",
		async:false,
		timeout: 5000,
		success: function (res) {
			if (res){
				var lpdObj={};
				var tagarr = [];
				//把tag列表保存起来
				sessionStorage.setItem("firstParam", JSON.stringify(res));
				for(var i=0;i<res.length;i++){
					lpdObj[res[i].tag]= res[i].lpd;
				}
                sessionStorage.setItem("treeEle",JSON.stringify(recursiontree(res, 1)));
				sessionStorage.setItem("lpdObj",JSON.stringify(lpdObj));
			}
		},
		error:function () {
			console.log("查询标签列表出错");
		},
		complete:function(XMLHttpRequest,status){ //请求完成后最终执行参数
			if(status=='timeout'){//超时
				tagTimeout.abort();
				console.log("查询标签列表超时");
			}
		}
	});
}
//递归找出树状结构
function recursiontree(res, id){
    var arr = [];
    for(var i=0; i<res.length; i++){
        if(res[i].pid == id){
            var obj = {
                name: res[i].name,
            }
            if(id == 1){
                obj.open = false;
			}
            var nodearr = recursiontree(res, res[i].id);
            if(nodearr.length != 0){
                obj.children = nodearr;
            }
            arr.push(obj);
        }
    }
    if(arr.lenth == 0){
        return;
    }else{
        return arr;
    }
}

//用户群组列表
function userGroup() {
	var tagTimeout = $.ajax({
		url:"./getPortraitUserGroupsNew",
		type:"get",
		async:false,
		timeout: 2000,
		success:function (res) {
			if(res){
				sessionStorage.setItem("userGroupList", JSON.stringify(res));
			}
		},
		error:function () {
			console.log("查询群组出错");
		},
		complete:function(XMLHttpRequest,status){ //请求完成后最终执行参数
			if(status=='timeout'){//超时
				tagTimeout.abort();
				console.log("查询群组超时");
			}
		}
	});
}
