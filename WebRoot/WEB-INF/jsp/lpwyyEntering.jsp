<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>
    <link href="./assets/plugins/datepicker/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css"/>
    <link href="./assets/plugins/compass/communityReport.css" rel="stylesheet" type="text/css"/>
    <c:import url="common/importJs2.jsp"></c:import>
    <style>
        body{ color: #fdfdfe;}
        #edit,#save{width: 70px;height: 30px;padding-top: 6px;}
        .datatableclass {background: RGB(29,44,51);padding: 10px;border-radius: 8px;  }
        .dataTables_scroll{position: relative;overflow: auto;}
        .table{border: 1px solid grey;border-bottom: 0;}
        #example{width:100%;border:1px solid #747d8a;background-color: #2a323c;overflow-x: auto; }
        .table thead tr td,#example td{border-right: 1px solid grey;}
        .dataTables_info, .dataTables_length{margin-bottom: 10px;}
        #example tbody tr td input{width: 50%;text-align: center}
        .fa:before {margin-right: 5px;}
        td{text-align: center;}
        #example tbody tr:nth-of-type(odd) {background:#383f48;}
        #example_wrapper .row{margin: 10px 0;}
        .errorTips {text-align: center;padding: 10px;background-color: #2a323c;display: none;}
        /*公共弹窗*/
        .pubMask{ display: none; width: 100%; height: 100%; position:absolute; top:0; left: 0;  z-index: 8;}
        .pubPopup{ display: none; width: 100%;  z-index: 999; position:fixed; top:50%; left:0;opacity:1;}
        .pubPopup .popCon { width:200px; max-width: 100%; background: rgba(256,256,256,0.8); border-radius: 8px; color: #000; font-size:13px; margin:0 auto; padding: 1em; text-align: center;}

    </style>
</head>
<!-- END HEAD -->
<body class="fix-header fix-sidebar card-no-border">

<div id="main-wrapper">
    <c:import url="common/header2.jsp"></c:import>
    <!-- End Topbar header -->
    <aside class="left-sidebar">
        <!-- Sidebar scroll-->
        <div class="scroll-sidebar">
            <!-- Sidebar navigation-->
            <nav class="sidebar-nav">
                <ul id="sidebarnav">
                    <c:import url="common/sidebarMenu2.jsp"></c:import>
                </ul>
            </nav>
            <!-- End Sidebar navigation -->
        </div>
        <!-- End Sidebar scroll-->
    </aside>

    <div class="page-wrapper">

        <div class="container-fluid">
            <div class="row page-titles">
                <div class="col-md-6 col-8 align-self-center">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="javascript:void(0)">${ sidebar_menu.parentName}</a></li>
                        <li class="breadcrumb-item active">${ sidebar_menu.name} </li>
                    </ol>
                </div>
            </div>
            <div class="col-md-12">

                <div class="card-body" style="padding:0;">

                    <div class="form-group col-md-12">
                        <div class="btn btn-info fa fa-edit" id="edit" onclick="editData()">编辑</div>
                        <div class="btn btn-info fa fa-save" id="save" onclick="saveData()">保存</div>
                    </div>

                    <div class="col-md-12 datatableclass">

                        <table id="example" class="table">
                            <thead>
                            <tr><td>序号</td><td>城市</td><td>待实施项目</td><td>实施中项目</td><td>已上线完成项目</td><td>取消或暂停项目</td><td>更新时间</td></tr>
                            </thead>
                            <tbody id="detailInfo">

                            </tbody>
                        </table>
                        <div class="errorTips"></div>
                    </div>
                </div>

            </div>

        </div>

        <!-- footer -->
        <c:import url="common/footer.jsp"></c:import>
        <!-- End footer -->
    </div>

</div>

<!--公共弹窗 toast提示-->
<div class="pubMask"></div>
<div class="pubPopup">
    <div class="popCon"></div>
</div>

<!-- End Wrapper -->

<script type="text/javascript" src="./assets/plugins/jquery.scrollTo.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/datatables.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/dataTables.bootstrap.js"></script>


<script>
    //语言样式设置
    var i18nZH = {
        "sProcessing":   "处理中...",
        "sLengthMenu":   "显示 _MENU_ 项结果",
        "sZeroRecords":  "没有匹配结果",
        "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix":  "",
        "sSearch":       "搜索:",
        "sUrl":          "",
        "sEmptyTable":     "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands":  ",",
        "oPaginate": {
            "sFirst":    "首页",
            "sPrevious": "上页",
            "sNext":     "下页",
            "sLast":     "末页"
        },
        "oAria": {
            "sSortAscending":  ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    };

    $(document).ready(function() {
        getLuopanCity();
    });
   // url: "http://dev-qddata.bigdata.qdingnet.com/midLuopanLuruCityDataList" ,
    function getLuopanCity() {
        $.ajax({
            type: "get",
            url: "/midLuopanLuruCityDataList" ,
            dataType: "json",
            success: function (res) {
                if (res != undefined &&  res != null) {
                    var str="",heji="";
                    var toto_total=0,doing_total=0,done_total=0,cancel_total=0;
                    for(var i=0;i<res.length;i++){
                        var regionId= res[i].regionId;
                        var regionName= res[i].regionName;
                        var todoProjectNum= res[i].todoProjectNum;
                        var doingProjectNum= res[i].doingProjectNum;
                        var doneProjectNum= res[i].doneProjectNum;
                        var cancelProjectNum= res[i].cancelProjectNum;
                        var sortNo= res[i].sortNo;
                        var updatetime =  res[i].updatetime;

                        toto_total += Number(todoProjectNum);
                        doing_total +=Number(doingProjectNum);
                        done_total +=Number(doneProjectNum);
                        cancel_total +=Number(cancelProjectNum);

                        str+='<tr>';
                        str+='<td>'+(i+1)+'</td>';
                        str+='<td data-sortNo="'+sortNo+'" data-city="'+regionId+'">'+regionName+'</td>';
                        str+='<td>'+todoProjectNum+'</td>';
                        str+='<td>'+doingProjectNum+'</td>';
                        str+='<td>'+doneProjectNum+'</td>';
                        str+='<td>'+cancelProjectNum+'</td>';
                        str+='<td>'+updatetime+'</td>';
                        str+='</tr>';
                    }

                    heji+='<tr>';
                    heji+='<td>'+(res.length+1)+'</td>';
                    heji+='<td>合计</td>';
                    heji+='<td>'+toto_total+'</td>';
                    heji+='<td>'+doing_total+'</td>';
                    heji+='<td>'+done_total+'</td>';
                    heji+='<td>'+cancel_total+'</td>';
                    heji+='<td></td>';
                    heji+='</tr>';
                    $("#detailInfo").append(str+heji);

                    //数据展示完成，调用插件
                    $('#example').DataTable({
                        language:i18nZH,
                        "bAutoWidth": false,//自动宽度
//                        "iDisplayLength": 100,//默认显示100条数据  共8个城市，一页可以展示
                        "scrollX": true
                    });
                } else {
                    $(".errorTips").text("暂无数据～").show();
                }
            },
            error: function () {
                $(".errorTips").text("请稍后重试～").show();
            }
        });
    }

    function editData() {
        var dataList = document.querySelectorAll('tbody tr');
        for(var j=0;j<dataList.length-1;j++){
            var obj = dataList[j].children;
            for(var i=2;i<obj.length-1;i++){
                var flag =obj[i].children.length;
                if(flag<=0){
                    obj[i].innerHTML="<input type='number' min='0' value='"+obj[i].innerText.trim()+"'>";
                }
            }
        }
    }

    function saveData(){
        var username = decodeURI(Cookies.get("username"));
        var paramList=[];

        var dataList = document.querySelectorAll('tbody tr');
        for(var j=0;j<dataList.length;j++){
            var obj = dataList[j].children;
            //console.log(obj);
            //遍历当前修改的td
            for(var i=2;i<obj.length-1;i++){
                var flag = obj[i].querySelector("input");
                if(flag){
                    if(flag.value ==undefined || flag.value ==null || flag.value =="" || flag.value<=0){
                        obj[i].innerHTML=0;
                    }else {
                        obj[i].innerHTML=flag.value;
                    }
                }
            }
            var city = obj[1].innerText;
            var sortNo = obj[1].getAttribute("data-sortNo");
            var cityId = obj[1].getAttribute("data-city");
            var todoNum = obj[2].innerText;
            var doingNum = obj[3].innerText;
            var doneNum = obj[4].innerText;
            var cancelNum = obj[5].innerText;
            if(city){
                if(!todoNum){ todoNum = 0; }
                if(!doingNum){ doingNum = 0; }
                if(!doneNum){ doneNum = 0; }
                if(!cancelNum){ cancelNum = 0; }
                //console.log("todoProjectNum="+todoNum+"&doingProjectNum="+doingNum+"&doneProjectNum="+doneNum+"&cancelProjectNum="+cancelNum+"&regionName="+city+"&regionId="+cityId);
                paramList.push({"todoProjectNum":todoNum,"doingProjectNum":doingNum,"doneProjectNum":doneNum,"cancelProjectNum":cancelNum,"regionName":city,"regionId":cityId,"sortNo":sortNo,"updateUser":username});
            }else{
                popEffect("缺少参数");
            }
        }
        //调接口
        //url: "http://dev-qddata.bigdata.qdingnet.com/midLuopanLuruCityDataUpdate",
        if(paramList!=null){
            $.ajax({
                type: "post",
                url: "/midLuopanLuruCityDataUpdate",
              /* headers: {
                   "Accept": "application/json",
                   "Content-Type": "application/json"
               },*/
                dataType: "json",
               data:{
                   paramList:JSON.stringify(paramList)
               },
                success: function (res) {
                    //console.log(res);
                    if (res.code == 0) {
                        popEffect("保存成功");
                        window.setTimeout(function () {
                            window.location.reload();
                        },2000);
                    } else {
                        popEffect("暂无数据～");
                    }
                },
                error: function () {
                    popEffect("请稍后重试～");
                }
            });
        }
    }

    //3秒弹窗
    function popEffect(text) {
        var times = 2;
        $('.pubPopup,.pubMask').show();
        $('.pubPopup .popCon').text(text);
        var i = setInterval(function () {
            times--;
            if (times == 1) {
                $('.pubPopup,.pubMask').addClass('popFadeOut');
            }
            if (times == 0) {
                clearInterval(i);
                $('.pubPopup,.pubMask').hide().removeClass('popFadeOut');
                times = 2;
            }
        }, 1000);
    }
</script>
</body>
</html>