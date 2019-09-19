<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
    <c:import url="common/importCss2.jsp"></c:import>
    <link rel='stylesheet' type='text/css' href='./assets/plugins/zTree/zTreeStyle.css'/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <style>
        .name-list{
            width: 100%;
            height: 400px;
            border: 1px solid #617775;
            text-align: center;
            overflow-y: auto;
            overflow-x: auto;
        }
        .name-list li{
            color: #ffffff;
            font-size: 14px;
            line-height: 23px;
            cursor: pointer;
            list-style-type:none;
        }
        #left {
            float: left;
            width: 30%;
        }
        #right {
            float: left;

        }
    </style>
</head>
<!-- END HEAD -->
<body class="fix-header fix-sidebar card-no-border">
<div class="preloader">
    <svg class="circular" viewBox="25 25 50 50">
        <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10"/>
    </svg>
</div>
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
    <!-- Page wrapper  -->
    <div class="page-wrapper">
        <!-- Container fluid  -->
        <div class="container-fluid">
            <!-- Bread crumb and right sidebar toggle -->
            <div class="row page-titles">
                <div class="col-md-6 col-8 align-self-center">
                    <h3 class="text-themecolor m-b-0 m-t-0">${ sidebar_menu.parentName}</h3>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="javascript:void(0)">${ sidebar_menu.parentName}</a></li>
                        <li class="breadcrumb-item active">${ sidebar_menu.name} </li>
                    </ol>
                </div>
            </div>
            <!-- End Bread crumb and right sidebar toggle -->
            <!-- Start Page Content -->
            <div class="row">
                <div class="col-md-12" style="padding-left: 185px">
                        <div  id="left">
                            <h2>人员列表</h2>
                            <ul class="name-list">
                                <!--<li>张三00001</li>-->
                            </ul>
                        </div>
                        <div id="right">
                        <h2>权限设置</h2>
                            <div class="zTreeDemoBackground" style="height: 400px;border: 1px solid #617775;text-align: center;padding-left: 50px;">
                                <ul id="treeDemo" class="ztree" style="height: 400px;overflow-y: auto;">
                                </ul>
                            </div>
                        </div>
                </div>
                <div class="col-md-12">
                    <div style="text-align: center;">
                        <button type="button" class="btn btn-success" onclick="saveqx();">保存</button>
                    </div>
                </div>
            </div>
            <!-- End PAge Content -->
        </div>
        <!-- End Container fluid  -->
        <!-- footer -->
        <c:import url="common/footer.jsp"></c:import>
        <!-- End footer -->
    </div>
    <!-- End Page wrapper  -->
</div>
<!-- End Wrapper -->
<c:import url="common/importJs2.jsp"></c:import>
<script type="text/javascript" src="./assets/plugins/zTree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="./assets/plugins/zTree/jquery.ztree.excheck-3.5.min.js"></script>
</body>
<script type='text/javascript'>
    $(".name-list").on("click","li",function(){
        $(".name-list li").eq($(this).index()).attr("style","background:#0178bc").siblings().removeAttr("style");;
    });

    var userid , username;
    var pp=[];
    // 人员列表
    function Getlist() {
        // 人员列表
        $.ajax({
            type: 'GET',
            url: './lpUserList',
            success:function(data) {
                if(data != null && data != undefined ){
                    $.each(data,function(index, item){
                        var lis =document.createElement('li');
                        lis.innerHTML = item.realName +"&nbsp;"+ item.userName;
                        lis.id = item.id;  // 人员id
                        lis.dataname = item.realName;  // 人员名
                        lis.username = item.userName;
                        lis.moduleName=item.moduleName;
                        $('.name-list').append(lis); // 添加在ul里面

                    })
                    //点击li 获取当前内容
                    $('.name-list li').click( function() {
                        $(this).addClass('actives')      //为选中项添加高亮
                            .siblings().removeClass('actives')//去除其他项的高亮形式
                            .end();
                        // 获取当前点击的内容
                        console.log($(this).context.id);
                        console.log($(this).context.username);
                        var idx = $(this).context.id;  //点击当前人员id
                        var names = $(this).context.username;  // 点击当前人员name名
                        var moduleName=$(this).context.moduleName;
                        userid=idx;
                        username=names;
                        // 请求权限列表
                        $.ajax({
                            type: 'POST',
                            url: './getRegionIdListByUser',
                            data: {
                                'userName': names,
                                'moduleName': moduleName
                            },
                            success: function(datas) {
                                pp= datas.projectSet;
                                /*console.log(datas);
                                 console.log(pp);*/
                                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                                treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                                for(var s=0;s<pp.length;s++){
                                    var node = treeObj.getNodeByParam("id", pp[s], null);
                                    if(node!=null){
                                        treeObj.checkNode(node, true, true);
                                    }
                                }
                            },
                            error: function(err){
                                //请求出错处理
                                alert(err);
                            }
                        })
                    })

                }
            },
            error: function(err) {
                alert(err);
            }
        })

    }
    var zNodes;
    var treeObj;
    var setting;
    // 权限列表
    function Jurisdiction() {
        $.ajax({
            type: 'GET',
            url: './getRegionList',
            success:function(data) {
                var id1  = data['region_id'];
                var name1 = data['region_name'];
                var level1=data['level'];
                var areaList  = data['areaList'];
                var area = [];
                for(var j=0;j<areaList.length;j++){
                    var id2=areaList[j]['region_id'];
                    var name2 = areaList[j]['region_name'];
                    var pid2 =areaList[j]['pid'];
                    var level2=areaList[j]['level'];
                    var cityList  =areaList[j]['cityList'];
                    var city=[];
                    if (typeof(cityList) != "undefined")
                    {
                        for(var l=0;l<cityList.length;l++){
                            var id3=cityList[l]['region_id'];
                            var name3 =cityList[l]['region_name'];
                            var pid3 =cityList[l]['pid'];
                            var level3=cityList[l]['level'];
                            var projectList  =cityList[l]['projectList'];
                            var project=[];
                            if (typeof(projectList) != "undefined")
                            {
                                for(var k=0;k<projectList.length;k++){
                                    var id4=projectList[k]['region_id'];
                                    var name4 =projectList[k]['region_name'];
                                    var pid4 =projectList[k]['pid'];
                                    var level4=projectList[k]['level'];
                                    project[k]={name:name4,id:id4,pid:pid4,level: level4};
                                }
                            }
                            city[l]={name:name3,id:id3,pid:pid3,level: level3, children:project}
                        }
                    }
                    area[j]={name:name2,id:id2,pid:pid2,level: level2,open:true,children:city }
                }
                zNodes={name:name1,id:id1,level: level1,open:true,children:area};
                //js方法
                function Check(event, treeId, treeNode){
                    if(treeNode.isParent){
                        var checked4=true;
                        if(treeNode.getParentNode() != null){
                            var peerNodes2=  treeNode.getParentNode().children;
                            for(var b=0;b<peerNodes2.length;b++){
                                var checked3 = peerNodes2[b].checked;
                                if(checked3){
                                }else{
                                    checked4=false;
                                }
                            }
                            treeNode.getParentNode().checked =checked4;
                            treeObj.updateNode(treeNode.getParentNode());
                        }
                    }else{
                        if(treeNode.getParentNode() != null){
                            var peerNodes1=  treeNode.getParentNode().children;
                            var checked1=true;
                            for(var a=0;a<peerNodes1.length;a++){
                                var checked2 = peerNodes1[a].checked;
                                if(checked2){
                                }else{
                                    checked1=false;
                                }
                            }
                            treeNode.getParentNode().checked =checked1;
                            treeObj.updateNode(treeNode.getParentNode());
                            var checked5=true;
                            if(treeNode.getParentNode().getParentNode() != null){
                                var peerNodes3=  treeNode.getParentNode().getParentNode().children;
                                for(var c=0;c<peerNodes3.length;c++){
                                    var checked6 = peerNodes3[c].checked;
                                    if(checked6){
                                    }else{
                                        checked5=false;
                                    }
                                }
                                treeNode.getParentNode().getParentNode().checked =checked5;
                                treeObj.updateNode(treeNode.getParentNode().getParentNode());
                            }
                        }
                    }
                }

                // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
                setting = {
                    check:{
                        chkboxType: { "Y": "s", "N": "s" },
                        enable: true,
                        autoCheckTrigger:true,
                        chkStyle:"checkbox"
                    },
                    callback:{
                        onCheck:Check
                    }
                };
                //页面加载成功后,开始加载树形结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）

            }
        })
    }

    /*
     data:    {
     simpleData:{
     enable:true
     }
     },
     var zNodes =[
     { id:1, pId:0, name:"随意勾选 1", open:false},
     { id:11, pId:1, name:"随意勾选 1-1", open:true},
     { id:111, pId:11, name:"随意勾选 1-1-1"},
     { id:112, pId:11, name:"随意勾选 1-1-2"},
     { id:12, pId:1, name:"随意勾选 1-2", open:true},
     { id:121, pId:12, name:"随意勾选 1-2-1"},
     { id:122, pId:12, name:"随意勾选 1-2-2"}
     ];*/
    $(document).ready(function(){
        Getlist();
       // console.log(${projectSet});
        Jurisdiction();
    });
    function  saveqx() {
        var nodes=treeObj.getCheckedNodes(true);
        var a=[];
        var b=[],b1=0;
        var c=[],c1=0;
        var d=[],d1=0;
        for(var i=0;i<nodes.length;i++){
            if(nodes[i].level=="0"){
                a[0]={regionId:nodes[i].id,regionName:nodes[i].name,regionLevel:(nodes[i].level+1)};
            }
            if(nodes[i].level=="1"){
                b[b1]={regionId:nodes[i].id,regionName:nodes[i].name,regionLevel:(nodes[i].level+1),parentId:nodes[i].pid};
                b1+=1;
            }
            if(nodes[i].level=="2"){
                c[c1]={regionId:nodes[i].id,regionName:nodes[i].name,regionLevel:(nodes[i].level+1),parentId:nodes[i].pid};
                c1+=1;
            }
            if(nodes[i].level=="3"){
                d[d1]={regionId:nodes[i].id,regionName:nodes[i].name,regionLevel:(nodes[i].level+1),parentId:nodes[i].pid};
                d1+=1;
            }
        }
        var m=[], w=0;
        var ma;
        if(a.length>0){
            ma=JSON.stringify(a);
        }else {
            if(b.length>1){
                //遍历所有城市
                for(var j=cl-1;j>=0;j--){
                    var dl=d.length;
                    //遍历所有社区
                    for(var k=dl-1;k>=0;k--){
                        if ( d[k].parentId==c[j].regionId) {
                            d.splice(k,1);
                        }
                    }
                }
                for(var i=0;i<b.length;i++){//遍历所有区域
                    var cl= c.length;
                    for(var j=cl-1;j>=0;j--){//遍历所有城市
                        if(c[j].parentId==b[i].regionId){
                            c.splice(j,1);
                        }
                    }
                    m[w]=b[i];
                    w+=1;
                }
                for(var j=0;j<c.length;j++){//遍历所有城市
                    m[w]=c[j];
                    w+=1;
                }
                for(var k=0;k<d.length;k++){//遍历所有社区
                    m[w]=d[k];
                    w+=1;
                }

            }else{
                if(c.length>1){
                    for(var j=0;j<c.length;j++){
                        var dl=d.length;
                        for(var k=dl-1;k>=0;k--){
                            if ( d[k].parentId==c[j].regionId) {
                                d.splice(k,1);
                            }
                        }
                        m[w]=c[j];
                        w+=1;
                    }
                    for(var k=0;k<d.length;k++){
                        m[w]=d[k];
                        w+=1;
                    }
                }else{
                    if(d.length>1){
                        for(var k=0;k<d.length;k++){
                            m[w]=d[k];
                            w+=1;
                        }
                    }
                }
            }
            ma=JSON.stringify(m);
        }
        $.ajax({
            type: 'post',
            url: './lpsave',
            data:'id='+userid+'&v='+ma,
            dataType:'json',
            success:function(data) {
                swal("提示",data.errorMsg, "success");
            }
        });
    }
</script>
</html>