/**
 * Created by 五道杠 on 2019/4/29.
 */

// <%--关闭新增漏斗的页面--%>


function getNowDay_7() {
    //当天的前7天  此一次进入页面使用
    var datetime = new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "-" + month + "-" + date
}
function getNowDay_1() {
    //当天前一天
    var datetime = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "-" + month + "-" + date
}
function getNowDay() {
    //当天日期
    var datetime = new Date;
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "-" + month + "-" + date
}
var addLouDou = document.getElementById("addLouDou");
var close = addLouDou.getElementsByClassName("close");
var addLouDouStyle = addLouDou.getElementsByClassName("addLouDouStyle");
var louDouMap = document.getElementById("louDouMap");
var louDouEdit = document.getElementById("louDouEdit");
var addLouDouBot = addLouDou.getElementsByClassName('addLouDouBot')[0];
var flagDeviceType = "all";//端标识默认
var flagStartDate = getNowDay_7();//默认起始日期标识；
var flagEndDate = getNowDay();//默认最后一天标识
var flagAnalysis = "pageAnalysis";//默认页面
var editLouDouId = document.getElementById('editLouDou');
var louDouMapTable = document.getElementById('louDouMapTable');
var flagLouDouSelect;//编辑漏斗旁边的select下拉框里面的值,默认第一条
var flagAllPage;

//----------增加漏斗的页面--------
function closeAddLouDou() {
    return addLouDou.style.display = "none"
}
function closeEditLouDou() {
    return editLouDouId.style.display = "none";
}
function addLouDouPageFn() {
    ol.innerHTML = null;
    var li = document.createElement("li");
    li.innerHTML = `<span class="stars">*</span>漏斗名称 <input type="text" placeholder="请输入漏斗名称"
                                                                            class="addLouDouStyle">`
    ol.appendChild(li)
    addLouDouStyle[0].value = '';
    addLouDou.style.display = "block";
    var lis = addLouDou.getElementsByTagName("li");
    if (lis.length <= 1) {
        var li = document.createElement("li");
        li.innerHTML = ` <label style="margin-left: 22px"><span class="stars">*</span>第` + lis.length + `步:</label>`;
        var select = document.createElement('select');
        select.className = "addLouDouStyle";
        for (let i in flagAllPage) {
            var option = document.createElement('option');
            option.value = i;
            option.innerText = flagAllPage[i];
            select.appendChild(option);
        }
        li.appendChild(select)
        var span = document.createElement("span");
        span.innerHTML = `  <span class="delete" onclick="deleteSteps(this.parentNode.parentNode)">
                                    <span class="delete1"></span>
                                    </span>`;
        li.appendChild(span);
        ol.appendChild(li);
    }
}
// <%-----------------------------------%>


//-----------------------------------------

(function () {//拉取编辑漏斗里面的select
    var params={
        "productId": 1,
        "status":0
    }
    $.ajax({
        url: "./getFunnelParamList",//插入漏斗参数
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        async: false,
        data: JSON.stringify(params),
        success: function (res) {
            for (var i = 0; i < res.length; i++) {
                var option = document.createElement("option");
                option.value = res[i]["codes"];
                option.innerText = res[i]["name"];
                option.setAttribute("id", res[i]["id"]);
                louDouEdit.appendChild(option)
            }
        }
    })

})();
function updateLouDou(codes, id, name) {//漏斗更新
    var params = {
        "codes": codes,
        "id": id,
        "name": name,
        "productId": 1,
        "status":0
    }
    $.ajax({
        url: "./updateFunnelParam",//漏斗参数更新
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        async: false,
        data: JSON.stringify(params),
        success: function (res) {

        }
    })
}

//        ---------------新增漏斗页面里面的---------

flagLouDouSelect = louDouEdit[louDouEdit.selectedIndex].value//先获取下拉框第一个
function louDouEditChange() {
    flagLouDouSelect = louDouEdit[louDouEdit.selectedIndex].value;
    getFunnelAnalysis();
}
var addSteps = addLouDou.getElementsByClassName("addSteps")[0];
var ol = addLouDou.getElementsByClassName("ol")[0];
var del = addLouDou.getElementsByClassName("delete");

addSteps.addEventListener("click", function () {
    var lis = addLouDou.getElementsByTagName("li");
    if (lis.length > 10) {
        return null
    }
    var li = document.createElement("li");
    li.innerHTML = ` <label style="margin-left: 22px"><span class="stars">*</span>第` + lis.length + `步:</label>`;
    var select = document.createElement('select');
    select.className = "addLouDouStyle";
    for (let i in flagAllPage) {
        var option = document.createElement('option');
        option.value = i;
        option.innerText = flagAllPage[i];
        select.appendChild(option);
    }
    li.appendChild(select);
    var span = document.createElement("span");
    span.innerHTML = `  <span class="delete" onclick="deleteSteps(this.parentNode.parentNode)">
                                    <span class="delete1"></span>
                                    </span>`;
    li.appendChild(span);
    ol.appendChild(li);

}, true);

function deleteSteps(par) {
    par.parentNode.removeChild(par)//删除当前项
}
function determine() {//确定按钮
    var flagOptions = '';//创建标识，把得到的值存进去
    for (var i = 1; i < addLouDouStyle.length; i++) {
        var ald = addLouDouStyle[i];
        flagOptions = flagOptions + "," + ald[ald.selectedIndex].value;
    }
    var option = document.createElement("option");
    option.value = flagOptions.slice(1, flagOptions.length);//逗号搞不定   就进行裁剪把
    option.innerHTML = addLouDouStyle[0].value || "未命名";
    louDouEdit.appendChild(option);
    closeAddLouDou();
    ol.innerHTML = `  <li><span class="stars">*</span>漏斗名称 <input type="text" placeholder="漏斗名称"
                                                                            class="addLouDouStyle">
                                </li>`;//再次进入恢复初始值

    var params = {
        "codes": option.value,
        "id": 0,
        "name": option.innerHTML,
        "productId":1,
        "status":0
    };
    $.ajax({
        url: "./insertFunnelParam",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        async: false,
        data: JSON.stringify(params),
        success: function (res) {
            console.log(res);
        }
    })
}
//漏斗编辑

var flagEditValue;//当前选项的value


function editLouDou() {
    var addSteps2 = editLouDouId.getElementsByClassName("addSteps")[0];
    var ol = editLouDouId.getElementsByClassName("ol")[0];
    var del = editLouDouId.getElementsByClassName("delete");
    var addLouDouStyle = editLouDouId.getElementsByClassName("addLouDouStyle")[0];
    ol.innerHTML = null;
    var li = document.createElement("li");
    var text = louDouEdit[louDouEdit.selectedIndex].innerText;
    var values = flagEditValue = louDouEdit[louDouEdit.selectedIndex].value.split(",");
    louDouEditIndex=louDouEdit.selectedIndex;//记录索引
    li.innerHTML = `<span class="stars">*</span>漏斗名称 <input type="text" placeholder="漏斗名称"
                                                                            class="addLouDouStyle" value="` + text + `">`
    ol.appendChild(li);
    var lis = editLouDouId.getElementsByTagName("li");
    //把原来的数据拿来
    for (var i = 0; i < values.length; i++) {
        var cur = values[i];
        var li = document.createElement("li");
        li.innerHTML = ` <label style="margin-left: 22px"><span class="stars">*</span>第` + lis.length + `步:</label>`
        var select = document.createElement('select');
        select.className = "addLouDouStyle";
        for (let j in flagAllPage) {
            var option = document.createElement('option');
            option.value = j;
            option.innerText = flagAllPage[j];
            select.appendChild(option);
        }
        select.value = cur;
        li.appendChild(select);
        var span = document.createElement("span");
        span.innerHTML = `  <span class="delete" onclick="deleteSteps(this.parentNode.parentNode)">
                                    <span class="delete1"></span>
                                    </span>`;
        li.appendChild(span);
        ol.appendChild(li);
    }

    //编辑的新增漏斗
    editLouDouId.style.display = "block";
    addSteps2.onclick = function () {
        var lis = editLouDouId.getElementsByTagName("li");
        if (lis.length > 10) {
            return null
        }
        var li = document.createElement("li");
        li.innerHTML = ` <label style="margin-left: 22px"><span class="stars">*</span>第` + lis.length + `步:</label>`
        var select = document.createElement('select');
        select.className = "addLouDouStyle";
        for (let i in flagAllPage) {
            var option = document.createElement('option');
            option.value = i;
            option.innerText = flagAllPage[i];
            select.appendChild(option);
        }
        li.appendChild(select)
        var span = document.createElement("span");
        span.innerHTML = `  <span class="delete" onclick="deleteSteps(this.parentNode.parentNode)">
                                    <span class="delete1"></span>
                                    </span>`;
        li.appendChild(span);
        ol.appendChild(li);
    }
}

function determine1() {
    var optionId = louDouEdit[louDouEdit.selectedIndex].getAttribute("id");
    if (louDouEdit[louDouEdit.selectedIndex]) {
        louDouEdit.removeChild(louDouEdit[louDouEdit.selectedIndex]);
    }
    var flagOptions = '';//创建标识，把得到的值存进去
    var editSelects = editLouDouId.getElementsByTagName("select");
    for (var i = 0; i < editSelects.length; i++) {
        var cur = editSelects[i];
        flagOptions = flagOptions + "," + cur[cur.selectedIndex].value
    }
    var liValue = editLouDouId.getElementsByTagName("input")[0].value;
    flagOptions = flagOptions.slice(1, flagOptions.length);
    var option = document.createElement("option");
    option.value = flagOptions;
    option.setAttribute("id", optionId)
    option.innerText = liValue || "未命名";
    option.selected=true;//修改后给当前选中
    louDouEdit.insertBefore(option,louDouEdit.childNodes[louDouEditIndex+1]);
    closeEditLouDou();
    louDouEditChange()
    updateLouDou(option.value, Number(optionId), option.innerText)//把数据给后端拿过去
}


//        ----------------------end-------------------------


//-------------以下是复制过来的，仅用于日期查找-------------
var businessType = 1;//千丁 or 丁管家
var startdate = '', enddate = '';
var param = {
    "queryDate": getCurrentDay(),
}
$(document).ready(function () {
    //埋点
    var obj = {
        "eventType": ["pageAnalyse"]
    }
    if (businessType == 0) {
        obj.eventType.push('qding');
    } else {
        obj.eventType.push('dgj');
    }
    getPointParameter(obj);
    setPageName(businessType);
    $('.c-datepicker-data-input').eq(0).val(dayHandle(getCurrentDay(), -7));//备注
    $('.c-datepicker-data-input').eq(1).val(dayHandle(getCurrentDay(), -1));
    //年月日范围
    function minTime() {
        //只能选择当月的日期
        var datetime = new Date();
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        return year + "-" + month + "-01"
    }

    $('.J-datepicker-range-day').datePicker({
        hasShortcut: true,
        isRange: true,
        show: calendarshow,
        min: minTime(),
        max: dayHandle(getCurrentDay(), -1),
        shortcutOptions: [{
            name: '过去7天',
            day: '-7,-1'
        }, {
            name: '过去30天',
            day: '-30,-1'
        }, {
            name: '过去60天',
            day: '-60,-1'
        }, {
            name: '过去90天',
            day: '-90,-1'
        }],
        hide: function (type) {
            this.$input.eq(0).val(this.$input.eq(0).val().substr(0, 10));
            this.$input.eq(1).val(this.$input.eq(1).val().substr(0, 10));
            startdate = this.$input.eq(0).val();
            enddate = this.$input.eq(1).val();
            $(".tab_1 > div,.tab_2 > div").eq(0).addClass("bg_active").siblings("div").removeClass("bg_active");
            //埋点
            var obj = {
                "eventType": ["dateSelect"],
            }
            getPointParameter(obj);
            flagEndDate = enddate;
            flagStartDate = startdate;
            //  --------时间变化在这里----

            getFunnelAnalysis()
            //    -----------------------
        }
    });
    flagStartDate = startdate = getCurrentDay();
    flagEndDate = enddate = getCurrentDay();

});
//日历显示时
function calendarshow() {
    $('.c-datepicker-picker').css({'left': '55px'});
    $('.c-datepicker-date-range-picker__editor-wrap').eq(1).css('display', 'none');
    $('.c-datepicker-date-range-picker__editor-wrap').eq(3).css('display', 'none');
}
// setPageName()
function setPageName(type) {
    $('#pageName').html("");
    var param = {
        "businessType": type
    }
    $.ajax({
        url: getLocation() + proxy("/dataApiQuery/gct_collect_log_page_info", param),
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (res) {
            if (res.data) {
                var list = res.data;
                var str = '<option value="all" selected>全部</option>';
                for (var i = 0; i < list.length; i++) {
                    str += '<option value="' + list[i].pageId + '">' + list[i].pageName + '</option>';
                }
                $('#pageName').append(str);
                $("#pageName").select2({
                    placeholder: '全部',
                    allowClear: true,
                    multiple: false
                });
            }
        }
    });
}

//------------------------------------------以上---------------


//------------------端标识-------------------

function getDevType() {
    //端改变被调用
    var appType = document.getElementById("appType");
    flagDeviceType = appType[appType.selectedIndex].value;
    getFunnelAnalysis()
}
//---------------------end---------------------------

//--------------页面获取-----------
getMaidianPage();
function getMaidianPage() {
    var pageParams = {
        "productId": 1,
        "status": 0
    };
    $.ajax({
        url: "./getMaidianPage",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        async: true,
        data:JSON.stringify(pageParams),
        success: function (res) {
            flagAllPage = res;
            //-------------初始化添加按钮的一个选项--------
            var ol = addLouDou.getElementsByClassName("ol")[0];
            var li = document.createElement("li");
            li.innerHTML = ` <label style="margin-left: 22px"><span class="stars">*</span>第1步:</label>`
            var select = document.createElement('select');
            select.className = "addLouDouStyle";
            for (let i in flagAllPage) {
                var option = document.createElement('option');
                option.value = i;
                option.innerText = flagAllPage[i];
                select.appendChild(option);
            }
            li.appendChild(select)
            var span = document.createElement("span");
            span.innerHTML = `  <span class="delete" onclick="deleteSteps(this.parentNode.parentNode)">
                                    <span class="delete1"></span>
                                    </span>`;
            li.appendChild(span);
            ol.appendChild(li);
            //    ------------------end-------------
        }
    })
}

// louDouLeft();
function count(m, n) {//计数
    n = n || 0.1//防止n得到零报错，n为零的时候m肯定为零
    return ((m / n).toFixed(2) * 100).toFixed() + "%"//外侧的toFixed()是为了防止出现浮点数
}
function louDouLeft(flagAllPages) {
    var ol = louDouMap.getElementsByTagName("ol")[0];
    //左侧漏斗视图
    ol.innerHTML = null;
    for (var i = 0; i < flagAllPages.length; i++) {
        var flagAllPage = flagAllPages[i];
        var li1 = document.createElement('li');
        li1.innerText = flagAllPage["name"];
        ol.appendChild(li1);
        var li2 = document.createElement("li");
        li2.innerText = `UV:` + flagAllPage["number"];
        ol.appendChild(li2);
        if (i == flagAllPages.length - 1) {
            var a = '';
            return
        } else {
            var a = count(flagAllPages[i + 1]["number"], flagAllPage["number"])
        }
        var li3 = document.createElement("li");
        li3.innerHTML = ` <span>` + a + `</span>
                            <div class="leftTop"></div>
                            <div class="leftBot"></div>`;
        ol.appendChild(li3);
    }
}
function louDouRight(flagAllPages) {

    louDouMapTable.innerHTML = null;
    louDouMapTable.innerHTML = `  <tr>
                            <th>步骤</th>
                            <th>页面名称</th>
                            <th>UV</th>
                            <th>上一步UV转化率</th>
                            <th>总体UV转化率</th>
                        </tr>`;
    if (JSON.stringify(flagAllPages).length <= 2) {
        louDouMapTable.style.display = "none"
    } else {
        louDouMapTable.style.display = "block"
    }
    var trs = louDouMapTable.getElementsByTagName("tr");
    for (var i = 0; i < flagAllPages.length; i++) {
        var flagAllPage = flagAllPages[i];
        var tr = document.createElement('tr');
        var a;
        i == 0 ? a = "" : a = count(flagAllPage["number"], flagAllPages[i - 1]["number"])
        var b = count(flagAllPage["number"], flagAllPages[0]['number']);
        tr.innerHTML = `  <td>` + (trs.length ) + `</td>
                            <td>` + flagAllPage["name"] + `</td>
                            <td>` + flagAllPage["number"] + `</td>
                            <td>` + a + `</td>
                            <td>` + b + `</td>`
        louDouMapTable.appendChild(tr);
    }

}


//----------------end---------------


//------------------------漏斗分析---------------------------------

function getFunnelAnalysis() {//漏斗分析
    flagStartDate = flagStartDate == getNowDay() ? getNowDay_7() : flagStartDate;//默认最近七天
    flagEndDate = flagEndDate == getNowDay() ? getNowDay_1() : flagEndDate;//默认最近七天
    // console.log(flagDeviceType);
    // console.log(flagStartDate);
    // console.log(flagEndDate);
    // console.log(flagLouDouSelect);
    // console.log(flagAllPage);
    var params = {
        "deviceType": flagDeviceType,
        "endDate": flagEndDate,
        "funnelPath": flagLouDouSelect,
        "startDate": flagStartDate,

    }

    $.ajax({
        url: "./getFunnelAnalysis",
        type: "post",
        dataType: "json",
        data: JSON.stringify(params),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (res) {
            console.log(res);
            var obj = {};
            for (var i = 0; i < res.length; i++) {
                obj[res[i]["id"]] = res[i]["name"];
            }
            louDouLeft(res);
            louDouRight(res);
        }
    });
}
getFunnelAnalysis();
//----------------------------end-----------------------------
